package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.channelcontroller;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBDSROnboardingRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch/")
public class PBChannelOnboardingVC {

    @Autowired
    IPBChannelService channelService;

    @PostMapping("/pb-create-account")
    public ResponseEntity<?> onboardNewCustomer(@RequestParam("customerDetails") String customerDetails,
                                                @RequestParam("signature") MultipartFile signature,
//                                                @RequestParam("customerPhoto") MultipartFile customerPhoto,
//                                                @RequestParam("KRAPin") MultipartFile KRAPin,
//                                                @RequestParam("CRBReport") MultipartFile CRBReport,
                                                @RequestParam("frontID") MultipartFile frontID,
                                                @RequestParam("backID") MultipartFile backID) {

        Object customer = channelService.onboardNewCustomer(customerDetails, signature, frontID, backID);
        boolean success = customer != null;
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

    //List all onboarded merchants
    @PostMapping(value = "/pb-get-all-dsr-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings(PBDSROnboardingRequest model) {
        List<?> customers = channelService.getAllOnboardings(model);
        boolean success = customers != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)customers);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}