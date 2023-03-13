package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IRolesService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@Api(value = "Role Management")
@RestController()
@RequestMapping(path = "/api/v1")
//@PreAuthorize("hasAuthority('"+SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
public class RolesVC {

    @Autowired
    ObjectMapper mObjectMapper;
    private final IRolesService roleService;

    private final IUsersService userService;

    public RolesVC(IRolesService roleService, IUsersService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }


    @PostMapping("/roles-get-details")
    public ResponseEntity<?> getUserRoleDetails(@RequestBody RoleDetailsRequest request) {

        //INSIDE SERVICE
        ObjectNode resp = roleService.getUserRoleDetails(request.getRoleId());

        //Response
        if(resp != null){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }
        //Response
        return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }

    @PutMapping(value="/roles-update-role")
    public ResponseEntity<?> updateRole(@RequestBody UpdateUserRoleRequest request) {

        //INSIDE SERVICE
        boolean success = roleService.updateUserRole(request);

        //Response
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }


            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }


    // Get all roles
    @ApiOperation(value = "Get all roles")
    @PostMapping("/roles-get-all")
    public ResponseEntity<?> getAllRoles() {

        ArrayNode list = roleService.loadAllRoles();

        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //assign role to user
    @ApiOperation(value = "Assign role to user")
    @PostMapping("/roles-assign-to-user")
    public ResponseEntity<?> assignRoleToUser(@RequestBody AssignRoleToUserRequest request){


        boolean success = roleService.assignRole(request);

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
    //unassign role to user
    @ApiOperation(value = "Unassign role to user")
    @DeleteMapping("/roles-remove-user-from-role")
    public ResponseEntity<?> unassignRoleToUser(@RequestBody RemoveUserFromRole request){

        boolean success = roleService.unassignRole(request);

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
    //delete role
    @ApiOperation(value = "Delete role")
    @DeleteMapping("/roles-delete-role")
    public ResponseEntity<?> deleteRole(@RequestBody  DeleteRoleRequest request){

        boolean success = roleService.deleteUserRole(request.getRoleId());
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

    //add new role
    @ApiOperation(value = "Create a new custom role")
    @PostMapping("/roles-add-new")
    public ResponseEntity<?> addNew(@RequestBody AddUserRoleRequest request) {

        boolean success = roleService.attemptCreateUserRole(request);

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

    // Get all roles
    @ApiOperation(value = "Get all users in a role")
    @PostMapping("/roles-get-users-in-role")
    public ResponseEntity<?> getAllUsersInRole(@RequestBody UsersInRoleRequest request) {

        ArrayNode list = roleService.loadUserRoles(request.getRoleId());

        //Response
        if(list != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
