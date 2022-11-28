package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingLeadEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingTargetEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsBYDSRRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSLeadEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSTargetEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSLeadRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSTargetRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsByDsrId;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSGetDSRLeadsRequest;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PSChannelService implements IPSChannelService{

    private  final PSCustomerVisitRepository psCustomerVisitRepository;
    private  final PSLeadRepository psLeadRepository;
    private final PSTargetRepository psTargetRepository;

    @Override
    public boolean createCustomerVisits(PSCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSCustomerVisitEntity psBankingVisitEntity = new PSCustomerVisitEntity();
            psBankingVisitEntity.setTypeOfVisit(model.getTypeOfVisit());
            psBankingVisitEntity.setChannel(model.getChannel());
            psBankingVisitEntity.setProductOffered(model.getProductOffered());
            psBankingVisitEntity.setOpportunityDiscussed(model.getOpportunityDiscussed());
            psBankingVisitEntity.setRemarks(model.getRemarks());
            psBankingVisitEntity.setStaffFromOtherDept(model.getStaffFromOtherDept());
            psBankingVisitEntity.setTimeTaken(model.getTimeTaken());
            psBankingVisitEntity.setDateOfAnotherVisit(model.getDateOfAnotherVisit());
            psBankingVisitEntity.setProductInvolvement(model.getProductInvolvement());
            psBankingVisitEntity.setCashManagementRep(model.getCashManagementRep());
            psBankingVisitEntity.setTradeRep(model.getTradeRep());
            psBankingVisitEntity.setCustodyRep(model.getCustodyRep());
            psBankingVisitEntity.setSnrCallRep(model.getSnrCallRep());
            psBankingVisitEntity.setCvpRep(model.getCvpRep());
            psBankingVisitEntity.setBancaRep(model.getBancaRep());
            psBankingVisitEntity.setTreasuryRep(model.getTreasuryRep());
            psBankingVisitEntity.setPeriodicRep(model.getPeriodicRep());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
            psBankingVisitEntity.setDsrName("test");
            psBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save customer visit
            psCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllDsrVisits(PSCustomerVisitsByDsrId model) {
        try {
            if (model == null){
                return  null;
            }
            List<PSCustomerVisitEntity> psBankingVisitEntities = psCustomerVisitRepository.findAllByDsrId(model.getDsrId());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            psBankingVisitEntities.forEach(psBankingVisitEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("visitId", psBankingVisitEntity.getId());
                objectNode.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                objectNode.put("customerName",psBankingVisitEntity.getCustomerName());
                objectNode.put("createdOn", psBankingVisitEntity.getCreatedOn().toString());
                objectNode.put("channel",psBankingVisitEntity.getChannel());
                objectNode.put("typeOfVisit",psBankingVisitEntity.getTypeOfVisit());
                objectNode.put("opportunityDiscussed",psBankingVisitEntity.getOpportunityDiscussed());
                objectNode.put("remarks",psBankingVisitEntity.getRemarks());
                objectNode.put("productOffered",psBankingVisitEntity.getProductOffered());
                objectNodeList.add(objectNode);
            });
            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits by DSR", e);
        }
        return null;

    }

    @Override
    public boolean createLead(PSAddLeadRequest model) {
        try {
            PSLeadEntity psLeadEntity = new PSLeadEntity();
            psLeadEntity.setCustomerName(model.getCustomerName());
            psLeadEntity.setBusinessUnit(model.getBusinessUnit());
            psLeadEntity.setPriority(model.getPriority());
            psLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            psLeadEntity.setTopic(model.getTopic());
            psLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psLeadRepository.save(psLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeads(PSGetDSRLeadsRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSLeadEntity psLeadEntity : psLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerId", psLeadEntity.getCustomerId());
                node.put("businessUnit", psLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(psLeadEntity.getLeadStatus()));
                node.put("topic", psLeadEntity.getTopic());
                node.put("priority", psLeadEntity.getPriority().ordinal());
                node.put("dsrId", psLeadEntity.getDsrId());
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
    public ArrayList<ObjectNode> getTargetSytem() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSTargetEntity psTargetEntity : psTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", psTargetEntity.getTargetAchievement());
                node.put("target", psTargetEntity.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (PSTargetEntity psBankingTargetEntity : psTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (PSTargetEntity psBankingTargetEntity : psTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (PSTargetEntity psBankingTargetEntity : psTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                onboardingNode.set("onboarding", node);
                list.add(onboardingNode);
            }
            //add to the list hard coded values for commission
            ObjectNode node = mapper.createObjectNode();
            ObjectNode commissionNode = mapper.createObjectNode();
            node.put("current-commission", 0);
            node.put("previous-commision", 0);
            commissionNode.set("commission", node);
            list.add(commissionNode);
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }
}

