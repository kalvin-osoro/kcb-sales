package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.TargetStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreasuryPortalService implements ITreasuryPortalService {
    private final TreasuryTradeRequestRepository treasuryTradeRequestRepository;
    private final TreasuryNegotiationRequestRepository negotiationRequestRepository;
    private final TreasuryLeadRepository treasuryLeadRepository;
    private final TreasuryTargetRepository treasuryTargetRepository;
    private final TreasuryQuestionnaireQuestionRepository treasuryQuestionnaireQuestionRepository;


    @Override
    public List<ObjectNode> getAllNegotiationReqs() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryTradeRequestEntity treasuryTradeRequestEntity : treasuryTradeRequestRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", treasuryTradeRequestEntity.getId());
                node.put("customerName", treasuryTradeRequestEntity.getCustomerName());
                node.put("customerID", treasuryTradeRequestEntity.getCustomerID());
                node.put("Amount", treasuryTradeRequestEntity.getAmount());
                node.put("currency", treasuryTradeRequestEntity.getCurrency());
                node.put("priority", treasuryTradeRequestEntity.getTreasuryPriority().ordinal());
                node.put("salesCode", treasuryTradeRequestEntity.getSalesCode());
                node.put("status", treasuryTradeRequestEntity.getStatus().toString());
                node.put("dateBooked", treasuryTradeRequestEntity.getDateBooked());

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
            TreasuryLeadEntity treasuryLeadEntity = treasuryLeadRepository.findById(model.getLeadId()).orElse(null);
            treasuryLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            treasuryLeadEntity.setStartDate(model.getStartDate());
            treasuryLeadEntity.setEndDate(model.getEndDate());
            //save
            treasuryLeadRepository.save(treasuryLeadEntity);
            //update is assigned to true
            treasuryLeadEntity.setAssigned(true);
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
            for (TreasuryLeadEntity treasuryLeadEntity : treasuryLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", treasuryLeadEntity.getId());
                objectNode.put("customerId", treasuryLeadEntity.getCustomerId());
                objectNode.put("businessUnit", treasuryLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", treasuryLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", treasuryLeadEntity.getTopic());
                objectNode.put("priority", treasuryLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", treasuryLeadEntity.getDsrId());
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


}
