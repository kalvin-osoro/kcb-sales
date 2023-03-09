package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.AccountType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.DeleteWrapper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddAdminUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "Upload User  Rest Api")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1")
//@PreAuthorize("hasAuthority('"+SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
//@PreAuthorize("isAuthenticated()")
//hasRole('USER') is the same as hasAuthority('ROLE_USER')
public class UsersVC {

    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    IUsersService usersService;

    @PostMapping("/users-create-user")
    public ResponseEntity<?> createAdminUser(@RequestBody AddAdminUserRequest request ) {
        //TODO; INSIDE SERVICE
        boolean success = usersService.attemptCreateUser(request, AccountType.ADMIN, false);
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/users-import-users")
    public ResponseEntity<?> importAdminUsers(@RequestBody MultipartFile file ) {
        //T
        ObjectNode resp = usersService.attemptImportUsers(file);
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/users-get-all-users")
    public ResponseEntity<?> getAllAdminUsers() {


        //TODO; INSIDE SERVICE
        List<?> list = usersService.loadAllUsers();
        //Response
        if(list != null){
            //Object
            //ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/users-sync-crm")
    public ResponseEntity<?> syncAdminsWithCRM() {
        //INSIDE SERVICE
        boolean success = usersService.syncUsersWithCRM();

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

    @PostMapping("/users-get-details")
    public ResponseEntity<?> getAdminUserDetails(@RequestBody AdminUserDetailsRequest request) {
        //TODO; INSIDE SERVICE
        ObjectNode node = usersService.loadUserDetails(request);

        //Response
        if(node != null){

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/users-reset-password")
    public ResponseEntity<?> resetUserAdminPassword(@RequestBody ResetUserPasswordRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = usersService.attemptResetPassword(request);

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

    @PostMapping("/users-assign-profile")
    public ResponseEntity<?> assignUserProfile(@RequestBody AssignUserProfileRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = usersService.assignUserToProfiles(request);

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

    @PostMapping("/users-update-profiles")
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserProfilesRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = usersService.updateUserProfiles(request);

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
    @PostMapping("/users-block-user")
    public ResponseEntity<?> blockUser(@RequestBody BlockAdminUserRequest request) {

        //
        boolean success = usersService.attemptBlockUser(request.getUserId());

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

    @PostMapping("/users-unblock-user")
    public ResponseEntity<?> unblockUser(@RequestBody UnblockAdminUserRequest request) {

        //
        boolean success = usersService.attemptUnblockUser(request.getUserId());

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

    @PostMapping("/users-get-audit-trail")
    public ResponseEntity<?> userAuditTrail(@RequestBody AdminUserAuditTrailRequest request) {

        //INSIDE SERVICE
        ArrayNode resp = usersService.loadUserAuditTrail(request.getUserId());

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
    //delete user
    @PostMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestBody DeleteWrapper request) {

        //INSIDE SERVICE
        boolean success = usersService.attemptDeleteUser(request);

        //Response
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();

                return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
            }else{

                //Response
                return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
            }
        }


}
