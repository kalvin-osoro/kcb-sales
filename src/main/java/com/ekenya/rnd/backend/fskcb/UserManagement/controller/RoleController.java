package com.ekenya.rnd.backend.fskcb.UserManagement.controller;

import com.ekenya.rnd.backend.fskcb.UserManagement.entity.Role;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.RoleService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@Api(value = "Role Management")
@RestController()

@RequestMapping(path = "/api/v1")
public class RoleController {
    private final RoleService roleService;

    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

//
//    }
@GetMapping("roles/findById/{id}")
    @ResponseBody
    public Role findById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PutMapping(value="/roles/update")
    public String update(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }


    // Get all roles
    @ApiOperation(value = "Get all roles")
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getRoles();
    }
    //assign role to user
    @ApiOperation(value = "Assign role to user")
    @PostMapping("/roles/assign/{userId}/{roleId}")
    public String assignRoleToUser( @PathVariable Long userId,
                                           @PathVariable Long roleId){
        roleService.assignRole(userId,roleId);
        return "redirect:/user/find/"+userId;
    }
    //unassign role to user
    @ApiOperation(value = "Unassign role to user")
    @DeleteMapping("/roles/unassign/{userId}/{roleId}")
    public String unassignRoleToUser(@PathVariable Long userId,
                                     @PathVariable Long roleId){
        roleService.unassignRole(userId, roleId);
        return "redirect:/user/find/"+userId;
    }
    //delete role
    @ApiOperation(value = "Delete role")
    @DeleteMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable Long id){
        roleService.delete(id);
        return "redirect:/roles";
    }

    //add new role
    @ApiOperation(value = "Add new role")
    @PostMapping("/roles/addNew")
    public String addNew(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }


}
