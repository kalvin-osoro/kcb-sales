package com.ekenya.rnd.backend.fskcb.UserManagement.portalcontroller;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.RoleService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.UserService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@Api(value = "Role Management")
@RestController()

@RequestMapping(path = "/api/v1")
public class RolesVC {
    private final RoleService roleService;

    private final UserService userService;

    public RolesVC(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

//
//    }
@PostMapping("roles-findById/{id}")
    @ResponseBody
    public UserRole findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PutMapping(value="/roles-update")
    public ResponseEntity<?> update(UserRole role) {
        roleService.save(role);
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


    // Get all roles
    @ApiOperation(value = "Get all roles")
    @PostMapping("/roles-get-all")
    public ResponseEntity<?> getAllRoles() {

        List<?> list = roleService.getRoles();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //assign role to user
    @ApiOperation(value = "Assign role to user")
    @PostMapping("/roles-assign/{userId}/{roleId}")
    public ResponseEntity<?> assignRoleToUser(@PathVariable Long userId,
                                                        @PathVariable Long roleId){
        boolean success = roleService.assignRole(userId,roleId);

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
    //unassign role to user
    @ApiOperation(value = "Unassign role to user")
    @DeleteMapping("/roles-unassign/{userId}/{roleId}")
    public ResponseEntity<?> unassignRoleToUser(@PathVariable Long userId,
                                                          @PathVariable Long roleId){
        boolean success = roleService.unassignRole(userId, roleId);

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
    //delete role
    @ApiOperation(value = "Delete role")
    @DeleteMapping("/roles-delete/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id){

        boolean success = roleService.delete(id);
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

    //add new role
    @ApiOperation(value = "Add new role")
    @PostMapping("/roles-add-new")
    public ResponseEntity<?> addNew(UserRole role) {
        boolean success = roleService.save(role);

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
