package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PBPortalService implements IPBPortalService {
    private final PSBankingLeadRepository psBankingLeadRepository;
    private final PSBankingTargetRepository psBankingTargetRepository;
    private final PSBankingOnboardingRepossitory psBankingOnboardingRepository;
    private  final PSBankingQuestionnaireQuestionRepository psBankingQuestionnaireQuestionRepository;
    
    private final PSBankingCustomerVisitRepository psBankingCustomerVisitRepository;
    private final PSBankingQuestionerResponseRepository psBankingQuestionerResponseRepository;

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingLeadEntity psBankingLeadEntity :psBankingLeadRepository.findAll()){
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id",psBankingLeadEntity.getId());
                objectNode.put("customerID",psBankingLeadEntity.getCustomerId());
                objectNode.put("businessUnit",psBankingLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", psBankingLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic",psBankingLeadEntity.getTopic());
                objectNode.put("priority", psBankingLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", psBankingLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all leads", e);

        }
        return null;
    }

    @Override
    public boolean assignLead(PBAssignLeadRequest model) {
        try {
            PSBankingLeadEntity psBankingLeadEntity = psBankingLeadRepository.findById(model.getLeadId()).get();
            psBankingLeadEntity.setDsrId(model.getDsrId());
            psBankingLeadEntity.setStartDate(model.getStartDate());
            psBankingLeadEntity.setEndDate(model.getEndDate());
            psBankingLeadEntity.setAssigned(true);

            psBankingLeadRepository.save(psBankingLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning lead", e);
        }
        return false;
    }

    @Override
    public boolean scheduleCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingVisitEntity psBankingVisitEntity = new PSBankingVisitEntity();
            psBankingVisitEntity.setMerchantName(model.getMerchantName());
            psBankingVisitEntity.setVisitDate(model.getVisitDate());
            psBankingVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psBankingVisitEntity.setStatus(Status.ACTIVE);
            psBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psBankingVisitEntity.setDsrName(model.getDsrName());
            //save
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
        }

    @Override
    public boolean rescheduleCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSBankingVisitEntity psBankingVisitEntity = psBankingCustomerVisitRepository.findById(model.getId()).orElse(null);
            if (psBankingVisitEntity == null) {
                return false;
            }
            psBankingVisitEntity.setVisitDate(model.getVisitDate());
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
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
            for (PSBankingVisitEntity psBankingVisitEntity : psBankingCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingVisitEntity.getId());
                objectNode.put("customerName", psBankingVisitEntity.getMerchantName());
                objectNode.put("visitDate", psBankingVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", psBankingVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all customer visits", e);

        }
        return null;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponses(PBCustomerVisitQuestionnaireRequest assetManagementRequest) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingQuestionerResponseEntity psBankingQuestionerResponseEntity : psBankingQuestionerResponseRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingQuestionerResponseEntity.getId());
                objectNode.put("visitId", psBankingQuestionerResponseEntity.getVisitId());
                objectNode.put("questionId", psBankingQuestionerResponseEntity.getVisitId());
                objectNode.put("response", psBankingQuestionerResponseEntity.getResponse());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all customer visits", e);

        }
        return null;
    }

    @Override
    public boolean createQuestionnaire(PSBankingAddQuestionnaireRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingQuestionnaireQuestionEntity psBankingQuestionnaireQuestionEntity = new PSBankingQuestionnaireQuestionEntity();
            psBankingQuestionnaireQuestionEntity.setQuestion(model.getQuestion());
            psBankingQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());
            psBankingQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());
            psBankingQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psBankingQuestionnaireQuestionRepository.save(psBankingQuestionnaireQuestionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating questionnaire", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllQuestionnaires() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingQuestionnaireQuestionEntity psBankingQuestionnaireQuestionEntity : psBankingQuestionnaireQuestionRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingQuestionnaireQuestionEntity.getId());
                objectNode.put("question", psBankingQuestionnaireQuestionEntity.getQuestion());
                objectNode.put("questionnaireDescription", psBankingQuestionnaireQuestionEntity.getQuestionnaireDescription());
                list.add(objectNode);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all questionnaires", e);

        }
        return null;
    }

    @Override
    public boolean createPBTarget(PBAddTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingTargetEntity psBankingTargetEntity = new PSBankingTargetEntity();
            psBankingTargetEntity.setTargetName(model.getTargetName());
            psBankingTargetEntity.setTargetSource(model.getTargetSource());
            psBankingTargetEntity.setTargetValue(model.getTargetValue());
            psBankingTargetEntity.setTargetType(model.getTargetType());
            psBankingTargetEntity.setTargetType(model.getTargetType());
            psBankingTargetEntity.setTargetDesc(model.getTargetDesc());
            psBankingTargetEntity.setTargetStatus(TargetStatus.ACTIVE);
            psBankingTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psBankingTargetRepository.save(psBankingTargetEntity);
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
            for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", psBankingTargetEntity.getId());
                node.put("targetName", psBankingTargetEntity.getTargetName());
                node.put("targetSource", psBankingTargetEntity.getTargetSource());
                node.put("TargetType", psBankingTargetEntity.getTargetType().ordinal());
                node.put("targetDesc", psBankingTargetEntity.getTargetDesc());
                node.put("targetStatus", psBankingTargetEntity.getTargetStatus().name());
                node.put("targetValue", psBankingTargetEntity.getTargetValue());
                node.put("createdOn", psBankingTargetEntity.getCreatedOn().toString());
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
    public List<ObjectNode> getAllOnboardings() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingOnboardingEntity psBankingOnboardingEntity : psBankingOnboardingRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", psBankingOnboardingEntity.getId());
                node.put("customerName", psBankingOnboardingEntity.getCustomerName());
                node.put("Region", psBankingOnboardingEntity.getRegion());
                node.put("phoneNumber", psBankingOnboardingEntity.getCustomerPhone());
                node.put("email", psBankingOnboardingEntity.getCustomerEmail());
                node.put("status", psBankingOnboardingEntity.getStatus().ordinal());
                node.put("agent Id", psBankingOnboardingEntity.getDsrId());
                node.put("createdOn", psBankingOnboardingEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading agents", e);
        }


        return null;
    }

    @Override
    public boolean approveOnboarding(PSBankingOnboardingEntity model) {
        try {
            if (model == null) {
                return false;
            }
            PSBankingOnboardingEntity psBankingOnboardingEntity = psBankingOnboardingRepository.findById(model.getId()).orElse(null);
            if (psBankingOnboardingEntity == null) {
                return false;
            }
            psBankingOnboardingEntity.setStatus(OnboardingStatus.APPROVED);
            psBankingOnboardingEntity.setIsApproved(true);
            psBankingOnboardingRepository.save(psBankingOnboardingEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving onboarding", e);
        }
        return false;
    }
}

        