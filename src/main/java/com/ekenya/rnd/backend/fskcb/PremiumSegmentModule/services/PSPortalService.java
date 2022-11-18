package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringQuestionnaireQuestionEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.DFSVoomaQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingFeedBackEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSBankingConvenantEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSLeadEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.entity.PSQuestionerResponseEntity;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSBankingConvenantRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSLeadRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.datasource.repositories.PSQuestionerResponseRepository;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.*;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PSPortalService implements IPSPortalService {
    private final PSBankingConvenantRepository psBankingConvenantRepository;
    private final PSLeadRepository psLeadRepository;
    private final PSCustomerVisitRepository psCustomerVisitRepository;
    private  final PSQuestionerResponseRepository psQuestionerResponseRepository;

    @Override
    public boolean addTrackedCovenant(PSAddConvenantRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSBankingConvenantEntity psBankingConvenantEntity = new PSBankingConvenantEntity();
               psBankingConvenantEntity.setCustomerId(model.getCustomerId());
               psBankingConvenantEntity.setEndDate(model.getEndDate());
               psBankingConvenantEntity.setIntervalForCheck(model.getIntervalForCheck());
               psBankingConvenantEntity.setAlertMessage(model.getAlertMessage());
               psBankingConvenantEntity.setDsrId(model.getDsrId());
               psBankingConvenantEntity.setAlertBeforeExpiry(model.getAlertBeforeExpiry());

            psBankingConvenantEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            psBankingConvenantRepository.save(psBankingConvenantEntity);
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
            for (PSBankingConvenantEntity psBankingConvenantEntity : psBankingConvenantRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("customerId", psBankingConvenantEntity.getCustomerId());
                ObjectNode period = mapper.createObjectNode();
                period.put("startDate", psBankingConvenantEntity.getStartDate());
                period.put("endDate", psBankingConvenantEntity.getEndDate());
                objectNode.set("period", period);
                objectNode.put("intervalForCheck", psBankingConvenantEntity.getIntervalForCheck());
                objectNode.put("dsrId", psBankingConvenantEntity.getDsrId());
                objectNode.put("status", psBankingConvenantEntity.getStatus().toString());
                objectNode.put("createdOn", psBankingConvenantEntity.getCreatedOn().toString());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error getting all tracked covenants", e);
        }

        return null;
    }

    @Override
    public boolean assignLead(PSAddLeadRequest model) {
        try {
            PSLeadEntity psLeadEntity = psLeadRepository.findById(model.getLeadId()).orElse(null);
            psLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            psLeadEntity.setStartDate(model.getStartDate());
            psLeadEntity.setEndDate(model.getEndDate());
            //save
            psLeadRepository.save(psLeadEntity);
            //update is assigned to true
            psLeadEntity.setAssigned(true);
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
            for (PSLeadEntity psLeadEntity : psLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psLeadEntity.getId());
                objectNode.put("customerId", psLeadEntity.getCustomerId());
                objectNode.put("businessUnit", psLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", psLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", psLeadEntity.getTopic());
                objectNode.put("priority", psLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", psLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error getting all leads", e);
        }
        return null;
    }

    @Override
    public boolean scheduleCustomerVisit(PSCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            PSCustomerVisitEntity psCustomerVisitEntity = new PSCustomerVisitEntity();
            psCustomerVisitEntity.setCustomerName(model.getCustomerName());
            psCustomerVisitEntity.setVisitDate(model.getVisitDate());
            psCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psCustomerVisitEntity.setStatus(Status.ACTIVE);
            psCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psCustomerVisitEntity.setDsrName(model.getDsrName());
            //save
            psCustomerVisitRepository.save(psCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean rescheduleCustomerVisit(PSCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSCustomerVisitEntity psCustomerVisitEntity = psCustomerVisitRepository.findById(model.getId()).orElse(null);
            if (psCustomerVisitEntity == null) {
                return false;
            }
            psCustomerVisitEntity.setVisitDate(model.getVisitDate());
            psCustomerVisitRepository.save(psCustomerVisitEntity);
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
            for (PSCustomerVisitEntity psCustomerVisitEntity : psCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psCustomerVisitEntity.getId());
                objectNode.put("customerName", psCustomerVisitEntity.getCustomerName());
                objectNode.put("visitDate", psCustomerVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", psCustomerVisitEntity.getReasonForVisit());
                objectNode.put("status", psCustomerVisitEntity.getStatus().toString());
                objectNode.put("createdOn", psCustomerVisitEntity.getCreatedOn().toString());
                objectNode.put("dsrName", psCustomerVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getCustomerVisitQuestionnaireResponses(PSCustomerVisitQuestionnaireRequest model) {
        try {//get question Response by visit id and question id
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSQuestionerResponseEntity psQuestionerResponseEntity : psQuestionerResponseRepository.findAllByVisitIdAndQuestionId(model.getVisitId(), model.getQuestionId())) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", psQuestionerResponseEntity.getId());
                objectNode.put("questionId", psQuestionerResponseEntity.getQuestionId());
                objectNode.put("questionResponse", psQuestionerResponseEntity.getResponse());
                objectNode.put("visitId", psQuestionerResponseEntity.getVisitId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting customer visit questionnaire responses", e);
        }
        return null;
    }


}
