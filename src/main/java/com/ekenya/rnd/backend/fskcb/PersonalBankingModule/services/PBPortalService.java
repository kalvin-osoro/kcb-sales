package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaFeedBackEntity;
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
    private  final PSBankingFeedBackRepository psBankingFeedBackRepository;
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

    @Override
    public List<ObjectNode> getOnboardingSummary(PBSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (PSBankingOnboardingEntity psBankingOnboardingEntity : psBankingOnboardingRepository.fetchAllOnboardingCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("customerName", psBankingOnboardingEntity.getCustomerName());
                    asset.put("onboarding-status", psBankingOnboardingEntity.getStatus().ordinal());
                    asset.put("agent Id", psBankingOnboardingEntity.getDsrId());
                    asset.put("date_onboarded", psBankingOnboardingEntity.getCreatedOn().getTime());
                    asset.put("latitude", psBankingOnboardingEntity.getLatitude());
                    asset.put("longitude", psBankingOnboardingEntity.getLongitude());
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
    public List<ObjectNode> getCustomerVisitsSummary(PBSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (PSBankingVisitEntity psBankingVisitEntity : psBankingCustomerVisitRepository.fetchAllVisitsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("id", psBankingVisitEntity.getId());
                    asset.put("customerName", psBankingVisitEntity.getMerchantName());
                    asset.put("visitDate", psBankingVisitEntity.getVisitDate());
                    asset.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                    asset.put("dsrName", psBankingVisitEntity.getDsrName());
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
    public List<ObjectNode> getTargetsSummary(PBSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.fetchAllTargetsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("id", psBankingTargetEntity.getId());
                    asset.put("targetName", psBankingTargetEntity.getTargetName());
                    asset.put("targetSource", psBankingTargetEntity.getTargetSource());
                    asset.put("TargetType", psBankingTargetEntity.getTargetType().ordinal());
                    asset.put("targetDesc", psBankingTargetEntity.getTargetDesc());
                    asset.put("targetStatus", psBankingTargetEntity.getTargetStatus().name());
                    asset.put("targetValue", psBankingTargetEntity.getTargetValue());
                    asset.put("createdOn", psBankingTargetEntity.getCreatedOn().toString());
                    //add to list
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
    public List<ObjectNode> getLeadsSummary(PBSummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            {
                for (PSBankingLeadEntity psBankingLeadEntity : psBankingLeadRepository.fetchAllLeadsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    //number of leads created
                    asset.put("lead_orginates",psBankingLeadRepository.countAllLeadsCreatedLast7Days());
                    asset.put("leads_assigned",psBankingLeadRepository.countAllLeadsCreatedLast7DaysAssigned());
                    asset.put("leads_open", psBankingLeadRepository.countAllLeadsCreatedLast7DaysOpen());
                    asset.put("leads_closed", psBankingLeadRepository.countAllLeadsCreatedLast7DaysClosed());
                    asset.put("lead_status", psBankingLeadEntity.getLeadStatus().ordinal());
                    ObjectNode leadStatus = mapper.createObjectNode();
                    leadStatus.put("hot", psBankingLeadRepository.countAllLeadsCreatedLast7DaysHot());
                    leadStatus.put("warm", psBankingLeadRepository.countAllLeadsCreatedLast7DaysWarm());
                    leadStatus.put("cold", psBankingLeadRepository.countAllLeadsCreatedLast7DaysCold());
                    //object containing lead topic,co-oridinates and created on
                    ObjectNode lead = mapper.createObjectNode();
                    lead.put("lead_topic", psBankingLeadEntity.getTopic());
                    lead.put("lead_created_on", psBankingLeadEntity.getCreatedOn().getTime());
                    //add to list
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
    public List<ObjectNode> getAllCustomerFeedbacks() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingFeedBackEntity psBankingFeedBackEntity : psBankingFeedBackRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psBankingFeedBackEntity.getId());
                objectNode.put("customerId", psBankingFeedBackEntity.getCustomerId());
                objectNode.put("channel", psBankingFeedBackEntity.getChannel());
                objectNode.put("visitRef", psBankingFeedBackEntity.getVisitRef());
                objectNode.put("customerName", psBankingFeedBackEntity.getCustomerName());
                objectNode.put("noOfQuestionAsked", psBankingFeedBackEntity.getNoOfQuestionAsked());
                objectNode.put("createdOn", psBankingFeedBackEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer feedbacks", e);
        }

        return null;
    }

    @Override
    public Object getCustomerFeedbackResponses(PSBankingFeedBackRequest psBankingFeedBackRequest) {
        try {
            PSBankingFeedBackEntity psBankingFeedBackEntity = psBankingFeedBackRepository.findById(psBankingFeedBackRequest.getId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", psBankingFeedBackEntity.getId());
            objectNode.put("questionAsked", psBankingFeedBackEntity.getQuestionAsked());
            objectNode.put("response", psBankingFeedBackEntity.getResponse());
            objectNode.put("createdOn", psBankingFeedBackEntity.getCreatedOn().getTime());
            return objectNode;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant by id", e);
        }
        return null;
    }
}

        