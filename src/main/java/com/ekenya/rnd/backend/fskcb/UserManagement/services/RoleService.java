package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileAndRoleEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.RoleType;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserAccountEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.ProfilesAndRolesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.RoleRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AddUserRoleRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.AssignRoleToUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.RemoveUserFromRole;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.UpdateUserRoleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RoleService implements IRolesService {

    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    ProfilesAndRolesRepository profilesAndRolesRepository;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public ArrayNode loadAllRoles(){

        try{

            ArrayNode list = mObjectMapper.createArrayNode();

            for (UserRoleEntity userRole: roleRepository.findAll()) {
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("name",userRole.getName());
                node.put("id",userRole.getId());
                node.put("type",userRole.getType().toString());
                node.put("details",userRole.getInfo());
                list.add(node);
            }

            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return null;
    }

    public boolean attemptCreateUserRole(AddUserRoleRequest model) {

        try{

            if(!roleRepository.findByName(model.getName()).isPresent()){
                UserRoleEntity userRole = new UserRoleEntity();

                //
                userRole.setType(RoleType.CUSTOM);
                userRole.setName(model.getName());
                userRole.setInfo(model.getDesc());
                //

                roleRepository.save(userRole);
                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return false;
    }

    @Override
    public boolean updateUserRole(UpdateUserRoleRequest model) {

        try{

            Optional<UserRoleEntity> optionalUserRole = roleRepository.findById(model.getRoleId());

            if(optionalUserRole.isPresent() && optionalUserRole.get().getType() == RoleType.CUSTOM){
                UserRoleEntity userRole = optionalUserRole.get();

                //
                userRole.setName(model.getName());
                userRole.setInfo(model.getDesc());
                //

                roleRepository.save(userRole);

                return true;
            }else {
                //
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    public ObjectNode getUserRoleDetails(long id) {

        try{

            Optional<UserRoleEntity> optionalUserRole = roleRepository.findById(id);

            if(optionalUserRole.isPresent()){
                //
                ObjectNode node =mObjectMapper.createObjectNode();
                node.put("name",optionalUserRole.get().getName());
                node.put("type",optionalUserRole.get().getType().toString());
                node.put("id",optionalUserRole.get().getId());
                node.put("desc",optionalUserRole.get().getInfo());

                return node;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return null;
    }

    //delete role
    public boolean deleteUserRole(long id) {

        try{

            Optional<UserRoleEntity> optionalUserRole = roleRepository.findById(id);

            if(optionalUserRole.isPresent() && optionalUserRole.get().getType() == RoleType.CUSTOM){
                //

                roleRepository.delete(optionalUserRole.get());
                //Delete roles and profile
                for (ProfileAndRoleEntity profileRole:
                     profilesAndRolesRepository.findAllByRoleId(id)) {
                    //
                    profilesAndRolesRepository.delete(profileRole);
                }

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return false;
    }

    public boolean assignRole(AssignRoleToUserRequest model){
        try{

            UserAccountEntity user = userRepository.findById(model.getUserId()).orElse(null);
            UserRoleEntity role = roleRepository.findById(model.getRoleId()).orElse(null);
            if(user != null && role != null){

                Set<UserRoleEntity> userRoles = (Set<UserRoleEntity>) user.getRoles();
                userRoles.add(role);
                user.setRoles(userRoles);
                userRepository.save(user);

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        //
        return false;
    }

    public boolean unassignRole(RemoveUserFromRole model){
        try {
            UserAccountEntity user = userRepository.findById(model.getUserId()).orElse(null);

            UserRoleEntity role = roleRepository.findById(model.getRoleId()).orElse(null);

            if (user != null && role != null) {
                //
                Set<UserRoleEntity> userRoles = (Set<UserRoleEntity>) user.getRoles();
                userRoles.removeIf(x -> Objects.equals(x.getId(), model.getRoleId()));
                user.setRoles(userRoles);
                userRepository.save(user);
                //

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        //
        return false;
    }

    public ArrayNode loadUserRoles(Long userId){

        try{

            Optional<UserAccountEntity> userAccount = userRepository.findById(userId);

            if(userAccount.isPresent()){

                ArrayNode list = mObjectMapper.createArrayNode();

                for (UserRoleEntity userRole: userAccount.get().getRoles()) {
                    //

                    ObjectNode node = mObjectMapper.createObjectNode();
                    node.put("name",userRole.getName());
                    node.put("id",userRole.getId());
                    node.put("desc",userRole.getInfo());

                    node.put("type",userRole.getType().toString());

                    list.add(node);

                }

                return list;
            }

        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }


    public ArrayNode getUserNotRoles(Long userId){

        try{

            ArrayNode list = mObjectMapper.createArrayNode();
            for (UserRoleEntity userRole:
            roleRepository.getUserNotRoles(userId)) {
                //

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("name",userRole.getName());
                node.put("id",userRole.getId());
                node.put("desc",userRole.getInfo());

                node.put("type",userRole.getType().toString());

                list.add(node);
            }

            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
}
