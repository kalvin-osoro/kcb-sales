package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.channelcontroller;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/ch/")
public class PBChannelOnboardingVC {

    @Autowired
    IPBPortalService pbService;

    @PostMapping("/pb-create-account")
    public ResponseEntity<?> onboardNewMerchant(@RequestParam("merchDetails") String merchDetails,
                                                @RequestParam("frontID") MultipartFile frontID,
                                                @RequestParam("backID") MultipartFile backID,
                                                @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                                @RequestParam("certificateOFGoodConduct") MultipartFile certificateOFGoodConduct,
                                                @RequestParam("businessLicense") MultipartFile businessLicense,
                                                @RequestParam("fieldApplicationForm") MultipartFile fieldApplicationForm,
                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                @RequestParam("customerPhoto") MultipartFile customerPhoto,
                                                @RequestParam("companyRegistrationDoc") MultipartFile companyRegistrationDoc,
                                                @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                                @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc,
                                                HttpServletRequest httpServletRequest){


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }

        //Response
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
}