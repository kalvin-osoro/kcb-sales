package com.ekenya.rnd.backend.fskcb.TreasuryModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryCallReportEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryNegotiationRequestEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryTradeRequestEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.TreasuryCallReportRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.TreasuryLeadRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.TreasuryNegotiationRequestRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.repositories.TreasuryTradeRequestRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.*;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TreasuryChannelService implements ITreasuryChannelService {
    private final TreasuryLeadRepository treasuryLeadRepository;
    private final TreasuryNegotiationRequestRepository treasuryNegotiationRequestRepository;
    private final TreasuryTradeRequestRepository treasuryTradeRequestRepository;
    private final TreasuryCallReportRepository treasuryCallReportRepository;

    @Override
    public boolean attemptCreateLead(TreasuryAddLeadRequest model) {

        try {
            TreasuryLeadEntity treasuryLeadEntity = new TreasuryLeadEntity();
            treasuryLeadEntity.setCustomerName(model.getCustomerName());
            treasuryLeadEntity.setBusinessUnit(model.getBusinessUnit());
            treasuryLeadEntity.setEmail(model.getEmail());
            treasuryLeadEntity.setPhoneNumber(model.getPhoneNumber());
            treasuryLeadEntity.setProduct(model.getProduct());
            treasuryLeadEntity.setPriority(model.getPriority());
            treasuryLeadEntity.setDsrId(model.getDsrId());
            treasuryLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            treasuryLeadEntity.setTopic(model.getTopic());
            treasuryLeadEntity.setLeadStatus(LeadStatus.OPEN);
            treasuryLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            treasuryLeadRepository.save(treasuryLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryLeadEntity treasuryLeadEntity : treasuryLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", treasuryLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", treasuryLeadEntity.getPriority().toString());
                node.put("businessUnit", treasuryLeadEntity.getBusinessUnit());
                node.put("leadId", treasuryLeadEntity.getId());
                node.put("leadStatus", treasuryLeadEntity.getLeadStatus().ordinal());
                node.put("createdOn", treasuryLeadEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;

    }

    @Override
    public ArrayNode getForexRates() {
        //use currecy exchange api to get forex rates use web client
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("currency", "USD");
            node.put("buying", 100);
            node.put("selling", 100);
            arrayNode.add(node);
            return arrayNode;
        } catch (Exception e) {
            log.error("Error occurred while loading forex rates", e);
        }
        return null;
    }

    @Override
    public ArrayNode loadForexCounterRates() {
        try {
            //use currecy exchange  buy and sell rates
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();
            ObjectNode node = mapper.createObjectNode();
            node.put("currency", "USD");
            node.put("buyRate", 100);
            node.put("sellRate", 100);
            arrayNode.add(node);
                return arrayNode;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean attemptCreateTradeRequest(TreasuryTradeRequest model) {
        try {
            if (model==null){
                return false;
            }
                TreasuryTradeRequestEntity treasuryTradeRequestEntity = new TreasuryTradeRequestEntity();
                treasuryTradeRequestEntity.setCustomerName(model.getCustomerName());
                treasuryTradeRequestEntity.setCustomerID(model.getCustomerID());
                treasuryTradeRequestEntity.setNatureOfTheBusiness(model.getNatureOfTheBusiness());
                treasuryTradeRequestEntity.setCustomerSegment(model.getCustomerSegment());
                treasuryTradeRequestEntity.setMonthlyTurnover(model.getMonthlyTurnover());
                treasuryTradeRequestEntity.setDealingFrequency(model.getDealingFrequency());
                treasuryTradeRequestEntity.setPeriod(model.getPeriod());
                treasuryTradeRequestEntity.setTreasuryPriority(model.getTreasuryPriority());
                treasuryTradeRequestEntity.setCurrency(model.getCurrency());
                treasuryTradeRequestEntity.setAmount(model.getAmount());
                treasuryTradeRequestEntity.setDateBooked(model.getDateBooked());
                treasuryTradeRequestEntity.setMethodOfTransaction(model.getMethodOfTransaction());
                treasuryTradeRequestEntity.setBranchName(model.getBranchName());
                treasuryTradeRequestEntity.setPhoneNumber(model.getPhoneNumber());
                treasuryTradeRequestEntity.setDealerName(model.getDealerName());
                treasuryTradeRequestEntity.setSector(model.getSector());
                //save
                treasuryTradeRequestRepository.save(treasuryTradeRequestEntity);
                return true;
            } catch (Exception e) {
                log.error("Error occurred while creating trade request", e);
            }

        return false;
    }


    @Override
    public List<ObjectNode> loadAllDSRTradeReqs(TreasuryGetDSRTradeRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryTradeRequestEntity treasuryTradeRequestEntity : treasuryTradeRequestRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", treasuryTradeRequestEntity.getCustomerName());
                node.put("customerID", treasuryTradeRequestEntity.getCustomerID());
                node.put("natureOfTheBusiness", treasuryTradeRequestEntity.getNatureOfTheBusiness());
                node.put("customerSegment", treasuryTradeRequestEntity.getCustomerSegment());
                node.put("monthlyTurnover", treasuryTradeRequestEntity.getMonthlyTurnover());
                node.put("dealingFrequency", treasuryTradeRequestEntity.getDealingFrequency());
                node.put("period", treasuryTradeRequestEntity.getPeriod());
                node.put("treasuryPriority", treasuryTradeRequestEntity.getTreasuryPriority().ordinal());
                node.put("currency", treasuryTradeRequestEntity.getCurrency());
                node.put("amount", treasuryTradeRequestEntity.getAmount());
                node.put("dateBooked", treasuryTradeRequestEntity.getDateBooked());
                node.put("methodOfTransaction", treasuryTradeRequestEntity.getMethodOfTransaction());
                node.put("branchName", treasuryTradeRequestEntity.getBranchName());
                node.put("dsrId", treasuryTradeRequestEntity.getDsrId());
                node.put("approved", treasuryTradeRequestEntity.isApproved());
                //add to list
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading trade requests", e);
        }
        return null;
    }

    @Override
    public boolean attemptCreateNegotiationRequest(TreasuryNegRequest request) {
        try {
            if (request==null){
                return false;
            }
                TreasuryNegotiationRequestEntity treasuryNegotiationRequestEntity = new TreasuryNegotiationRequestEntity();
                treasuryNegotiationRequestEntity.setCustomerName(request.getCustomerName());
                treasuryNegotiationRequestEntity.setCustomerID(request.getCustomerID());
                treasuryNegotiationRequestEntity.setNatureOfBusiness(request.getNatureOfBusiness());
                treasuryNegotiationRequestEntity.setCustomerSegment(request.getCustomerSegment());
                treasuryNegotiationRequestEntity.setPriority(request.getPriority());
                treasuryNegotiationRequestEntity.setCurrency(request.getCurrency());
                treasuryNegotiationRequestEntity.setPhoneNumber(request.getPhoneNumber());
                treasuryNegotiationRequestEntity.setSector(request.getSector());
                treasuryNegotiationRequestEntity.setAmount(request.getAmount());
                treasuryNegotiationRequestEntity.setDateBooked(request.getDateBooked());
                treasuryNegotiationRequestRepository.save(treasuryNegotiationRequestEntity);
                return true;
            } catch (Exception e) {
                log.error("Error occurred while creating negotiation request", e);
            }
        return false;
    }



    @Override
    public ArrayNode loadDSRNegotiationRequests() {
        return null;
    }

    @Override
    public ObjectNode targetsSummary() {
        return null;
    }

    @Override
    public boolean attemptScheduleCustomerVisit(TreasuryCustomerVisitsRequest request) {
        return false;
    }

    @Override
    public boolean attemptRescheduleCustomerVisit(TreasuryCustomerVisitsRequest request) {
        return false;
    }

    @Override
    public ArrayNode loadCustomerVisits() {
        return null;
    }

    @Override
    public ArrayNode loadCustomerVisitQuestionnaireResponses(TreasuryCustomerCallReportRequest request) {
        return null;
    }

    @Override
    public ObjectNode searchCustomer(String query) {
        return null;
    }

    @Override
    public ObjectNode getNearbyCustomers(AcquiringNearbyCustomersRequest request) {
        return null;
    }

    @Override
    public ObjectNode loadSummary() {
        return null;
    }

    @Override
    public boolean createCallReport(TreasuryCustomerCallReportRequest model) {
        try {
            if (model==null){
                return false;
            }
            TreasuryCallReportEntity treasuryCustomerCallReportEntity = new TreasuryCallReportEntity();
            treasuryCustomerCallReportEntity.setSupplierName(model.getSupplierName());
            treasuryCustomerCallReportEntity.setTheirCustomers(model.getTheirCustomers());
            treasuryCustomerCallReportEntity.setFrequency(model.getFrequency());
            treasuryCustomerCallReportEntity.setMonthlyFx(model.getMonthlyFx());
            treasuryCustomerCallReportEntity.setCurrencyOfExpense(model.getCurrencyOfExpense());
            treasuryCustomerCallReportEntity.setCurrencyOfRelivable(model.getCurrencyOfRelivable());
            treasuryCustomerCallReportEntity.setManageRiskAndCashFlow(model.getManageRiskAndCashFlow());
            treasuryCustomerCallReportEntity.setAgreedSalesMargin(model.getAgreedSalesMargin());
            treasuryCustomerCallReportEntity.setRemarks(model.getRemarks());
            //save
            treasuryCallReportRepository.save(treasuryCustomerCallReportEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating call report", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (TreasuryLeadEntity treasuryLeadEntity : treasuryLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", treasuryLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", treasuryLeadEntity.getPriority().toString());
                node.put("businessUnit", treasuryLeadEntity.getBusinessUnit());
                node.put("leadId", treasuryLeadEntity.getId());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;
    }

    @Override
    public boolean attemptUpdateLead(TreasuryUpdateLeadRequest model) {
        try {
            if (model==null){
                return false;
            }
            TreasuryLeadEntity treasuryLeadEntity = treasuryLeadRepository.findById(model.getLeadId()).orElse(null);
            treasuryLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            treasuryLeadEntity.setLeadStatus(model.getLeadStatus());
            treasuryLeadRepository.save(treasuryLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return false;
    }

}
