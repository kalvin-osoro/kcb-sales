package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingFeedBackEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.*;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.*;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PSPortalService implements IPSPortalService {
    private final PSQuestionnaireQuestionRepository psQuestionnaireQuestionRepository;
    private final PSBankingConvenantRepository psBankingConvenantRepository;
    private final PSLeadRepository psLeadRepository;

    private final IDSRAccountsRepository dsrAccountsRepository;
    private final IDSRTeamsRepository dsrTeamsRepository;
    private final PSTargetRepository psTargetRepository;
    private  final PSFeedBackRepository psFeedBackRepository;
    private final PSCustomerVisitRepository psCustomerVisitRepository;
    private  final PSQuestionerResponseRepository psQuestionerResponseRepository;
    private final PSOnboardingRepository psOnboardingRepository;

    @Override
    public boolean addTrackedCovenant(PSAddConvenantRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingConvenantEntity psBankingConvenantEntity = new PSBankingConvenantEntity();
               psBankingConvenantEntity.setCustomerId(model.getCustomerId());
               psBankingConvenantEntity.setEndDate(model.getEndDate());
               psBankingConvenantEntity.setIntervalForCheck(model.getIntervalForCheck());
               psBankingConvenantEntity.setAlertMessage(model.getAlertMessage());
               psBankingConvenantEntity.setDsrId(model.getDsrId());
               psBankingConvenantEntity.setAlertBeforeExpiry(model.getAlertBeforeExpiry());

            psBankingConvenantEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psBankingConvenantRepository.save(psBankingConvenantEntity);
            return true;
        } catch (Exception e) {
            log.error("Error adding tracked covenant", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllTrackedCovenants() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingConvenantEntity psBankingConvenantEntity : psBankingConvenantRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("customerId", psBankingConvenantEntity.getCustomerId());
                ObjectNode period = mapper.createObjectNode();
                period.put("startDate", psBankingConvenantEntity.getStartDate());
                period.put("endDate", psBankingConvenantEntity.getEndDate());
                objectNode.set("period", period);
                objectNode.put("intervalForCheck", psBankingConvenantEntity.getIntervalForCheck());
                objectNode.put("dsrId", psBankingConvenantEntity.getDsrId());
                objectNode.put("status", psBankingConvenantEntity.getStatus().toString());
                objectNode.put("createdOn", psBankingConvenantEntity.getCreatedOn().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error getting all tracked covenants", e);
        }

        return null;
    }

    @Override
    public boolean assignLead(PSAddLeadRequest model) {
        try {
            PSLeadEntity psLeadEntity = psLeadRepository.findById(model.getDsrId()).orElse(null);
            psLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            psLeadEntity.setStartDate(model.getStartDate());
            psLeadEntity.setEndDate(model.getEndDate());
            //save
            psLeadRepository.save(psLeadEntity);
            //update is assigned to true
            psLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSLeadEntity psLeadEntity : psLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psLeadEntity.getId());
                objectNode.put("customerId", psLeadEntity.getCustomerId());
                objectNode.put("businessUnit", psLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", psLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", psLeadEntity.getTopic());
                objectNode.put("priority", psLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", psLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error getting all leads", e);
        }
        return null;
    }

    @Override
    public boolean scheduleCustomerVisit(PSCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSCustomerVisitEntity psCustomerVisitEntity = new PSCustomerVisitEntity();
            psCustomerVisitEntity.setCustomerName(model.getCustomerName());
            psCustomerVisitEntity.setVisitDate(model.getVisitDate());
            psCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psCustomerVisitEntity.setStatus(Status.ACTIVE);
            psCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psCustomerVisitEntity.setDsrName(model.getDsrName());
            //save
            psCustomerVisitRepository.save(psCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean rescheduleCustomerVisit(PSCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSCustomerVisitEntity psCustomerVisitEntity = psCustomerVisitRepository.findById(model.getId()).orElse(null);
            if (psCustomerVisitEntity == null) {
                return false;
            }
            psCustomerVisitEntity.setVisitDate(model.getVisitDate());
            psCustomerVisitRepository.save(psCustomerVisitEntity);
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
            for (PSCustomerVisitEntity psCustomerVisitEntity : psCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psCustomerVisitEntity.getId());
                objectNode.put("customerName", psCustomerVisitEntity.getCustomerName());
                objectNode.put("visitDate", psCustomerVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", psCustomerVisitEntity.getReasonForVisit());
                objectNode.put("status", psCustomerVisitEntity.getStatus().toString());
                objectNode.put("createdOn", psCustomerVisitEntity.getCreatedOn().toString());
                objectNode.put("dsrName", psCustomerVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponses(PSCustomerVisitQuestionnaireRequest model) {
        try {//get question Response by visit id and question id
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSQuestionerResponseEntity psQuestionerResponseEntity : psQuestionerResponseRepository.findAllByVisitIdAndQuestionId(model.getVisitId(), model.getQuestionId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psQuestionerResponseEntity.getId());
                objectNode.put("questionId", psQuestionerResponseEntity.getQuestionId());
                objectNode.put("questionResponse", psQuestionerResponseEntity.getResponse());
                objectNode.put("visitId", psQuestionerResponseEntity.getVisitId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting customer visit questionnaire responses", e);
        }
        return null;
    }

    @Override
    public boolean createQuestionnaire(AcquiringAddQuestionnaireRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSQuestionnaireQuestionEntity psQuestionnaireQuestionEntity = new PSQuestionnaireQuestionEntity();
            psQuestionnaireQuestionEntity.setQuestion(model.getQuestion());
            psQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());
            psQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());
            psQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psQuestionnaireQuestionRepository.save(psQuestionnaireQuestionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating questionnaire", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadQuestionnaires() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSQuestionnaireQuestionEntity psQuestionnaireQuestionEntity : psQuestionnaireQuestionRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psQuestionnaireQuestionEntity.getQuestionnaireId());
                objectNode.put("question", psQuestionnaireQuestionEntity.getQuestion());
                objectNode.put("questionnaireDescription", psQuestionnaireQuestionEntity.getQuestionnaireDescription());
                objectNode.put("createdOn", psQuestionnaireQuestionEntity.getCreatedOn().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllOnboardings() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSOnboardingEntity psOnboardingEntity : psOnboardingRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", psOnboardingEntity.getId());
                node.put("customerName", psOnboardingEntity.getMerchantName());
                node.put("Region", psOnboardingEntity.getRegion());
                node.put("phoneNumber", psOnboardingEntity.getMerchantPhone());
                node.put("email", psOnboardingEntity.getMerchantEmail());
                node.put("status", psOnboardingEntity.getStatus().ordinal());
                node.put("agent Id", psOnboardingEntity.getDsrId());
                node.put("createdOn", psOnboardingEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading merchant", e);
        }


        return null;
    }

    @Override
    public boolean approveOnboarding(PSApproveMerchantOnboarindRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSOnboardingEntity psOnboardingEntity = psOnboardingRepository.findById(model.getId()).orElse(null);
            if (psOnboardingEntity == null) {
                return false;
            }
            psOnboardingEntity.setStatus(OnboardingStatus.APPROVED);
            psOnboardingEntity.setIsApproved(true);
            psOnboardingRepository.save(psOnboardingEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving onboarding", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerFeedbacks() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSFeedBackEntity psFeedBackEntity : psFeedBackRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psFeedBackEntity.getId());
                objectNode.put("customerId", psFeedBackEntity.getCustomerId());
                objectNode.put("channel", psFeedBackEntity.getChannel());
                objectNode.put("visitRef", psFeedBackEntity.getVisitRef());
                objectNode.put("customerName", psFeedBackEntity.getCustomerName());
                objectNode.put("noOfQuestionAsked", psFeedBackEntity.getNoOfQuestionAsked());
                objectNode.put("createdOn", psFeedBackEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer feedbacks", e);
        }

        return null;
    }

    @Override
    public Object getCustomerFeedbackResponses(PSFeedBackRequest model) {
        try {
            PSFeedBackEntity psFeedBackEntity = psFeedBackRepository.findById(model.getId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", psFeedBackEntity.getId());
            objectNode.put("questionAsked", psFeedBackEntity.getQuestionAsked());
            objectNode.put("response", psFeedBackEntity.getResponse());
            objectNode.put("createdOn", psFeedBackEntity.getCreatedOn().getTime());
            return objectNode;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant by id", e);
        }
        return null;
    }

    @Override
    public boolean createTarget(PSAddTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSTargetEntity psTargetEntity = new PSTargetEntity();
            psTargetEntity.setTargetName(model.getTargetName());
            psTargetEntity.setTargetSource(model.getTargetSource());
            psTargetEntity.setTargetValue(model.getTargetValue());
            psTargetEntity.setTargetType(model.getTargetType());
            psTargetEntity.setTargetType(model.getTargetType());
            psTargetEntity.setTargetDesc(model.getTargetDesc());
            psTargetEntity.setTargetStatus(TargetStatus.ACTIVE);
            psTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psTargetRepository.save(psTargetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating target", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSTargetEntity psTargetEntity : psTargetRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", psTargetEntity.getId());
                node.put("targetName", psTargetEntity.getTargetName());
                node.put("targetSource", psTargetEntity.getTargetSource());
                node.put("TargetType", psTargetEntity.getTargetType().ordinal());
                node.put("targetDesc", psTargetEntity.getTargetDesc());
                node.put("targetStatus", psTargetEntity.getTargetStatus().name());
                node.put("targetValue", psTargetEntity.getTargetValue());
                node.put("createdOn", psTargetEntity.getCreatedOn().toString());
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
    public boolean assignTargetToDSR(DSRTAssignTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRAccountEntity user = dsrAccountsRepository.findById(model.getDsrId()).orElse(null);

            PSTargetEntity target = psTargetRepository.findById(model.getTargetId()).orElse(null);
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

            Set<PSTargetEntity> premiumTarget = (Set<PSTargetEntity>) user.getPremiumTargetEntities();
            premiumTarget.add(target);
            user.setPremiumTargetEntities(premiumTarget);
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

            PSTargetEntity target = psTargetRepository.findById(model.getTargetId()).orElse(null);

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

            Set<PSTargetEntity> premiumTarget = (Set<PSTargetEntity>) teamEntity.getPremiumBankingTargetEntities();
            premiumTarget.add(target);
            teamEntity.setPremiumBankingTargetEntities(premiumTarget);
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
            if (model==null){
                return  false;
            }
            PSTargetEntity psTargetEntity = psTargetRepository.findById(model.getId()).orElse(null);
            return psTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }
}
