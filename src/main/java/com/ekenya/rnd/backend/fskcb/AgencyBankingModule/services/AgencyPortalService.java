package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.helper.AgentExcelHelper;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.AgentExcelImportResult;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyRescheduleVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor

@Service
public class AgencyPortalService implements IAgencyPortalService {
    private final AgencyBankingVisitRepository agencyBankingVisitRepository;
    private final AgencyBankingQuestionerResponseRepository agencyBankingQuestionerResponseRepository;
    private final IDSRAccountsRepository dsrAccountRepository;
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
            //save
            agencyBankingVisitRepository.save(agencyBankingVisitEntity);
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
                node.put("agencyTargetType", agencyBankingTargetEntity.getTargetType().ordinal());
                node.put("targetDesc", agencyBankingTargetEntity.getTargetDesc());
                node.put("targetStatus", agencyBankingTargetEntity.getTargetStatus().name());
                node.put("targetValue", agencyBankingTargetEntity.getTargetValue());
                node.put("createdOn", agencyBankingTargetEntity.getCreatedOn().toString());
                //add to list
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
                node.put("merchantName", agencyOnboardingEntity.getAgentName());
                node.put("Region", agencyOnboardingEntity.getRegion());
                node.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
                node.put("email", agencyOnboardingEntity.getAgentEmail());
                node.put("status", agencyOnboardingEntity.getStatus().toString());
                node.put("agent Id", agencyOnboardingEntity.getDsrId());
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
            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                user.setCampaignTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                user.setLeadsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                user.setVisitsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                user.setOnboardTargetValue(model.getTargetValue());
            }

            Set<AgencyBankingTargetEntity> agencyBankingTargetEntities = (Set<AgencyBankingTargetEntity>) user.getAgencyBankingTargetEntities();
            agencyBankingTargetEntities.add(target);
            user.setAgencyBankingTargetEntities(agencyBankingTargetEntities);
            dsrAccountRepository.save(user);
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
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
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
            for (AgencyOnboardingEntity agencyOnboardingEntity : agencyBankingLeadRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", agencyOnboardingEntity.getId());
                objectNode.put("customerName", agencyOnboardingEntity.getBusinessName());
                objectNode.put("region", agencyOnboardingEntity.getRegion());
                objectNode.put("status", agencyOnboardingEntity.getStatus().toString());
                objectNode.put("dateOnborded", agencyOnboardingEntity.getCreatedOn().getTime());
                objectNode.put("phoneNumber", agencyOnboardingEntity.getAgentPhone());
                objectNode.put("email", agencyOnboardingEntity.getAgentEmail());
                objectNode.put("dsrId", agencyOnboardingEntity.getDsrId());

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

            AcquiringAddAssetRequest acquiringAddAssetRequest =
                    mapper.readValue(assetDetails, AcquiringAddAssetRequest.class);
            AgencyAssetEntity acquiringAssetEntity = new AgencyAssetEntity();
            acquiringAssetEntity.setSerialNumber(acquiringAddAssetRequest.getSerialNumber());
            acquiringAssetEntity.setAssetCondition(acquiringAddAssetRequest.getAssetCondition());
            AgencyAssetEntity savedAsset = agencyAssetRepository.save(acquiringAssetEntity);
            String subFolder = "agency-assets";

            List<String> filePathList = new ArrayList<>();
            //save files

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileName("Asset_", assetFiles);
            //save file paths to db
            filePathList.forEach(filePath -> {
                AgencyAssetFilesEntity assetFilesEntity = new AgencyAssetFilesEntity();
                assetFilesEntity.setAgencyAssetEntity(savedAsset);
                assetFilesEntity.setFilePath(filePath);
                agencyAssetFilesRepository.save(assetFilesEntity);
            });
            return true;

        } catch (JsonMappingException e) {
            //return ResponseEntity.badRequest().body("Invalid asset details");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public List<ObjectNode> getAllAssets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyAssetEntity acquiringAssetEntity : agencyAssetRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringAssetEntity.getId());
                asset.put("condition", acquiringAssetEntity.getAssetCondition().toString());
                asset.put("sno", acquiringAssetEntity.getSerialNumber());
                //asset.put("type",acquiringAssetEntity.getAssetType());
                //
                ArrayNode images = mapper.createArrayNode();

                //"http://10.20.2.12:8484/"

                // "/files/acquiring/asset-23-324767234.png;/files/acquiring/asset-23-3247672ewqee8.png"


                asset.put("images", images);


                //
                list.add(asset);
            }


            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all assets", e);
        }
        return null;
    }

    @Override
    public Object getAssetById(AssetByIdRequest model) {
        try {
            if (model.getAssetId() == null) {
                log.error("Merchant id is null");
                return null;
            }
            //get merchant by id
            AgencyAssetEntity acquiringOnboardEntity = agencyAssetRepository.findById(model.getAssetId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("condition", acquiringOnboardEntity.getAssetCondition().toString());
            asset.put("serialNo", acquiringOnboardEntity.getSerialNumber());

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
                agent.setStatus(OnboardingStatus.PENDING);
                agent.setIsApproved(false);
                //save agent
                agencyOnboardingRepository.save(agent);
                imported++;
//            result.getAgents().add(agent);


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
}


