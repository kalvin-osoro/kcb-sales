package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.*;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.DFSVoomaOnboardRepository;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;



@Service
@RequiredArgsConstructor
@Slf4j
public class  AcquiringPortalPortalService implements IAcquiringPortalService {

    private final IAcquiringLeadsRepository mLeadsRepo;
    private final IAcquiringTargetsRepository iAcquiringTargetsRepository;
    private final DFSVoomaOnboardRepository  dfsVoomaOnboardRepository;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private  final IDSRAccountsRepository dsrAccountsRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    private final AcquiringAssetFileRepository acquiringAssetFileRepository;
    private final IDSRTeamsRepository dsrTeamsRepository;
    private final AcquiringCustomerVisitRepository acquiringCustomerVisitRepository;
    private final AcquiringQuestionnaireRepository acquiringQuestionnaireRepository;

    private final IAcquiringDSRsInTargetRepository iAcquiringDSRsInTargetRepository;

    private final AcquiringQuestionerResponseRepository acquiringQuestionerResponseRepository;
    private final IAcquiringLeadsRepository acquiringLeadsRepository;

    private final AcquiringOnboardingKYCRepository acquiringOnboardingKYCRepository;

    private final IAcquiringOnboardingsRepository acquiringOnboardingsRepository;




    @Override
    public boolean assignLeadToDsr(CBAssignLeadRequest model) {
        try {
            AcquiringLeadEntity acquiringLeadEntity = acquiringLeadsRepository.findById(model.getLeadId()).get();
            acquiringLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            acquiringLeadEntity.setStartDate(model.getStartDate());
            acquiringLeadEntity.setEndDate(model.getEndDate());
            acquiringLeadEntity.setAssigned(true);
            //save
            acquiringLeadsRepository.save(acquiringLeadEntity);
            //update is assigned to true

            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringTargetEntity acquiringTargetEntity : iAcquiringTargetsRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", acquiringTargetEntity.getId());
                objectNode.put("targetName", acquiringTargetEntity.getTargetName());
                objectNode.put("targetSource", acquiringTargetEntity.getTargetSource());
                objectNode.put("agencyTargetType", acquiringTargetEntity.getTargetType().toString());
                objectNode.put("targetDesc", acquiringTargetEntity.getTargetDesc());
                objectNode.put("targetStatus", acquiringTargetEntity.getTargetStatus().name());
                objectNode.put("targetValue", acquiringTargetEntity.getTargetValue());
//                objectNode.put("targetAchieved",dfsVoomaTargetEntity.getTargetAchievement());
                objectNode.put("createdOn", acquiringTargetEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all targets", e);
        }
        return null;
    }

    @Override
    public boolean addNewTarget(AcquiringAddTargetRequest acquiringAddTargetRequest) {
        try {
            if (acquiringAddTargetRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            AcquiringTargetEntity acquiringTargetEntity = new AcquiringTargetEntity();
            acquiringTargetEntity.setTargetName(acquiringAddTargetRequest.getTargetName());
            acquiringTargetEntity.setTargetSource(acquiringAddTargetRequest.getTargetSource());
            acquiringTargetEntity.setTargetType(acquiringAddTargetRequest.getTargetType());
            acquiringTargetEntity.setTargetDesc(acquiringAddTargetRequest.getTargetDesc());
            acquiringTargetEntity.setTargetStatus(TargetStatus.ACTIVE);
            acquiringTargetEntity.setTargetValue(acquiringAddTargetRequest.getTargetValue());
            acquiringTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            iAcquiringTargetsRepository.save(acquiringTargetEntity);
            return true;

            //save
        } catch (Exception e) {
            log.error("Error while adding new target", e);
        }
        return false;
    }

    @Override
    public boolean addAsset(String assetDetails, MultipartFile[] assetFiles) {
        try {
            if (assetDetails == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            DFSVoomaAddAssetRequest dfsVoomaAddAssetRequest = mapper.readValue(assetDetails, DFSVoomaAddAssetRequest.class);
            AcquiringAssetEntity dfsVoomaAssetEntity = new AcquiringAssetEntity();
            dfsVoomaAssetEntity.setSerialNumber(dfsVoomaAddAssetRequest.getSerialNumber());
//            dfsVoomaAssetEntity.setAssetCondition(dfsVoomaAddAssetRequest.getAssetCondition());
            dfsVoomaAssetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaAssetEntity.setAssetNumber(dfsVoomaAddAssetRequest.getAssetNumber());
            dfsVoomaAssetEntity.setAssetType(dfsVoomaAddAssetRequest.getAssetType());
//            dfsVoomaAddAssetRequest.setDeviceId(dfsVoomaAddAssetRequest.getDeviceId());
            AcquiringAssetEntity savedAsset = acquiringAssetRepository.save(dfsVoomaAssetEntity);

            List<String> filePathList = new ArrayList<>();

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileNameV("AcquiringAsset" , assetFiles,Utility.getSubFolder());
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/"+Utility.getSubFolder()+"/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);;
                //save to db
                AcquiringAssetFilesEntity dfsVoomaAssetFilesEntity = new AcquiringAssetFilesEntity();
                dfsVoomaAssetFilesEntity.setAcquiringAssetEntity(dfsVoomaAssetEntity);
                dfsVoomaAssetFilesEntity.setFilePath(downloadUrl);
                dfsVoomaAssetFilesEntity.setFileName(filePath);
                dfsVoomaAssetFilesEntity.setIdAsset(savedAsset.getId());
                acquiringAssetFileRepository.save(dfsVoomaAssetFilesEntity);

            }
            return true;

        } catch (JsonMappingException e) {
            //return ResponseEntity.badRequest().body("Invalid asset details");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public List<ObjectNode> getAllAssets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringAssetEntity acquiringAssetEntity : acquiringAssetRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringAssetEntity.getId());
                asset.put("condition", acquiringAssetEntity.getAssetCondition().toString());
                asset.put("sno", acquiringAssetEntity.getSerialNumber());
                ArrayNode images = mapper.createArrayNode();


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
    public boolean deleteAssetById(Long id) {
        try {
            acquiringAssetRepository.deleteById(id);
            //
            return true;
        } catch (Exception e) {
            log.error("Error occurred while deleting asset by id", e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> loadQuestionnaires() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringQuestionnaireQuestionEntity acquiringQuestionnaireEntity : acquiringQuestionnaireRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", acquiringQuestionnaireEntity.getQuestionnaireId());
                node.put("question", acquiringQuestionnaireEntity.getQuestion());
                node.put("description", acquiringQuestionnaireEntity.getQuestionnaireDescription());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }
//TODO: wrong implementation
    @Override
    public List<ObjectNode> loadDSRsInTarget(AcquiringDSRsInTargetRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringDSRsInTargetEntity acquiringDSRsInTargetEntity : iAcquiringDSRsInTargetRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringDSRsInTargetEntity.getId());
                asset.put("dsrName", acquiringDSRsInTargetEntity.getDsrName());
                asset.put("targetName", acquiringDSRsInTargetEntity.getTargetName());
                asset.put("setTarget", acquiringDSRsInTargetEntity.getSetTarget());
                asset.put("achievedTarget", acquiringDSRsInTargetEntity.getAchievedTarget());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all assets", e);
        }
        return null;
    }

    @Override
    public boolean scheduleCustomerVisit(CustomerVisitsRequest customerVisitsRequest) {
        try {
            if (customerVisitsRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringCustomerVisitEntity acquiringCustomerVisitEntity = new AcquiringCustomerVisitEntity();
            acquiringCustomerVisitEntity.setMerchantName(customerVisitsRequest.getMerchantName());
            acquiringCustomerVisitEntity.setVisitDate(customerVisitsRequest.getVisitDate());
            acquiringCustomerVisitEntity.setReasonForVisit(customerVisitsRequest.getReasonForVisit());
            acquiringCustomerVisitEntity.setStatus(Status.ACTIVE);
            acquiringCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            acquiringCustomerVisitEntity.setDsrName(customerVisitsRequest.getDsrName());
            //save
            acquiringCustomerVisitRepository.save(acquiringCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean reScheduleCustomerVisit(CustomerVisitsRequest customerVisitsRequest, Long id) {
        //update schedule customer visit by only changing the date
        try {
            if (customerVisitsRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringCustomerVisitEntity acquiringCustomerVisitEntity = acquiringCustomerVisitRepository.findById(id).get();
            acquiringCustomerVisitEntity.setVisitDate(customerVisitsRequest.getVisitDate());
            acquiringCustomerVisitEntity.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            acquiringCustomerVisitRepository.save(acquiringCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadCustomerVisits() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringCustomerVisitEntity acquiringCustomerVisitEntity : acquiringCustomerVisitRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringCustomerVisitEntity.getId());
                asset.put("merchantName", acquiringCustomerVisitEntity.getMerchantName());
                asset.put("visitDate", acquiringCustomerVisitEntity.getVisitDate());
                asset.put("reasonForVisit", acquiringCustomerVisitEntity.getReasonForVisit());
                asset.put("dsrName", acquiringCustomerVisitEntity.getDsrName());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all assets", e);
        }
        return null;
    }

    @Override
    public boolean addNewQuestionnaire(AcquiringAddQuestionnaireRequest acquiringAddQuestionnaireRequest) {
        //add new questionnaire
        try {
            if (acquiringAddQuestionnaireRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringQuestionnaireQuestionEntity acquiringQuestionnaireEntity = new AcquiringQuestionnaireQuestionEntity();
            acquiringQuestionnaireEntity.setQuestion(acquiringAddQuestionnaireRequest.getQuestion());
            acquiringQuestionnaireEntity.setQuestionnaireDescription(acquiringAddQuestionnaireRequest.getQuestionnaireDescription());
            acquiringQuestionnaireEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            acquiringQuestionnaireRepository.save(acquiringQuestionnaireEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<?> getCustomerVisitQuestionnaireResponses(Long visitId, Long questionnaireId) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringQuestionerResponseEntity acquiringCustomerVisitQuestionnaireResponseEntity : acquiringQuestionerResponseRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringCustomerVisitQuestionnaireResponseEntity.getId());
                asset.put("questionId", acquiringCustomerVisitQuestionnaireResponseEntity.getQuestionId());
                asset.put("response", acquiringCustomerVisitQuestionnaireResponseEntity.getResponse());
                asset.put("visitId", acquiringCustomerVisitQuestionnaireResponseEntity.getVisitId());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching responses", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringLeadEntity acquiringLeadEntity : acquiringLeadsRepository.findAll()) {
                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringLeadEntity.getId());
                asset.put("customerId", acquiringLeadEntity.getCustomerId());
                asset.put("businessUnit", acquiringLeadEntity.getBusinessUnit());
                asset.put("leadStatus", String.valueOf(acquiringLeadEntity.getLeadStatus()));
                asset.put("topic", acquiringLeadEntity.getTopic());
                asset.put("priority", acquiringLeadEntity.getPriority().toString());
                asset.put("dsrId", acquiringLeadEntity.getDsrId());
                asset.put("dsrName", acquiringLeadEntity.getDsrName());
                asset.put("createdOn",acquiringLeadEntity.getCreatedOn().getTime());

                //add to list
                list.add(asset);


            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all leads", e);
        }


        return null;
    }

    @Override
    public boolean approveMerchantOnboarding(AcquiringApproveMerchant model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringOnboardEntity acquiringOnboardEntity1 = acquiringOnboardingsRepository.findById(model.getMerchantId()).get();
            acquiringOnboardEntity1.setStatus(OnboardingStatus.APPROVED);
            acquiringOnboardEntity1.setApproved(true);
            //save
            acquiringOnboardingsRepository.save(acquiringOnboardEntity1);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAllOnboardedMerchants() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.findAll()) {
                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringOnboardEntity.getId());
                asset.put("merchantName", acquiringOnboardEntity.getClientLegalName());
                asset.put("region", acquiringOnboardEntity.getRegion());
                asset.put("phoneNumber", acquiringOnboardEntity.getBusinessPhoneNumber());
                asset.put("email", acquiringOnboardEntity.getBusinessEmail());
                asset.put("status", acquiringOnboardEntity.getStatus().toString());
                asset.put("agentId", acquiringOnboardEntity.getDsrId());
                asset.put("createdOn", acquiringOnboardEntity.getCreatedOn().getTime());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all onboarded merchants", e);
        }
        return null;
    }

    @Override
    public List<?> getOnboardingSummary(AcquringSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
             {
                for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.fetchAllOnboardingCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("merchantName", acquiringOnboardEntity.getClientLegalName());
                    asset.put("onboarding-status", acquiringOnboardEntity.getStatus().ordinal());
                    asset.put("agent Id", acquiringOnboardEntity.getDsrId());
                    asset.put("date_onboarded", acquiringOnboardEntity.getCreatedOn().getTime());
                    asset.put("latitude", acquiringOnboardEntity.getLatitude());
                    asset.put("longitude", acquiringOnboardEntity.getLongitude());
                    list.add(asset);
                }
            }


        } catch (Exception e) {
            log.error("Error occurred while fetching onboarding summary", e);
        }
        return null;
    }

    @Override
    public ObjectNode getMerchantById(AcquiringMerchantDetailsRequest acquiringMerchantDetailsRequest) {
        try {
            if (acquiringMerchantDetailsRequest.getMerchantId() == null) {
                log.error("Merchant id is null");
                return null;
            }
            //get merchant by id
            AcquiringOnboardEntity acquiringOnboardEntity = acquiringOnboardingsRepository.findById(acquiringMerchantDetailsRequest.getMerchantId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("merchantName", acquiringOnboardEntity.getClientLegalName());
            asset.put("Region", acquiringOnboardEntity.getRegion());
            asset.put("phoneNumber", acquiringOnboardEntity.getBusinessPhoneNumber());
            asset.put("email", acquiringOnboardEntity.getBusinessEmail());
            asset.put("status", acquiringOnboardEntity.getStatus().toString());
            asset.put("agent Id", acquiringOnboardEntity.getDsrId());
            asset.put("createdOn", acquiringOnboardEntity.getCreatedOn().getTime());
            ObjectNode cordinates = mapper.createObjectNode();
            cordinates.put("latitude", acquiringOnboardEntity.getLatitude());
            cordinates.put("longitude", acquiringOnboardEntity.getLongitude());
            asset.put("cordinates", cordinates);
            ObjectNode businessDetails = mapper.createObjectNode();
            businessDetails.put("businessName", acquiringOnboardEntity.getBusinessName());
            businessDetails.put("physicalLocation", acquiringOnboardEntity.getRegion());

            asset.set("businessDetails", businessDetails);
            List<AcquiringOnboardingKYCentity> dfsVoomaFileUploadEntities = acquiringOnboardingKYCRepository.findByMerchantId(acquiringMerchantDetailsRequest.getMerchantId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (AcquiringOnboardingKYCentity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                String[] fileName = dfsVoomaFileUploadEntity.getFileName().split("\\\\");
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
    public List<ObjectNode> getTargetsSummary(AcquringSummaryRequest filters) {
try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summary targets for last 7 days
    //[{
    //    "target_id":"",
    //    "target_name":"",
    //    "target_value":"",
    //    "target_achieved":"", //Sum of value achieved by department staff
    //    "start_date":"dd-MMM-yyyy",
    //    "target_status":"active",//completed, expired
    //}]
             {
                for (AcquiringTargetEntity acquiringTargetEntity : iAcquiringTargetsRepository.fetchAllTargetCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("agentId", acquiringTargetEntity.getId());
                    asset.put("target_name", acquiringTargetEntity.getTargetName());
                    asset.put("target_achieved", acquiringTargetEntity.getTargetAchievement());
                    asset.put("start_date", acquiringTargetEntity.getStartDate().getTime());
                    asset.put("target_status", acquiringTargetEntity.getTargetStatus().ordinal());
                    list.add(asset);
                }
                return list;
            }

    } catch (Exception e) {
            log.error("Error occurred while fetching targets summary", e);
        }
        return null;
    }

    @Override
    public List<?> getLeadsSummary(AcquringSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            {
                for (AcquiringLeadEntity acquiringLeadEntity : acquiringLeadsRepository.fetchAllLeadsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    //number of leads created
                    asset.put("lead_orginates",acquiringLeadsRepository.countAllLeadsCreatedLast7Days());
                    asset.put("leads_assigned",acquiringLeadsRepository.countAllLeadsCreatedLast7DaysAssigned());
                    asset.put("leads_open", acquiringLeadsRepository.countAllLeadsCreatedLast7DaysOpen());
                    asset.put("leads_closed", acquiringLeadsRepository.countAllLeadsCreatedLast7DaysClosed());
                    asset.put("lead_status", acquiringLeadEntity.getLeadStatus().ordinal());
                    ObjectNode leadStatus = mapper.createObjectNode();
                    leadStatus.put("hot", acquiringLeadsRepository.countAllLeadsCreatedLast7DaysHot());
                    leadStatus.put("warm", acquiringLeadsRepository.countAllLeadsCreatedLast7DaysWarm());
                    leadStatus.put("cold", acquiringLeadsRepository.countAllLeadsCreatedLast7DaysCold());
                    //object containing lead topic,co-oridinates and created on
                    ObjectNode lead = mapper.createObjectNode();
                    lead.put("lead_topic", acquiringLeadEntity.getTopic());
                    lead.put("lead_created_on", acquiringLeadEntity.getCreatedOn().getTime());
                    //add to list
                    list.add(asset);
                }
                return list;
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching leads summary", e);
        }
        //[{
        //    "leads_originated":"400",
        //    "leads_assigned":"500",
        //    "leads_open":"500",
        //    "leads_closed":"500",
        //    "open_leads_by_status":{
        //          "hot":500,
        //            "warm":300,
        //                "cold":78
        //    },
        //    "leads":[{"topic":"","latlng":{"lat":"","lng":""},"date_originated":"dd-mmm-yyyy"},{}],
        //    "our_leads_summary":["date":"dd-mmm-yyyy",{"hot":67,"warm":400,"cold":3}},{}],
        //}]
        return null;
    }

    @Override
    public List<?> getAssetsSummary(AcquringSummaryRequest filters) {
        //[{
        //    "faulty_assets":"400",
        //    "working_assets":"500",
        //    "assigned_assets":"500",
        //    "unassigned_assets":"500",
        //    "assets_loc":[{"serial":"","latlng":{"lat":"","lng":""},"pic":"/gg.png"},{}],
        //}]
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
             {
                for (AcquiringAssetEntity acquiringAssetEntity : acquiringAssetRepository.fetchAllAssetsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("faulty_assets", acquiringAssetRepository.countFaultyAssets());
                    asset.put("working_assets", acquiringAssetRepository.countWorkingAssets());
                    asset.put("assigned_assets", acquiringAssetRepository.countAssignedAssets());
                    asset.put("unassigned_assets", acquiringAssetRepository.countUnAssignedAssets());
                    asset.put("asset_logitude", acquiringAssetEntity.getLongitude());
                    asset.put("asset_latitude", acquiringAssetEntity.getLatitude());
                    asset.put("asset_serial", acquiringAssetEntity.getSerialNumber());

                    list.add(asset);
                }
                return list;
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching assets summary", e);
        }
        return null;
    }

    @Override
    public boolean assignTargetToDSR(DSRTAssignTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRAccountEntity user = dsrAccountsRepository.findById(model.getDsrId()).orElse(null);

            AcquiringTargetEntity target = iAcquiringTargetsRepository.findById(model.getTargetId()).orElse(null);
            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                user.setCampaignTargetValue(model.getTargetValue());
            } if (target.getTargetType().equals(TargetType.LEADS)) {
                user.setLeadsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                user.setVisitsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                user.setOnboardTargetValue(model.getTargetValue());
            }

            Set<AcquiringTargetEntity> acquiringTargetEntities = (Set<AcquiringTargetEntity>) user.getAcquiringTargetEntities();
            acquiringTargetEntities.add(target);
            user.setAcquiringTargetEntities(acquiringTargetEntities);
            dsrAccountsRepository.save(user);
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
            DSRTeamEntity teamEntity = dsrTeamsRepository.findById(model.getTeamId()).orElse(null);

            AcquiringTargetEntity target = iAcquiringTargetsRepository.findById(model.getTargetId()).orElse(null);

            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                teamEntity.setCampaignTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                teamEntity.setLeadsTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                teamEntity.setVisitsTargetValue(model.getTargetValue());
            }
            if  (target.getTargetType().equals(TargetType.ONBOARDING)) {
                teamEntity.setOnboardTargetValue(model.getTargetValue());
            }

            Set<AcquiringTargetEntity> acquiringTargetEntities = (Set<AcquiringTargetEntity>) teamEntity.getAcquiringTargetEntities();
            acquiringTargetEntities.add(target);
            teamEntity.setAcquiringTargetEntities(acquiringTargetEntities);
            dsrTeamsRepository.save(teamEntity);
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
                return null;
            }
            AcquiringTargetEntity target = iAcquiringTargetsRepository.findById(model.getId()).orElse(null);
            return target;
        } catch (Exception e) {
            log.error("Error occurred while fetching target by id", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadAllApprovedMerchants() {
        try {

            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", acquiringOnboardEntity.getId());
                objectNode.put("merchantName", acquiringOnboardEntity.getClientLegalName());
                objectNode.put("region", acquiringOnboardEntity.getRegion());
                objectNode.put("status", acquiringOnboardEntity.getStatus().toString());
                objectNode.put("dateOnborded", acquiringOnboardEntity.getCreatedOn().getTime());
                objectNode.put("payBillNo",Utility.generateRandomNumber());
                objectNode.put("tillNo",Utility.generateRandomNumber());
                objectNode.put("phoneNumber", acquiringOnboardEntity.getBusinessPhoneNumber());
                objectNode.put("email", acquiringOnboardEntity.getBusinessEmail());
                objectNode.put("dsrId", acquiringOnboardEntity.getDsrId());
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(acquiringOnboardEntity.getLongitude());
                arrayNode.add(acquiringOnboardEntity.getLatitude());
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
    public boolean rejectMerchantOnboarding(AcquiringApproveMerchant model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringOnboardEntity acquiringOnboardEntity1 = acquiringOnboardingsRepository.findById(model.getMerchantId()).get();
            acquiringOnboardEntity1.setStatus(OnboardingStatus.REJECTED);
            acquiringOnboardEntity1.setApproved(false);
            //save
            acquiringOnboardingsRepository.save(acquiringOnboardEntity1);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while reject merchant onboarding", e);
        }
        return false;
    }

    @Override
    public Object getAssetById(AssetByIdRequest model) {
        try {
            if (model.getAssetId() == null) {
                log.error("Asset id is null");
                return null;
            }
            //get merchant by id
            AcquiringAssetEntity acquiringOnboardEntity = acquiringAssetRepository.findById(model.getAssetId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("condition", acquiringOnboardEntity.getAssetCondition().toString());
            asset.put("serialNo", acquiringOnboardEntity.getSerialNumber());
            asset.put("createdOn", acquiringOnboardEntity.getSerialNumber());
            asset.put("dsrId", acquiringOnboardEntity.getDsrId());
            asset.put("visitDate", acquiringOnboardEntity.getVisitDate());
            asset.put("location", acquiringOnboardEntity.getLocation());
            asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
            asset.put("status", acquiringOnboardEntity.getStatus().toString());
            List<AcquiringAssetFilesEntity> dfsVoomaFileUploadEntities = acquiringAssetFileRepository.findByIdAsset(model.getAssetId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (AcquiringAssetFilesEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                String[] fileName = dfsVoomaFileUploadEntity.getFileName().split("\\\\");
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


}

