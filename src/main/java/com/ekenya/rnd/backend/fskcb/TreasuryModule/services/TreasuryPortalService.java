package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreasuryPortalService implements ITreasuryPortalService {
    private final TreasuryTradeRequestRepository treasuryTradeRequestRepository;
    private final TreasuryCurrencyRepository treasuryCurrencyRepository;
    private final IDSRTeamsRepository dsrTeamsRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;
    private final TreasuryNegotiationRequestRepository negotiationRequestRepository;
    private final TreasuryLeadRepository treasuryLeadRepository;
    private final TreasuryTargetRepository treasuryTargetRepository;
    private final TreasuryQuestionnaireQuestionRepository treasuryQuestionnaireQuestionRepository;


    @Override
    public List<ObjectNode> getAllNegotiationReqs() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryNegotiationRequestEntity treasuryNegotiationRequestEntity : negotiationRequestRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", treasuryNegotiationRequestEntity.getId());
                node.put("customerName", treasuryNegotiationRequestEntity.getCustomerName());
                node.put("customerID", treasuryNegotiationRequestEntity.getCustomerID());
                node.put("Amount", treasuryNegotiationRequestEntity.getAmount());
                node.put("currency", treasuryNegotiationRequestEntity.getCurrency());
                node.put("priority", treasuryNegotiationRequestEntity.getPriority().toString());
                node.put("salesCode", treasuryNegotiationRequestEntity.getSalesCode());
                node.put("status", treasuryNegotiationRequestEntity.getStatus().toString());
                node.put("refCode",treasuryNegotiationRequestEntity.getRefCode());
                node.put("dateBooked", treasuryNegotiationRequestEntity.getDateBooked());

                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while fetching all negotiation requests", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllTradeReqs() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryNegotiationRequestEntity treasuryNegotiationRequestEntity : negotiationRequestRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", treasuryNegotiationRequestEntity.getId());
                node.put("customerName", treasuryNegotiationRequestEntity.getCustomerName());
                node.put("customerID", treasuryNegotiationRequestEntity.getCustomerID());
                node.put("Amount", treasuryNegotiationRequestEntity.getAmount());
                node.put("currency", treasuryNegotiationRequestEntity.getCurrency());
                node.put("priority", treasuryNegotiationRequestEntity.getPriority().toString());
                node.put("salesCode", treasuryNegotiationRequestEntity.getSalesCode());
                node.put("refCode",treasuryNegotiationRequestEntity.getRefCode());
                node.put("status", treasuryNegotiationRequestEntity.getStatus().toString());
                node.put("dateBooked", treasuryNegotiationRequestEntity.getDateBooked());

                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while fetching all negotiation requests", e);
        }
        return null;
    }

    @Override
    public boolean approveTradeRequest(TreasuryApproveTradeRequest model) {
        try {
            if (model == null) {
                return false;
            }
            TreasuryTradeRequestEntity treasuryTradeRequestEntity = treasuryTradeRequestRepository.findById(model.getRefCode()).orElse(null);
            if (treasuryTradeRequestEntity == null) {
                return false;
            }
            treasuryTradeRequestEntity.setApproved(true);
            treasuryTradeRequestRepository.save(treasuryTradeRequestEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving trade request", e);
        }
        return false;
    }

    @Override
    public boolean approveNegotiationRequest(TreasuryAppveNegRequest model) {
        try {
            if (model == null) {
                return false;
            }
            TreasuryNegotiationRequestEntity treasuryNegotiationRequestEntity = negotiationRequestRepository.findById(model.getRefCode()).orElse(null);
            if (treasuryNegotiationRequestEntity == null) {
                return false;
            }
            treasuryNegotiationRequestEntity.setApproved(true);
            negotiationRequestRepository.save(treasuryNegotiationRequestEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while approving trade request", e);
        }
        return false;
    }

    @Override
    public boolean assignLead(TreasuryAssignLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DSRAccountEntity dsrAccountsEntity = dsrAccountsRepository.findById(model.getDsrId()).orElse(null);
            if (dsrAccountsEntity == null) {
                return false;
            }
            TreasuryLeadEntity treasuryLeadEntity = treasuryLeadRepository.findById(model.getLeadId()).orElse(null);
            if (treasuryLeadEntity == null) {
                return false;
            }
            treasuryLeadEntity.setAssigned(true);
            treasuryLeadEntity.setPriority(model.getPriority());
            //set dsrAccId from dsrAccountsEntity
            treasuryLeadEntity.setDsrAccountEntity(dsrAccountsEntity);
            treasuryLeadRepository.save(treasuryLeadEntity);
            return true;



        } catch (Exception e) {
            log.error("Error occurred while assigning lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryLeadEntity treasuryLeadEntity : treasuryLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", treasuryLeadEntity.getId());
                objectNode.put("customerId", treasuryLeadEntity.getCustomerId());
                objectNode.put("businessUnit", treasuryLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", treasuryLeadEntity.getLeadStatus().toString());
                objectNode.put("createdOn",treasuryLeadEntity.getCreatedOn().getTime());
                objectNode.put("product",treasuryLeadEntity.getProduct());
                objectNode.put("dsrName",treasuryLeadEntity.getDsrName());
                objectNode.put("priority", treasuryLeadEntity.getPriority().toString());
//                objectNode.put("dsrId", treasuryLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }

    @Override
    public boolean createQuestionnaire(TreasuryAddQuestionnaireRequest model) {
        try {
            TreasuryQuestionnaireQuestionEntity treasuryQuestionnaireQuestionEntity = new TreasuryQuestionnaireQuestionEntity();
            treasuryQuestionnaireQuestionEntity.setQuestion(model.getQuestion());
            treasuryQuestionnaireQuestionEntity.setQuestionnaireDescription(model.getQuestionnaireDescription());

            treasuryQuestionnaireQuestionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            treasuryQuestionnaireQuestionRepository.save(treasuryQuestionnaireQuestionEntity);
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
            for (TreasuryQuestionnaireQuestionEntity treasuryQuestionnaireQuestionEntity : treasuryQuestionnaireQuestionRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", treasuryQuestionnaireQuestionEntity.getId());
                objectNode.put("question", treasuryQuestionnaireQuestionEntity.getQuestion());
                objectNode.put("questionnaireDescription", treasuryQuestionnaireQuestionEntity.getQuestionnaireDescription());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean createTarget(TreasuryAddTargetRequest model) {
        try {
            TreasuryTargetEntity treasuryTargetEntity = new TreasuryTargetEntity();
            treasuryTargetEntity.setTargetName(model.getTargetName());
            treasuryTargetEntity.setTargetSource(model.getTargetSource());
            treasuryTargetEntity.setTargetType(model.getTargetType());
            treasuryTargetEntity.setTargetDesc(model.getTargetDesc());
            treasuryTargetEntity.setTargetStatus(TargetStatus.ACTIVE);
            treasuryTargetEntity.setTargetValue(model.getTargetValue());
            treasuryTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            treasuryTargetRepository.save(treasuryTargetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating treasury target", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllTargets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryTargetEntity treasuryTargetEntity : treasuryTargetRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", treasuryTargetEntity.getId());
                objectNode.put("targetName", treasuryTargetEntity.getTargetName());
                objectNode.put("targetSource", treasuryTargetEntity.getTargetSource());
                objectNode.put("targetDesc", treasuryTargetEntity.getTargetDesc());
                objectNode.put("targetValue", treasuryTargetEntity.getTargetValue());
                objectNode.put("targetStatus", treasuryTargetEntity.getTargetStatus().ordinal());
                objectNode.put("agencyTargetType", treasuryTargetEntity.getTargetType().ordinal());
                objectNode.put("createdOn",treasuryTargetEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all targets", e);
        }
        return null;
    }

    @Override
    public boolean getDSRsInTarget(TreasuryDSRsInTargetRequest model) {
        //TODO implement this method
        return false;
    }

    @Override
    public List<ObjectNode> getTargetsSummary(TreasurySummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            //summarize for last 7 days
            {
                for (TreasuryTargetEntity treasuryTargetEntity : treasuryTargetRepository.fetchAllTargetsCreatedLast7Days()) {
                    ObjectNode asset = mapper.createObjectNode();
                    asset.put("id", treasuryTargetEntity.getId());
                    asset.put("targetName", treasuryTargetEntity.getTargetName());
                    asset.put("targetSource", treasuryTargetEntity.getTargetSource());
                    asset.put("TargetType", treasuryTargetEntity.getTargetType().ordinal());
                    asset.put("targetDesc", treasuryTargetEntity.getTargetDesc());
                    asset.put("targetStatus", treasuryTargetEntity.getTargetStatus().name());
                    asset.put("targetValue", treasuryTargetEntity.getTargetValue());
                    asset.put("createdOn", treasuryTargetEntity.getCreatedOn().toString());
                    //add to list
                    list.add(asset);
                }
                return list;
            }

        } catch (Exception e) {
            log.error("Error occurred while getting targets summary", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getLeadsSummary(TreasurySummaryRequest filters) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            {
                for (TreasuryLeadEntity treasuryLeadEntity : treasuryLeadRepository.findAll()) {
                    ObjectNode asset = mapper.createObjectNode();
                    //number of leads created
                    asset.put("lead_orginates",treasuryLeadRepository.countAllLeadsCreatedLast7Days());
                    asset.put("leads_assigned",treasuryLeadRepository.countAllLeadsCreatedLast7DaysAssigned());
                    asset.put("leads_open", treasuryLeadRepository.countAllLeadsCreatedLast7DaysOpen());
                    asset.put("leads_closed", treasuryLeadRepository.countAllLeadsCreatedLast7DaysClosed());
                    ObjectNode leadPriority = mapper.createObjectNode();
                    leadPriority.put("hot", treasuryLeadRepository.countAllLeadsCreatedLast7DaysHot());
                    leadPriority.put("warm", treasuryLeadRepository.countAllLeadsCreatedLast7DaysWarm());
                    leadPriority.put("cold", treasuryLeadRepository.countAllLeadsCreatedLast7DaysCold());
                    asset.set("lead_priority", leadPriority);
                    asset.put("lead_topic", treasuryLeadEntity.getTopic());
                    asset.put("lead_created_on", treasuryLeadEntity.getCreatedOn().getTime());
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
    public ObjectNode loadRequestsSummary(TreasurySummaryRequest filters) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objectNode = mapper.createObjectNode();
            {
                objectNode.put("total_requests", treasuryTradeRequestRepository.countAllRequestsCreatedLast7Days());
                objectNode.put("total_requests_assigned", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysAssigned());
                ObjectNode requestPriority = mapper.createObjectNode();
                requestPriority.put("high", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysHigh());
                requestPriority.put("medium", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysMedium());
                requestPriority.put("low", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysLow());
                objectNode.set("request_priority", requestPriority);
                ObjectNode requestStatus = mapper.createObjectNode();
                requestStatus.put("open", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysOpen());
                requestStatus.put("closed", treasuryTradeRequestRepository.countAllRequestsCreatedLast7DaysClosed());
                objectNode.set("request_status", requestStatus);
                return objectNode;
            }

        } catch (Exception e) {
            log.error("Error occurred while fetching requests summary", e);
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

            TreasuryTargetEntity target = treasuryTargetRepository.findById(model.getTargetId()).orElse(null);

            //conversion happen here
            Long userTargetVale = Long.parseLong(model.getTargetValue());
            //
            Long targetTargetVale = Long.parseLong(String.valueOf(target.getTargetValue()));
            //
            if (userTargetVale > targetTargetVale) {
                return false;
            }

            if (target.getTargetType().equals(TargetType.CAMPAINGS)) {
                user.setCampaignTargetValue(model.getTargetValue());
                user.setTreasuryTargetId(model.getTargetId());
            } if (target.getTargetType().equals(TargetType.LEADS)) {
                user.setLeadsTargetValue(model.getTargetValue());
                user.setTreasuryTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.VISITS)) {
                user.setVisitsTargetValue(model.getTargetValue());
                user.setTreasuryTargetId(model.getTargetId());
            }
            if (target.getTargetType().equals(TargetType.ONBOARDING)) {
                user.setOnboardTargetValue(model.getTargetValue());
                user.setTreasuryTargetId(model.getTargetId());
            }

            Set<TreasuryTargetEntity> treasuryTargetEntities = (Set<TreasuryTargetEntity>) user.getTreasuryTargetEntities();
            treasuryTargetEntities.add(target);
            user.setTreasuryTargetEntities(treasuryTargetEntities);
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

            TreasuryTargetEntity target = treasuryTargetRepository.findById(model.getTargetId()).orElse(null);

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

            Set<TreasuryTargetEntity> treasuryTargetEntities = (Set<TreasuryTargetEntity>) teamEntity.getTreasuryTargetEntities();
            treasuryTargetEntities.add(target);
            teamEntity.setTreasuryTargetEntities(treasuryTargetEntities);
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
            TreasuryTargetEntity treasuryTargetEntity = treasuryTargetRepository.findById(model.getId()).orElse(null);
            return treasuryTargetEntity;
        } catch (Exception e) {
            log.error("Error occurred while getting target by id", e);
        }

        return false;
    }

    @Override
    public boolean createCurrencyRate(TreasuryRateRequest model) {
        try {
            if (model == null) {
                return false;
            }
            TreasuryCurrencyEntity treasuryRateEntity = new TreasuryCurrencyEntity();
            treasuryRateEntity.setCurrencyCode(model.getCurrencyCode());
            treasuryRateEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            treasuryRateEntity.setCurrencyName(model.getCurrencyName());
            //save
            treasuryCurrencyRepository.save(treasuryRateEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating currency rate", e);
        }
        return false;
    }

    @Override
    public List<?> getAllCurrencyRates() {
        try {
            List<TreasuryCurrencyEntity> treasuryCurrencyEntities = treasuryCurrencyRepository.findAll();
            return treasuryCurrencyEntities;
        } catch (Exception e) {
            log.error("Error occurred while fetching all currency rates", e);
        }
        return null;
    }

    @Override
    public boolean editCurrencyRate(TreasuryRateRequest model) {
        try {
            if (model == null) {
                return false;
            }
            TreasuryCurrencyEntity treasuryRateEntity = treasuryCurrencyRepository.findById(model.getCurrencyId()).orElse(null);
            treasuryRateEntity.setCurrencyCode(model.getCurrencyCode());
            treasuryRateEntity.setCurrencyName(model.getCurrencyName());
            //save
            treasuryCurrencyRepository.save(treasuryRateEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while editing currency rate", e);
        }
        return false;
    }

    @Override
    public boolean updateCurrencyRate(TreasuryUpdateRequest model) {
        try {
            if (model == null) {
                return false;
            }
            TreasuryCurrencyEntity treasuryRateEntity = treasuryCurrencyRepository.findById(model.getCurrencyId()).orElse(null);
            treasuryRateEntity.setBuyRate(model.getBuyRate());
            treasuryRateEntity.setSellRate(model.getSellRate());
            //save
            treasuryCurrencyRepository.save(treasuryRateEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating currency rate", e);
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
            for (DSRAccountEntity dsrAccountEntity : dsrAccountsRepository.findByVoomaTargetId(model.getTargetId())) {
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
}
