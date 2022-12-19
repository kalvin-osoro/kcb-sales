package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CRMRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CustomerDetailsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.fskcb.SpringBootKcbRestApiApplication;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(path = "/api/v1/ch")
@Slf4j
public class AcquiringChannelCustomer360VC {

    @Autowired
    IAcquiringChannelService channelService;
    @Autowired
    ICRMService crmService;

    @PostMapping("/acquiring-customer-lookup")
    public ResponseEntity<?> lookupCustomer(@RequestParam String account) {


        JsonObject resp = channelService.findCustomerByAccNo(account);//

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (resp == null) {
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }


    //CUSTOMER360 VIEW
//    @PostMapping("/acquiring-get-customer-details")
//    public ResponseEntity<?> getCustomerDetails(Long  accountNo) {
//
//
//        JsonObject resp = crmService.getCustomerDetails(accountNo);
//
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(resp != null){
//            //Object
//            ObjectNode node = objectMapper.createObjectNode();
//            node.put("customer",resp.toString());
//
//            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//                //Response
//                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
//        }
//
//    }

    @GetMapping("/get-crm-customers")
    public ResponseEntity<?> getCRMCustomer() {
        try {
            String uri = "http://10.216.2.10:8081/api/Values?entity=accounts&paramval=none";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class);
            return ResponseEntity.ok(new BaseAppResponse(1, result, "Request Processed Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/acquiring-get-customer-360-details-by-account")
    public ResponseEntity<?> getCustomerDetailsByAccount(@RequestBody CRMRequest model) {
        try {
            String uri = "http://10.216.2.10:8081/api/Values?entity=accountsbyaccno&paramval={accountNo}";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(uri, String.class, model.getAccount());
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(response);
            return ResponseEntity.ok(new BaseAppResponse(1, json, "Request Processed Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/acquiring-get-customer-360-details-by-accountNumber")
    public JsonObject getCustomerDetailsById(@RequestBody CRMRequest model) {
        try {
            String uri = "http://10.216.2.10:8081/api/Values?entity=accountsbyaccno&paramval={accountNo}";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,request, String.class, model.getAccount());
            String result = response.getBody();
            JsonParser parser = new JsonParser();
            JsonObject json = (JsonObject) parser.parse(result);
            return json;
        } catch (Exception e) {
            log.error("Error Occured: " + e.getMessage());
            return null;
        }
    }
}
