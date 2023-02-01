package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AquiringCustomerOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAllDSROnboardingsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.AgencySearchRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyChannelService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.fskcb.Calender.model.DSRAgent;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AgencyChannelOnboardingVC {

    @Autowired
    IAgencyChannelService agencyChannelService;

    //Channel request
    @PostMapping("/agency-onboard-customer")
    public ResponseEntity<?> onboardNewCustomer(@RequestParam("AgentDetails") String AgentDetails,
                                                @RequestParam("frontID") MultipartFile frontID,
                                                @RequestParam("backID") MultipartFile backID,
                                                @RequestParam("certificateOFGoodConduct") MultipartFile certificateOFGoodConduct,
                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                @RequestParam("financialStatement") MultipartFile financialStatement,
                                                @RequestParam("cv") MultipartFile cv,
                                                @RequestParam("customerPhoto") MultipartFile customerPhoto,
                                                @RequestParam("crbReport") MultipartFile crbReport) {
        Object agentObject = agencyChannelService.onboardNewCustomer(AgentDetails,
                frontID, backID,  certificateOFGoodConduct,  shopPhoto, financialStatement, cv, customerPhoto,   crbReport);
        boolean success = agentObject != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//            node.put("message", "Customer onboarding request sent successfully");
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //List all onboarded merchants
    @PostMapping(value = "/agency-get-all-dsr-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings(AgencyAllDSROnboardingsRequest model) {
        List<?> allOnboardings = agencyChannelService.getAllOnboardingsByDsr(model);
        boolean success = allOnboardings != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)allOnboardings);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/getDsrAgent")
    public ResponseEntity<?> searchAgent(@RequestBody DSRAgent model) {
        List<?>  agent=agencyChannelService.getDSRAgent(model);
        boolean success = agent != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)agent);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }



}
