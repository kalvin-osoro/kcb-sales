package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.DSRLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/ch")
public class AgencyChannelLeadsVC {

    @Autowired
    IAgencyChannelService agencyChannelService;

    //Create new lead
    @PostMapping("/agency-create-lead")
    public ResponseEntity<?> createAgencyLead(@RequestBody AgencyAddLeadRequest request) {
        boolean success = agencyChannelService.createLead(request);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/agency-get-all-leads")
    public ResponseEntity<?> getAllLeads(@RequestBody DSRLeadRequest model) {
        List<?> leads = agencyChannelService.getAllLeadsByDsrId(model);
        boolean success = leads != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)leads);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
