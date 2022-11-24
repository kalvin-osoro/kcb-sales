package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAssignAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCollectAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAssetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
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
public class AgencyChannelService implements IAgencyChannelService {
    private final AgencyAssetRepository agencyAssetRepository;
    private final AgencyAssetFilesRepository agencyAssetFilesRepository;
    private final AgencyOnboardingRepository agencyOnboardingRepository;
    private final AgencyBankingLeadRepository agencyBankingLeadRepository;
    private final AgencyBankingTargetRepository agencyBankingTargetRepository;
    private final AgencyBankingVisitRepository agencyBankingVisitRepository;
    private final int totalTransaction = (int) (Math.random() * 1000000);

    @Override
    public boolean assignAsset(AgencyAssignAssetRequest model) {
        try {
            if (model ==null){
                return false;
            }
            AgencyOnboardingEntity agencyOnboardingEntity = agencyOnboardingRepository.findById(model.getCustomerId()).orElse(null);
            AgencyAssetEntity agencyAssetEntity =agencyAssetRepository.findById(model.getAssetId()).orElse(null);
            if (agencyOnboardingEntity == null || agencyAssetEntity == null){
                return false;
            }
            agencyAssetEntity.setAgencyOnboardingEntity(agencyOnboardingEntity);
            agencyAssetEntity.setAssigned(true);
            agencyAssetRepository.save(agencyAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning asset to agent ", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllAgentAssets(Long agentId) {
        try {
            //get all assets for merchant
            List<AgencyAssetEntity> agencyAssetEntities = agencyAssetRepository.findAllByAgentId(agentId);
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            agencyAssetEntities.forEach(dfsVoomaAssetEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("assetId", dfsVoomaAssetEntity.getId());
                objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
                objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().ordinal());
                objectNode.put("totalTransaction", totalTransaction);
                objectNodeList.add(objectNode);
            });

            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all agent assets ", e);
        }
        return null;
    }

    @Override
    public boolean recollectAsset(Long assetId) {
        try {
            if (assetId ==null){
                return false;
            }
            AgencyAssetEntity agencyAssetEntity =agencyAssetRepository.findById(assetId).orElse(null);
            if (agencyAssetEntity == null){
                return false;
            }
            agencyAssetEntity.setAgencyOnboardingEntity(null);
            agencyAssetEntity.setAssigned(false);
            agencyAssetRepository.save(agencyAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while recollecting asset from agent ", e);
        }
        return false;
    }

    @Override
    public boolean createLead(AgencyAddLeadRequest request) {
        try {
            AgencyBankingLeadEntity agencyBankingLeadEntity = new AgencyBankingLeadEntity();
            agencyBankingLeadEntity.setCustomerName(request.getCustomerName());
            agencyBankingLeadEntity.setBusinessUnit(request.getBusinessUnit());
            agencyBankingLeadEntity.setPriority(request.getPriority());
            agencyBankingLeadEntity.setCustomerAccountNumber(request.getCustomerAccountNumber());
            agencyBankingLeadEntity.setTopic(request.getTopic());
            agencyBankingLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeadsByDsrId(Long dsrId) {
        try {
            List<AgencyBankingLeadEntity> agencyBankingLeadEntities = agencyBankingLeadRepository.findAllByDsrId(dsrId);
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            agencyBankingLeadEntities.forEach(agencyBankingLeadEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("leadId", agencyBankingLeadEntity.getId());
                objectNode.put("customerName", agencyBankingLeadEntity.getCustomerName());
                objectNode.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                objectNode.put("priority", agencyBankingLeadEntity.getPriority().ordinal());
                objectNode.put("customerAccountNumber", agencyBankingLeadEntity.getCustomerAccountNumber());
                objectNode.put("topic", agencyBankingLeadEntity.getTopic());
                objectNode.put("createdOn", agencyBankingLeadEntity.getCreatedOn().toString());
                objectNodeList.add(objectNode);
            });

            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads for dsr ", e);
        }
        return null;
    }

    @Override
    public ArrayList<ObjectNode> getTargetsSummary() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingTargetEntity agencyBankingTarget : agencyBankingTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTarget.getTargetAchievement());
                node.put("target", agencyBankingTarget.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
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

    @Override
    public boolean createCustomerVisit(AgencyCustomerVisitsRequest model) {
        return false;
    }

}
