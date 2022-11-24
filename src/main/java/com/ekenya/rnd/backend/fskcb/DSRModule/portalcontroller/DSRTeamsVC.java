package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class DSRTeamsVC {
    //
    @Autowired
    IDSRPortalService dsrPortalService;

    @Autowired
    ObjectMapper mObjectMapper;

    @PostMapping("/dsr-create-team")
    public ResponseEntity<?> createTeam(@RequestBody AddTeamRequest request ) {
        //INSIDE SERVICE
        boolean success = dsrPortalService.addDSRTeam(request);

        //Response
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/dsr-get-all-teams")
    public ResponseEntity<?> getAllTeams() {
        //INSIDE SERVICE
        ArrayNode list = dsrPortalService.getAllDSRTeams();
        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-team-details")
    public ResponseEntity<?> getTeamDetails(@RequestBody Long teamId ) {
        //TODO; INSIDE SERVICE
        ObjectNode resp = dsrPortalService.loadTeamDetails(teamId);

        //Response
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-update-team-members")
    public ResponseEntity<?> updateTeamMembers(@RequestBody UpdateTeamMembersRequest request ) {
        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.attemptUpdateTeamMembers(request);

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
    @PostMapping("/dsr-disable-team")
    public ResponseEntity<?> disableTeam(@RequestBody long teamId) {
        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.attemptDeactivateTeam(teamId);

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
    @PostMapping("/dsr-enable-team")
    public ResponseEntity<?> enableTeam(@RequestBody long teamId ) {
        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.attemptActivateTeam(teamId);

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


    @PostMapping("/dsr-add-team-member")
    public ResponseEntity<?> addTeamMember(@RequestBody AddTeamMemberRequest request) {
        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.attemptAddTeamMember(request);

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
    @PostMapping("/dsr-remove-team-member")
    public ResponseEntity<?> removeTeamMember(@RequestBody RemoveTeamMemberRequest request ) {
        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.attemptRemoveTeamMember(request);

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
