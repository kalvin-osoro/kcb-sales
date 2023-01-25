package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.SearchType;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.CRMRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.AgencyOnboardingRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.SearchRequest;
import com.ekenya.rnd.backend.fskcb.CrmAdapters.services.ICRMService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/ch")
public class AgencyChannelCustomer360VC {

    @Autowired
    IAcquiringChannelService channelService;

    @Autowired
    AgencyOnboardingRepository agencyOnboardingRepository;

   @Autowired
    ICRMService crmService;

    @PostMapping("/agency-get-customer-360-details-by-account")
    public ResponseEntity<?> getCustomerDetailsByAccount(@RequestBody SearchRequest model) {
        try {
            if (model ==null){
                return ResponseEntity.ok(new BaseAppResponse(0, null, "Request could NOT be processed. Please try again later"));
            }
            if (model.getSearchType()== SearchType.Account){
                return getResponseEntity(model.getAccount(), model);
            }
            if (model.getSearchType()== SearchType.PhoneNo){
                AgencyOnboardingEntity agencyOnboardingEntity = agencyOnboardingRepository.findAgentByagentPhone(model.getAgentNumber());
                if (agencyOnboardingEntity==null){
                    return ResponseEntity.ok(new BaseAppResponse(0, null, "Request could NOT be processed. Please try again later"));
                }
                return ResponseEntity.ok(new BaseAppResponse(1, agencyOnboardingEntity, "Request processed successfully"));

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

}
