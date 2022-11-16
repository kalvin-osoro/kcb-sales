package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.DFSVoomaCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.DFSVoomaLeadRepository;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.payload.*;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoomaPortalService implements IVoomaPortalService {
    private final DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;
    private final DFSVoomaLeadRepository dfsVoomaLeadRepository;



    @Override
    public boolean scheduleCustomerVisit(VoomaCustomerVisitsRequest model) {
        try {
            if (model == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = new DFSVoomaCustomerVisitEntity();
            dfsVoomaCustomerVisitEntity.setCustomerName(model.getCustomerName());
            dfsVoomaCustomerVisitEntity.setVisitDate(model.getVisitDate());
            dfsVoomaCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            dfsVoomaCustomerVisitEntity.setStatus(Status.ACTIVE);
            dfsVoomaCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaCustomerVisitEntity.setDsrName(model.getDsrName());
            //save
            dfsVoomaCustomerVisitRepository.save(dfsVoomaCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public boolean reScheduleCustomerVisit(VoomaCustomerVisitsRequest voomaCustomerVisitsRequest) {
        try {
            if (voomaCustomerVisitsRequest == null) {
                return false;
            }
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = dfsVoomaCustomerVisitRepository.findById(voomaCustomerVisitsRequest.getId()).orElse(null);
            if (dfsVoomaCustomerVisitEntity == null) {
                return false;
            }
            dfsVoomaCustomerVisitEntity.setVisitDate(voomaCustomerVisitsRequest.getVisitDate());
            dfsVoomaCustomerVisitRepository.save(dfsVoomaCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while rescheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisits() {
        try {
            List<ObjectNode>list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity : dfsVoomaCustomerVisitRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaCustomerVisitEntity.getId());
                objectNode.put("customerName", dfsVoomaCustomerVisitEntity.getCustomerName());
                objectNode.put("visitDate", dfsVoomaCustomerVisitEntity.getVisitDate());
                objectNode.put("reasonForVisit", dfsVoomaCustomerVisitEntity.getReasonForVisit());
                objectNode.put("dsrName", dfsVoomaCustomerVisitEntity.getDsrName());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits", e);
        }
        return null;
    }

    @Override
    public boolean assignLeadToDsr(VoomaAssignLeadRequest model) {
        try {
            DFSVoomaLeadEntity dfsVoomaLeadEntity = dfsVoomaLeadRepository.findById(model.getLeadId()).orElse(null);
            dfsVoomaLeadEntity.setDsrId(model.getDsrId());
            //set start date from input
            dfsVoomaLeadEntity.setStartDate(model.getStartDate());
            dfsVoomaLeadEntity.setEndDate(model.getEndDate());
            //save
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
            //update is assigned to true
            dfsVoomaLeadEntity.setAssigned(true);
            return true;


        } catch (Exception e) {
            log.error("Error assigning lead to dsr", e);
        }
        return false;

    }

    @Override
    public List<ObjectNode> getAllLeads(VoomaLeadsListRequest filters) {
        //get all leads
        try {
            List<ObjectNode>list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaLeadRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dfsVoomaLeadEntity.getId());
                objectNode.put("customerId", dfsVoomaLeadEntity.getCustomerId());
                objectNode.put("businessUnit", dfsVoomaLeadEntity.getBusinessUnit());
                objectNode.put("leadStatus", dfsVoomaLeadEntity.getLeadStatus().ordinal());
                objectNode.put("topic", dfsVoomaLeadEntity.getTopic());
                objectNode.put("priority", dfsVoomaLeadEntity.getPriority().ordinal());
                objectNode.put("dsrId", dfsVoomaLeadEntity.getDsrId());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }
}
