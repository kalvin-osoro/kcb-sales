package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AddUserProfileRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AssignProfileRolesRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.RemoveProfileRolesRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UpdateUserProfileRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.PrivilegeService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.RoleService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Api(value = "Privilege Management")
@RestController
@RequestMapping(path = "/api/v1")
public class ProfilesVC {
    public final RoleService roleService;
    public  final PrivilegeService privilegeService;

    public ProfilesVC(RoleService roleService, PrivilegeService privilegeService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
    }

    @PostMapping("/users-create-profile")
    public ResponseEntity<?> createProfile(@RequestBody AddUserProfileRequest leadRequest ) {

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

    @RequestMapping(value = "/users-get-all-profiles", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProfiles() {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/users-update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateUserProfileRequest leadRequest ) {

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


    @PostMapping("/users-get-profile-details")
    public ResponseEntity<?> getProfileDetails(@RequestBody int id ) {

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


    @PostMapping("/users-assign-profile-roles")
    public ResponseEntity<?> assignProfileRoles(@RequestBody AssignProfileRolesRequest leadRequest ) {

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


    @PostMapping("/users-remove-profile-roles")
    public ResponseEntity<?> removeProfileRoles(@RequestBody RemoveProfileRolesRequest leadRequest ) {

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


    @PostMapping("/users-profile-users")
    public ResponseEntity<?> usersInProfile(@RequestBody int id) {

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
