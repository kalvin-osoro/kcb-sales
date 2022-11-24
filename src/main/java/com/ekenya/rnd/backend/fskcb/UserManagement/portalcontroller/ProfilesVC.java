package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IProfilesService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.RoleService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Api(value = "Privilege Management")
@RestController
@RequestMapping(path = "/api/v1")
@Secured(SystemRoles.USER)
public class ProfilesVC {
    @Autowired
    ObjectMapper mObjectMapper;

    public final RoleService roleService;
    public  final IProfilesService profilesService;

    public ProfilesVC(RoleService roleService, IProfilesService profilesService) {
        this.roleService = roleService;
        this.profilesService = profilesService;
    }

    @ApiOperation(value = "Create a new profile")
    @PostMapping("/users-create-profile")
    public ResponseEntity<?> createProfile(@RequestBody AddUserProfileRequest request ) {

        //INSIDE SERVICE
        boolean success = profilesService.attemptAddProfile(request);

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

    @ApiOperation(value = "Get all profiles")
    @PostMapping(value = "/users-get-all-profiles")
    public ResponseEntity<?> getAllProfiles() {


        //TODO; INSIDE SERVICE
        ArrayNode list = profilesService.getAllProfiles();
        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @ApiOperation(value = "Update a profile")
    @PostMapping("/users-update-profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = profilesService.attemptUpdateProfile(request);

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

    @ApiOperation(value = "Get profile details")
    @PostMapping("/users-get-profile-details")
    public ResponseEntity<?> getProfileDetails(@RequestBody ProfileDetailsRequest request ) {

        //INSIDE SERVICE
        ObjectNode resp = profilesService.getProfileDetails(request.getProfileId());

        //Response
        if(resp != null){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @ApiOperation(value = "Assign roles to a profile")
    @PostMapping("/users-assign-profile-roles")
    public ResponseEntity<?> assignProfileRoles(@RequestBody AssignProfileRolesRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = profilesService.assignRolesToProfile(request);

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

    @ApiOperation(value = "Remove roles from a profile")

    @PostMapping("/users-remove-profile-roles")
    public ResponseEntity<?> removeProfileRoles(@RequestBody RemoveProfileRolesRequest request ) {

        //INSIDE SERVICE
        boolean success = profilesService.removeRolesFromProfile(request);

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


    @ApiOperation(value = "Get all users in a profile")
    @PostMapping("/users-profile-users")
    public ResponseEntity<?> usersInProfile(@RequestBody UsersInProfileRequest request) {

        //INSIDE SERVICE
        ArrayNode list = profilesService.loadUsersInProfile(request.getProfileId());

        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @ApiOperation(value = "Get all users in a profile")
    @PostMapping("/users-profile-roles")
    public ResponseEntity<?> getProfileRoles(@RequestBody ProfileRolesRequest request) {

        //INSIDE SERVICE
        ArrayNode list = profilesService.loadRolesInProfile(request.getProfileId());

        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
