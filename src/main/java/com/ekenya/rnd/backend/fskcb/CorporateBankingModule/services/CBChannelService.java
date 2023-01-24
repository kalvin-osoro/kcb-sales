package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.CBGetLeadsByDsrIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
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

@Service
@RequiredArgsConstructor
public class CBChannelService implements ICBChannelService {
    private final ICBLeadsRepository cbLeadsRepository;
    private final CBOpportunitiesRepository cbOpportunitiesRepository;
    private final CBCustomerAppointmentRepository cbCustomerAppointmentRepository;
    private final CBConcessionRepository cbConcessionRepository;
    private final CBCustomerVisitRepository cbCustomerVisitRepository;
    private final CBBankingConvenantRepository cbBankingConvenantRepository;
    private final CBTargetRepository cbTargetRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;

    private final double commission = Math.round(Math.random() * 1000000*1.35)/100.0;
    private final double preCommission = Math.round(Math.random() * 1000000*1.35)/100.0;





    @Override
    public boolean createCustomerVisit(CBCustomerVisitsRequest request) {
        //create customer visit
        try {
            if (request == null) {
                return false;
            }
            CBCustomerVisitEntity cbCustomerVisitEntity = new CBCustomerVisitEntity();
            cbCustomerVisitEntity.setTypeOfVisit(request.getTypeOfVisit());
            cbCustomerVisitEntity.setStage(request.getStage());
            cbCustomerVisitEntity.setReasonForVisit(request.getReasonForVisit());
            cbCustomerVisitEntity.setProbality(request.getProbality());
            cbCustomerVisitEntity.setRegion(request.getRegion());
            cbCustomerVisitEntity.setChannel(request.getChannel());
            cbCustomerVisitEntity.setProspect(request.getProspect());
            cbCustomerVisitEntity.setExpectedAmount(request.getExpectedAmount());
            cbCustomerVisitEntity.setDsrId(request.getDsrId());
            cbCustomerVisitEntity.setCustomerName(request.getCustomerName());
            cbCustomerVisitEntity.setVisitDate(request.getVisitDate());
            cbCustomerVisitEntity.setProductOffered(request.getProductOffered());
            cbCustomerVisitEntity.setOpportunities(request.getOpportunities());
            cbCustomerVisitEntity.setRemarks(request.getRemarks());
            cbCustomerVisitEntity.setStaffOfOtherDepartmentPresent(request.isStaffOfOtherDepartmentPresent());
            cbCustomerVisitEntity.setTimeSpent(request.getTimeSpent());
            cbCustomerVisitEntity.setNextVisitDate(request.getNextVisitDate());
            cbCustomerVisitEntity.setProductInvolvement(request.getProductInvolvement());
            cbCustomerVisitEntity.setCashManagement(request.getCashManagement());
            cbCustomerVisitEntity.setTradeRepresentation(request.getTradeRepresentation());
            cbCustomerVisitEntity.setCustodyRepresentation(request.getCustodyRepresentation());
            cbCustomerVisitEntity.setSnrCallRep(request.getSnrCallRep());
            cbCustomerVisitEntity.setCVPRep(request.getCVPRep());
            cbCustomerVisitEntity.setBancaRep(request.getBancaRep());
            cbCustomerVisitEntity.setTreasuryRep(request.getTreasuryRep());
            cbCustomerVisitEntity.setPeriodic(request.getPeriodic());
            cbCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbCustomerVisitRepository.save(cbCustomerVisitEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisitsByDSR(CBCustomerVisitsRequest model) {
        //get all customer visits by dsr id
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBCustomerVisitEntity cbCustomerVisitEntity : cbCustomerVisitRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                   node.put("customerName", cbCustomerVisitEntity.getCustomerName());
                   node.put("typeOfVisit", cbCustomerVisitEntity.getTypeOfVisit());
                   node.put("channel", cbCustomerVisitEntity.getChannel());
                   node.put("productOffered", cbCustomerVisitEntity.getProductOffered());
                   node.put("opportunities", cbCustomerVisitEntity.getOpportunities());
                   node.put("remarks", cbCustomerVisitEntity.getRemarks());
                   node.put("staffOfOtherDepartmentPresent", cbCustomerVisitEntity.isStaffOfOtherDepartmentPresent());
                   node.put("timeSpent", cbCustomerVisitEntity.getTimeSpent());
                   node.put("productInvolvement", cbCustomerVisitEntity.getProductInvolvement());
                   node.put("cashManagement", cbCustomerVisitEntity.getCashManagement());
                   node.put("tradeRepresentation", cbCustomerVisitEntity.getTradeRepresentation());
                   node.put("custodyRepresentation", cbCustomerVisitEntity.getCustodyRepresentation());
                   node.put("snrCallRep", cbCustomerVisitEntity.getSnrCallRep());
                   node.put("cVPRep", cbCustomerVisitEntity.getCVPRep());
                   node.put("bancaRep", cbCustomerVisitEntity.getBancaRep());
                   node.put("treasuryRep", cbCustomerVisitEntity.getTreasuryRep());
                   node.put("periodic", cbCustomerVisitEntity.getPeriodic());

                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public ArrayList<ObjectNode> getTargetsSummary() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBTargetEntity cbTargetEntity : cbTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", cbTargetEntity.getTargetAchievement());
                node.put("target", cbTargetEntity.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (CBTargetEntity cbTargetEntity : cbTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", cbTargetEntity.getTargetAchievement());
                node.put("target", cbTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (CBTargetEntity cbTargetEntity : cbTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", cbTargetEntity.getTargetAchievement());
                node.put("target", cbTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (CBTargetEntity cbTargetEntity : cbTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", cbTargetEntity.getTargetAchievement());
                node.put("target", cbTargetEntity.getTargetValue());
                onboardingNode.set("onboarding", node);
                list.add(onboardingNode);
            }
           //add to the list hard coded values for commission
            ObjectNode node = mapper.createObjectNode();
            ObjectNode commissionNode = mapper.createObjectNode();
            node.put("current-commission", commission);
            node.put("previous-commision", preCommission);
            commissionNode.set("commission", node);
            list.add(commissionNode);
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllCustomerCovenants(CBCustomerConcession model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBBankingConvenantEntity cbBankingConvenantEntity : cbBankingConvenantRepository.findByCustomerId(model.getCustomerId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("referenceNumber", cbBankingConvenantEntity.getReferenceNumber());
                node.put("customerName", cbBankingConvenantEntity.getCustomerName());
                node.put("expiryDate", cbBankingConvenantEntity.getEndDate());
                node.put("condition", cbBankingConvenantEntity.getConvenantCondition());
                node.put("baseAmount", 789000);
                node.put("status", cbBankingConvenantEntity.getStatus().toString());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }

        return null;
    }

    @Override
    public boolean createCustomerAppointment(CBAppointmentRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBCustomerAppointment cbAppointmentEntity = new CBCustomerAppointment();
            cbAppointmentEntity.setCustomerName(model.getCustomerName());
            cbAppointmentEntity.setCustomerPhoneNumber(model.getCustomerPhoneNumber());
            cbAppointmentEntity.setTypeOfAppointment(model.getTypeOfAppointment());
            cbAppointmentEntity.setAppointmentDate(model.getAppointmentDate());
            cbAppointmentEntity.setAppointmentTime(model.getAppointmentTime());
//            cbAppointmentEntity.setAppointmentStatus(AppointmentStatus.PENDING);
            cbAppointmentEntity.setDuration(model.getDuration());
//            cbAppointmentEntity.setDsrAccountEntity(dsrAccountsRepository.findById(model.getDsrId()).get());
            cbAppointmentEntity.setDsrId(model.getDsrId());
            cbAppointmentEntity.setReasonForVisit(model.getReasonForVisit());
            cbAppointmentEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

            cbCustomerAppointmentRepository.save(cbAppointmentEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer appointment", e);
        }
        return true;
    }

    @Override
    public List<ObjectNode> getCustomerAppointmentByDSRIdAndDate(CBAppointmentDateRequest model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBCustomerAppointment cbAppointmentEntity : cbCustomerAppointmentRepository.findAllByDsrIdAndAppointmentDate(model.getDsrId(), model.getAppointmentDate())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id",cbAppointmentEntity.getId());
                node.put("customerName", cbAppointmentEntity.getCustomerName());
                node.put("customerPhoneNumber", cbAppointmentEntity.getCustomerPhoneNumber());
                node.put("typeOfAppointment", cbAppointmentEntity.getTypeOfAppointment());
                node.put("appointmentDate", cbAppointmentEntity.getAppointmentDate());
                node.put("appointmentTime", cbAppointmentEntity.getAppointmentTime());
                node.put("duration", cbAppointmentEntity.getDuration());
                node.put("reasonForVisit", cbAppointmentEntity.getReasonForVisit());
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public boolean updateCustomerAppointment(CBAppointmentUpdateRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBCustomerAppointment cbAppointmentEntity = cbCustomerAppointmentRepository.findById(model.getAppointmentId()).get();
            cbAppointmentEntity.setAppointmentDate(model.getAppointmentDate());
            cbAppointmentEntity.setAppointmentTime(model.getAppointmentTime());
            cbCustomerAppointmentRepository.save(cbAppointmentEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while updating customer appointment", e);
        }
        return false;
    }

    @Override
    public boolean attemptCreateLead(TreasuryAddLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CBLeadEntity cbLeadEntity = new CBLeadEntity();
            cbLeadEntity.setCustomerName(model.getCustomerName());
            cbLeadEntity.setBusinessUnit(model.getBusinessUnit());
            cbLeadEntity.setEmail(model.getEmail());
            cbLeadEntity.setPhoneNumber(model.getPhoneNumber());
            cbLeadEntity.setProduct(model.getProduct());
            cbLeadEntity.setPriority(model.getPriority());
            cbLeadEntity.setDsrId(model.getDsrId());
            cbLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            cbLeadEntity.setTopic(model.getTopic());
            cbLeadEntity.setLeadStatus(LeadStatus.OPEN);
            cbLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            cbLeadsRepository.save(cbLeadEntity);
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
            for (CBLeadEntity cbLeadEntity : cbLeadsRepository.findAllByDsrIdAndAssigned(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", cbLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", cbLeadEntity.getPriority().toString());
                node.put("businessUnit", cbLeadEntity.getBusinessUnit());
                node.put("leadId", cbLeadEntity.getId());
                node.put("leadStatus", cbLeadEntity.getLeadStatus().toString());
                node.put("createdOn", cbLeadEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;

    }


    @Override
    public List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (CBLeadEntity cbLeadEntity : cbLeadsRepository.findAllAssignedLeadByDSRId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", cbLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", cbLeadEntity.getPriority().toString());
                node.put("businessUnit", cbLeadEntity.getBusinessUnit());
                node.put("leadId", cbLeadEntity.getId());
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
            CBLeadEntity cbLeadEntity = cbLeadsRepository.findById(model.getLeadId()).orElse(null);
            cbLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            cbLeadEntity.setLeadStatus(model.getLeadStatus());
            cbLeadsRepository.save(cbLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return false;
    }

    @Override
    public ArrayNode getDSRSummary(DSRSummaryRequest model) {
        try {
            if (model == null) {
                return null;
            }
            ArrayNode arrayNode = new ObjectMapper().createArrayNode();
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            short commission=0;
            short targetVolumes=0;
            short volumesDone=0;
            objectNode.put("commission", commission);
            int totalVisits = cbCustomerVisitRepository.countTotalVisits(model.getDsrId());
            objectNode.put("customer-visits", totalVisits);
            if (totalVisits == 0) {
                objectNode.put("customer-visits", 0);
            }
            int totalAssignedLeads = cbLeadsRepository.countTotalAssignedLeads(model.getDsrId());
            objectNode.put("assigned-leads", totalAssignedLeads);
            if (totalAssignedLeads == 0) {
                objectNode.put("assigned-leads", 0);
            }
            objectNode.put("targetVolumes",targetVolumes);
            objectNode.put("actualVolumes",volumesDone);
            if (targetVolumes==0){
                objectNode.put("percentage",0);
            }else {
                objectNode.put("percentage", (volumesDone/targetVolumes)*100);
            }
            arrayNode.add(objectNode);
            return arrayNode;
        } catch (Exception e) {
            log.error("Error occurred while getting dsr summary", e);
        }
        return null;
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
                objectNode.put("name", cbOpportunitiesEntity.getProduct());
                objectNode.put("stage", cbOpportunitiesEntity.getStage().toString());
                objectNode.put("probability", cbOpportunitiesEntity.getProbability());
                objectNode.put("createdOn", cbOpportunitiesEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all opportunities", e);
        }
        return null;
    }
}


//    @Override
//    public List<?> getAllCustomerAppointment() {
//        return null;
//    }
//
//    @Override
//    public boolean createCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
//        return false;
//    }
//
//    @Override
//    public boolean editCustomerAppointment(CustomerAppointementRequest customerAppointementRequest) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteCustomerAppointment(long id) {
//        return false;
//    }
//
//    @Override
//    public List<?> getAllAppointmentByDsrId(long dsrId) {
//        return null;
//    }

