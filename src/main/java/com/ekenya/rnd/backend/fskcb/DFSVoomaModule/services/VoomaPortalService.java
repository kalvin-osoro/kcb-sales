package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTargetEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class VoomaPortalService implements IVoomaPortalService {
    @Autowired
    private DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;
    @Autowired
    private DFSVoomaLeadRepository dfsVoomaLeadRepository;

    @Autowired
    private DFSVoomaAgentOnboardingRepository dfsVoomaAgentOnboardingRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private IDSRTeamsRepository idsrTeamsRepository;
    @Autowired
    private DFSVoomaQuestionerResponseRepository dfsVoomaQuestionerResponseRepository;
    @Autowired
    private DFSVoomaQuestionnaireQuestionRepository dfsVoomaQuestionnaireQuestionRepository;
    @Autowired
    private DFSVoomaOnboardRepository dfsVoomaOnboardRepository;
    @Autowired
    private DFSVoomaTargetRepository dfsVoomaTargetRepository;
    @Autowired
    private IDSRAccountsRepository dsrAccountsRepository;
    @Autowired
    private DFSVoomaAssetFilesRepository dfsVoomaAssetFilesRepository;
    @Autowired
    private DFSVoomaAssetRepository dfsVoomaAssetRepository;
    @Autowired
    private DFSVoomaFeedBackRepository dfsVoomaFeedBackRepository;
    @Autowired
    private FileStorageService fileStorageService;


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
            dfsVoomaCustomerVisitEntity.setDsrName(model.getDsrName());

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
            dfsVoomaLeadEntity.setPriority(model.getPriority());
            //set start date from input
//            dfsVoomaLeadEntity.setStartDate(model.getStartDate());
//            dfsVoomaLeadEntity.setEndDate(model.getEndDate());
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
                objectNode.put("leadStatus", dfsVoomaLeadEntity.getLeadStatus().toString());
                objectNode.put("topic", dfsVoomaLeadEntity.getTopic());
                objectNode.put("priority", dfsVoomaLeadEntity.getPriority().toString());
                objectNode.put("dsrId", dfsVoomaLeadEntity.getDsrId());
                objectNode.put("product",dfsVoomaLeadEntity.getProduct());
                objectNode.put("createdOn",dfsVoomaLeadEntity.getCreatedOn().getTime());
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

//    @Override
//    public List<ObjectNode> loadAllOnboardedMerchants( ) {
//       try {
//           List<ObjectNode> list = new ArrayList<>();
//                ObjectMapper mapper = new ObjectMapper();
//                for (DFSVoomaOnboardEntity dfsVoomaMerchantEntity : dfsVoomaOnboardRepository.findAll()) {
//                    ObjectNode objectNode = mapper.createObjectNode();
//                    objectNode.put("id", dfsVoomaMerchantEntity.getId());
//                    objectNode.put("merchantName", dfsVoomaMerchantEntity.getMerchantName());
//                    objectNode.put("region", dfsVoomaMerchantEntity.getRegion());
//                    objectNode.put("phone", dfsVoomaMerchantEntity.getMerchantPhone());
//                    objectNode.put("email", dfsVoomaMerchantEntity.getMerchantEmail());
//                    objectNode.put("status", dfsVoomaMerchantEntity.getStatus().ordinal());
//                    objectNode.put("dsrId", dfsVoomaMerchantEntity.getDsrId());
//                    objectNode.put("createdOn", dfsVoomaMerchantEntity.getCreatedOn().getTime());
//                    list.add(objectNode);
//       }
//        return list;
//       } catch (Exception e) {
//           log.error("Error occurred while loading all onboarded merchants", e);
//       }
//         return null;
//    }

    @Override
    public boolean approveMerchantOnboarding(DFSVoomaApproveMerchantOnboarindRequest dfsVoomaApproveMerchantOnboarindRequest) {
        try {
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = dfsVoomaOnboardRepository.findById(dfsVoomaApproveMerchantOnboarindRequest.getCustomerId()).get();
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
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = dfsVoomaOnboardRepository.findById(model.getMerchantId()).get();
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
            ObjectNode cordinates = mapper.createObjectNode();
            cordinates.put("latitude", dfsVoomaOnboardEntity.getLatitude());
            cordinates.put("longitude", dfsVoomaOnboardEntity.getLongitude());
            objectNode.set("cordinates", cordinates);
            ObjectNode businessDetails=mapper.createObjectNode();
            businessDetails.put("businessName",dfsVoomaOnboardEntity.getTradingName());
            businessDetails.put("physicalLocation",dfsVoomaOnboardEntity.getRegion());
            businessDetails.put("pobox",dfsVoomaOnboardEntity.getMerchantPbox());
            businessDetails.put("postalCode",dfsVoomaOnboardEntity.getMerchantPostalCode());
            businessDetails.put("city",dfsVoomaOnboardEntity.getCity());
            objectNode.set("businessDetails",businessDetails);
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
    public List<ObjectNode> getOnboardingSummary() {
        //get onboarding summary
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaOnboardEntity dfsVoomaOnboardEntity : dfsVoomaOnboardRepository.findAllByCreatedOn()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaOnboardEntity.getId());
                objectNode.put("merchantName", dfsVoomaOnboardEntity.getMerchantName());
                objectNode.put("region", dfsVoomaOnboardEntity.getRegion());
                objectNode.put("status", dfsVoomaOnboardEntity.getStatus().toString());
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
            if (model == null) {
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
    public ArrayNode loadAllApprovedMerchants() {
        try {
            ArrayNode list = objectMapper.createArrayNode();
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
            return mapper.valueToTree(list);
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

            DFSVoomaAddAssetRequest dfsVoomaAddAssetRequest =
                    mapper.readValue(assetDetails, DFSVoomaAddAssetRequest.class);
            DFSVoomaAssetEntity dfsVoomaAssetEntity = new DFSVoomaAssetEntity();
            dfsVoomaAssetEntity.setSerialNumber(dfsVoomaAddAssetRequest.getSerialNumber());
//            dfsVoomaAssetEntity.setAssetCondition(dfsVoomaAddAssetRequest.getAssetCondition());
            dfsVoomaAssetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaAssetEntity.setAssetNumber(dfsVoomaAddAssetRequest.getAssetNumber());
            dfsVoomaAssetEntity.setAssetType(dfsVoomaAddAssetRequest.getAssetType());
            dfsVoomaAddAssetRequest.setDeviceId(dfsVoomaAddAssetRequest.getDeviceId());
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

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", dfsVoomaAssetEntity.getId());
                asset.put("condition", dfsVoomaAssetEntity.getAssetCondition().toString());
                asset.put("sno", dfsVoomaAssetEntity.getSerialNumber());
                asset.put("lastServiceDate", dfsVoomaAssetEntity.getLastServiceDate().getTime());
                asset.put("assigned", dfsVoomaAssetEntity.isAssigned());
                asset.put("deviceId", dfsVoomaAssetEntity.getDeviceId());
                asset.put("assetType", dfsVoomaAssetEntity.getAssetType().ordinal());
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
    public List<ObjectNode> getAllMerchantOnboardings() {
        try {

            List<ObjectNode> list = new ArrayList<>();
            List<DFSVoomaOnboardEntity> dfsVoomaOnboardEntityList = dfsVoomaOnboardRepository.findAll();
            for (DFSVoomaOnboardEntity entity : dfsVoomaOnboardEntityList) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                node.put("id", entity.getId());
                node.put("merchantName", entity.getMerchantName());
                node.put("region", entity.getRegion());
                node.put("status", entity.getStatus().ordinal());
                node.put("dateOnborded", entity.getCreatedOn().getTime());
                node.put("phoneNumber", entity.getMerchantPhone());
                node.put("email", entity.getMerchantEmail());
                node.put("dsrId", entity.getDsrId());
                //list of documents
                ArrayNode arrayNode = mapper.createArrayNode();
                for (DFSVoomaOnboardingKYCentity dfsVoomaOnboardFilesEntity : entity.getKycEntities()) {
                    ObjectNode document = mapper.createObjectNode();
                    document.put("id", dfsVoomaOnboardFilesEntity.getId());
                    document.put("path", dfsVoomaOnboardFilesEntity.getFilePath());
                    arrayNode.add(document);
                }
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting onboarding summary", e);
        }

        return null;
    }
    // List<ObjectNode> list = new ArrayList<>();
    //            List<CBOpportunitiesEntity> cbOpportunitiesEntities = cbOpportunitiesRepository.findAll();
    //            for (CBOpportunitiesEntity cbOpportunitiesEntity : cbOpportunitiesEntities) {
    //                ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object getCustomerVisitById(VoomaCustomerVisitsByIdRequest model) {
        try {
            if (model == null) {
                return null;
            }
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity =
                    dfsVoomaCustomerVisitRepository.findById(model.getVisitId()).orElse(null);

            //  //region,branch,dsrName,customerName,visitDate,latitude,longitude
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", dfsVoomaCustomerVisitEntity.getId());
            objectNode.put("customerName", dfsVoomaCustomerVisitEntity.getCustomerName());
            objectNode.put("dsrName", dfsVoomaCustomerVisitEntity.getDsrName());
            objectNode.put("branch", dfsVoomaCustomerVisitEntity.getBranch());
            objectNode.put("region", dfsVoomaCustomerVisitEntity.getRegion());
            objectNode.put("location", dfsVoomaCustomerVisitEntity.getLocation());
            objectNode.put("visitDate", dfsVoomaCustomerVisitEntity.getVisitDate());
            //object of longitude and latitude
            ObjectNode node = mapper.createObjectNode();
            node.put("latitude", dfsVoomaCustomerVisitEntity.getLongitude());
            node.put("longitude", dfsVoomaCustomerVisitEntity.getLatitude());
            objectNode.put("co-ordinates", node);
            return objectNode;


        } catch (Exception e) {
            log.error("Error occurred while getting customer visit by id", e);
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

            DFSVoomaTargetEntity target = dfsVoomaTargetRepository.findById(model.getTargetId()).orElse(null);
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

            Set<DFSVoomaTargetEntity> dfsVoomaTargetEntities = (Set<DFSVoomaTargetEntity>) user.getDfsVoomaTargetEntities();
            dfsVoomaTargetEntities.add(target);
            user.setDfsVoomaTargetEntities(dfsVoomaTargetEntities);
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
            DSRTeamEntity teamEntity = idsrTeamsRepository.findById(model.getTeamId()).orElse(null);

            DFSVoomaTargetEntity target = dfsVoomaTargetRepository.findById(model.getTargetId()).orElse(null);

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

            Set<DFSVoomaTargetEntity> dfsVoomaTargetEntities = (Set<DFSVoomaTargetEntity>) teamEntity.getDfsVoomaTargetEntities();
            dfsVoomaTargetEntities.add(target);
            teamEntity.setDfsVoomaTargetEntities(dfsVoomaTargetEntities);
            idsrTeamsRepository.save(teamEntity);
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
            DFSVoomaTargetEntity dfsVoomaTargetEntity = dfsVoomaTargetRepository.findById(model.getId()).orElse(null);
            return dfsVoomaTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }

    @Override
    public List<?> getAllOnboardingV2() {
        try {
            List<DFSVoomaOnboardEntity> dfsVoomaOnboardEntityList = dfsVoomaOnboardRepository.findAll();
            List<DFSVoomaOnboardEntity> dfsVoomaOnboardEntityList1 = new ArrayList<>();
            for (DFSVoomaOnboardEntity dfsVoomaOnboardEntity : dfsVoomaOnboardEntityList) {
                dfsVoomaOnboardEntity.getAccountName();
                dfsVoomaOnboardEntity.getMerchantName();
                dfsVoomaOnboardEntity.getRegion();
                dfsVoomaOnboardEntity.getCreatedOn();
                dfsVoomaOnboardEntity.getMerchantPhone();
                dfsVoomaOnboardEntity.getMerchantEmail();
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
    public List<?> fetchAllAssetsV2() {
        try {
            List<DFSVoomaAssetEntity> dfsVoomaAssetEntityList = dfsVoomaAssetRepository.findAll();
            List<DFSVoomaAssetEntity> dfsVoomaAssetEntityList1 = new ArrayList<>();
            for (DFSVoomaAssetEntity dfsVoomaAssetEntity : dfsVoomaAssetEntityList) {
                dfsVoomaAssetEntity.getAgentId();
                dfsVoomaAssetEntity.getCreatedOn();
                dfsVoomaAssetEntity.getAssetType();
                dfsVoomaAssetEntity.getAssetCondition();
                dfsVoomaAssetEntity.getSerialNumber();
                dfsVoomaAssetEntity.getAssetNumber();
                dfsVoomaAssetEntity.getDeviceId();
                dfsVoomaAssetEntityList1.add(dfsVoomaAssetEntity);
            }
            return dfsVoomaAssetEntityList;

        } catch (Exception e) {
            log.error("Error occurred while getting all assets", e);
        }

        return null;
    }

    @Override
    public List<?> fetchAllLeadsV2() {

        //fetch all leadstry
        try {
            List<DFSVoomaLeadEntity> dfsVoomaList = dfsVoomaLeadRepository.findAll();
            List<DFSVoomaLeadEntity> dfsVoomaLeadEntityList = new ArrayList<>();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaList) {
               dfsVoomaLeadEntity.getId();
                dfsVoomaLeadEntity.getCustomerId();
                dfsVoomaLeadEntity.getCustomerName();
                dfsVoomaLeadEntity.getLeadStatus();
                dfsVoomaLeadEntity.getPriority();
                dfsVoomaLeadEntity.getCreatedOn();
                dfsVoomaLeadEntity.getPriority();
                dfsVoomaLeadEntity.getBusinessUnit();
                dfsVoomaLeadEntityList.add(dfsVoomaLeadEntity);
            }
            return dfsVoomaList;

        } catch (Exception e) {
            log.error("Error occurred while getting all assets", e);
        }

        return null;

        }

    @Override
    public Object agentById(VoomaMerchantDetailsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DFSVoomaAgentOnboardingEntity dfsVoomaAgentEntity = dfsVoomaAgentOnboardingRepository.findById(model.getMerchantId()).orElse(null);
            return dfsVoomaAgentEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting agent by id", e);
        }
        return null;
    }

    @Override
    public boolean rejectMerchantOnboarding(DFSVoomaRejectMerchantOnboarindRequest model) {
        try {
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = dfsVoomaOnboardRepository.findById(model.getCustomerId()).get();
            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.REJECTED);
            dfsVoomaOnboardEntity.setIsApproved(false);
            dfsVoomaOnboardEntity.setRemarks(model.getRemarks());
            dfsVoomaOnboardRepository.save(dfsVoomaOnboardEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public ArrayNode getAllApprovedMerchantCoordinates() {
        try {
            ArrayNode list = objectMapper.createArrayNode();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaOnboardEntity dfsVoomaOnboardEntity : dfsVoomaOnboardRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode node = mapper.createObjectNode();
                node.put("latitude",dfsVoomaOnboardEntity.getLatitude());
                node.put("longititude",dfsVoomaOnboardEntity.getLongitude());
                objectNode.put("co-ordinates", node);
                list.add(node);

            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting onboarding summary", e);
        }
        return null;
    }

    @Override
    public boolean rejectAgentOnboarding(DFSVoomaRejectMerchantOnboarindRequest model) {
        try {
            DFSVoomaAgentOnboardingEntity dfsVoomaOnboardEntity = dfsVoomaAgentOnboardingRepository.findById(model.getCustomerId()).get();
            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.REJECTED);
            dfsVoomaOnboardEntity.setApproved(false);
            dfsVoomaOnboardEntity.setRemarks(model.getRemarks());
            dfsVoomaAgentOnboardingRepository.save(dfsVoomaOnboardEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public boolean approveAgentOnboarding(DFSVoomaRejectMerchantOnboarindRequest model) {
        try {
            DFSVoomaAgentOnboardingEntity dfsVoomaOnboardEntity = dfsVoomaAgentOnboardingRepository.findById(model.getCustomerId()).get();
            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.APPROVED);
            dfsVoomaOnboardEntity.setApproved(true);
            dfsVoomaOnboardEntity.setRemarks(model.getRemarks());
            dfsVoomaAgentOnboardingRepository.save(dfsVoomaOnboardEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public ArrayNode getAllApprovedAgentCoordinates() {
        try {
            ArrayNode list = objectMapper.createArrayNode();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaAgentOnboardingEntity dfsVoomaOnboardEntity : dfsVoomaAgentOnboardingRepository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode node = mapper.createObjectNode();
                node.put("latitude",dfsVoomaOnboardEntity.getLatitude());
                node.put("longititude",dfsVoomaOnboardEntity.getLongitude());
                objectNode.put("co-ordinates", node);
                list.add(node);

            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting onboarding summary", e);
        }
        return null;
    }
}









