package com.ekenya.rnd.backend.fskcb.CrmAdapters;

import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomTillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.models.VoomaPaybillRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.CRMService;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.vooma.IVoomaCRMAdapter;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class CRMAdapterTestController {

    @Autowired
    IVoomaCRMAdapter voomaCRMAdapter;

    @PostMapping("/create-vooma-paybill")
        public ResponseEntity<?> createVoomaPaybill(@RequestBody VoomaPaybillRequest model) {
        boolean success = voomaCRMAdapter.createVoomaPaybill(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    @PostMapping("/create-vooma-till")
        public ResponseEntity<?> createVoomaTill(@RequestBody VoomTillRequest model) {
        boolean success = voomaCRMAdapter.createVoomaTill(model);
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    @PostMapping("/search-vooma-customer")
        public ResponseEntity<?> searchVoomaCustomer(@RequestBody String result) {
        boolean success = voomaCRMAdapter.searchVoomaCustomer(result);
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ObjectNode node = objectMapper.createObjectNode();
            return ResponseEntity.ok(new BaseAppResponse(1,objectMapper.createObjectNode(),"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    @PostMapping("/process-vooma-customer-search-result")
        public ResponseEntity<?> processVoomaCustomerSearchResult(@RequestBody String result) {
        voomaCRMAdapter.processVoomaCustomerSearchResult(result);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        if (node != null) {
            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {
            return ResponseEntity.ok(new BaseAppResponse(0, node, "Request could NOT be processed. Please try again later"));
        }

    }

}
