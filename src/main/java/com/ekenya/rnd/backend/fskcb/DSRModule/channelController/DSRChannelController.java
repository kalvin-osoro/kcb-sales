package com.ekenya.rnd.backend.fskcb.DSRModule.channelController;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.DSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.DSRDetailsRequest;
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
public class DSRChannelController {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    IDSRPortalService dsrPortalService;
    @PostMapping(value = "/dsr-get-accounts-allV1")
    public ResponseEntity<?> getAllAccountsV1() {

        //INSIDE SERVICE
        List<?> visits = dsrPortalService.getAllDSRAccountsV1();
        boolean success = visits != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) visits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-get-details")
    public ResponseEntity<?> getDSRDetails(@RequestBody DSRDetailsRequest request ) {


        //INSIDE SERVICE
        ObjectNode info = dsrPortalService.getDSRProfile(request.getStaffNo());

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(info != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,info,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
