package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaRejectMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.GetALLDSRMerchantOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaOnboardingVC {
    @Autowired
    private IVoomaPortalService voomaService;


    //List all onboarded merchants
    @PostMapping(value = "/vooma-get-all-onboarded-merchant")
    public ResponseEntity<?> getAllMerchantOnboardings() {
       List<?> list = voomaService.getAllMerchantOnboardings();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list !=null){
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));

        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/vooma-get-all-approved-merchant")
    public ResponseEntity<?> getAllMerchantApprovals() {
        List<?> list = voomaService.loadAllApprovedMerchants();


        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)list);
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/vooma-approve-onboarding")
    public ResponseEntity<?> approveOnboarding(@RequestBody DFSVoomaApproveMerchantOnboarindRequest dfsVoomaApproveMerchantOnboarindRequest) {

        boolean success = voomaService.approveMerchantOnboarding(dfsVoomaApproveMerchantOnboarindRequest);
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
    //getAllOnboardingV2
    @PostMapping(value = "/vooma-get-all-onboarding-v2")
    public ResponseEntity<?> getAllOnboardingV2() {
        List<?> list = voomaService.getAllOnboardingV2();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list !=null){
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));

        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/vooma-reject-merchant-onboarding")
    public ResponseEntity<?> rejectOnboarding(@RequestBody DFSVoomaRejectMerchantOnboarindRequest model) {

        boolean success = voomaService.rejectMerchantOnboarding(model);
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
    @PostMapping(value = "/vooma-get-all-approved-merchant-cordinates")
    public ResponseEntity<?> getAllApprovedMerchantCoordinates() {
        ArrayNode list = voomaService.getAllApprovedMerchantCoordinates();


        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
//            ArrayNode node = objectMapper.createArrayNode();
//            node.addAll((List)list);
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/vooma-reject-agent-onboarding")
    public ResponseEntity<?> rejectMerchantOnboarding(@RequestBody DFSVoomaRejectMerchantOnboarindRequest model) {

        boolean success = voomaService.rejectAgentOnboarding(model);
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

    @PostMapping("/vooma-approve-agent-onboarding")
    public ResponseEntity<?> approveAgentOnboarding(@RequestBody DFSVoomaRejectMerchantOnboarindRequest model) {

        boolean success = voomaService.approveAgentOnboarding(model);
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
    @PostMapping(value = "/vooma-get-all-approved-agent-cordinates")
    public ResponseEntity<?> getAllApprovedAgentCoordinates() {
        ArrayNode list = voomaService.getAllApprovedAgentCoordinates();


        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
//            ArrayNode node = objectMapper.createArrayNode();
//            node.addAll((List)list);
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
