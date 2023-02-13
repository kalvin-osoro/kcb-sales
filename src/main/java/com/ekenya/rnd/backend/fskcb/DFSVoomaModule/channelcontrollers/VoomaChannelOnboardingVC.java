package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.AgencySearchRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaOnboardRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCustomerOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch/")
public class  VoomaChannelOnboardingVC {

    @Autowired
    IVoomaChannelService voomaChannelService;

    //Channel request
//    @PostMapping("/vooma-onboard-merchant")
//    public ResponseEntity<?> onboardNewMerchant(@RequestParam("merchDetails") String merchDetails,
//                                                @RequestParam("frontID") MultipartFile frontID,
//                                                @RequestParam("backID") MultipartFile backID,
//                                                @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
//                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
//                                                @RequestParam("signatureDoc") MultipartFile signatureDoc,
//                                                @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
//        Object merchantObj=voomaChannelService.onboardNewMerchant(merchDetails,frontID,backID,kraPinCertificate,shopPhoto,signatureDoc,businessPermitDoc);
//        boolean success = merchantObj != null;
//
//        System.out.println(merchantObj);
//
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ObjectNode node = objectMapper.createObjectNode();
////          node.put("id",0);
//
//            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }

    @PostMapping("/vooma-onboard-agent")
    public ResponseEntity<?> onboardNewAgent(@RequestParam("agentDetails") String agentDetails,
                                             @RequestParam("frontID") MultipartFile frontID,
                                             @RequestParam("backID") MultipartFile backID,
                                             @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                             @RequestParam("businessCertificateOfRegistration") MultipartFile businessCertificateOfRegistration,
                                             @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                             @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                             @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
        Object agentObj=voomaChannelService.onboardNewAgent(agentDetails,frontID,backID,kraPinCertificate,businessCertificateOfRegistration,
                shopPhoto,signatureDoc,businessPermitDoc);
        boolean success = agentObj != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    //List all onboarded merchants
    @PostMapping(value = "/vooma-get-all-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings() {

//TODO;
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/vooma-onboard-merchantV1")
    public ResponseEntity<?> onboardNewMerchantv1(@RequestParam("merchDetails") String merchDetails,
                                                @RequestParam("frontID") MultipartFile frontID,
                                                @RequestParam("backID") MultipartFile backID,
                                                @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                                @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                                @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
        Object merchantObj=voomaChannelService.onboardNewMerchantV1(merchDetails,frontID,backID,kraPinCertificate,shopPhoto,signatureDoc,businessPermitDoc);
        boolean success = merchantObj != null;

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

    @PostMapping("/vooma-onboard-agentV1")
    public ResponseEntity<?> onboardNewAgentV1(@RequestParam("agentDetails") String agentDetails,
                                             @RequestParam("frontID") MultipartFile frontID,
                                             @RequestParam("backID") MultipartFile backID,
                                             @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                             @RequestParam("businessCertificateOfRegistration") MultipartFile businessCertificateOfRegistration,
                                             @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                             @RequestParam("signatureDoc") MultipartFile[] signatureDoc,
                                             @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc) {
        Object agentObj=voomaChannelService.onboardNewAgentV1(agentDetails,frontID,backID,kraPinCertificate,businessCertificateOfRegistration,
                shopPhoto,signatureDoc,businessPermitDoc);
        boolean success = agentObj != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //
    @PostMapping(value = "/search-vooma-merchant")
    public ResponseEntity<?> searchMerchant(@RequestBody AgencySearchRequest model) {
        List<?> agent =voomaChannelService.searchMerchant(model);
        boolean success = agent != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ArrayNode node =objectMapper.createArrayNode();
            node.addAll((List)agent);


            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }

    //agent
    @PostMapping(value = "/search-vooma-agent")
    public ResponseEntity<?> searchAgent(@RequestBody AgencySearchRequest model) {
        List<?> agent =voomaChannelService.searchAgent(model);
        boolean success = agent != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ArrayNode node= objectMapper.createArrayNode();
            node.addAll((List)agent);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }



}
