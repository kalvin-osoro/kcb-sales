package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyAssetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

@Slf4j
@Service
public class VoomaPortalService implements IVoomaPortalService {
    @Autowired
    private DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    QuestionResponseRepository questionResponseRepository;
    @Autowired
    private DFSVoomaOnboardingKYRepository dfsVoomaOnboardingKYRepository;
    @Autowired
    private DFSVoomaAgentOnboardV1Repository dfsVoomaAgentOnboardV1Repository;
    @Autowired
    private DFSVoomaMerchantOnboardV1Repository dfsVoomaMerchantOnboardV1Repository;
    @Autowired
    private DFSVoomaContactDetailsRepository dfsVoomaContactDetailsRepository;
    @Autowired
    private DFSVoomaOwnerDetailsRepository dfsVoomaOwnerDetailsRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionRepository questionRepository;
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

    //variable of todays date
    private String today = Utility.getTodayDate();


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
            dfsVoomaLeadEntity.setAssigned(true);
            //save
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
            //update is assigned to true

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
                objectNode.put("dsrName", dfsVoomaLeadEntity.getDsrName());
                objectNode.put("product", dfsVoomaLeadEntity.getProduct());
                objectNode.put("createdOn", dfsVoomaLeadEntity.getCreatedOn().getTime());
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
            DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity = dfsVoomaMerchantOnboardV1Repository.findById(dfsVoomaApproveMerchantOnboarindRequest.getCustomerId()).get();
            dfsVoomaOnboardEntity.setOnboardingStatus(OnboardingStatus.APPROVED);
            dfsVoomaOnboardEntity.setApproved(true);
            dfsVoomaOnboardEntity.setRemarks(dfsVoomaApproveMerchantOnboarindRequest.getRemarks());
            dfsVoomaMerchantOnboardV1Repository.save(dfsVoomaOnboardEntity);
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


            DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity = dfsVoomaMerchantOnboardV1Repository.findById(model.getMerchantId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", dfsVoomaOnboardEntity.getId());
            objectNode.put("businessName", dfsVoomaOnboardEntity.getBusinessName());
//            objectNode.put("region", dfsVoomaOnboardEntity.getCityOrTown());
            objectNode.put("phoneNumber", dfsVoomaOnboardEntity.getOutletPhoneNumber());
            objectNode.put("businessEmail", dfsVoomaOnboardEntity.getBusinessEmailAddress());
            objectNode.put("status", dfsVoomaOnboardEntity.getOnboardingStatus().toString());
            objectNode.put("remarks", dfsVoomaOnboardEntity.getRemarks());
            objectNode.put("branchName", dfsVoomaOnboardEntity.getBranchName());
            objectNode.put("accountName", dfsVoomaOnboardEntity.getAccountName());
            objectNode.put("accountNumber", dfsVoomaOnboardEntity.getAccountNumber());
            objectNode.put("settlementType", dfsVoomaOnboardEntity.getSettlmentType().toString());
//            objectNode.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
            objectNode.put("createdOn", dfsVoomaOnboardEntity.getCreatedOn().getTime());
            ObjectNode cordinates = mapper.createObjectNode();
            cordinates.put("latitude", dfsVoomaOnboardEntity.getLatitude());
            cordinates.put("longitude", dfsVoomaOnboardEntity.getLongitude());
            objectNode.set("cordinates", cordinates);
            ObjectNode businessDetails = mapper.createObjectNode();
            businessDetails.put("businessName", dfsVoomaOnboardEntity.getTradingName());
            businessDetails.put("nearbyLandMark", dfsVoomaOnboardEntity.getNearestLandmark());
            businessDetails.put("pobox", dfsVoomaOnboardEntity.getPostalAddress());
            businessDetails.put("KRAPin", dfsVoomaOnboardEntity.getBusinessKraPin());
            businessDetails.put("postalCode", dfsVoomaOnboardEntity.getPostalCode());
            businessDetails.put("natureOfBusiness", dfsVoomaOnboardEntity.getNatureOfBusiness());
            businessDetails.put("VATNumber", dfsVoomaOnboardEntity.getVATNumber());
            businessDetails.put("voomaTill", dfsVoomaOnboardEntity.getWantTillNumber());
            businessDetails.put("voomaPaybill", dfsVoomaOnboardEntity.getWantPaybillNumber());
            businessDetails.put("city", dfsVoomaOnboardEntity.getCityOrTown());
            objectNode.set("businessDetails", businessDetails);
            List<DFSVoomaContactDetailsEntity> dfsVoomaContactPersonList = dfsVoomaContactDetailsRepository.findByMerchantId(model.getMerchantId());
            ArrayNode contactPersonArray = mapper.createArrayNode();
            for (DFSVoomaContactDetailsEntity dfsVoomaContactPerson : dfsVoomaContactPersonList) {
                ObjectNode contactPerson = mapper.createObjectNode();
                contactPerson.put("id", dfsVoomaContactPerson.getId());
                contactPerson.put("contactPersonName", dfsVoomaContactPerson.getContactPersonName());
                contactPerson.put("contactPersonNameEmail", dfsVoomaContactPerson.getContactPersonEmailAddress());
                contactPerson.put("contactPersonNamePhoneNumber", dfsVoomaContactPerson.getContactPersonPhoneNumber());
                contactPerson.put("contactPersonNameiIdNumber", dfsVoomaContactPerson.getContactPersonIdNumber());
                contactPerson.put("contactPersonNameIdType", dfsVoomaContactPerson.getContactPersonIdType());
                //
                contactPerson.put("financeContactPersonName", dfsVoomaContactPerson.getFinancialContactPersonIdType());
                contactPerson.put("financeContactPersonPhone", dfsVoomaContactPerson.getFinancialContactPersonPhoneNumber());
                contactPerson.put("financeContactPersonEmail", dfsVoomaContactPerson.getFinancialContactPersonEmailAddress());
                contactPerson.put("financeContactPersonIdNumber", dfsVoomaContactPerson.getFinancialContactPersonIdNumber());
                contactPerson.put("financeContactPersonIdType", dfsVoomaContactPerson.getFinancialContactPersonIdType());
                //administrative
                contactPerson.put("nameOfAdministrator", dfsVoomaContactPerson.getAdministrativeContactPersonName());
                contactPerson.put("administratorPhone", dfsVoomaContactPerson.getAdministrativeContactPersonPhoneNumber());
                contactPerson.put("administratorEmail", dfsVoomaContactPerson.getAdministrativeContactPersonEmailAddress());
                contactPerson.put("administratorIdType", dfsVoomaContactPerson.getAdministrativeContactPersonIdType());
                contactPerson.put("administratorIdNumber", dfsVoomaContactPerson.getAdministrativeContactPersonIdNumber());


                contactPersonArray.add(contactPerson);
            }
            objectNode.put("contactPerson", contactPersonArray);
            //
            List<DFSVoomaOwnerDetailsEntity> dfsVoomaOwnerDetailsEntities = dfsVoomaOwnerDetailsRepository.findByMerchantId(model.getMerchantId());
            ArrayNode ownerOrDirector = mapper.createArrayNode();
            for (DFSVoomaOwnerDetailsEntity dfsVoomaOwnerDetailsEntity : dfsVoomaOwnerDetailsEntities) {
                ObjectNode owner = mapper.createObjectNode();
                owner.put("id", dfsVoomaOwnerDetailsEntity.getId());
                owner.put("fullName", dfsVoomaOwnerDetailsEntity.getFullName());
                owner.put("phoneNumber", dfsVoomaOwnerDetailsEntity.getPhoneNumber());
                owner.put("email", dfsVoomaOwnerDetailsEntity.getEmailAddress());
                owner.put("idNumber", dfsVoomaOwnerDetailsEntity.getIdNumber());
                owner.put("idType", dfsVoomaOwnerDetailsEntity.getIdType());
                ownerOrDirector.add(owner);
            }
            objectNode.put("ownerDirector", ownerOrDirector);
            List<DFSVoomaOnboardingKYCentity> dfsVoomaFileUploadEntities = dfsVoomaOnboardingKYRepository.findByMerchantId(model.getMerchantId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (DFSVoomaOnboardingKYCentity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                fileUpload.put("fileName", dfsVoomaFileUploadEntity.getFileName());
                fileUploads.add(fileUpload);
            }
            objectNode.put("fileUploads", fileUploads);
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
                objectNode.put("targetAssignedTeam", dfsVoomaTargetEntity.getTargetAssignedTeam());
                objectNode.put("targetAssignedDSR", dfsVoomaTargetEntity.getTargetAssignedDSR());
                objectNode.put("createdOn", dfsVoomaTargetEntity.getCreatedOn() ==null ? null : dfsVoomaTargetEntity.getCreatedOn().getTime());
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
    public List<ObjectNode> loadAllApprovedMerchants() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity : dfsVoomaMerchantOnboardV1Repository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaOnboardEntity.getId());
                objectNode.put("businessName", dfsVoomaOnboardEntity.getBusinessName());
                objectNode.put("region", dfsVoomaOnboardEntity.getCityOrTown());
                objectNode.put("status", dfsVoomaOnboardEntity.getOnboardingStatus().toString());
                objectNode.put("dateOnborded", dfsVoomaOnboardEntity.getCreatedOn().getTime());
                objectNode.put("payBillNo", Utility.generateRandomNumber());
                objectNode.put("tillNo", Utility.generateRandomNumber());
                objectNode.put("phoneNumber", dfsVoomaOnboardEntity.getOutletPhoneNumber());
                objectNode.put("email", dfsVoomaOnboardEntity.getBusinessEmailAddress());
//                objectNode.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
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

            DFSVoomaAddAssetRequest dfsVoomaAddAssetRequest = mapper.readValue(assetDetails, DFSVoomaAddAssetRequest.class);
            DFSVoomaAssetEntity dfsVoomaAssetEntity = new DFSVoomaAssetEntity();
            dfsVoomaAssetEntity.setSerialNumber(dfsVoomaAddAssetRequest.getSerialNumber());
//            dfsVoomaAssetEntity.setAssetCondition(dfsVoomaAddAssetRequest.getAssetCondition());
            dfsVoomaAssetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaAssetEntity.setAssetNumber(dfsVoomaAddAssetRequest.getAssetNumber());
            dfsVoomaAssetEntity.setAssetType(dfsVoomaAddAssetRequest.getAssetType());
//            dfsVoomaAddAssetRequest.setDeviceId(dfsVoomaAddAssetRequest.getDeviceId());
            DFSVoomaAssetEntity savedAsset = dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);

            List<String> filePathList = new ArrayList<>();

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileNameV("VoomaAsset" , assetFiles,Utility.getSubFolder());
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/"+Utility.getSubFolder()+"/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);;
                //save to db
                DFSVoomaAssetFilesEntity dfsVoomaAssetFilesEntity = new DFSVoomaAssetFilesEntity();
                dfsVoomaAssetFilesEntity.setDfsVoomaAssetEntity(dfsVoomaAssetEntity);
                dfsVoomaAssetFilesEntity.setFilePath(downloadUrl);
                dfsVoomaAssetFilesEntity.setFileName(filePath);
                dfsVoomaAssetFilesEntity.setIdAsset(savedAsset.getId());
                dfsVoomaAssetFilesRepository.save(dfsVoomaAssetFilesEntity);

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
            for (DFSVoomaAssetEntity dfsVoomaOnboardEntity : dfsVoomaAssetRepository.findAll()) {
                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", dfsVoomaOnboardEntity.getId());
                asset.put("condition", dfsVoomaOnboardEntity.getAssetCondition().toString());
                asset.put("serialNo", dfsVoomaOnboardEntity.getSerialNumber());
                asset.put("createdOn", dfsVoomaOnboardEntity.getSerialNumber());
                asset.put("dateAssigned", dfsVoomaOnboardEntity.getDateAssigned() == null ? null :dfsVoomaOnboardEntity.getDateAssigned().getTime());
                asset.put("dsrId", dfsVoomaOnboardEntity.getDsrId());
                asset.put("visitDate", dfsVoomaOnboardEntity.getVisitDate());
                asset.put("location", dfsVoomaOnboardEntity.getLocation());
                asset.put("merchantName", dfsVoomaOnboardEntity.getMerchantName());
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
    public List<ObjectNode> getAllMerchantOnboardings() {
        try {

            List<ObjectNode> list = new ArrayList<>();
            List<DFSVoomaMerchantOnboardV1> dfsVoomaOnboardEntityList = dfsVoomaMerchantOnboardV1Repository.findAll();
            for (DFSVoomaMerchantOnboardV1 entity : dfsVoomaOnboardEntityList) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                node.put("id", entity.getId());
                node.put("businessName", entity.getBusinessName());
                node.put("cityOrTown", entity.getCityOrTown());
                node.put("status", entity.getOnboardingStatus().toString());
                node.put("dateOnborded", entity.getCreatedOn().getTime());
                node.put("phoneNumber", entity.getOutletPhoneNumber());
                node.put("email", entity.getOutletPhoneNumber());
                node.put("dsrName", entity.getDsrName());
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
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = dfsVoomaCustomerVisitRepository.findById(model.getVisitId()).orElse(null);

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
                user.setVoomaTargetId(model.getTargetId());
                user.setTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                user.setLeadsTargetValue(model.getTargetValue());
                user.setVoomaTargetId(model.getTargetId());
                user.setTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                user.setVisitsTargetValue(model.getTargetValue());
                user.setVoomaTargetId(model.getTargetId());
                user.setTargetValue(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                user.setOnboardTargetValue(model.getTargetValue());
                user.setVoomaTargetId(model.getTargetId());
                user.setTargetValue(model.getTargetValue());
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
                target.setTargetAssignedTeam(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.LEADS)) {
                teamEntity.setLeadsTargetValue(model.getTargetValue());
                target.setTargetAssignedTeam(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                teamEntity.setVisitsTargetValue(model.getTargetValue());
                target.setTargetAssignedTeam(model.getTargetValue());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                teamEntity.setOnboardTargetValue(model.getTargetValue());
                target.setTargetAssignedTeam(model.getTargetValue());
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
            DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity = dfsVoomaMerchantOnboardV1Repository.findById(model.getCustomerId()).get();
            dfsVoomaOnboardEntity.setOnboardingStatus(OnboardingStatus.REJECTED);
            dfsVoomaOnboardEntity.setApproved(false);
            dfsVoomaOnboardEntity.setRemarks(model.getRemarks());
            dfsVoomaMerchantOnboardV1Repository.save(dfsVoomaOnboardEntity);
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
            for (DFSVoomaMerchantOnboardV1 dfsVoomaOnboardEntity : dfsVoomaMerchantOnboardV1Repository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode node = mapper.createObjectNode();
                node.put("latitude", dfsVoomaOnboardEntity.getLatitude());
                node.put("longititude", dfsVoomaOnboardEntity.getLongitude());
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
            for (DFSVoomaAgentOnboardV1 dfsVoomaOnboardEntity : dfsVoomaAgentOnboardV1Repository.findAllByIsApproved()) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode node = mapper.createObjectNode();
                node.put("latitude", dfsVoomaOnboardEntity.getLatitude());
                node.put("longititude", dfsVoomaOnboardEntity.getLongitude());
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
    public boolean addQuestionnaire(QuestionnaireRequest model) {
        try {
            if (model == null) {
                return false;
            }
            //check if questionnaire of same questionnaireType already exists and it is status is active
            QuestionnaireEntity questionnaireEntity1 = questionnaireRepository.findByQuestionnaireTypeAndStatus(model.getQuestionnaireType(), Status.ACTIVE);
            if (questionnaireEntity1 != null) {
                log.error("Questionnaire of same type already exists");
                return false;
            }
            QuestionnaireEntity questionnaireEntity = new QuestionnaireEntity();
            questionnaireEntity.setQuestionnaireTitle(model.getQuestionnaireTitle());
            questionnaireEntity.setQuestionnaireType(model.getQuestionnaireType());
            questionnaireEntity.setQuestionnaireDesc(model.getQuestionnaireDesc());
            questionnaireEntity.setProfileCode(model.getProfileCode());
            questionnaireEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            questionnaireRepository.save(questionnaireEntity);

            List<QuestionRequest> questionRequestList = model.getQuestionRequests();
            for (QuestionRequest questionRequest : questionRequestList) {
                QuestionEntity questionEntity = new QuestionEntity();
                questionEntity.setQuestion(questionRequest.getQuestion());
                questionEntity.setQuestionType(questionRequest.getQuestionType());
                questionEntity.setQuestionnaireEntity(questionnaireEntity);
                questionnaireEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                questionRepository.save(questionEntity);
            }
            return true;

        } catch (Exception e) {
            log.error("Error occurred while adding concession", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllAllQuestionnaireV1() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (QuestionnaireEntity questionnaireEntity : questionnaireRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", questionnaireEntity.getId());
                objectNode.put("questionnaireTitle", questionnaireEntity.getQuestionnaireTitle());
                objectNode.put("questionnaireType", questionnaireEntity.getQuestionnaireType().toString());
                objectNode.put("status", questionnaireEntity.getStatus().toString());
                objectNode.put("createdOn", questionnaireEntity.getCreatedOn().getTime());

                List<QuestionEntity> questionEntityList = questionnaireEntity.getQuestionEntitySet();
                ArrayNode arrayNode = mapper.createArrayNode();
                for (QuestionEntity questionEntity : questionEntityList) {
                    ObjectNode node = mapper.createObjectNode();
                    node.put("id",questionEntity.getId());
                    node.put("question", questionEntity.getQuestion());
                    node.put("questionType", questionEntity.getQuestionType());
                    arrayNode.add(node);
                }
                objectNode.put("questions", arrayNode);
                list.add(objectNode);

            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all assets", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getOnboardingSummaryv1() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("name", "Successful Onboarding");
            objectNode.put("total", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime());
            node.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node.get("value").asInt() == 0) {
                node.put("value", 0);
            }
            arrayNode.add(node);
            ObjectNode node1 = mapper.createObjectNode();
            node1.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000);
            node1.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node1.get("value").asInt() == 0) {
                node1.put("value", 0);
            }
            arrayNode.add(node1);
            ObjectNode node2 = mapper.createObjectNode();
            node2.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 2);
            node2.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node2.get("value").asInt() == 0) {
                node2.put("value", 0);
            }
            arrayNode.add(node2);
            ObjectNode node3 = mapper.createObjectNode();
            node3.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 3);
            node3.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node3.get("value").asInt() == 0) {
                node3.put("value", 0);
            }
            arrayNode.add(node3);
            ObjectNode node4 = mapper.createObjectNode();
            node4.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 4);
            node4.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node4.get("value").asInt() == 0) {
                node4.put("value", 0);
            }
            arrayNode.add(node4);
            ObjectNode node5 = mapper.createObjectNode();
            node5.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 5);
            node5.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node5.get("value").asInt() == 0) {
                node5.put("value", 0);
            }
            arrayNode.add(node5);
            ObjectNode node6 = mapper.createObjectNode();
            node6.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 6);
            node6.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.APPROVED));
            if (node6.get("value").asInt() == 0) {
                node6.put("value", 0);
            }
            arrayNode.add(node6);
            objectNode.put("series", arrayNode);
            list.add(objectNode);

            ObjectNode objectNode1 = mapper.createObjectNode();
            objectNode1.put("name", "Failed Onboarding");
            objectNode1.put("total", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            ArrayNode arrayNode1 = mapper.createArrayNode();
            ObjectNode node7 = mapper.createObjectNode();
            node7.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime());
            node7.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node7.get("value").asInt() == 0) {
                node7.put("value", 0);
            }
            arrayNode1.add(node7);
            ObjectNode node8 = mapper.createObjectNode();
            node8.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000);
            node8.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node8.get("value").asInt() == 0) {
                node8.put("value", 0);
            }
            arrayNode1.add(node8);
            ObjectNode node9 = mapper.createObjectNode();
            node9.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 2);
            node9.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node9.get("value").asInt() == 0) {
                node9.put("value", 0);
            }
            arrayNode1.add(node9);
            ObjectNode node10 = mapper.createObjectNode();
            node10.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 3);
            node10.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node10.get("value").asInt() == 0) {
                node10.put("value", 0);
            }
            arrayNode1.add(node10);
            ObjectNode node11 = mapper.createObjectNode();
            node11.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 4);
            node11.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node11.get("value").asInt() == 0) {
                node11.put("value", 0);
            }
            arrayNode1.add(node11);
            ObjectNode node12 = mapper.createObjectNode();
            node12.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 5);
            node12.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node12.get("value").asInt() == 0) {
                node12.put("value", 0);
            }
            arrayNode1.add(node12);
            ObjectNode node13 = mapper.createObjectNode();
            node13.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 6);
            node13.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.REJECTED));
            if (node13.get("value").asInt() == 0) {
                node13.put("value", 0);
            }
            arrayNode1.add(node13);
            objectNode1.put("series", arrayNode1);
            list.add(objectNode1);

            //pending onboarding
            ObjectNode objectNode2 = mapper.createObjectNode();
            objectNode2.put("name", "Pending Onboarding");
            objectNode2.put("total", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            ArrayNode arrayNode2 = mapper.createArrayNode();
            ObjectNode node14 = mapper.createObjectNode();
            node14.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime());
            node14.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node14.get("value").asInt() == 0) {
                node14.put("value", 0);
            }
            arrayNode2.add(node14);
            ObjectNode node15 = mapper.createObjectNode();
            node15.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000);
            node15.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node15.get("value").asInt() == 0) {
                node15.put("value", 0);
            }
            arrayNode2.add(node15);
            ObjectNode node16 = mapper.createObjectNode();
            node16.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 2);
            node16.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node16.get("value").asInt() == 0) {
                node16.put("value", 0);
            }
            arrayNode2.add(node16);
            ObjectNode node17 = mapper.createObjectNode();
            node17.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 3);
            node17.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node17.get("value").asInt() == 0) {
                node17.put("value", 0);
            }
            arrayNode2.add(node17);
            ObjectNode node18 = mapper.createObjectNode();
            node18.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 4);
            node18.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node18.get("value").asInt() == 0) {
                node18.put("value", 0);
            }
            arrayNode2.add(node18);
            ObjectNode node19 = mapper.createObjectNode();
            node19.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 5);
            node19.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node19.get("value").asInt() == 0) {
                node19.put("value", 0);
            }
            arrayNode2.add(node19);
            ObjectNode node20 = mapper.createObjectNode();
            node20.put("date", Utility.getPostgresCurrentTimeStampForInsert().getTime() - 86400000 * 6);
            node20.put("value", dfsVoomaOnboardRepository.countByStatusForLast7Days(OnboardingStatus.PENDING));
            if (node20.get("value").asInt() == 0) {
                node20.put("value", 0);
            }
            arrayNode2.add(node20);
            objectNode2.put("series", arrayNode2);
            list.add(objectNode2);
            return list;
        } catch (Exception e) {
            log.error("Exception in getOnboardingStatusForLast7Days() method", e);

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
            DFSVoomaAssetEntity acquiringOnboardEntity = dfsVoomaAssetRepository.findById(model.getAssetId()).orElseThrow(() -> new ResourceNotFoundException("asset","id", model.getAssetId()));
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
            List<DFSVoomaAssetFilesEntity> dfsVoomaFileUploadEntities = dfsVoomaAssetFilesRepository.findByIdAsset(model.getAssetId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (DFSVoomaAssetFilesEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
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
    public List<ObjectNode> getQuestionnaireResponses(GetRQuestionnaireRequest model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVOOMAQuestionerResponseEntity questionerResponse : questionResponseRepository.findByQuestionnaireId(model.getQuestionnaireId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                ObjectNode node = mapper.createObjectNode();
                node.put("id", questionerResponse.getId());
                node.put("response", questionerResponse.getResponse());
                node.put("question", questionerResponse.getQuestion());
                node.put("respodent", questionerResponse.getCustomerName());
                node.put("nationalId", questionerResponse.getNationalId());
                node.put("accountNumber", questionerResponse.getAccountNo());
                Long questionnaireId = questionerResponse.getQuestionnaireId();
                QuestionnaireEntity questionnaireEntity = questionnaireRepository.findById(questionnaireId).get();
                node.put("questionnaireTitle", questionnaireEntity.getQuestionnaireTitle());
                node.put("createdOn", questionerResponse.getCreatedOn().getTime());
                list.add(node);
            }
            return list;
        } catch (Exception ex) {
            log.error("something went,try again later");
        }
        return null;
    }

    @Override
    public boolean disableQuestionnaire(GetRQuestionnaireRequest model) {
        try {
            if (model == null) {
                return false;
            }
            QuestionnaireEntity questionnaireEntity = questionnaireRepository.findById(model.getQuestionnaireId()).get();
            questionnaireEntity.setStatus(Status.INACTIVE);
            questionnaireRepository.save(questionnaireEntity);
            return true;
        } catch (Exception e) {
            log.error("something went wrong,try again later");
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
//            List<DSRAccountEntity>list = dsrAccountsRepository.findByVoomaTargetId(model.getTargetId());
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
                return list;
            }
        } catch (Exception e) {
            log.error("Error occurred while getting dsr in target", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> loadApprovedAgent() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaAgentOnboardV1 voomaAgentOnboardV1 : dfsVoomaAgentOnboardV1Repository.findByOnboardingStatus(OnboardingStatus.APPROVED)) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", voomaAgentOnboardV1.getId());
                objectNode.put("agentName", voomaAgentOnboardV1.getAccountName());
                objectNode.put("email", voomaAgentOnboardV1.getBusinessEmail());
                objectNode.put("paybill", Utility.generateRandomNumber());
                objectNode.put("till", Utility.generateRandomNumber());
                objectNode.put("createdOn", voomaAgentOnboardV1.getCreatedOn().getTime());
                objectNode.put("region", voomaAgentOnboardV1.getCityOrTown());
                list.add(objectNode);
                return list;
        }
        return null;
    } catch (Exception e) {
            log.error("Error occurred while getting approved agents", e);
        }
        return null;
    }

    @Override
    public boolean assignFeedback(AssignFeedBackRequest model) {
        try {
            if (model==null){
                return false;
            }
            QuestionnaireEntity questionnaireEntity = questionnaireRepository.findById(model.getQuestionnaireId()).get();
            questionnaireEntity.setSalesPersonName(model.getSalesPersonName());
            questionnaireEntity.setDuration(model.getDuration());
            questionnaireEntity.setPriority(model.getPriority());
            questionnaireEntity.setAssignedOn(Utility.getPostgresCurrentTimeStampForInsert());
            questionnaireEntity.setFeedbackAssigned(true);
            //TODO:Send EscalationEmail if not atteded
            questionnaireRepository.save(questionnaireEntity);

            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning feedback", e);
        }
        return false;
    }


    public List<Map<String, Object>> getKycData(DFSVoomaMerchantOnboardV1 customerDetails) {
        Map<String, Object> resp = new LinkedHashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        customerDetails.getMerchantKYCList().stream().map(x -> new File(x.getFilePath()).getName()).forEach(x -> resp.put(x.split("_")[0], x));
        data.add(resp);
        return data;
    }

    

    private void sendEmail(String salesPersonName, Long questionnaireId, String priority, String escalationEmail) {
        try {
            //send email
            String subject = "Escalation";
            String message = "Dear " + salesPersonName + ",\n" +
                    "You have been assigned a feedback with priority " + priority + " and questionnaire id " + questionnaireId + ".\n" +
                    "Please respond to the feedback as soon as possible.\n" +
                    "Thank you.";
            javaMailSender.send(new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                    mimeMessageHelper.setFrom("");
                    mimeMessageHelper.setTo(escalationEmail);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(message);
                }
            });
        } catch (Exception e) {
            log.error("Error occurred while sending email", e);
        }
    }
    }












