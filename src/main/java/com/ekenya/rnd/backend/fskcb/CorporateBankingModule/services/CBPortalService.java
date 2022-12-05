package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddConcessionRequest;
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
@RequiredArgsConstructor

@Service
public class CBPortalService implements ICBPortalService {

  private final ICBLeadsRepository cbLeadsRepository;
  private final IDSRTeamsRepository idsrTeamsRepository;
  private final IDSRAccountsRepository dsrAccountRepository;
  private final CBTargetRepository targetRepository;
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
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbCustomerVisitEntity.getId());
                objectNode.put("customerName", cbCustomerVisitEntity.getCustomerName());
                objectNode.put("visitDate", cbCustomerVisitEntity.getVisitDate().toString());
                objectNode.put("reasonForVisit", cbCustomerVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", cbCustomerVisitEntity.getDsrName());
                objectNode.put("status", cbCustomerVisitEntity.getStatus().toString());
                objectNode.put("createdOn", cbCustomerVisitEntity.getCreatedOn().toString());
                objectNode.put("region", cbCustomerVisitEntity.getRegion());
                list.add(objectNode);

            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all targets", e);
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

    @Override
    public boolean createCBTarget(CBAddTargetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            CBTargetEntity cbTargetEntity = new CBTargetEntity();
            cbTargetEntity.setTargetName(model.getTargetName());
            cbTargetEntity.setTargetSource(model.getTargetSource());
            cbTargetEntity.setTargetType(model.getTargetType());
            cbTargetEntity.setTargetDesc(model.getTargetDesc());
            cbTargetEntity.setStartDate(model.getStartDate());
            cbTargetEntity.setEndDate(model.getEndDate());
            cbTargetEntity.setAssignmentType(model.getAssignmentType());


            cbTargetEntity.setTargetStatus(TargetStatus.ACTIVE);

            cbTargetEntity.setTargetValue(model.getTargetValue());
            cbTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            targetRepository.save(cbTargetEntity);
            return true;

            //save
        } catch (Exception e) {
            log.error("Error while adding new target", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBTargetEntity cbTargetEntity : targetRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbTargetEntity.getId());
                objectNode.put("targetName", cbTargetEntity.getTargetName());
                objectNode.put("targetSource", cbTargetEntity.getTargetSource());
                objectNode.put("agencyTargetType", cbTargetEntity.getTargetType().ordinal());
                objectNode.put("targetDesc", cbTargetEntity.getTargetDesc());
                objectNode.put("targetStatus", cbTargetEntity.getTargetStatus().name());
                objectNode.put("targetValue", cbTargetEntity.getTargetValue());
//                objectNode.put("targetAchieved",dfsVoomaTargetEntity.getTargetAchievement());
                objectNode.put("createdOn", cbTargetEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all targets", e);
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

            CBTargetEntity target = targetRepository.findById(model.getTargetId()).orElse(null);
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

            Set<CBTargetEntity> cbTargetEntities = (Set<CBTargetEntity>) user.getCbTargetEntities();
            cbTargetEntities.add(target);
            user.setCbTargetEntities(cbTargetEntities);
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
            DSRTeamEntity teamEntity = idsrTeamsRepository.findById(model.getTeamId()).orElse(null);

            CBTargetEntity target = targetRepository.findById(model.getTargetId()).orElse(null);

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

            Set<CBTargetEntity> cbTargetEntities = (Set<CBTargetEntity>) teamEntity.getCbTargetEntities();
            cbTargetEntities.add(target);
            teamEntity.setCbTargetEntities(cbTargetEntities);
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
            if (model==null){
                return  false;
            }
            CBTargetEntity cbTargetEntity = targetRepository.findById(model.getId()).orElse(null);
            return cbTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }
}
