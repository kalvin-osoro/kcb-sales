package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingVisitEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingCustomerVisitRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsBYDSRRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
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
    private final PSBankingCustomerVisitRepository psBankingCustomerVisitRepository;

    @Override
    public boolean createCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model==null){
                return false;
            }
            PSBankingVisitEntity psBankingVisitEntity = new PSBankingVisitEntity();
            psBankingVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psBankingVisitEntity.setTypeOfVisit(model.getTypeOfVisit());
            psBankingVisitEntity.setChannelUsed(model.getChannelUsed());
            psBankingVisitEntity.setActionplan(model.getActionplan());
            psBankingVisitEntity.setHighlightOfVisit(model.getHighlightOfVisit());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
            psBankingVisitEntity.setDsrName("test");
            psBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save customer visit
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisitsByDSR(PBCustomerVisitsBYDSRRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<PSBankingVisitEntity> psBankingVisitEntities = psBankingCustomerVisitRepository.findAllByDsrId(model.getDsrId());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            psBankingVisitEntities.forEach(psBankingVisitEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("visitId", psBankingVisitEntity.getId());
                objectNode.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                objectNode.put("actionPlan", psBankingVisitEntity.getActionplan());
                objectNode.put("highlights", psBankingVisitEntity.getHighlightOfVisit());
                objectNode.put("dsrName", psBankingVisitEntity.getDsrName());
                objectNode.put("customerName",psBankingVisitEntity.getCustomerName());
                objectNode.put("createdOn", psBankingVisitEntity.getCreatedOn().toString());
                objectNodeList.add(objectNode);
            });
            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits by DSR", e);
        }
        return null;
    }
}
