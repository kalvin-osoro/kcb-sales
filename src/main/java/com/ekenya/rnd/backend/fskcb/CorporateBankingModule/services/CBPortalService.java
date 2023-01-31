package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringTargetEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.IAcquiringTargetsRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyBankingTargetRepository;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.DFSVoomaTargetRepository;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingTargetRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.fskcb.QSSAdapter.services.IQssService;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.ChangeConvenantStatus;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTargetEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.TreasuryTargetRepository;
import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor

@Service
public class CBPortalService implements ICBPortalService {

    private final ICBLeadsRepository cbLeadsRepository;
    private final JavaMailSender javaMailSender;
    private final QuestionTypeRepository questionTypeRepository;
    private final QuestionnareQuestionRepository questionnareQuestionRepository;
    private final QuestionnaireResponseRepository questionnaireResponseRepository;
    private final CBRevenueLineRepository cbRevenueLineRepository;
    private final CBOpportunitiesRepository cbOpportunitiesRepository;
    private final CBJustificationRepository cbJustificationRepository;
    private final IDSRTeamsRepository idsrTeamsRepository;
    private final IDSRAccountsRepository dsrAccountRepository;
    private final CBTargetRepository targetRepository;
    private final DFSVoomaTargetRepository dfsVoomaTargetRepository;
    private final AgencyBankingTargetRepository agencyBankingTargetRepository;
    private final IAcquiringTargetsRepository acquiringTargetsRepository;
    private final PSBankingTargetRepository psBankingTargetRepository;
    private final TreasuryTargetRepository treasuryTargetRepository;
    private final CBOnboardingRepository cbOnboardingRepository;
    private final CBBankingConvenantRepository cbBankingConvenantRepository;
    private final CBConcessionRepository cbConcessionRepository;
    private final CBCCampaignRepository cbcCampaignRepository;
    private final CBBankingFeedBackRepository cbBankingFeedBackRepository;
    private final CBCustomerVisitRepository cbCustomerVisitRepository;
    private final CBQuestionnaireQuestionRepository cbQuestionnaireQuestionRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IQssService iQssService;

    @Override
    public boolean assignLead(CBAssignLeadRequest model) {
        try {
            CBLeadEntity cbLeadEntity = cbLeadsRepository.findById(model.getLeadId()).orElse(null);
            cbLeadEntity.setDsrId(model.getDsrId());
            cbLeadEntity.setLeadStatus(LeadStatus.OPEN);
            cbLeadEntity.setEscaleteEmail(model.getEscalateEmail());
            cbLeadEntity.setAssignTime(Utility.getPostgresCurrentTimeStampForInsert());
            DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).get();
            cbLeadEntity.setPriority(model.getPriority());
            //set start date from input
            cbLeadEntity.setAssigned(true);
            //save
            cbLeadsRepository.save(cbLeadEntity);
            iQssService.sendAlert(
                    dsrAccountEntity.getStaffNo(),
                    "New Lead Assigned",
                    "You have been assigned a new lead. Please check your App for more details",
                    null
            );
            //update is assigned to true

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
                node.put("priority", cbLeadEntity.getPriority().toString());
                node.put("dsrName", cbLeadEntity.getDsrName());
                node.put("createdOn", cbLeadEntity.getCreatedOn().getTime());
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
    public boolean rescheduleCustomerVisit(CBRescheduleRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBCustomerVisitEntity cbCustomerVisitEntity = cbCustomerVisitRepository.findById(model.getVisitId()).orElse(null);
            cbCustomerVisitEntity.setVisitDate(model.getNextVisitDate());
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
                objectNode.put("visitDate", cbCustomerVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", cbCustomerVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", cbCustomerVisitEntity.getDsrName());
//                objectNode.put("status", cbCustomerVisitEntity.getStatus() == null ? null :cbCustomerVisitEntity.getStatus().toString());
                objectNode.put("createdOn", cbCustomerVisitEntity.getCreatedOn().getTime());
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
                node.put("campainType", cbCampaignsEntity.getCampainType().toString());
                node.put("startDate", cbCampaignsEntity.getStartDate());
                node.put("endDate", cbCampaignsEntity.getEndDate());
                node.put("status", cbCampaignsEntity.getStatus().toString());
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
    public List<ObjectNode> getAllConcessions() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBConcessionEntity cbConcessionEntity : cbConcessionRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", cbConcessionEntity.getId());
                node.put("concessionStatus", cbConcessionEntity.getConcessionStatus().toString());
                node.put("createdOn", cbConcessionEntity.getCreatedOn().getTime());
                node.put("submittedBy", cbConcessionEntity.getSubmittedBy());
                node.put("customerName", cbConcessionEntity.getCustomerName());
                node.put("submissionDate", cbConcessionEntity.getSubmissionDate());

                List<CBRevenueLineEntity> cbRevenueLineEntityList = cbConcessionEntity.getCbRevenueLineEntityList();
                ArrayNode arrayNode = mapper.createArrayNode();
                for (CBRevenueLineEntity cbRevenueLineEntity : cbRevenueLineEntityList) {
                    ObjectNode node1 = mapper.createObjectNode();
                    node1.put("foregoneRevenue", cbRevenueLineEntity.getForgoneRevenue());
                    node1.put("revenueLineType", cbRevenueLineEntity.getRevenueLineType().toString());
                    arrayNode.add(node1);
                }
                node.put("revenueLines", arrayNode);
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
            cbBankingConvenantEntity.setCustomerName(model.getCustomerName());
            cbBankingConvenantEntity.setCustomerId(model.getCustomerId());
            cbBankingConvenantEntity.setEndDate(model.getEndDate());
            cbBankingConvenantEntity.setIntervalForCheck(model.getIntervalForCheck());
            cbBankingConvenantEntity.setAlertMessage(model.getAlertMessage());
            cbBankingConvenantEntity.setAlertBeforeExpiry(model.getAlertBeforeExpiry());

            cbBankingConvenantEntity.setStatus(ConcessionTrackingStatus.GREEN);
            cbBankingConvenantEntity.setReferenceNumber(model.getReferenceNumber());

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
    public boolean setTrackedCovenantStatus(ChangeConvenantStatus model) {
        try {
            if (model == null) {
                return false;
            }
            cbBankingConvenantRepository.findById(model.getId()).ifPresent(covenant -> {
                        covenant.setStatus(model.getStatus());
                        //save
                        cbBankingConvenantRepository.save(covenant);
                    }
            );
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
                objectNode.put("id", cbBankingConvenantEntity.getId());
                objectNode.put("customerId", cbBankingConvenantEntity.getCustomerId());
                objectNode.put("customerName", cbBankingConvenantEntity.getCustomerName());
                ObjectNode period = mapper.createObjectNode();
                period.put("endDate", cbBankingConvenantEntity.getEndDate());
                period.put("createdOn", cbBankingConvenantEntity.getCreatedOn().toString());
                objectNode.set("period", period);
                objectNode.put("intervalForCheck", cbBankingConvenantEntity.getIntervalForCheck());
                objectNode.put("status", cbBankingConvenantEntity.getStatus().toString());
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
                    asset.put("onboarding-status", cbOnboardingEntity.getStatus().toString());
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
                objectNode.put("agencyTargetType", cbTargetEntity.getTargetType().toString());
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
            if (target.getTargetType().equals(CBTargetType.CROSS_SALES)) {
                user.setCampaignTargetValue(model.getTargetValue());
                user.setCbTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(CBTargetType.MFI)) {
                user.setLeadsTargetValue(model.getTargetValue());
                user.setCbTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(CBTargetType.PRODUCTS)) {
                user.setVisitsTargetValue(model.getTargetValue());
                user.setCbTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(CBTargetType.ASSETS_AND_LIABILITIES)) {
                user.setVisitsTargetValue(model.getTargetValue());
                user.setCbTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(CBTargetType.FINANCIAL_SERVICES)) {
                user.setOnboardTargetValue(model.getTargetValue());
                user.setCbTargetId(model.getTargetId());
            }

            Set<CBTargetEntity> cbTargetEntities = user.getCbTargetEntities();
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
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                teamEntity.setOnboardTargetValue(model.getTargetValue());
            }

            Set<CBTargetEntity> cbTargetEntities = teamEntity.getCbTargetEntities();
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
            if (model == null) {
                return false;
            }
            CBTargetEntity cbTargetEntity = targetRepository.findById(model.getId()).orElse(null);
            return cbTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }


    @Override
    public boolean addOpportunity(CBAddOpportunityRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBOpportunitiesEntity cbOpportunityEntity = new CBOpportunitiesEntity();
            cbOpportunityEntity.setCustomerName(model.getCustomerName());
            cbOpportunityEntity.setProduct(model.getProduct());
            cbOpportunityEntity.setStage(model.getStage());
            cbOpportunityEntity.setValue(model.getValue());
            cbOpportunityEntity.setCbSectors(model.getCbSectors());
            cbOpportunityEntity.setProbability(model.getProbability());
            cbOpportunityEntity.setStatus(OpportunityStatus.OPEN);
            cbOpportunityEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbOpportunitiesRepository.save(cbOpportunityEntity);
            return true;

        } catch (Exception e) {
            log.error("Error while adding new opportunity", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllOpportunities() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            List<CBOpportunitiesEntity> cbOpportunitiesEntities = cbOpportunitiesRepository.findAll();
            for (CBOpportunitiesEntity cbOpportunitiesEntity : cbOpportunitiesEntities) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbOpportunitiesEntity.getId());
                objectNode.put("customerName", cbOpportunitiesEntity.getCustomerName());
                objectNode.put("product", cbOpportunitiesEntity.getProduct());
                objectNode.put("value", cbOpportunitiesEntity.getValue());
                objectNode.put("stage", cbOpportunitiesEntity.getStage().toString());
                objectNode.put("probability", cbOpportunitiesEntity.getProbability());
                objectNode.put("status", cbOpportunitiesEntity.getStatus().toString());
                objectNode.put("createdOn", cbOpportunitiesEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all opportunities", e);
        }
        return null;
    }

    @Override
    public Object getOpportunityById(CBGetOppByIdRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBOpportunitiesEntity cbOpportunitiesEntity = cbOpportunitiesRepository.findById(model.getOpportunityId()).orElse(null);
            return cbOpportunitiesEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting opportunity by id", e);
        }
        return null;
    }

    @Override
    public boolean createQuestionnareType(QuestionTypeRequest model) {
        try {
            if (model == null) {
                return false;
            }
            QuestionType cbQuestionTypeEntity = new QuestionType();
            cbQuestionTypeEntity.setName(model.getName());
            cbQuestionTypeEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbQuestionTypeEntity.setExpectedAnswer(model.getExpectedResponse());
            cbQuestionTypeEntity.setStatus(Status.ACTIVE);
            questionTypeRepository.save(cbQuestionTypeEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating question type", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllQuestionnareTypes() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            List<QuestionType> cbQuestionTypeEntities = questionTypeRepository.findAll();
            for (QuestionType cbQuestionTypeEntity : cbQuestionTypeEntities) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbQuestionTypeEntity.getId());
                objectNode.put("name", cbQuestionTypeEntity.getName());
                objectNode.put("expectedAnswer", cbQuestionTypeEntity.getExpectedAnswer());
                objectNode.put("status", cbQuestionTypeEntity.getStatus().ordinal());
                objectNode.put("createdOn", cbQuestionTypeEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all question types", e);
        }
        return null;
    }

    @Override
    public boolean addQuestionnareQuestions(UQuestionnaireQuestionRequest model) {
        try {
            if (model == null) {
                return false;
            }
            QuestionnareQuestion cbQuestionnaireQuestionEntity = new QuestionnareQuestion();
            QuestionType cbQuestionTypeEntity = questionTypeRepository.findById(model.getQuestionType()).orElse(null);
//            cbQuestionnaireQuestionEntity.setQuestionType(cbQuestionTypeEntity);
            cbQuestionnaireQuestionEntity.setQuestion(model.getQuestion());
            cbQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbQuestionnaireQuestionEntity.setStatus(Status.ACTIVE);
            cbQuestionnaireQuestionEntity.setQuestionDescription(model.getQuestionDescription());
            cbQuestionnaireQuestionEntity.setChoices(model.getChoices());
            questionnareQuestionRepository.save(cbQuestionnaireQuestionEntity);
            return true;


        } catch (Exception e) {
            log.error("Error occurred while adding question", e);
        }
        return false;
    }

    @Override
    public boolean addQuestionnareResponse(QuestionResponseRequest model) {
        try {
            if (model == null) {
                return false;
            }
            List<QuestionnaireResponseRequest> questionnaireResponseRequestList =
                    model.getListQuestionResponse();
            for (QuestionnaireResponseRequest questionnaireResponseRequest : questionnaireResponseRequestList) {
                QuestionnaireResponse cbQuestionnaireResponseEntity = new QuestionnaireResponse();
                QuestionnareQuestion cbQuestionnaireQuestionEntity = questionnareQuestionRepository.findById(questionnaireResponseRequest.getQuestionnaireQuestion()).orElse(null);
                cbQuestionnaireResponseEntity.setQuestion(cbQuestionnaireQuestionEntity);
                cbQuestionnaireResponseEntity.setQuestionResponse(questionnaireResponseRequest.getQuestionResponse());
//                cbQuestionnaireResponseEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                cbQuestionnaireResponseEntity.setStatus(String.valueOf(Status.ACTIVE));
                questionnaireResponseRepository.save(cbQuestionnaireResponseEntity);
                return true;
            }
        } catch (Exception e) {
            log.error("Error occurred while adding question response", e);
        }
        return false;
    }

    @Override
    public boolean addConcession(CBConcessionRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBConcessionEntity cbConcessionEntity = new CBConcessionEntity();
            cbConcessionEntity.setCustomerName(model.getCustomerName());
            cbConcessionEntity.setSubmittedBy(model.getSubmittedBy());
            cbConcessionEntity.setSubmissionDate(model.getSubmissionDate());
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.PENDING);
            cbConcessionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbConcessionRepository.save(cbConcessionEntity);
            List<CBRevenueLineRequest> revenueLineRequestList = model.getCbRevenueLineRequests();
            for (CBRevenueLineRequest revenueLineRequest : revenueLineRequestList) {
                CBRevenueLineEntity cbRevenueLineEntity = new CBRevenueLineEntity();
                cbRevenueLineEntity.setSSRrate(revenueLineRequest.getSsrcRate());
                cbRevenueLineEntity.setRecommendedRate(revenueLineRequest.getRecommendedRate());
                cbRevenueLineEntity.setRevenueLineType(revenueLineRequest.getRevenueLineType());
                cbRevenueLineEntity.setBaseAmount(revenueLineRequest.getBaseAmount());
                cbRevenueLineEntity.setDuration(revenueLineRequest.getDuration());
                cbRevenueLineEntity.setForgoneRevenue(revenueLineRequest.getForgoneRevenue());
                cbRevenueLineEntity.setCbConcessionEntity(cbConcessionEntity);//

                cbRevenueLineRepository.save(cbRevenueLineEntity);
            }
            List<CBJustificationRequest> justificationRequestList = model.getCbJustificationRequests();
            for (CBJustificationRequest justificationRequest : justificationRequestList) {
                CBJustificationEntity cbJustificationEntity = new CBJustificationEntity();
                cbJustificationEntity.setJustification(justificationRequest.getJustification());
                cbJustificationEntity.setMonitoringMechanism(justificationRequest.getMonitoringMechanism());
                cbJustificationEntity.setStakeholder(justificationRequest.getStakeholder());
                cbJustificationEntity.setCbConcessionEntity(cbConcessionEntity);
                cbJustificationRepository.save(cbJustificationEntity);
            }
            return true;
        } catch (Exception e) {
            log.error("Error occurred while adding concession", e);
        }
        return false;
    }

    @Override
    public boolean updateOpportunity(CBUpdateOpportunity model) {
        try {
            if (model == null) {
                return false;
            }
            CBOpportunitiesEntity cbOpportunitiesEntity = cbOpportunitiesRepository.findById(model.getOpportunityId()).orElse(null);
            cbOpportunitiesEntity.setProbability(model.getProbability());
            cbOpportunitiesEntity.setStage(model.getStage());
            cbOpportunitiesRepository.save(cbOpportunitiesEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while updating opportunity", e);
        }
        return false;
    }

    @Override
    public boolean approveCBConcession(CBApproveConcessionRequest model) {
        try {
            CBConcessionEntity cbConcessionEntity = cbConcessionRepository.findById(model.getConcessionId()).get();
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.APPROVED);
            cbConcessionEntity.setApproved(true);
            cbConcessionRepository.save(cbConcessionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving merchant onboarding", e);
        }
        return false;
    }

    @Override
    public boolean sendEmailForApproval(CBApproveConcessionRequest model) {
        try {
            if (model == null) {
                log.error("please provide email address");
            }
            CBConcessionEntity cbConcessionEntity = cbConcessionRepository.findById(model.getConcessionId()).get();
            sendEmail(model.getEmailUrl(), cbConcessionEntity);
            return true;


        } catch (Exception e) {
            log.error("Error occurred while sending email for approval", e);
        }
        return false;
    }

    public void sendEmail(String email, CBConcessionEntity cbConcessionEntity) {
        try {
            MimeMessage message1 = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message1);
            helper.setFrom("kcb@test.com", "KCB");
            helper.setTo(email);
            String subject1 = "Concession Approval";
            String content = "<p>Hello,</p>"
                    + "<p>You have a new concession request.</p>"
                    + "<p>Please login to the system to approve</p>"
                    + "<p>Regards,</p>"
                    + "<p>KCB</p>";
            helper.setSubject(subject1);
            helper.setText(content, true);
            javaMailSender.send(message1);
        } catch (Exception e) {
            log.error("Error occurred while sending email", e);
        }
    }

    @Override
    public boolean rejectCBConcession(CBApproveConcessionRequest model) {
        try {
            CBConcessionEntity cbConcessionEntity = cbConcessionRepository.findById(model.getConcessionId()).get();
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.REJECTED);
            cbConcessionEntity.setApproved(false);
            cbConcessionRepository.save(cbConcessionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rejecting concession", e);
        }
        return false;
    }

    @Override
    public boolean reAssignLead(CBAssignLeadRequest model) {
        try {
            CBLeadEntity cbLeadEntity = cbLeadsRepository.findById(model.getLeadId()).orElse(null);
//            cbLeadEntity.setDsrId(model.getDsrId());
            cbLeadEntity.setBusinessUnit(model.getBusinessUnit());
            cbLeadEntity.setRemarks(model.getRemarks());
            //set start date from input
            cbLeadEntity.setAssigned(false);
            //save
            cbLeadsRepository.save(cbLeadEntity);
            //update is assigned to true

            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;
    }

    @Override
    public boolean updateTarget(UpdateTargetRequest model) {
        try {
            if (model==null){
                return false;
            }
            //if profileCode==corporateBanking
            if (model.getProfileCode().equalsIgnoreCase("corporateBanking")){
                CBTargetEntity cbTargetEntity = targetRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            //if profileCode==dfsVooma
            if (model.getProfileCode().equalsIgnoreCase("dfsVooma")){
                DFSVoomaTargetEntity dfsVoomaTargetEntity = dfsVoomaTargetRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            //if profileCode==dfsAgency
            if (model.getProfileCode().equalsIgnoreCase("dfsAgency")){
                AgencyBankingTargetEntity agencyBankingTargetEntity = agencyBankingTargetRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            //if profileCode==treasuryBanking
            if (model.getProfileCode().equalsIgnoreCase("treasuryBanking")){
                TreasuryTargetEntity treasuryTargetEntity = treasuryTargetRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            //if profileCode==personalBanking
            if (model.getProfileCode().equalsIgnoreCase("personalBanking")){
                PSBankingTargetEntity personalBankingTargetEntity = psBankingTargetRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            //else if profileCode==dfsAcquiring
           else if (model.getProfileCode().equalsIgnoreCase("dfsAcquiring")){
                AcquiringTargetEntity dfsAcquiringTargetEntity = acquiringTargetsRepository.findById(model.getTargetId()).orElse(null);
                DSRAccountEntity dsrAccountEntity = dsrAccountRepository.findById(model.getDsrId()).orElse(null);
                dsrAccountEntity.setTargetValue(model.getTargetValue());
                dsrAccountRepository.save(dsrAccountEntity);
            }
            return true;

        } catch (Exception e) {
            log.error("Error occurred while updating target", e);
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
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //bring all fields from dsr
            for (DSRAccountEntity dsrAccountEntity : dsrAccountRepository.findByVoomaTargetId(model.getTargetId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dsrAccountEntity.getId());
                objectNode.put("staffNo", dsrAccountEntity.getStaffNo());
                objectNode.put("fullName", dsrAccountEntity.getFullName());
                objectNode.put("targetValue", dsrAccountEntity.getTargetValue());
                //add to list
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting dsr in target", e);
        }
        return null;
    }

    //send escalation automatic email if assignedTime is past 48 hrs
    public void sendEscalationEmail(String email, CBLeadEntity cbLeadEntity) {
        try {
            if (cbLeadEntity.getAssignTime() != null && cbLeadEntity.getLeadStatus() == LeadStatus.OPEN && cbLeadEntity.isEscalated() == false) {
                Date date = new Date();
                long diff = date.getTime() - cbLeadEntity.getAssignTime().getTime();
                log.info("diff is " + diff);
                long diffHours = diff / (60 * 60 * 1000) % 24;
                if (diffHours > 48) {
                    MimeMessage message1 = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message1);
                    helper.setFrom("corporate@gmail.com", "KCB");
                    helper.setTo(cbLeadEntity.getEscaleteEmail());
                    String subject1 = "Lead Escalation";
                    String content = "<p>Hello,</p>"
                            + "<p>You have a new lead escalation.</p>"
                            + "<p>Please Login to the System and reasign </p>"
                            + "<p>Regards,</p>"
                            + "<p>KCB</p>";
                    helper.setSubject(subject1);
                    helper.setText(content, true);
                    javaMailSender.send(message1);
                    //isEscalated to true
                    cbLeadEntity.setEscalated(true);
                    cbLeadsRepository.save(cbLeadEntity);

                }
            }
        } catch (Exception e) {
            log.error("Error occurred while sending email", e);
        }
    }
}




