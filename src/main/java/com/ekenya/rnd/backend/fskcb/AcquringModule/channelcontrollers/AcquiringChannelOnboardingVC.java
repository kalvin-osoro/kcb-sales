package com.ekenya.rnd.backend.fskcb.AcquringModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AquiringCustomerOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringChannelService;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
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
public class AcquiringChannelOnboardingVC {

    @Autowired
    IAcquiringChannelService acquiringChannelService;

    //Channel request
    @PostMapping("/acquiring-onboard-customer")
    public ResponseEntity<?> onboardNewMerchant(@RequestParam("merchDetails") String merchDetails,
                                                @RequestParam("frontID") MultipartFile frontID,
                                                @RequestParam("backID") MultipartFile backID,
                                                @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                                @RequestParam("certificateOFGoodConduct") MultipartFile certificateOFGoodConduct,
                                                @RequestParam("businessLicense") MultipartFile businessLicense,
                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                @RequestParam("customerPhoto") MultipartFile customerPhoto,
                                                @RequestParam("companyRegistrationDoc") MultipartFile companyRegistrationDoc,
                                                @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                                @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
        Object merchantObject= acquiringChannelService.onboardNewMerchant(merchDetails,frontID,backID,kraPinCertificate,certificateOFGoodConduct,businessLicense,shopPhoto,customerPhoto,companyRegistrationDoc,signatureDoc,businessPermitDoc);
        boolean success = merchantObject!=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            //success message
            node.put("message","Merchant onboarded successfully");

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //List all onboarded merchants
    @PostMapping(value = "/acquiring-get-all-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings() {
        List<?> merchantObject= acquiringChannelService.getAllOnboardings();
        boolean success = merchantObject!=null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) merchantObject);
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


}
