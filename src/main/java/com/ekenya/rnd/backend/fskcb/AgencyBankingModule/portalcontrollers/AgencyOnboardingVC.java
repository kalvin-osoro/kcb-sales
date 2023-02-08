package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringApproveMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AssignMerchant;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.DSRMerchantRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyById;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaMerchantDetailsRequest;
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
@RequestMapping(path = "/api/v1")
public class AgencyOnboardingVC {

    @Autowired
    IAgencyPortalService agencyPortalService;
    @Autowired
    ObjectMapper mObjectMapper;

    //List all onboarded merchants
    @PostMapping(value = "/agency-get-all-onboarded-customers")
    public ResponseEntity<?> getAllOnboardings() {
        List<?> list = agencyPortalService.loadAllOnboardedAgents();
        boolean success = list != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)list);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/agency-approve-onboarding")
    public ResponseEntity<?> approveMerchantOnboarding(@RequestBody AcquiringApproveMerchant model) {

        boolean success = agencyPortalService.approveAgentOnboarding(model);
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
    @PostMapping("/agency-reject-onboarding")
    public ResponseEntity<?> rejectMerchantOnboarding(@RequestBody AcquiringApproveMerchant model) {

        boolean success = agencyPortalService.rejectAgentOnboarding(model);
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
    @PostMapping(value = "/agency-get-all-approved-merchant")
    public ResponseEntity<?> getAllCustomerApprovals() {
        List<?> list = agencyPortalService.loadAllApprovedMerchants();


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
    @PostMapping("/agency-import-agents")
    public ResponseEntity<?> importAgent(@RequestBody MultipartFile file ) {
        //T
        ObjectNode resp = agencyPortalService.attemptImportAgents(file);
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/agency-get-agent-by-id")
    public ResponseEntity<?> getAgentById(@RequestBody AgencyById model) {
        Object merchant = agencyPortalService.getAgentById(model);
        boolean success = merchant != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("agent").add(objectMapper.valueToTree(merchant));

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }

    @PostMapping("/agency-assign-agent")
    public ResponseEntity<?> assignAgent(@RequestBody AssignMerchant model) {
        boolean success = agencyPortalService.assignAgentToDSR(model);
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

//    @PostMapping(value = "/agency-get-all-dsr-agent")
//    public ResponseEntity<?> getDSRAgent(@RequestBody DSRMerchantRequest model) {
//        List<?> list = agencyPortalService.getDSRAgent(model);
//        //
//        boolean success  = list != null;
//
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ArrayNode node = objectMapper.createArrayNode();
//            node.addAll((List)list);
////          node.put("id",0);
//
//            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }
}
