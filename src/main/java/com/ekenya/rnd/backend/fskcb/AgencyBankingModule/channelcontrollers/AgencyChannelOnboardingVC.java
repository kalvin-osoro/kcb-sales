package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AquiringCustomerOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAllDSROnboardingsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyChannelService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
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
                                                @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                                @RequestParam("certificateOFGoodConduct") MultipartFile certificateOFGoodConduct,
                                                @RequestParam("businessLicense") MultipartFile businessLicense,
                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                @RequestParam("financialStatement") MultipartFile financialStatement,
                                                @RequestParam("cv") MultipartFile cv,
                                                @RequestParam("customerPhoto") MultipartFile customerPhoto,
                                                @RequestParam("companyRegistrationDoc") MultipartFile companyRegistrationDoc,
                                                @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                                @RequestParam("passportPhoto1") MultipartFile passportPhoto1,
                                                @RequestParam("passportPhoto2") MultipartFile passportPhoto2,
                                                @RequestParam("acceptanceLetter") MultipartFile acceptanceLetter,
                                                @RequestParam("crbReport") MultipartFile crbReport,
                                                @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
        Object agentObject = agencyChannelService.onboardNewCustomer(AgentDetails,
                frontID, backID, kraPinCertificate, certificateOFGoodConduct, businessLicense, shopPhoto, financialStatement, cv, customerPhoto, companyRegistrationDoc, signatureDoc, passportPhoto1, passportPhoto2, acceptanceLetter, crbReport, businessPermitDoc);
        boolean success = agentObject != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message", "Customer onboarding request sent successfully");
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


}
