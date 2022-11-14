package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services.IPBService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1/ch/")
public class VoomaChannelOnboardingVC {

    @Autowired
    IPBService pbService;

    @PostMapping("/vooma-onboard-customer")
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


//        ResponseEntity<?> l =  voomaService.addMerchant(frontID, backID, kraPinCertificate,certificateOFGoodConduct,
//                businessLicense, fieldApplicationForm, merchDetails,
//                shopPhoto, customerPhoto,
//                companyRegistrationDoc, signatureDoc, businessPermitDoc, httpServletRequest);

        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
