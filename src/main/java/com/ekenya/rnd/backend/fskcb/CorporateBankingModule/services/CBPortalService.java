package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingFeedBackEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSBankingConvenantEntity;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddConcessionRequest;
import com.ekenya.rnd.backend.fskcb.entity.CustomerAppointments;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor

@Service
public class CBPortalService implements ICBPortalService {

  private final ICBLeadsRepository cbLeadsRepository;
  private final CBOnboardingRepository cbOnboardingRepository;
  private final CBBankingConvenantRepository cbBankingConvenantRepository;
  private final CBConcessionRepository cbConcessionRepository;
  private final CBCCampaignRepository cbcCampaignRepository;
  private final CBBankingFeedBackRepository cbBankingFeedBackRepository;
  private final CBCustomerVisitRepository cbCustomerVisitRepository;
  private final CBQuestionnaireQuestionRepository cbQuestionnaireQuestionRepository;

    @Override
    public boolean assignLead(CBAssignLeadRequest model) {
        try {
            CBLeadEntity cbLeadEntity = cbLeadsRepository.findById(model.getLeadId()).orElse(null);
            cbLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            cbLeadEntity.setStartDate(model.getStartDate());
            cbLeadEntity.setEndDate(model.getEndDate());
            //save
            cbLeadsRepository.save(cbLeadEntity);
            //update is assigned to true
            cbLeadEntity.setAssigned(true);
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
            for (CBLeadEntity cbLeadEntity : cbLeadsRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbLeadEntity.getId());
                node.put("customerId", cbLeadEntity.getCustomerId());
                node.put("businessUnit", cbLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(cbLeadEntity.getLeadStatus()));
                node.put("topic", cbLeadEntity.getTopic());
                node.put("priority", cbLeadEntity.getPriority().ordinal());
                node.put("dsrId", cbLeadEntity.getDsrId());
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
    public boolean createQuestionnaire(AcquiringAddQuestionnaireRequest model) {
        try {
            CBQuestionnaireQuestionEntity cbQuestionnaireQuestionEntity = new CBQuestionnaireQuestionEntity();
            cbQuestionnaireQuestionEntity.setQuestion(model.getQuestion());
            cbQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());

            cbQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbQuestionnaireQuestionRepository.save(cbQuestionnaireQuestionEntity);
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
            for (CBQuestionnaireQuestionEntity cbQuestionnaireQuestionEntity : cbQuestionnaireQuestionRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbQuestionnaireQuestionEntity.getId());
                node.put("question", cbQuestionnaireQuestionEntity.getQuestion());
                node.put("createdon", cbQuestionnaireQuestionEntity.getCreatedOn().toString());
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
    public boolean scheduleCustomerVisit(CBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            CBCustomerVisitEntity cbCustomerVisitEntity = new CBCustomerVisitEntity();
            cbCustomerVisitEntity.setCustomerName(model.getCustomerName());
            cbCustomerVisitEntity.setVisitDate(model.getVisitDate());
            cbCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            cbCustomerVisitEntity.setStatus(Status.ACTIVE);
            cbCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbCustomerVisitEntity.setDsrName(model.getDsrName());
            //save
            cbCustomerVisitRepository.save(cbCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean rescheduleCustomerVisit(CBCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBCustomerVisitEntity cbCustomerVisitEntity = cbCustomerVisitRepository.findById(model.getId()).orElse(null);
            if (model == null) {
                return false;
            }
            cbCustomerVisitEntity.setVisitDate(model.getVisitDate());
            cbCustomerVisitRepository.save(cbCustomerVisitEntity);
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
            for (CBCustomerVisitEntity cbCustomerVisitEntity : cbCustomerVisitRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbCustomerVisitEntity.getId());
                node.put("customerName", cbCustomerVisitEntity.getCustomerName());
                node.put("visitDate", cbCustomerVisitEntity.getVisitDate().toString());
                node.put("reasonForVisit", cbCustomerVisitEntity.getReasonForVisit());
                node.put("dsrName", cbCustomerVisitEntity.getDsrName());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading customer visits", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllCustomerFeedbacks() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBBankingFeedBackEntity cbBankingFeedBackEntity : cbBankingFeedBackRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbBankingFeedBackEntity.getId());
                objectNode.put("customerId", cbBankingFeedBackEntity.getCustomerId());
                objectNode.put("channel", cbBankingFeedBackEntity.getChannel());
                objectNode.put("visitRef", cbBankingFeedBackEntity.getVisitRef());
                objectNode.put("customerName", cbBankingFeedBackEntity.getCustomerName());
                objectNode.put("noOfQuestionAsked", cbBankingFeedBackEntity.getNoOfQuestionAsked());
                objectNode.put("createdOn", cbBankingFeedBackEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer feedbacks", e);
        }

        return null;
    }

    @Override
    public Object getCustomerFeedbackResponses(CBBankingFeedBackRequest model) {
        try {
            CBBankingFeedBackEntity cbBankingFeedBackEntity = cbBankingFeedBackRepository.findById(model.getId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", cbBankingFeedBackEntity.getId());
            objectNode.put("questionAsked", cbBankingFeedBackEntity.getQuestionAsked());
            objectNode.put("response", cbBankingFeedBackEntity.getResponse());
            objectNode.put("createdOn", cbBankingFeedBackEntity.getCreatedOn().getTime());
            return objectNode;
        } catch (Exception e) {
            log.error("Error occurred while getting merchant by id", e);
        }
        return null;
    }

    @Override
    public boolean createCampain(CBCampaignsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBCCampaignEntity cbCampaignsEntity = new CBCCampaignEntity();
            cbCampaignsEntity.setCampaignName(model.getCampaignName());
            cbCampaignsEntity.setCampaignDesc(model.getCampaignDesc());
            cbCampaignsEntity.setCampainType(model.getCampainType());
            cbCampaignsEntity.setStartDate(model.getStartDate());
            cbCampaignsEntity.setEndDate(model.getEndDate());
            cbCampaignsEntity.setStatus(Status.ACTIVE);
            cbCampaignsEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbcCampaignRepository.save(cbCampaignsEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating campaign", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCampaigns() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBCCampaignEntity cbCampaignsEntity : cbcCampaignRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbCampaignsEntity.getId());
                node.put("campaignName", cbCampaignsEntity.getCampaignName());
                node.put("campaignDesc", cbCampaignsEntity.getCampaignDesc());
                node.put("campainType", cbCampaignsEntity.getCampainType().ordinal());
                node.put("startDate", cbCampaignsEntity.getStartDate().toString());
                node.put("endDate", cbCampaignsEntity.getEndDate().toString());
                node.put("status", cbCampaignsEntity.getStatus().ordinal());
                node.put("createdOn", cbCampaignsEntity.getCreatedOn().getTime());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading campaigns", e);
        }
        return null;
    }

    @Override
    public boolean addConcession(RetailAddConcessionRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            CBConcessionEntity cbConcessionEntity = new CBConcessionEntity();
            cbConcessionEntity.setCustomerName(model.getCustomerName());
            cbConcessionEntity.setSubmissionRate(model.getSubmissionRate());
            cbConcessionEntity.setSubmittedBy(model.getSubmittedBy());
            cbConcessionEntity.setStatus(Status.ACTIVE);
            //TODO: set Revenue Line Implementation

            cbConcessionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            cbConcessionRepository.save(cbConcessionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating concession", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllConcessions() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBConcessionEntity cbConcessionEntity : cbConcessionRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbConcessionEntity.getId());
                node.put("customerName", cbConcessionEntity.getCustomerName());
                node.put("submissionRate", cbConcessionEntity.getSubmissionRate());
                node.put("submittedBy", cbConcessionEntity.getSubmittedBy());
                node.put("status", cbConcessionEntity.getStatus().name());
                node.put("createdOn", cbConcessionEntity.getCreatedOn().toString());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading concessions", e);
        }
        return null;
    }

    @Override
    public boolean addTrackedCovenant(CBAddConvenantRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            CBBankingConvenantEntity cbBankingConvenantEntity = new CBBankingConvenantEntity();
            cbBankingConvenantEntity.setCustomerId(model.getCustomerId());
            cbBankingConvenantEntity.setEndDate(model.getEndDate());
            cbBankingConvenantEntity.setIntervalForCheck(model.getIntervalForCheck());
            cbBankingConvenantEntity.setAlertMessage(model.getAlertMessage());
            cbBankingConvenantEntity.setDsrId(model.getDsrId());
            cbBankingConvenantEntity.setAlertBeforeExpiry(model.getAlertBeforeExpiry());

            cbBankingConvenantEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            cbBankingConvenantRepository.save(cbBankingConvenantEntity);
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
            for (CBBankingConvenantEntity cbBankingConvenantEntity : cbBankingConvenantRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("customerId", cbBankingConvenantEntity.getCustomerId());
                ObjectNode period = mapper.createObjectNode();
                period.put("startDate", cbBankingConvenantEntity.getStartDate());
                period.put("endDate", cbBankingConvenantEntity.getEndDate());
                objectNode.set("period", period);
                objectNode.put("intervalForCheck", cbBankingConvenantEntity.getIntervalForCheck());
                objectNode.put("dsrId", cbBankingConvenantEntity.getDsrId());
                objectNode.put("status", cbBankingConvenantEntity.getStatus().toString());
                objectNode.put("createdOn", cbBankingConvenantEntity.getCreatedOn().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error getting all tracked covenants", e);
        }

        return null;
    }

    @Override
    public List<ObjectNode> getOnboardingSummary() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (CBOnboardingEntity cbOnboardingEntity : cbOnboardingRepository.fetchAllOnboardingCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("customerName", cbOnboardingEntity.getCustomerName());
                    asset.put("onboarding-status", cbOnboardingEntity.getStatus().ordinal());
                    asset.put("agent Id", cbOnboardingEntity.getDsrId());
                    asset.put("date_onboarded", cbOnboardingEntity.getCreatedOn().getTime());
                    asset.put("latitude", cbOnboardingEntity.getLatitude());
                    asset.put("longitude", cbOnboardingEntity.getLongitude());
                    list.add(asset);
                }
                return list;
            }


        } catch (Exception e) {
            log.error("Error occurred while fetching onboarding summary", e);
        }
        return null;
    }
}
