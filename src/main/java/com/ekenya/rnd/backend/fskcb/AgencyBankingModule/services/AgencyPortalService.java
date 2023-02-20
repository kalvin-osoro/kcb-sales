package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AssetLogsRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AssignMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.DSRMerchantRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.helper.AgentExcelHelper;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgentExcelImportResult;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyRescheduleVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor

@Service
public class AgencyPortalService implements IAgencyPortalService {
    private final AgencyBankingVisitRepository agencyBankingVisitRepository;
    private final AssetLogsRepository assetLogsRepository;
    private final AgencyBankingVisitFileRepository  agencyBankingVisitFileRepository;

    private final AgencyBankingQuestionerResponseRepository agencyBankingQuestionerResponseRepository;
    private final IDSRAccountsRepository dsrAccountRepository;
    private final AgencyOnboardingKYCRepository agencyOnboardingKYCRepository;

    private final IQssService iQssService;
    @Autowired
    ObjectMapper mapper;
    private final FileStorageService fileStorageService;
    private final AgencyAssetFilesRepository agencyAssetFilesRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;
    private final AgencyAssetRepository agencyAssetRepository;
    private final IDSRTeamsRepository teamsRepository;
    private final AgencyBankingLeadRepository agencyBankingLeadRepository;
    private final AgencyBankingQuestionnaireQuestionRepository agencyBankingQuestionnaireQuestionRepository;
    private final AgencyBankingTargetRepository agencyBankingTargetRepository;
    private final AgencyOnboardingRepository agencyOnboardingRepository;


    @Override
    public boolean scheduleCustomerVisit(AgencyCustomerVisitsRequest agencyCustomerVisitsRequest) {
        try {
            if (agencyCustomerVisitsRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AgencyBankingVisitEntity agencyBankingVisitEntity = new AgencyBankingVisitEntity();
            agencyBankingVisitEntity.setAgentName(agencyCustomerVisitsRequest.getAgentName());
            agencyBankingVisitEntity.setVisitDate(agencyCustomerVisitsRequest.getVisitDate());
            agencyBankingVisitEntity.setReasonForVisit(agencyCustomerVisitsRequest.getReasonForVisit());
            agencyBankingVisitEntity.setStatus(Status.ACTIVE);
            agencyBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingVisitEntity.setDsrName(agencyCustomerVisitsRequest.getDsrName());
            agencyBankingVisitEntity.setDsrId(agencyBankingVisitEntity.getDsrId());
            DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(agencyCustomerVisitsRequest.getDsrId()).get();
            //save
            agencyBankingVisitRepository.save(agencyBankingVisitEntity);
            iQssService.sendAlert(
                    dsrAccountEntity.getStaffNo(),
                    "New Visit",
                    "You have been assigned a new visit. Please check your App for more details",
                    null
            );
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean reScheduleCustomerVisit(AgencyRescheduleVisitsRequest agencyCustomerVisitsRequest) {
        try {
            if (agencyCustomerVisitsRequest == null) {
                return false;
            }
            AgencyBankingVisitEntity agencyBankingVisitEntity = agencyBankingVisitRepository.findById(agencyCustomerVisitsRequest.getVisitId()).orElse(null);
            if (agencyBankingVisitEntity == null) {
                return false;
            }
            agencyBankingVisitEntity.setVisitDate(agencyCustomerVisitsRequest.getNewDate());
            agencyBankingVisitRepository.save(agencyBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rescheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponse(AgencyCustomerVisitQuestionnaireRequest agencyCustomerVisitQuestionnaireRequest) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingQuestionerResponseEntity agencyBankingQuestionerResponseEntity : agencyBankingQuestionerResponseRepository.findAllByVisitIdAndQuestionId(agencyCustomerVisitQuestionnaireRequest.getVisitId(), agencyCustomerVisitQuestionnaireRequest.getQuestionId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingQuestionerResponseEntity.getId());
                node.put("visitId", agencyBankingQuestionerResponseEntity.getVisitId());
                node.put("questionId", agencyBankingQuestionerResponseEntity.getQuestionId());
                node.put("response", agencyBankingQuestionerResponseEntity.getResponse());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingLeadEntity agencyBankingLeadEntity : agencyBankingLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", agencyBankingLeadEntity.getId());
                objectNode.put("customerId", agencyBankingLeadEntity.getCustomerId());
                objectNode.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", agencyBankingLeadEntity.getLeadStatus().toString());
                objectNode.put("createdOn", agencyBankingLeadEntity.getCreatedOn().getTime());
                objectNode.put("product", agencyBankingLeadEntity.getProduct());
                objectNode.put("dsrName", agencyBankingLeadEntity.getDsrName());
                objectNode.put("priority", agencyBankingLeadEntity.getPriority().toString());
//                objectNode.put("dsrId", treasuryLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }


    @Override
    public boolean createQuestionnaire(AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest agencyBankingQuestionnareQuestionRequest) {
        try {
            if (agencyBankingQuestionnareQuestionRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AgencyBankingQuestionnaireQuestionEntity agencyBankingQuestionnaireQuestionEntity = new AgencyBankingQuestionnaireQuestionEntity();
            agencyBankingQuestionnaireQuestionEntity.setQuestion(agencyBankingQuestionnareQuestionRequest.getQuestion());
            agencyBankingQuestionnaireQuestionEntity.setQuestionnaireDescription(agencyBankingQuestionnareQuestionRequest.getQuestionnaireDescription());
            agencyBankingQuestionnaireQuestionEntity.setQuestionnaireDescription(agencyBankingQuestionnareQuestionRequest.getQuestionnaireDescription());
            agencyBankingQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            agencyBankingQuestionnaireQuestionRepository.save(agencyBankingQuestionnaireQuestionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllQuestionnaires() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingQuestionnaireQuestionEntity agencyBankingQuestionnaireQuestionEntity : agencyBankingQuestionnaireQuestionRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingQuestionnaireQuestionEntity.getId());
                node.put("question", agencyBankingQuestionnaireQuestionEntity.getQuestion());
                node.put("questionnaireDescription", agencyBankingQuestionnaireQuestionEntity.getQuestionnaireDescription());
                node.put("createdOn", agencyBankingQuestionnaireQuestionEntity.getCreatedOn().toString());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean createAgencyTarget(AgencyAddTargetRequest agencyAddTargetRequest) {
        try {
            if (agencyAddTargetRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            AgencyBankingTargetEntity agencyBankingTargetEntity = new AgencyBankingTargetEntity();
            agencyBankingTargetEntity.setTargetName(agencyAddTargetRequest.getTargetName());
            agencyBankingTargetEntity.setTargetSource(agencyAddTargetRequest.getTargetSource());
            agencyBankingTargetEntity.setTargetType(agencyAddTargetRequest.getAgencyTargetType());
            agencyBankingTargetEntity.setTargetDesc(agencyAddTargetRequest.getTargetDesc());
            agencyBankingTargetEntity.setTargetStatus(TargetStatus.ACTIVE);
            agencyBankingTargetEntity.setTargetValue(agencyAddTargetRequest.getTargetValue());
            agencyBankingTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            agencyBankingTargetRepository.save(agencyBankingTargetEntity);
            return true;

            //save
        } catch (Exception e) {
            log.error("Error while adding new target", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAgencyTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyBankingTargetEntity.getId());
                node.put("targetName", agencyBankingTargetEntity.getTargetName());
                node.put("targetSource", agencyBankingTargetEntity.getTargetSource());
                node.put("agencyTargetType", agencyBankingTargetEntity.getTargetType().toString());
                node.put("targetDesc", agencyBankingTargetEntity.getTargetDesc());
                node.put("targetRemained", agencyBankingTargetEntity.getTargetAssigned());
                node.put("targetStatus", agencyBankingTargetEntity.getTargetStatus().toString());
                node.put("targetValue", agencyBankingTargetEntity.getTargetValue());
                node.put("createdOn", agencyBankingTargetEntity.getCreatedOn() == null ?null:agencyBankingTargetEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading targets", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadAllOnboardedAgents() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyOnboardingEntity agencyOnboardingEntity : agencyOnboardingRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", agencyOnboardingEntity.getId());
                node.put("agentName", agencyOnboardingEntity.getAgentName());
                node.put("region", agencyOnboardingEntity.getRegion());
                node.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
                node.put("email", agencyOnboardingEntity.getAgentEmail());
                node.put("status", agencyOnboardingEntity.getStatus().toString());
                node.put("dsrName", agencyOnboardingEntity.getDsrName());
                node.put("createdOn", agencyOnboardingEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading agents", e);
        }


        return null;
    }

    @Override
    public boolean approveAgentOnboarding(AcquiringApproveMerchant model) {
        try {
            if (model == null) {
                return false;
            }
            AgencyOnboardingEntity agencyOnboardingEntity1 = agencyOnboardingRepository.findById(model.getMerchantId()).orElse(null);
            if (agencyOnboardingEntity1 == null) {
                return false;
            }
            agencyOnboardingEntity1.setStatus(OnboardingStatus.APPROVED);
            agencyOnboardingEntity1.setIsApproved(true);
            agencyOnboardingRepository.save(agencyOnboardingEntity1);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving onboarding", e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> getAgentOnboardSummary(AgencySummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (AgencyOnboardingEntity agencyOnboardingEntity : agencyOnboardingRepository.fetchAllOnboardingCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("agentName", agencyOnboardingEntity.getAgentName());
                    asset.put("onboarding-status", agencyOnboardingEntity.getStatus().ordinal());
                    asset.put("agent Id", agencyOnboardingEntity.getDsrId());
                    asset.put("date_onboarded", agencyOnboardingEntity.getCreatedOn().getTime());
                    asset.put("latitude", agencyOnboardingEntity.getLatitude());
                    asset.put("longitude", agencyOnboardingEntity.getLongitude());
                    list.add(asset);
                }
                return list;
            }


        } catch (Exception e) {
            log.error("Error occurred while fetching onboarding summary", e);
        }
        return null;
    }

    @Override
    public boolean assignTargetToDSR(DSRTAssignTargetRequest model) {
        try {

            if (model == null) {
                return false;
            }
            DSRAccountEntity user = dsrAccountRepository.findById(model.getDsrId()).orElse(null);

            AgencyBankingTargetEntity target = agencyBankingTargetRepository.findById(model.getTargetId()).orElse(null);

            //conversion happen here
            Long userTargetVale = Long.parseLong(model.getTargetValue());
            //
            Long targetTargetVale = Long.parseLong(String.valueOf(target.getTargetValue()));
            //
            if (userTargetVale > targetTargetVale) {
                return false;
            }

            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                user.setCampaignTargetValue(model.getTargetValue());
                user.setAgencyTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                user.setLeadsTargetValue(model.getTargetValue());
                user.setAgencyTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                user.setVisitsTargetValue(model.getTargetValue());
                user.setAgencyTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                user.setOnboardTargetValue(model.getTargetValue());
                user.setAgencyTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.VOLUMES)) {
                user.setVolumeTargetValue(model.getTargetValue());
                user.setAgencyTargetId(model.getTargetId());
            }

            Set<AgencyBankingTargetEntity> agencyBankingTargetEntities = (Set<AgencyBankingTargetEntity>) user.getAgencyBankingTargetEntities();
            agencyBankingTargetEntities.add(target);
            user.setAgencyBankingTargetEntities(agencyBankingTargetEntities);
            dsrAccountRepository.save(user);
            target.setTargetAssigned(target.getTargetAssigned() + userTargetVale);
            agencyBankingTargetRepository.save(target);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning target to dsr", e);
        }
        return false;
    }

    @Override
    public boolean assignTargetToTeam(TeamTAssignTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRTeamEntity teamEntity = teamsRepository.findById(model.getTeamId()).orElse(null);

            AgencyBankingTargetEntity target = agencyBankingTargetRepository.findById(model.getTargetId()).orElse(null);

            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                teamEntity.setCampaignTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                teamEntity.setLeadsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                teamEntity.setVisitsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                teamEntity.setOnboardTargetValue(model.getTargetValue());
            }

            Set<AgencyBankingTargetEntity> agencyBankingTargetEntities = (Set<AgencyBankingTargetEntity>) teamEntity.getAgencyBankingTargetEntities();
            agencyBankingTargetEntities.add(target);
            teamEntity.setAgencyBankingTargetEntities(agencyBankingTargetEntities);
            teamsRepository.save(teamEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while assigning target to team", e);
        }
        return false;
    }

    @Override
    public Object getTargetById(VoomaTargetByIdRequest model) {
        try {
            if (model == null) {
                return false;
            }
            AgencyBankingTargetEntity agencyBankingTargetEntity = agencyBankingTargetRepository.findById(model.getId()).orElse(null);
            return agencyBankingTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }

    @Override
    public boolean rejectAgentOnboarding(AcquiringApproveMerchant model) {
        try {
            if (model == null) {
                return false;
            }
            AgencyOnboardingEntity agencyOnboardingEntity1 = agencyOnboardingRepository.findById(model.getMerchantId()).orElse(null);
            if (agencyOnboardingEntity1 == null) {
                return false;
            }
            agencyOnboardingEntity1.setStatus(OnboardingStatus.REJECTED);
            agencyOnboardingEntity1.setIsApproved(false);
            agencyOnboardingRepository.save(agencyOnboardingEntity1);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rejected onboarding", e);
        }
        return false;
    }

    @Override
    public List<?> getAllVisitsV2() {
        try {
            List<AgencyBankingVisitEntity> dfsVoomaOnboardEntityList = agencyBankingVisitRepository.findAll();
            List<AgencyBankingVisitEntity> dfsVoomaOnboardEntityList1 = new ArrayList<>();
            for (AgencyBankingVisitEntity dfsVoomaOnboardEntity : dfsVoomaOnboardEntityList) {
                dfsVoomaOnboardEntity.getCreatedOn();
                dfsVoomaOnboardEntity.getId();
                dfsVoomaOnboardEntityList1.add(dfsVoomaOnboardEntity);
//                return dfsVoomaOnboardEntityList1;
            }
            return dfsVoomaOnboardEntityList;
        } catch (Exception e) {
            log.error("Error occurred while getting all onboarding", e);
        }
        return null;

    }

    @Override
    public boolean assignLead(TreasuryAssignLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRAccountEntity dsrAccountsEntity = dsrAccountsRepository.findById(model.getDsrId()).orElse(null);
            if (dsrAccountsEntity == null) {
                return false;
            }
            AgencyBankingLeadEntity agencyBankingLeadEntity = agencyBankingLeadRepository.findById(model.getLeadId()).orElse(null);
            if (agencyBankingLeadEntity == null) {
                return false;
            }
            agencyBankingLeadEntity.setAssigned(true);
            agencyBankingLeadEntity.setPriority(model.getPriority());
            //set dsrAccId from dsrAccountsEntity
            agencyBankingLeadEntity.setDsrAccountEntity(dsrAccountsEntity);
            DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).get();
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
//            if(agencyBankingLeadEntity.isAssigned()==true){
//
//            }

            iQssService.sendAlert(
                    dsrAccountEntity.getStaffNo(),
                    "New Lead Assigned",
                    "You have been assigned a new lead. Please check your App for more details",
                    null
            );
            return true;


        } catch (Exception e) {
            log.error("Error occurred while assigning lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAllApprovedMerchants() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyOnboardingEntity agencyOnboardingEntity : agencyOnboardingRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", agencyOnboardingEntity.getId());
                objectNode.put("customerName", agencyOnboardingEntity.getAgentName());
                objectNode.put("region", agencyOnboardingEntity.getRegion());
                objectNode.put("status", agencyOnboardingEntity.getStatus().toString());
                objectNode.put("dateOnborded", agencyOnboardingEntity.getCreatedOn().getTime());
                objectNode.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
                objectNode.put("email", agencyOnboardingEntity.getAgentEmail());
                objectNode.put("dsrName", agencyOnboardingEntity.getDsrName());

//                objectNode.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(agencyOnboardingEntity.getLongitude());
                arrayNode.add(agencyOnboardingEntity.getLatitude());
                objectNode.put("co-ordinates", arrayNode);
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting onboarding summary", e);
        }
        return null;
    }

    @Override
    public boolean addAsset(String assetDetails, MultipartFile[] assetFiles) {
        try {
            if (assetDetails == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            DFSVoomaAddAssetRequest dfsVoomaAddAssetRequest = mapper.readValue(assetDetails, DFSVoomaAddAssetRequest.class);
            AgencyAssetEntity dfsVoomaAssetEntity = new AgencyAssetEntity();
            dfsVoomaAssetEntity.setSerialNumber(dfsVoomaAddAssetRequest.getSerialNumber());
//            dfsVoomaAssetEntity.setAssetCondition(dfsVoomaAddAssetRequest.getAssetCondition());
            dfsVoomaAssetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaAssetEntity.setAssetNumber(dfsVoomaAddAssetRequest.getAssetNumber());
            dfsVoomaAssetEntity.setAssetType(dfsVoomaAddAssetRequest.getAssetType());
//            dfsVoomaAddAssetRequest.setDeviceId(dfsVoomaAddAssetRequest.getDeviceId());
            AgencyAssetEntity savedAsset = agencyAssetRepository.save(dfsVoomaAssetEntity);

            //logs
            AssetLogsEntity assetLogsEntity = new AssetLogsEntity();
            assetLogsEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            assetLogsEntity.setAssetType(dfsVoomaAddAssetRequest.getAssetType());
            assetLogsEntity.setAssetNumber(dfsVoomaAddAssetRequest.getAssetNumber());
            assetLogsEntity.setAction("Asset Added to the system");
            assetLogsEntity.setProfileCode(dfsVoomaAddAssetRequest.getProfileCode());
            assetLogsEntity.setRemarks(dfsVoomaAddAssetRequest.getRemarks());
            assetLogsEntity.setSerialNumber(dfsVoomaAddAssetRequest.getSerialNumber());
            assetLogsRepository.save(assetLogsEntity);

            List<String> filePathList = new ArrayList<>();

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileNameV("AgencyAsset_" , assetFiles,Utility.getSubFolder());
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/"+Utility.getSubFolder()+"/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                AgencyAssetFilesEntity dfsVoomaAssetFilesEntity = new AgencyAssetFilesEntity();
                dfsVoomaAssetFilesEntity.setAgencyAssetEntity(dfsVoomaAssetEntity);
                dfsVoomaAssetFilesEntity.setFilePath(downloadUrl);
                dfsVoomaAssetFilesEntity.setFileName(filePath);
                dfsVoomaAssetFilesEntity.setIdAsset(savedAsset.getId());
                agencyAssetFilesRepository.save(dfsVoomaAssetFilesEntity);
            }
            return true;


        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<ObjectNode> getAllAssets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyAssetEntity dfsVoomaOnboardEntity : agencyAssetRepository.findAll()) {
                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", dfsVoomaOnboardEntity.getId());
                asset.put("condition", dfsVoomaOnboardEntity.getAssetCondition().toString());
                asset.put("serialNo", dfsVoomaOnboardEntity.getSerialNumber());
                asset.put("createdOn", dfsVoomaOnboardEntity.getSerialNumber());
                asset.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
                asset.put("dateAssigned", dfsVoomaOnboardEntity.getDateAssigned() == null ? null :dfsVoomaOnboardEntity.getDateAssigned().getTime());
                asset.put("visitDate", dfsVoomaOnboardEntity.getVisitDate());
                asset.put("location", dfsVoomaOnboardEntity.getLocation());
                asset.put("assetNumber", dfsVoomaOnboardEntity.getAssetNumber());
                asset.put("agentAssigned", dfsVoomaOnboardEntity.getAgentAccNumber());
                asset.put("agentName", dfsVoomaOnboardEntity.getAgentName());
                asset.put("assetType", dfsVoomaOnboardEntity.getAssetType().toString());
                asset.put("status", dfsVoomaOnboardEntity.getStatus().toString());
                list.add(asset);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting onboarding summary", e);
        }
        return null;
    }

    @Override
    public Object getAssetById(AssetByIdRequest model) {
        try {
            if (model.getAssetId() == null) {
                log.error("Asset id is null");
                return null;
            }
            //get merchant by id
            AgencyAssetEntity acquiringOnboardEntity = agencyAssetRepository.findById(model.getAssetId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("condition", acquiringOnboardEntity.getAssetCondition().toString());
            asset.put("serialNo", acquiringOnboardEntity.getSerialNumber());
            asset.put("createdOn", acquiringOnboardEntity.getSerialNumber());
            asset.put("dsrId", acquiringOnboardEntity.getDsrId());
            asset.put("visitDate", acquiringOnboardEntity.getVisitDate());
            asset.put("location", acquiringOnboardEntity.getLocation());
            asset.put("agentName", acquiringOnboardEntity.getAgentName());
            asset.put("status", acquiringOnboardEntity.getStatus().toString());
            List<AgencyAssetFilesEntity> dfsVoomaFileUploadEntities = agencyAssetFilesRepository.findByIdAsset(model.getAssetId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (AgencyAssetFilesEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                String[] fileName = dfsVoomaFileUploadEntity.getFileName().split("/");
                fileUpload.put("fileName", fileName[fileName.length - 1]);
                fileUploads.add(fileUpload);
            }
            asset.put("fileUploads", fileUploads);
            return asset;
        } catch (Exception e) {
            log.error("Error occurred while fetching merchant by id", e);
        }
        return null;
    }

    @Override
    public ObjectNode attemptImportAgents(MultipartFile file) {
        try {
            AgentExcelImportResult results = AgentExcelHelper.excelToAgents(file.getInputStream());
            int imported = 0;
            for (AgencyOnboardingEntity agent : results.getAgents()) {
                agent.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                agent.setStatus(OnboardingStatus.APPROVED);
                agent.setIsApproved(true);
                agencyOnboardingRepository.save(agent);
                imported++;


            }
            ObjectNode node = mapper.createObjectNode();
            node.put("imported", imported);
            //
            if (!results.getErrors().isEmpty()) {
                //
                node.putPOJO("import-errors", mapper.convertValue(results.getErrors(), ArrayNode.class));
                //
            } else {
                //
                node.putPOJO("import-errors", mapper.createArrayNode());
                //
            }
            return node;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public Object getAgentById(AgencyById model) {
        try {
            if (model==null){
                return null;
            }
            AgencyOnboardingEntity agencyOnboardingEntity = agencyOnboardingRepository.findById(model.getAgentId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", agencyOnboardingEntity.getId());
            objectNode.put("businessName", agencyOnboardingEntity.getBusinessName());
//            objectNode.put("region", dfsVoomaOnboardEntity.getCityOrTown());
            objectNode.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
            objectNode.put("businessEmail", agencyOnboardingEntity.getAgentEmail());
            objectNode.put("status", agencyOnboardingEntity.getStatus().toString());
            objectNode.put("remarks", agencyOnboardingEntity.getRemarks());
            objectNode.put("branchName", agencyOnboardingEntity.getBranch());
            objectNode.put("accountName", agencyOnboardingEntity.getAgentName());
            objectNode.put("dsrId", agencyOnboardingEntity.getDsrId());
            objectNode.put("createdOn", agencyOnboardingEntity.getCreatedOn().getTime());
            ObjectNode cordinates = mapper.createObjectNode();
            cordinates.put("latitude", agencyOnboardingEntity.getLatitude());
            cordinates.put("longitude", agencyOnboardingEntity.getLongitude());
            objectNode.set("cordinates", cordinates);
            ObjectNode businessDetails = mapper.createObjectNode();
            businessDetails.put("businessName", agencyOnboardingEntity.getBusinessName());
            businessDetails.put("nearbyLandMark", agencyOnboardingEntity.getStreetName());
            businessDetails.put("pobox", agencyOnboardingEntity.getAgentPbox());
            businessDetails.put("postalCode", agencyOnboardingEntity.getAgentPostalCode());
            businessDetails.put("natureOfBusiness", agencyOnboardingEntity.getBusinessType());
            businessDetails.put("city", agencyOnboardingEntity.getTown());
            objectNode.set("businessDetails", businessDetails);

            List<AgencyOnboardingKYCEntity> dfsVoomaFileUploadEntities = agencyOnboardingKYCRepository.findByAgentId(model.getAgentId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (AgencyOnboardingKYCEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                fileUpload.put("fileName", dfsVoomaFileUploadEntity.getFilename());
                fileUploads.add(fileUpload);
            }
            objectNode.put("fileUploads", fileUploads);
            return objectNode;
        } catch (Exception e) {
            log.error("An error have occured,please try again later");
        }
        return null;
    }

    @Override
    public Object getCustomerVisitById(AgencyVisitRequest model) {
        try {
            if (model.getVisitId() == null) {
                log.error("Asset id is null");
                return null;
            }
            AgencyBankingVisitEntity agencyBankingVisitEntity = agencyBankingVisitRepository.findById(model.getVisitId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", agencyBankingVisitEntity.getId());
            asset.put("agentName", agencyBankingVisitEntity.getAgentName());
            asset.put("visitDate", agencyBankingVisitEntity.getVisitDate());
            asset.put("dsrId", agencyBankingVisitEntity.getDsrId());
            asset.put("chargesUpfront", agencyBankingVisitEntity.getChargesUpfront());
            asset.put("maintainsOpenedAcctRecords", agencyBankingVisitEntity.getMaintainsOpenedAcctRecords());
            asset.put("usesManualReceiptBook", agencyBankingVisitEntity.getUsesManualReceiptBook());
            asset.put("reconcileFloatAcctStat", agencyBankingVisitEntity.getReconcileFloatAcctStat());
            asset.put("moreThanXTransactions", agencyBankingVisitEntity.getMoreThanXTransactions());
            asset.put("branchCollectsRegisters", agencyBankingVisitEntity.getBranchCollectsRegisters());
            asset.put("tariffPosterWellDisplayed", agencyBankingVisitEntity.getTariffPosterWellDisplayed());
            asset.put("customersSignRegister", agencyBankingVisitEntity.getCustomersSignRegister());
            asset.put("registerReflected", agencyBankingVisitEntity.getRegisterReflected());
            asset.put("outletWellBranded", agencyBankingVisitEntity.getOutletWellBranded());
            asset.put("registerCompleted", agencyBankingVisitEntity.getRegisterCompleted());
            asset.put("visitedByStaff", agencyBankingVisitEntity.getVisitedByStaff());
            asset.put("locatedStrategically", agencyBankingVisitEntity.getLocatedStrategically());
            asset.put("csLevel", agencyBankingVisitEntity.getCsLevel());
            asset.put("outletPresentable", agencyBankingVisitEntity.getOutletPresentable());
            asset.put("hasFloat", agencyBankingVisitEntity.getHasFloat());
            asset.put("customerInflow", agencyBankingVisitEntity.getCustomerInflow());
            asset.put("agentTrxInForeignCur", agencyBankingVisitEntity.getAgentTrxInForeignCur());
            asset.put("comments", agencyBankingVisitEntity.getComments());
            asset.put("hasMaterials", agencyBankingVisitEntity.getHasMaterials());
            asset.put("branch", agencyBankingVisitEntity.getBranch());
            asset.put("longitude", agencyBankingVisitEntity.getLongitude());
            asset.put("latitude", agencyBankingVisitEntity.getLatitude());
            asset.put("pdqVersionCorrect", agencyBankingVisitEntity.getPdqVersionCorrect());
            asset.put("cashDeposit", agencyBankingVisitEntity.getCashDeposit());
            asset.put("isAgentActive", agencyBankingVisitEntity.getIsAgentActive());
            asset.put("shylocking", agencyBankingVisitEntity.getShylockingActivities());
            asset.put("chargeCustomerUpfront", agencyBankingVisitEntity.getChargeCustomerUpfront());
            asset.put("countyLicence", agencyBankingVisitEntity.getCountyLicence());
            asset.put("transactionOnRegisterReflectOnT24", agencyBankingVisitEntity.getTransactionOnRegisterReflectOnT24());
            asset.put("coreBusinessViable", agencyBankingVisitEntity.getCoreBusinessViable());
            List<AgencyBankingVisitFileEntity> dfsVoomaFileUploadEntities = agencyBankingVisitFileRepository.findByIdVisit(model.getVisitId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (AgencyBankingVisitFileEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                fileUpload.put("fileName",dfsVoomaFileUploadEntity.getFileName());

                fileUploads.add(fileUpload);
            }
            asset.put("fileUploads", fileUploads);
            return asset;
        } catch (Exception e) {
            log.error("Error occurred while fetching merchant by id", e);
        }
        return null;
    }

    @Override
    public boolean createVisitQuestion(AgencyCollectAssetRequest.AgencyBankingQuestionnareQuestionRequest model) {
        try {
            if (model == null){
                return  false;
            }
            AgencyBankingQuestionnaireQuestionEntity questionnaireQuestionEntity = new AgencyBankingQuestionnaireQuestionEntity();
            questionnaireQuestionEntity.setQuestion(model.getQuestion());
            questionnaireQuestionEntity.setQuestionType(model.getQuestionType());
            questionnaireQuestionEntity.setBusinessUnit(model.getBusinessUnit());
            questionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingQuestionnaireQuestionRepository.save(questionnaireQuestionEntity);
            return true;

        } catch (Exception e) {
            log.error("something went wrong,please try again later");
        }
        return false;
    }

    @Override
    public boolean assignAgentToDSR(AssignMerchant model) {
        try {
            if (model== null){
                return false;
            }
            AgencyOnboardingEntity agencyOnboardingEntity = agencyOnboardingRepository.findById(model.getCustomerId()).get();
//            agencyOnboardingEntity.setDsrSalesCode(model.getDsrSalesCode());
            DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).get();
            //set start date from input
            agencyOnboardingEntity.setAssigned(true);
            //save
            agencyOnboardingRepository.save(agencyOnboardingEntity);
            iQssService.sendAlert(
                    dsrAccountEntity.getStaffNo(),
                    "New Agent Assigned",
                    "You have been assigned a new Agent. Please check your App for more details",
                    null
            );
            //update is assigned to true
            return true;
        } catch (Exception e) {
            log.error("something went wrong,please try again later");
        }
        return false;
    }

    @Override
    public List<ObjectNode> salesPersonTarget(AcquiringDSRsInTargetRequest model) {
        try {
            if (model == null) {
                return null;
            }
            //list of dsr
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //bring all fields from dsr
            for (DSRAccountEntity dsrAccountEntity : dsrAccountsRepository.findByVoomaTargetId(model.getTargetId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dsrAccountEntity.getId());
                objectNode.put("staffNo", dsrAccountEntity.getStaffNo());
                objectNode.put("fullName", dsrAccountEntity.getFullName());
                objectNode.put("targetValue", dsrAccountEntity.getTargetValue());
                //add to list
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting dsr in target", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getDSRAgent(DSRMerchantRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode>list= new ArrayList<>();
            ObjectMapper mapper1=new ObjectMapper();

            for (AgencyOnboardingEntity agencyOnboardingEntity :agencyOnboardingRepository.findByDsrId(model.getDsrId())){
                ObjectNode objectNode =mapper.createObjectNode();
                objectNode.put("id", agencyOnboardingEntity.getId());
                objectNode.put("businessName", agencyOnboardingEntity.getBusinessName());
//            objectNode.put("region", dfsVoomaOnboardEntity.getCityOrTown());
                objectNode.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
                objectNode.put("businessEmail", agencyOnboardingEntity.getAgentEmail());
                objectNode.put("status", agencyOnboardingEntity.getStatus().toString());
                objectNode.put("remarks", agencyOnboardingEntity.getRemarks());
                objectNode.put("branchName", agencyOnboardingEntity.getBranch());
                objectNode.put("accountName", agencyOnboardingEntity.getAgentName());
                objectNode.put("dsrId", agencyOnboardingEntity.getDsrId());
                objectNode.put("createdOn", agencyOnboardingEntity.getCreatedOn().getTime());
                ObjectNode cordinates = mapper.createObjectNode();
                cordinates.put("latitude", agencyOnboardingEntity.getLatitude());
                cordinates.put("longitude", agencyOnboardingEntity.getLongitude());
                objectNode.set("cordinates", cordinates);
                ObjectNode businessDetails = mapper.createObjectNode();
                businessDetails.put("businessName", agencyOnboardingEntity.getBusinessName());
                businessDetails.put("nearbyLandMark", agencyOnboardingEntity.getStreetName());
                businessDetails.put("pobox", agencyOnboardingEntity.getAgentPbox());
                businessDetails.put("postalCode", agencyOnboardingEntity.getAgentPostalCode());
                businessDetails.put("natureOfBusiness", agencyOnboardingEntity.getBusinessType());
                businessDetails.put("city", agencyOnboardingEntity.getTown());
                objectNode.set("businessDetails", businessDetails);
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.info("something went wrong,please try again later");
        }
        return null;
    }
}


