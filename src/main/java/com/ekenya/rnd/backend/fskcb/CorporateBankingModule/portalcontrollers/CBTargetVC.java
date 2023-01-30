package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddTargetRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.UpdateTargetRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBPortalService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
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
public class CBTargetVC {

    @Autowired
    ICBPortalService cbPortalService ;

    @PostMapping("/cb-create-target")
    public ResponseEntity<?> createCBTarget(@RequestBody CBAddTargetRequest model) {

        boolean success= cbPortalService.createCBTarget(model);

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

    @PostMapping(value = "/cb-get-all-targets")
    public ResponseEntity<?> getAllTargets() {
        List<?> list = cbPortalService.getAllTargets();
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


    @PostMapping(value = "/cb-get-agents-in-target")
    public ResponseEntity<?> getVoomaAgentsInTarget(@RequestBody VoomaDSRsInTargetRequest model) {
        //TODO: Implement this
        boolean success = /*voomaService.getCBAgentsInTarget(model)*/true;
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
    //get all targets for a given agent




    @PostMapping(value = "/cb-sync-crm-targets")
    public ResponseEntity<?> getCBSyncTargetsWithCRM() {

        //

        //TODO;
        boolean success = false;//acquiringService..(model);

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
    //Assign targets to a team  and dsr
    @PostMapping(value = "/cb-assign-targets")
    public ResponseEntity<?> getCBAssignTargetsToDSR(@RequestBody DSRTAssignTargetRequest model) {
        boolean success = cbPortalService.assignTargetToDSR(model);

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
    //assign targets to a team
    @PostMapping(value = "/cb-assign-targets-to-team")
    public ResponseEntity<?> getCBAssignTargetsToTeam(@RequestBody TeamTAssignTargetRequest model) {
        boolean success = cbPortalService.assignTargetToTeam(model);

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
    @PostMapping(value = "/cb-get-all-targets-by-id")
    public ResponseEntity<?> getCBGetTargetById( @RequestBody VoomaTargetByIdRequest model) {
        Object target = cbPortalService.getTargetById(model);
        Boolean success = target != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("target",target);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/cb-update-target")
    public ResponseEntity<?> updateTarget(@RequestBody UpdateTargetRequest model) {
        boolean success = cbPortalService.updateTarget(model);

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

    @PostMapping(value = "/cb-get-dsr-in-target")
    public ResponseEntity<?> salesPersonInTarget(@RequestBody AcquiringDSRsInTargetRequest model) {
        List<?> list = cbPortalService.salesPersonTarget(model);
        boolean success = list != null;// acquiringTargetsResponse.size() > 0;



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
}

