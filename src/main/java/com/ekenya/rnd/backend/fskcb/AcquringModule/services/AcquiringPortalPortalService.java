package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.*;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
@Slf4j
public class AcquiringPortalPortalService implements IAcquiringPortalService {

    private final IAcquiringLeadsRepository mLeadsRepo;
    private final IAcquiringTargetsRepository iAcquiringTargetsRepository;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;
    private final AcquiringAssetFileRepository acquiringAssetFileRepository;
    private final AcquiringCustomerVisitRepository acquiringCustomerVisitRepository;
    private final AcquiringQuestionnaireRepository acquiringQuestionnaireRepository;

    private final IAcquiringDSRsInTargetRepository iAcquiringDSRsInTargetRepository;

    private final AcquiringQuestionerResponseRepository acquiringQuestionerResponseRepository;
    private final IAcquiringLeadsRepository acquiringLeadsRepository;

    private final IAcquiringOnboardingsRepository acquiringOnboardingsRepository;




    @Override
    public boolean assignLeadToDsr(AcquiringLeadRequest acquiringLeadRequest) {
        try {
            AcquiringLeadEntity acquiringLeadEntity = acquiringLeadsRepository.findById(acquiringLeadRequest.getLeadId()).get();
            acquiringLeadEntity.setDsrId(acquiringLeadRequest.getDsrId());
            //set start date from input
            acquiringLeadEntity.setStartDate(acquiringLeadRequest.getStartDate());
            acquiringLeadEntity.setEndDate(acquiringLeadRequest.getEndDate());
            //save
            acquiringLeadsRepository.save(acquiringLeadEntity);
            //update is assigned to true
            acquiringLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadTargets() {
        try {
            List<ObjectNode> targets = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringTargetEntity target : iAcquiringTargetsRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", target.getId());
                node.put("name", target.getTargetName());
                node.put("description", target.getTargetDesc());
                node.put("status", target.getTargetStatus().toString());
                node.put("value", target.getTargetValue());
                node.put("achievement", target.getTargetAchievement());
                node.put("source", target.getTargetSource());
                node.put("startDate", target.getStartDate().toString());
                node.put("endDate", target.getEndDate().toString());
                node.put("type", target.getAcquiringTargetType().toString());
                targets.add(node);
            }
            return targets;
        } catch (Exception e) {
            log.error("Error loading targets", e);
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
            acquiringTargetEntity.setAcquiringTargetType(acquiringAddTargetRequest.getAquiringTargetType());
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
                //return ResponseEntity.badRequest().body("Asset details are required");
            }
            //

            ObjectMapper mapper = new ObjectMapper();

            AcquiringAddAssetRequest acquiringAddAssetRequest =
                    mapper.readValue(assetDetails, AcquiringAddAssetRequest.class);
            AcquiringAssetEntity acquiringAssetEntity = new AcquiringAssetEntity();
            //
            acquiringAssetEntity.setSerialNumber(acquiringAddAssetRequest.getSerialNumber());
            //
            acquiringAssetEntity.setAssetCondition(acquiringAddAssetRequest.getAssetCondition());
            //
            AcquiringAssetEntity savedAsset = acquiringAssetRepository.save(acquiringAssetEntity);
            //

            List<String> filePathList = new ArrayList<>();
            //save files
            filePathList = fileStorageService.saveMultipleFileWithSpecificFileName("Asset_", assetFiles);
            //save file paths to db
            filePathList.forEach(filePath -> {
                AcquiringAssetFilesEntity assetFilesEntity = new AcquiringAssetFilesEntity();
                assetFilesEntity.setAcquiringAssetEntity(savedAsset);
                assetFilesEntity.setFilePath(filePath);
                acquiringAssetFileRepository.save(assetFilesEntity);
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
            for (AcquiringAssetEntity acquiringAssetEntity : acquiringAssetRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringAssetEntity.getId());
                asset.put("condition", acquiringAssetEntity.getAssetCondition().toString());
                asset.put("sno", acquiringAssetEntity.getSerialNumber());
                //asset.put("type",acquiringAssetEntity.getAssetType());
                //
                ArrayNode images = mapper.createArrayNode();

                //"http://10.20.2.12:8484/"

                // "/files/acquiring/asset-23-324767234.png;/files/acquiring/asset-23-3247672ewqee8.png"
                for (String path : acquiringAssetEntity.getImages().split(";")) {
                    images.add(path);
                }

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
    public ObjectNode getAssetById(Long id) {
        try {
            AcquiringAssetEntity acquiringAssetEntity = acquiringAssetRepository.findById(id).get();


        } catch (Exception e) {
            log.error("Error occurred while fetching asset by id", e);
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
                asset.put("priority", acquiringLeadEntity.getPriority().ordinal());
                asset.put("dsrId", acquiringLeadEntity.getDsrId());
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
    public boolean approveMerchantOnboarding(AcquiringOnboardEntity acquiringOnboardEntity) {
        try {
            if (acquiringOnboardEntity == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            AcquiringOnboardEntity acquiringOnboardEntity1 = acquiringOnboardingsRepository.findById(acquiringOnboardEntity.getId()).get();
            acquiringOnboardEntity1.setIsApproved(true);
            //save
            acquiringOnboardingsRepository.save(acquiringOnboardEntity1);
            log.info("Merchant onboarding approved successfully");
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
                asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
                asset.put("Region", acquiringOnboardEntity.getRegion());
                asset.put("phoneNumber", acquiringOnboardEntity.getMerchantPhone());
                asset.put("email", acquiringOnboardEntity.getMerchantEmail());
                asset.put("status", acquiringOnboardEntity.getStatus().ordinal());
                asset.put("agent Id", acquiringOnboardEntity.getDsrId());
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
                    asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
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
            asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
            asset.put("Region", acquiringOnboardEntity.getRegion());
            asset.put("phoneNumber", acquiringOnboardEntity.getMerchantPhone());
            asset.put("email", acquiringOnboardEntity.getMerchantEmail());
            asset.put("status", acquiringOnboardEntity.getStatus().ordinal());
            asset.put("agent Id", acquiringOnboardEntity.getDsrId());
            asset.put("createdOn", acquiringOnboardEntity.getCreatedOn().getTime());
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
                    asset.put("image", acquiringAssetEntity.getImages());

                    list.add(asset);
                }
                return list;
            }
        } catch (Exception e) {
            log.error("Error occurred while fetching assets summary", e);
        }
        return null;
    }

}

