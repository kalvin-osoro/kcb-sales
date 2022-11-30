package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.fskcb.payload.*;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoomaPortalService implements IVoomaPortalService {
    private final DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;
    private final DFSVoomaLeadRepository dfsVoomaLeadRepository;
    private final DFSVoomaQuestionerResponseRepository dfsVoomaQuestionerResponseRepository;
    private final DFSVoomaQuestionnaireQuestionRepository dfsVoomaQuestionnaireQuestionRepository;
    private final DFSVoomaOnboardRepository dfsVoomaOnboardRepository;
    private final DFSVoomaTargetRepository dfsVoomaTargetRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;
    private final DFSVoomaAssetFilesRepository dfsVoomaAssetFilesRepository;

    private final DFSVoomaAssetRepository dfsVoomaAssetRepository;

    private final DFSVoomaFeedBackRepository dfsVoomaFeedBackRepository;

    private final FileStorageService fileStorageService;


    @Override
    public boolean scheduleCustomerVisit(VoomaCustomerVisitsRequest model) {
       try {
           if (model == null) {
               return false;
           }
              DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = new DFSVoomaCustomerVisitEntity();
           dfsVoomaCustomerVisitEntity.setCustomerName(model.getCustomerName());
           dfsVoomaCustomerVisitEntity.setVisitDate(model.getVisitDate());
           dfsVoomaCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
           dfsVoomaCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
           dfsVoomaCustomerVisitEntity.setStatus(Status.ACTIVE);
              dfsVoomaCustomerVisitRepository.save(dfsVoomaCustomerVisitEntity);
              return true;

       } catch (Exception e) {
              log.error("Error occurred while scheduling customer visit", e);
       }
        return false;
    }

    @Override
    public boolean reScheduleCustomerVisit(VoomaCustomerVisitsRequest voomaCustomerVisitsRequest) {
        try {
            if (voomaCustomerVisitsRequest == null) {
                return false;
            }
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = dfsVoomaCustomerVisitRepository.findById(voomaCustomerVisitsRequest.getId()).orElse(null);
            if (dfsVoomaCustomerVisitEntity == null) {
                return false;
            }
            dfsVoomaCustomerVisitEntity.setVisitDate(voomaCustomerVisitsRequest.getVisitDate());
            dfsVoomaCustomerVisitRepository.save(dfsVoomaCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rescheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisits() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity : dfsVoomaCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaCustomerVisitEntity.getId());
                objectNode.put("customerName", dfsVoomaCustomerVisitEntity.getCustomerName());
                objectNode.put("visitDate", dfsVoomaCustomerVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", dfsVoomaCustomerVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", dfsVoomaCustomerVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits", e);
        }
        return null;
    }

    @Override
    public boolean assignLeadToDsr(VoomaAssignLeadRequest model) {
        try {
            DFSVoomaLeadEntity dfsVoomaLeadEntity = dfsVoomaLeadRepository.findById(model.getLeadId()).orElse(null);
            dfsVoomaLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            dfsVoomaLeadEntity.setStartDate(model.getStartDate());
            dfsVoomaLeadEntity.setEndDate(model.getEndDate());
            //save
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
            //update is assigned to true
            dfsVoomaLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;

    }

    @Override
    public List<ObjectNode> getAllLeads() {
        //get all leads
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaLeadEntity.getId());
                objectNode.put("customerId", dfsVoomaLeadEntity.getCustomerId());
                objectNode.put("businessUnit", dfsVoomaLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", dfsVoomaLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", dfsVoomaLeadEntity.getTopic());
                objectNode.put("priority", dfsVoomaLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", dfsVoomaLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponses(VoomaCustomerVisitQuestionnaireRequest voomaCustomerVisitQuestionnaireRequest) {
        try {//get question Response by visit id and question id
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaQuestionerResponseEntity dfsVoomaQuestionerResponseEntity : dfsVoomaQuestionerResponseRepository.findAllByVisitIdAndQuestionId(voomaCustomerVisitQuestionnaireRequest.getVisitId(), voomaCustomerVisitQuestionnaireRequest.getQuestionId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaQuestionerResponseEntity.getId());
                objectNode.put("questionId", dfsVoomaQuestionerResponseEntity.getQuestionId());
                objectNode.put("questionResponse", dfsVoomaQuestionerResponseEntity.getResponse());
                objectNode.put("visitId", dfsVoomaQuestionerResponseEntity.getVisitId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visit questionnaire responses", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllQuestionnaires() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaQuestionnaireQuestionEntity dfsVoomaQuestionnaireQuestionEntity : dfsVoomaQuestionnaireQuestionRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaQuestionnaireQuestionEntity.getId());
                objectNode.put("question", dfsVoomaQuestionnaireQuestionEntity.getQuestion());
                objectNode.put("createdOn", dfsVoomaQuestionnaireQuestionEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean createQuestionnaire(VoomaAddQuestionnaireRequest voomaAddQuestionnaireRequest) {
        try {
            DFSVoomaQuestionnaireQuestionEntity dfsVoomaQuestionnaireQuestionEntity = new DFSVoomaQuestionnaireQuestionEntity();
            dfsVoomaQuestionnaireQuestionEntity.setQuestion(voomaAddQuestionnaireRequest.getQuestion());
            dfsVoomaQuestionnaireQuestionEntity.setQuestionnaireDescription(voomaAddQuestionnaireRequest.getQuestionnaireDescription());

            dfsVoomaQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaQuestionnaireQuestionRepository.save(dfsVoomaQuestionnaireQuestionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating questionnaire", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAllOnboardedMerchants( ) {
       try {
           List<ObjectNode> list = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                for (DFSVoomaOnboardEntity dfsVoomaMerchantEntity : dfsVoomaOnboardRepository.findAll()) {
                    ObjectNode objectNode = mapper.createObjectNode();
                    objectNode.put("id", dfsVoomaMerchantEntity.getId());
                    objectNode.put("merchantName", dfsVoomaMerchantEntity.getMerchantName());
                    objectNode.put("region", dfsVoomaMerchantEntity.getRegion());
                    objectNode.put("phone", dfsVoomaMerchantEntity.getMerchantPhone());
                    objectNode.put("email", dfsVoomaMerchantEntity.getMerchantEmail());
                    objectNode.put("status", dfsVoomaMerchantEntity.getStatus().ordinal());
                    objectNode.put("dsrId", dfsVoomaMerchantEntity.getDsrId());
                    objectNode.put("createdOn", dfsVoomaMerchantEntity.getCreatedOn().getTime());
                    list.add(objectNode);
       }
        return list;
       } catch (Exception e) {
           log.error("Error occurred while loading all onboarded merchants", e);
       }
         return null;
    }

        @Override
    public boolean approveMerchantOnboarding(DFSVoomaApproveMerchantOnboarindRequest dfsVoomaApproveMerchantOnboarindRequest) {
        try {
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = dfsVoomaOnboardRepository.findById(dfsVoomaApproveMerchantOnboarindRequest.getId()).get();
            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.APPROVED);
            dfsVoomaOnboardEntity.setIsApproved(true);
            dfsVoomaOnboardEntity.setRemarks(dfsVoomaApproveMerchantOnboarindRequest.getRemarks());
            dfsVoomaOnboardRepository.save(dfsVoomaOnboardEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public Object getMerchantById(VoomaMerchantDetailsRequest model) {
        //get merchant by id
        try {
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = dfsVoomaOnboardRepository.findById(model.getId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", dfsVoomaOnboardEntity.getId());
            objectNode.put("merchantName", dfsVoomaOnboardEntity.getMerchantName());
            objectNode.put("region", dfsVoomaOnboardEntity.getRegion());
            objectNode.put("phoneNumber", dfsVoomaOnboardEntity.getMerchantPhone());
            objectNode.put("email", dfsVoomaOnboardEntity.getMerchantEmail());
            objectNode.put("status", dfsVoomaOnboardEntity.getStatus().ordinal());
            objectNode.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
            objectNode.put("createdOn", dfsVoomaOnboardEntity.getCreatedOn().getTime());
            return objectNode;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant by id", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaTargetEntity dfsVoomaTargetEntity : dfsVoomaTargetRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaTargetEntity.getId());
                objectNode.put("targetName", dfsVoomaTargetEntity.getTargetName());
                objectNode.put("targetSource", dfsVoomaTargetEntity.getTargetSource());
                objectNode.put("agencyTargetType", dfsVoomaTargetEntity.getTargetType().ordinal());
                objectNode.put("targetDesc", dfsVoomaTargetEntity.getTargetDesc());
                objectNode.put("targetStatus", dfsVoomaTargetEntity.getTargetStatus().name());
                objectNode.put("targetValue", dfsVoomaTargetEntity.getTargetValue());
//                objectNode.put("targetAchieved",dfsVoomaTargetEntity.getTargetAchievement());
                objectNode.put("createdOn", dfsVoomaTargetEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all targets", e);
        }
        return null;
    }

    @Override
    public boolean createVoomaTarget(DFSVoomaAddTargetRequest voomaAddTargetRequest) {
        try {
            if (voomaAddTargetRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            DFSVoomaTargetEntity dfsVoomaTargetEntity = new DFSVoomaTargetEntity();
            dfsVoomaTargetEntity.setTargetName(voomaAddTargetRequest.getTargetName());
            dfsVoomaTargetEntity.setTargetSource(voomaAddTargetRequest.getTargetSource());
            dfsVoomaTargetEntity.setTargetType(voomaAddTargetRequest.getTargetType());
            dfsVoomaTargetEntity.setTargetDesc(voomaAddTargetRequest.getTargetDesc());
            dfsVoomaTargetEntity.setStartDate(voomaAddTargetRequest.getStartDate());
            dfsVoomaTargetEntity.setEndDate(voomaAddTargetRequest.getEndDate());
            dfsVoomaTargetEntity.setAssignmentType(voomaAddTargetRequest.getAssignmentType());


            dfsVoomaTargetEntity.setTargetStatus(TargetStatus.ACTIVE);

            dfsVoomaTargetEntity.setTargetValue(voomaAddTargetRequest.getTargetValue());
            dfsVoomaTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            dfsVoomaTargetRepository.save(dfsVoomaTargetEntity);
            return true;

            //save
        } catch (Exception e) {
            log.error("Error while adding new target", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getOnboardingSummary(VoomaSummaryRequest filters) {
        //get onboarding summary
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaOnboardEntity dfsVoomaOnboardEntity : dfsVoomaOnboardRepository.findAllByCreatedOn()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaOnboardEntity.getId());
                objectNode.put("merchantName", dfsVoomaOnboardEntity.getMerchantName());
                objectNode.put("region", dfsVoomaOnboardEntity.getRegion());
                objectNode.put("status", dfsVoomaOnboardEntity.getStatus().ordinal());
                objectNode.put("dateOnborded", dfsVoomaOnboardEntity.getCreatedOn().getTime());
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(dfsVoomaOnboardEntity.getLongitude());
                arrayNode.add(dfsVoomaOnboardEntity.getLatitude());
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
    public List<ObjectNode> getAllCustomerFeedbacks() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaFeedBackEntity dfsVoomaCustomerFeedbackEntity : dfsVoomaFeedBackRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaCustomerFeedbackEntity.getId());
                objectNode.put("customerId", dfsVoomaCustomerFeedbackEntity.getCustomerId());
                objectNode.put("channel", dfsVoomaCustomerFeedbackEntity.getChannel());
                objectNode.put("visitRef", dfsVoomaCustomerFeedbackEntity.getVisitRef());
                objectNode.put("customerName", dfsVoomaCustomerFeedbackEntity.getCustomerName());
                objectNode.put("noOfQuestionAsked", dfsVoomaCustomerFeedbackEntity.getNoOfQuestionAsked());
                objectNode.put("createdOn", dfsVoomaCustomerFeedbackEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer feedbacks", e);
        }

        return null;
    }

    @Override
    public ArrayNode getCustomerFeedbackResponses(DFSVoomaFeedBackRequestById model) {
        try {
            if (model == null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode objectNode = mapper.createArrayNode();
                for (DFSVoomaFeedBackEntity dfsVoomaFeedBackResponseEntity : dfsVoomaFeedBackRepository.findAll()) {
                ObjectNode objectNode1 = mapper.createObjectNode();
                objectNode1.put("id", dfsVoomaFeedBackResponseEntity.getId());
                objectNode1.put("questionAsked", dfsVoomaFeedBackResponseEntity.getQuestionAsked());
                objectNode1.put("response", dfsVoomaFeedBackResponseEntity.getResponse());
                objectNode1.put("createdOn", dfsVoomaFeedBackResponseEntity.getCreatedOn().getTime());
                objectNode.add(objectNode1);
            }
            return objectNode;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant by id", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadAllApprovedMerchants() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaOnboardEntity dfsVoomaOnboardEntity : dfsVoomaOnboardRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaOnboardEntity.getId());
                objectNode.put("merchantName", dfsVoomaOnboardEntity.getMerchantName());
                objectNode.put("region", dfsVoomaOnboardEntity.getRegion());
                objectNode.put("status", dfsVoomaOnboardEntity.getStatus().ordinal());
                objectNode.put("dateOnborded", dfsVoomaOnboardEntity.getCreatedOn().getTime());
                objectNode.put("phoneNumber", dfsVoomaOnboardEntity.getMerchantPhone());
                objectNode.put("email", dfsVoomaOnboardEntity.getMerchantEmail());
                objectNode.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
                ArrayNode arrayNode = mapper.createArrayNode();
                arrayNode.add(dfsVoomaOnboardEntity.getLongitude());
                arrayNode.add(dfsVoomaOnboardEntity.getLatitude());
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
    public boolean createAsset(String assetDetails, MultipartFile[] assetFiles) {
        try {
            if (assetDetails == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            DFSVoomaAddAssetRequest acquiringAddAssetRequest =
                    mapper.readValue(assetDetails, DFSVoomaAddAssetRequest.class);
            DFSVoomaAssetEntity dfsVoomaAssetEntity = new DFSVoomaAssetEntity();
            dfsVoomaAssetEntity.setSerialNumber(acquiringAddAssetRequest.getSerialNumber());
            dfsVoomaAssetEntity.setAssetCondition(acquiringAddAssetRequest.getAssetCondition());
            dfsVoomaAssetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            DFSVoomaAssetEntity savedAsset = dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);
            String subFolder = "vooma-assets";

            List<String> filePathList = new ArrayList<>();
            //save files

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileName("Asset_", assetFiles);
            //save file paths to db
            filePathList.forEach(filePath -> {
                DFSVoomaAssetFilesEntity dfsVoomaAssetFilesEntity = new DFSVoomaAssetFilesEntity();
                dfsVoomaAssetFilesEntity.setDfsVoomaAssetEntity(savedAsset);
                dfsVoomaAssetFilesEntity.setFilePath(filePath);
                dfsVoomaAssetFilesRepository.save(dfsVoomaAssetFilesEntity);
            });
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
                for (DFSVoomaAssetEntity dfsVoomaAssetEntity : dfsVoomaAssetRepository.findAll()) {
                    ObjectNode objectNode = mapper.createObjectNode();
                   objectNode.put("id", dfsVoomaAssetEntity.getId());
                    objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
                    objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().ordinal());
                    objectNode.put("createdOn", dfsVoomaAssetEntity.getCreatedOn().getTime());
                    objectNode.put("assigned", dfsVoomaAssetEntity.isAssigned());
                    objectNode.put("lastAssigned", dfsVoomaAssetEntity.getLastServiceDate().getTime());
                    list.add(objectNode);
                }
                return list;
            } catch (Exception e) {
                log.error("Error occurred while getting all targets", e);
            }
        return null;
    }


}



