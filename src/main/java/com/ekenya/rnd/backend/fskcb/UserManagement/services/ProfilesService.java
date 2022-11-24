package com.ekenya.rnd.backend.fskcb.UserManagement.services;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProfilesService implements IProfilesService{

    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    ProfilesAndRolesRepository profilesAndRolesRepository;

    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;

    @Autowired
    UserRepository userRepository;

    public final UserProfilesRepository userProfilesRepository;
    
    public final RoleRepository roleRepository;

    public ProfilesService(UserProfilesRepository userProfilesRepository, RoleRepository roleRepository) {
        this.userProfilesRepository = userProfilesRepository;
        this.roleRepository = roleRepository;
    }
    public ArrayNode getAllProfiles(){

        try{

            ArrayNode list = mObjectMapper.createArrayNode();

            for (UserProfileEntity profileEntity:
                 userProfilesRepository.findAll()) {
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("name",profileEntity.getName());
                node.put("id",profileEntity.getId());
                node.put("code",profileEntity.getCode());
                node.put("details",profileEntity.getInfo());
                list.add(node);
            }

            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    public boolean assignRolesToProfile(AssignProfileRolesRequest model){
        try{
            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(model.getProfileId());

            if(optionalUserProfile.isPresent()){

                UserProfileEntity profileEntity = optionalUserProfile.get();

                for (long rid: model.getRoles()) {
                    //

                    if(!profilesAndRolesRepository.findAllByProfileIdAndRoleIdAndStatus(model.getProfileId(), rid, Status.ACTIVE).isEmpty()) {
                        //Add
                        UserRoleEntity role = roleRepository.findById(rid).get();
                        //
                        ProfileAndRoleEntity profileRoleEntity  = new ProfileAndRoleEntity();
                        profileRoleEntity.setRoleId(role.getId());
                        profileRoleEntity.setProfileId(profileEntity.getId());
                        profileRoleEntity.setStatus(Status.ACTIVE);
                        //
                        userProfilesRepository.save(profileEntity);
                    }else{
                        //Already existing in profile
                    }
                }
            }
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }


        return true;
    }
    public boolean removeRolesFromProfile(RemoveProfileRolesRequest model){

        try{
            for (long id: model.getRemovedRoles()) {
                //
                for (ProfileAndRoleEntity profileRole: profilesAndRolesRepository.findAllByProfileIdAndStatus(model.getProfileId(), Status.ACTIVE)) {

                    UserRoleEntity role = roleRepository.findById(profileRole.getRoleId()).get();

                    if(id == role.getId()){
                        //
                        profileRole.setStatus(Status.DELETED);
                        break;
                    }

                }
            }
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }

        return true;
    }

    public boolean attemptAddProfile(AddUserProfileRequest model) {

        try{

            if(!userProfilesRepository.findByName(model.getName()).isPresent()){
                UserProfileEntity userProfile = new UserProfileEntity();

                userProfile.setCode(model.getCode());
                userProfile.setName(model.getName());
userProfile.setInfo(model.getDesc());

                userProfilesRepository.save(userProfile);
                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean attemptUpdateProfile(UpdateProfileRequest model) {

        try{

            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(model.getId());
            if(optionalUserProfile.isPresent()){
                UserProfileEntity userProfile = optionalUserProfile.get();

                userProfile.setCode(model.getCode());
                userProfile.setName(model.getName());
                userProfile.setInfo(model.getDesc());

                userProfilesRepository.save(userProfile);
                return true;
            }else{
                //Not found ..
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    public ArrayNode getRoleProfiles(long roleId){

        try{

            UserRoleEntity userRole = roleRepository.findById(roleId).get();

            ArrayNode list = mObjectMapper.createArrayNode();

            for (UserProfileEntity profileEntity:
                    userRole.getUserProfiles()) {
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("name",profileEntity.getName());
                node.put("id",profileEntity.getId());
                node.put("code",profileEntity.getCode());
                node.put("details",profileEntity.getInfo());

                list.add(node);
            }


            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
   public ArrayNode getRoleNotProfile(Long roleId){
       try{

           ArrayNode list = mObjectMapper.createArrayNode();

           for (UserProfileEntity profileEntity:  userProfilesRepository.getRoleNotPrivilege(roleId)) {
               ObjectNode node = mObjectMapper.createObjectNode();
               node.put("name",profileEntity.getName());
               node.put("id",profileEntity.getId());
               node.put("code",profileEntity.getCode());
               node.put("details",profileEntity.getInfo());

               list.add(node);
           }


           return list;
       }catch (Exception ex){
           log.error(ex.getMessage(),ex);
       }

        return null;
    }
    public boolean drop(long id) {
        try{

            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(id);

            if(optionalUserProfile.isPresent()) {
                UserProfileEntity profileEntity = optionalUserProfile.get();
                //
                userProfilesRepository.delete(profileEntity);

                //Remove profiles and users

                for (ProfileAndUserEntity profileUser:
                     profilesAndUsersRepository.findAllByProfileId(id)) {
                    profileUser.setStatus(Status.DELETED);
                    profilesAndUsersRepository.save(profileUser);
                }

                //Remove profiles and roles

                for (ProfileAndRoleEntity profileRole:
                        profilesAndRolesRepository.findAllByProfileId(id)) {
                    profileRole.setStatus(Status.DELETED);
                    profilesAndRolesRepository.save(profileRole);
                }
                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    public ObjectNode getProfileDetails(long id) {

        try{

            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(id);

            if(optionalUserProfile.isPresent()) {
                UserProfileEntity profileEntity = optionalUserProfile.get();

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("name", profileEntity.getName());
                node.put("id", profileEntity.getId());
                node.put("code", profileEntity.getCode());
                node.put("details", profileEntity.getInfo());
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public ArrayNode loadUsersInProfile(long profileId) {

        try{

            ArrayNode list = mObjectMapper.createArrayNode();

            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(profileId);

            //
            if(optionalUserProfile.isPresent()){

                for (ProfileAndUserEntity profileUser:
                     profilesAndUsersRepository.findAllByProfileIdAndStatus(profileId, Status.ACTIVE)) {

                    //
                    Optional<UserAccountEntity> account = userRepository.findById(profileUser.getUserId());
                    //
                    if(account.isPresent()){
                        //
                        ObjectNode node = mObjectMapper.createObjectNode();
                        node.put("name",account.get().getFullName());
                        node.put("id",account.get().getId());
                        node.put("staffNo",account.get().getStaffNo());
                        node.put("phoneNo",account.get().getPhoneNumber());
                        //
                        list.add(node);
                    }
                }
            }

            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public ArrayNode loadRolesInProfile(long profileId) {
        try{

            ArrayNode list = mObjectMapper.createArrayNode();

            Optional<UserProfileEntity> optionalUserProfile = userProfilesRepository.findById(profileId);

            //
            if(optionalUserProfile.isPresent()){

                for (ProfileAndRoleEntity profileRole:
                        profilesAndRolesRepository.findAllByProfileIdAndStatus(profileId, Status.ACTIVE)) {

                    //
                    Optional<UserRoleEntity> role = roleRepository.findById(profileRole.getRoleId());
                    //
                    if(role.isPresent()){
                        //
                        ObjectNode node = mObjectMapper.createObjectNode();
                        node.put("name",role.get().getName());
                        node.put("id",role.get().getId());
                        node.put("desc",role.get().getInfo());
                        node.put("type",role.get().getType().toString());
                        //
                        list.add(node);
                    }
                }
            }

            return list;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
}
