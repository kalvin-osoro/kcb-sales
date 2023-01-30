package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
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
public class VoomaTargetsVC {

    @Autowired
    private IVoomaPortalService voomaService;

    @PostMapping("/vooma-create-target")
    public ResponseEntity<?> createVoomaTarget(@RequestBody DFSVoomaAddTargetRequest voomaAddTargetRequest) {

        boolean success= voomaService.createVoomaTarget(voomaAddTargetRequest);

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

    @PostMapping(value = "/vooma-get-all-targets")
    public ResponseEntity<?> getAllTargets() {
        List<?> list = voomaService.getAllTargets();
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


    @PostMapping(value = "/vooma-get-agents-in-target")
    public ResponseEntity<?> getVoomaAgentsInTarget(@RequestBody VoomaDSRsInTargetRequest model) {
        //TODO: Implement this
        boolean success = /*voomaService.getVoomaAgentsInTarget(model)*/true;
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




    @PostMapping(value = "/vooma-sync-crm-targets")
    public ResponseEntity<?> getVoomaSyncTargetsWithCRM() {

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
    @PostMapping(value = "/vooma-assign-targets")
    public ResponseEntity<?> getVoomaAssignTargetsToDSR(@RequestBody DSRTAssignTargetRequest model) {
        boolean success = voomaService.assignTargetToDSR(model);

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
        @PostMapping(value = "/vooma-assign-targets-to-team")
        public ResponseEntity<?> getVoomaAssignTargetsToTeam(@RequestBody TeamTAssignTargetRequest model) {
            boolean success = voomaService.assignTargetToTeam(model);

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
    @PostMapping(value = "/vooma-get-all-targets-by-id")
    public ResponseEntity<?> getVoomaGetTargetById( @RequestBody VoomaTargetByIdRequest model) {
        Object target = voomaService.getTargetById(model);
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
    @PostMapping(value = "/vooma-get-dsr-in-target")
    public ResponseEntity<?> salesPersonInTarget(@RequestBody AcquiringDSRsInTargetRequest model) {
        List<?> list = voomaService.salesPersonTarget(model);
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



