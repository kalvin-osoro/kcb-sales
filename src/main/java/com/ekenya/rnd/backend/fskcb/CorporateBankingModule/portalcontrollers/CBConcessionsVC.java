package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddConvenantRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBApproveConcessionRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBConcessionRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaApproveMerchantOnboarindRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.ChangeConvenantStatus;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddConcessionRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddCovenantRequest;
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
public class CBConcessionsVC {
    @Autowired
    private ICBPortalService cbService;


    @PostMapping("/cb-add-concession")
    public ResponseEntity<?> addConcession(@RequestBody CBConcessionRequest model) {
        boolean success = cbService.addConcession(model);

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

    @PostMapping(value = "/cb-get-all-concessions")
    public ResponseEntity<?> getAllConcessions() {
        List<?> response = cbService.getAllConcessions();
        boolean success = response != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(response));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/cb-add-tracked-covenant")
    public ResponseEntity<?> addTrackedCovenant(@RequestBody CBAddConvenantRequest model) {
        boolean success = cbService.addTrackedCovenant(model);
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

    @PostMapping("/cb-change-tracked-covenant-status")
    public ResponseEntity<?> changeTrackedCovenantStatus(@RequestBody ChangeConvenantStatus model) {
        boolean success = cbService.setTrackedCovenantStatus(model);
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

    @PostMapping(value = "/cb-get-all-tracked-covenants")
    public ResponseEntity<?> getAllTracked() {
        List<?> response = cbService.getAllTrackedCovenants();
        boolean success = response != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(response));
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/cb-approve-concession")
    public ResponseEntity<?> approveConcession(@RequestBody CBApproveConcessionRequest model) {

        boolean success = cbService.approveCBConcession(model);
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

  //send email for Approval
    @PostMapping("/cb-send-email-for-approval")
    public ResponseEntity<?> sendEmailForApproval(@RequestBody CBApproveConcessionRequest model) {

        boolean success = cbService.sendEmailForApproval(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.put("message","email sent successful for approver{ "+model.getEmailUrl()+" }");
//          node.put("id",0);

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
            }
        }

        @PostMapping("/cb-reject-concession")
        public ResponseEntity<?> rejectConcession(@RequestBody CBApproveConcessionRequest model) {

            boolean success = cbService.rejectCBConcession(model);
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

}
