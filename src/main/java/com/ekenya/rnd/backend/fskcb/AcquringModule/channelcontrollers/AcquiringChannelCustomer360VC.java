package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SearchType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.IAcquiringOnboardingsRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CRMRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CRMRequestID;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CustomerDetailsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyOnboardingRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.SearchRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.AgencySearchRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.fskcb.SpringBootKcbRestApiApplication;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.ekenya.rnd.backend.utils.Utility;
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

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(path = "/api/v1/ch")
@Slf4j
public class AcquiringChannelCustomer360VC {

    @Autowired
    IAcquiringChannelService channelService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IAcquiringOnboardingsRepository acquiringOnboardingsRepository;
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
            String result = restTemplate.getForObject(uri, String.class, model.getAccount());
            String customer1 = result.trim();
            String newString = customer1.replace("\\", "");
            String removeFirstAndLastQuotes = newString.substring(1, newString.length() - 1);
            return ResponseEntity.ok(new BaseAppResponse(1, removeFirstAndLastQuotes, "Request Processed Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/acquiring-get-customer-360-details-by-idNumber")
    public ResponseEntity<?> getCustomerDetailsByIdNumber(@RequestBody CRMRequestID model) {
        try {
            String uri = "http://10.216.2.10:8081/api/Values?entity=accountsbyid&paramval={idNumber}";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(uri, String.class, model.getIdNumber());
            String customer1 = result.trim();
            String newString = customer1.replace("\\", "");
            String removeFirstAndLastQuotes = newString.substring(1, newString.length() - 1);
            return ResponseEntity.ok(new BaseAppResponse(1, removeFirstAndLastQuotes, "Request Processed Successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

////new for all search type in one
    @PostMapping("/acquiring-get-customer-360-details-by-accountV1")
    public ResponseEntity<?> getCustomerDetailsByAccount(@RequestBody SearchRequest model) {
        try {
            if (model ==null){
                return ResponseEntity.ok(new BaseAppResponse(0, null, "Request could NOT be processed. Please try again later"));
            }
            if (model.getSearchType()== SearchType.Account){
                return getResponseEntity(model.getAccount(), model);
            }
            if (model.getSearchType()== SearchType.PhoneNo){
                AcquiringOnboardEntity acquiringOnboardEntity = acquiringOnboardingsRepository.findMerchantByOutletPhone(model.getAgentNumber());
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringOnboardEntity.getId());
                asset.put("merchantName", acquiringOnboardEntity.getClientLegalName());
                asset.put("Region", acquiringOnboardEntity.getRegion());
                asset.put("phoneNumber", acquiringOnboardEntity.getBusinessPhoneNumber());
                asset.put("email", acquiringOnboardEntity.getBusinessEmail());
                asset.put("status", acquiringOnboardEntity.getStatus().toString());
                asset.put("agent Id", acquiringOnboardEntity.getDsrId());
                asset.put("createdOn", acquiringOnboardEntity.getCreatedOn().getTime());
                ObjectNode cordinates = mapper.createObjectNode();
                cordinates.put("latitude", acquiringOnboardEntity.getLatitude());
                cordinates.put("longitude", acquiringOnboardEntity.getLongitude());
                asset.put("cordinates", cordinates);
                ObjectNode businessDetails = mapper.createObjectNode();
                businessDetails.put("businessName", acquiringOnboardEntity.getBusinessName());
                businessDetails.put("physicalLocation", acquiringOnboardEntity.getRegion());

                asset.set("businessDetails", businessDetails);
                if (acquiringOnboardEntity==null){
                    return ResponseEntity.ok(new BaseAppResponse(0, null, "Request could NOT be processed. Please try again later"));
                }
                return ResponseEntity.ok(new BaseAppResponse(1, asset, "Request Processed Successfully"));

            }
            else {
                return ResponseEntity.ok(new BaseAppResponse(0, null, "Request could NOT be processed. Please try again later"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @NotNull
    public static ResponseEntity<?> getResponseEntity(String account, @RequestBody SearchRequest model) {
        String uri = "http://10.216.2.10:8081/api/Values?entity=accountsbyaccno&paramval={accountNo}";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class, account);
        String customer1 = result.trim();
        String newString = customer1.replace("\\", "");
        String removeFirstAndLastQuotes = newString.substring(1, newString.length() - 1);
        return ResponseEntity.ok(new BaseAppResponse(1, removeFirstAndLastQuotes, "Request Processed Successfully"));
    }

    //
    @PostMapping(value = "/search-merchant")
    public ResponseEntity<?> searchAgent(@RequestBody AgencySearchRequest model) {
        Object agent =channelService.searchAgent(model);
        boolean success = agent != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("agent").add(objectMapper.valueToTree(agent));

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }
}
