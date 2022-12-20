package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.ConcessionStatus;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.entites.*;
import com.ekenya.rnd.backend.fskcb.RetailModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.ChangeConvenantStatus;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.ekenya.rnd.backend.utils.ConcessionTrackingStatus;
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
public class RetailPortalService implements IRetailPortalService {
    private final RetailLeadRepository retailLeadRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;
    private final RetailConcessionRepository retailConcessionRepository;
    private final RetailConvenantTrackingRepository retailConvenantTrackingRepository;
    private final RetailJustificationRepository retailJustificationRepository;
    private final RetailRevenueLineRepository retailRevenueLineRepository;


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
            RetailLeadEntity retailLeadEntity = retailLeadRepository.findById(model.getLeadId()).orElse(null);
            if (retailLeadEntity == null) {
                return false;
            }
            retailLeadEntity.setAssigned(true);
            retailLeadEntity.setPriority(model.getPriority());
            //set dsrAccId from dsrAccountsEntity
            retailLeadEntity.setDsrAccountEntity(dsrAccountsEntity);
            retailLeadRepository.save(retailLeadEntity);
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
            for (RetailLeadEntity retailLeadEntity : retailLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", retailLeadEntity.getId());
                objectNode.put("customerId", retailLeadEntity.getCustomerId());
                objectNode.put("businessUnit", retailLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", retailLeadEntity.getLeadStatus().toString());
                objectNode.put("createdOn",retailLeadEntity.getCreatedOn().getTime());
                objectNode.put("product",retailLeadEntity.getProduct());
                objectNode.put("dsrName",retailLeadEntity.getDsrName());
                objectNode.put("priority", retailLeadEntity.getPriority().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }

    @Override
    public boolean addConcession(CBConcessionRequest model) {
        try {
            if (model == null) {
                return false;
            }
            RetailConcessionEntity cbConcessionEntity = new RetailConcessionEntity();
            cbConcessionEntity.setCustomerName(model.getCustomerName());
            cbConcessionEntity.setSubmittedBy(model.getSubmittedBy());
            cbConcessionEntity.setSubmissionDate(model.getSubmissionDate());
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.PENDING);
            cbConcessionEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            retailConcessionRepository.save(cbConcessionEntity);
            List<CBRevenueLineRequest> revenueLineRequestList = model.getCbRevenueLineRequests();
            for (CBRevenueLineRequest revenueLineRequest : revenueLineRequestList) {
                RetailRevenueLineEntity cbRevenueLineEntity = new RetailRevenueLineEntity();
                cbRevenueLineEntity.setSSRrate(revenueLineRequest.getSsrcRate());
                cbRevenueLineEntity.setRecommendedRate(revenueLineRequest.getRecommendedRate());
                cbRevenueLineEntity.setRevenueLineType(revenueLineRequest.getRevenueLineType());
                cbRevenueLineEntity.setBaseAmount(revenueLineRequest.getBaseAmount());
                cbRevenueLineEntity.setDuration(revenueLineRequest.getDuration());
                cbRevenueLineEntity.setRetailConcessionEntity(cbConcessionEntity);//
                retailRevenueLineRepository.save(cbRevenueLineEntity);
            }
            List<CBJustificationRequest> justificationRequestList = model.getCbJustificationRequests();
            for (CBJustificationRequest justificationRequest : justificationRequestList) {
                RetailJustificationEntity cbJustificationEntity = new RetailJustificationEntity();
                cbJustificationEntity.setJustification(justificationRequest.getJustification());
                cbJustificationEntity.setMonitoringMechanism(justificationRequest.getMonitoringMechanism());
                cbJustificationEntity.setStakeholder(justificationRequest.getStakeholder());
                cbJustificationEntity.setRetailConcessionEntity(cbConcessionEntity);
                retailJustificationRepository.save(cbJustificationEntity);
            }
            return true;
        } catch (Exception e) {
            log.error("Error occurred while adding concession", e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> getAllConcessions() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (RetailConcessionEntity cbConcessionEntity : retailConcessionRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id",cbConcessionEntity.getId());
                node.put("concessionStatus", cbConcessionEntity.getConcessionStatus().toString());
                node.put("createdOn", cbConcessionEntity.getCreatedOn().getTime());
                node.put("submittedBy",cbConcessionEntity.getSubmittedBy());
                node.put("customerName",cbConcessionEntity.getCustomerName());
                node.put("submissionDate",cbConcessionEntity.getSubmissionDate());

                List<RetailRevenueLineEntity> cbRevenueLineEntityList = cbConcessionEntity.getRetailRevenueLineEntityList();
                ArrayNode arrayNode = mapper.createArrayNode();
                for (RetailRevenueLineEntity cbRevenueLineEntity : cbRevenueLineEntityList) {
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
            RetailConvenantTrackingEntoity cbBankingConvenantEntity = new RetailConvenantTrackingEntoity();
            cbBankingConvenantEntity.setCustomerName(model.getCustomerName());
            cbBankingConvenantEntity.setEndDate(model.getEndDate());
            cbBankingConvenantEntity.setIntervalForCheck(model.getIntervalForCheck());
            cbBankingConvenantEntity.setAlertMessage(model.getAlertMessage());
            cbBankingConvenantEntity.setAlertBeforeExpiry(model.getAlertBeforeExpiry());

            cbBankingConvenantEntity.setStatus(ConcessionTrackingStatus.GREEN);
            cbBankingConvenantEntity.setReferenceNumber(model.getReferenceNumber());

            cbBankingConvenantEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            retailConvenantTrackingRepository.save(cbBankingConvenantEntity);
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
            retailConvenantTrackingRepository.findById(model.getId()).ifPresent(covenant -> {
                        covenant.setStatus(model.getStatus());
                        //save
                        retailConvenantTrackingRepository.save(covenant);
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
            for (RetailConvenantTrackingEntoity cbBankingConvenantEntity : retailConvenantTrackingRepository.findAll()) {

                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", cbBankingConvenantEntity.getId());
                objectNode.put("customerName", cbBankingConvenantEntity.getCustomerName());
                ObjectNode period = mapper.createObjectNode();
                period.put("endDate", cbBankingConvenantEntity.getEndDate());
                period.put("createdOn",cbBankingConvenantEntity.getCreatedOn().toString());
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
    public boolean approveCBConcession(CBApproveConcessionRequest model) {
        try {
            RetailConcessionEntity cbConcessionEntity = retailConcessionRepository.findById(model.getConcessionId()).get();
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.APPROVED);
            cbConcessionEntity.setApproved(true);
            retailConcessionRepository.save(cbConcessionEntity);
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
                return false;
            }
            RetailConcessionEntity cbConcessionEntity = retailConcessionRepository.findById(model.getConcessionId()).get();
            model.getEmailUrl();
            return true;
        } catch (Exception e) {
            log.error("Error occurred while sending email for approval", e);
        }
        return false;
    }

    @Override
    public boolean rejectCBConcession(CBApproveConcessionRequest model) {
        try {
            RetailConcessionEntity cbConcessionEntity = retailConcessionRepository.findById(model.getConcessionId()).get();
            cbConcessionEntity.setConcessionStatus(ConcessionStatus.REJECTED);
            cbConcessionEntity.setApproved(false);
            retailConcessionRepository.save(cbConcessionEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rejecting concession", e);
        }
        return false;
    }

}
