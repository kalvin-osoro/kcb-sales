package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaLeadsVC {
    @Autowired
    private IVoomaPortalService voomaService;
    //Assign lead to a sales person
    @PostMapping("/vooma-assign-lead")
    public ResponseEntity<?> assignLead(@RequestBody VoomaAssignLeadRequest model) {
        boolean success = voomaService.assignLeadToDsr(model);
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

    //List all leads
    @PostMapping("/vooma-get-all-leads")
    ResponseEntity<?> getAllLeads( ){
        List<?> response = voomaService.getAllLeads();
        boolean success = response != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)response);
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    //fetchAllLeadsV2
    @PostMapping("/vooma-get-all-leads-v2")
    public ResponseEntity<?> getAllLeadsV2() {
        List<?> voomaLeadRequests = voomaService.fetchAllLeadsV2();
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(voomaLeadRequests != null){
            //Object
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,voomaLeadRequests,"Request Processed Successfully"));
        }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
        }
            //Object


}
