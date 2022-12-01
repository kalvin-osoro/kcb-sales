package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.BranchEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.JsonLatLng;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.ResetDSRPINRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.RegionsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.ProfileAndUserEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserProfileEntity;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.ProfilesAndUsersRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.ExcelHelper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.util.*;
import java.util.logging.Logger;

@Slf4j

@Service
public class DSRPortalService implements IDSRPortalService {

    @Autowired
    private IDSRTeamsRepository dsrTeamsRepository;

    @Autowired
    private IDSRRegionsRepository dsrRegionsRepository;
    @Autowired
    private IDSRAccountsRepository dsrAccountsRepository;
    @Autowired
    IBranchesRepository branchesRepository;

    @Autowired
    UserProfilesRepository userProfilesRepository;
    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;

    @Autowired
    IAuthService authService;

    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    private IZoneCoordinatesRepository zoneCoordinatesRepository;
    @Autowired
    private ExcelService excelService;

    @Autowired
    DateFormat dateFormat;


    private final static java.util.logging.Logger logger = Logger.getLogger(DSRPortalService.class.getName());

    @Override
    public boolean createRegion(AddRegionRequest model) {

        try{

            Optional<DSRRegionEntity> optionalDSRRegionEntity = dsrRegionsRepository.findByName(model.getName());

            if(!optionalDSRRegionEntity.isPresent()){

                DSRRegionEntity dsrRegionEntity = new DSRRegionEntity();

                dsrRegionEntity.setName(model.getName());
                dsrRegionEntity.setCode(model.getCode());
                if(model.getBounds() != null) {
                    dsrRegionEntity.setGeoJsonBounds(mObjectMapper.writeValueAsString(model.getBounds()));
                }
                dsrRegionsRepository.save(dsrRegionEntity);

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean updateRegion(UpdateRegionRequest model) {

        try{

            Optional<DSRRegionEntity> optionalDSRRegionEntity = dsrRegionsRepository.findById(model.getId());

            if(optionalDSRRegionEntity.isPresent()){

                DSRRegionEntity dsrRegionEntity = optionalDSRRegionEntity.get();

                dsrRegionEntity.setName(model.getName());
                dsrRegionEntity.setCode(model.getCode());
                if(model.getBounds() != null) {
                    dsrRegionEntity.setGeoJsonBounds(mObjectMapper.convertValue(model.getBounds(), String.class));
                }
                dsrRegionsRepository.save(dsrRegionEntity);

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public ObjectNode attemptImportRegions(MultipartFile importFile) {

        try{

            RegionsExcelImportResult results = ExcelHelper.excelToDSRRegions(importFile.getInputStream());

            int imported = 0;
            for (DSRRegionEntity region: results.getRegions()) {
                //
                if(!dsrRegionsRepository.findByName(region.getName()).isPresent()){

                    //
                    dsrRegionsRepository.save(region);
                    //
                    imported ++;
                }else{
                    results.getErrors().add(new ExcelImportError(0,0,"An DSR Region with Name '"+region.getName()+"' already exists"));
                }
            }
            //
            if(!results.getErrors().isEmpty()){
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("imported",imported);
                node.putPOJO("import-errors",mObjectMapper.convertValue(results.getErrors(),ArrayNode.class));
                //
                return node;
            }else{
                //
                return mObjectMapper.createObjectNode();
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public ArrayNode getAllRegions() {

        try {
            List<DSRRegionEntity> dsrRegionEntities = dsrRegionsRepository.findByStatus(Status.ACTIVE);
            ArrayNode list = mObjectMapper.createArrayNode();
            for (DSRRegionEntity entity : dsrRegionEntities){
                //int teamMembersCount = dsrAccountsRepository.findAllByTeamId(entity.getId()).size();
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",entity.getId());
                node.put("name",entity.getName());
                node.put("status",entity.getStatus().equals(Status.ACTIVE)?"Active":"Inactive");
                node.put("dateCreated",dateFormat.format(entity.getDateCreated()));
                //node.put("bounds",entity.getGeoJsonBounds());

                if(entity.getGeoJsonBounds() != null) {
                    TypeFactory typeFactory = mObjectMapper.getTypeFactory();
                    node.putPOJO("bounds", mObjectMapper.readValue(entity.getGeoJsonBounds(),
                            typeFactory.constructCollectionType(List.class, JsonLatLng.class)));
                }else{
                    node.putPOJO("bounds",mObjectMapper.createArrayNode());
                }
                //
                List<DSRTeamEntity> teams = dsrTeamsRepository.findAllByRegionId(entity.getId());
                int dsrCount = 0;
                for (DSRTeamEntity team: teams) {
                    dsrCount += dsrAccountsRepository.findAllByTeamId(team.getId()).size();
                }

                //
                node.put("teams",teams.size());
                //
                node.put("dsr-count",dsrCount);
                list.add(node);
            }

            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public boolean addDSRTeam(AddTeamRequest addTeamRequest) {

        try {
            if(addTeamRequest == null) throw new RuntimeException("Bad request");
            if (dsrTeamsRepository.existsByName(addTeamRequest.getTeamName())) {
                log.error("Error: DSR team is already taken!", "failed");
                return false;
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null)throw new RuntimeException("Please login to add Dsr");
            String userId= userDetails.getUsername();
            logger.info("adding team step 2");


            DSRTeamEntity dsrTeam = new DSRTeamEntity();
            dsrTeam.setName(addTeamRequest.getTeamName());
            dsrTeam.setLocation(addTeamRequest.getTeamLocation());
            dsrTeam.setCreatedBy(userId);
            dsrTeam.setStatus(Status.ACTIVE);
            dsrTeam.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeamsRepository.save(dsrTeam);
//            responseObject.put("status", "success");
//            responseObject.put("message", "DSR team "
//                    + addTeamRequest.getTeamName()+" successfully created");
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return false;
    }

    @Override
    public boolean editDSRTeam(AddTeamRequest addTeamRequest) {
//        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();

        try {
            if (addTeamRequest == null)throw new RuntimeException("Bad request");
            if(!Utility.validateStatus(addTeamRequest.getStatus()))
                throw new RuntimeException("Status is invalid");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String userId = userDetails.getUsername();
            Optional<DSRTeamEntity> optionalDSRTeam =
                    dsrTeamsRepository.findById(addTeamRequest.getId());
            DSRTeamEntity dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(addTeamRequest.getStatus());
            dsrTeam.setName(addTeamRequest.getTeamName());
            dsrTeam.setLocation(addTeamRequest.getTeamLocation());
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamsRepository.save(dsrTeam);
//            responseObject.put("status", "success");
//            responseObject.put("message", "DSR team "
//                    + addTeamRequest.getTeamName()+" successfully updated");
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public ArrayNode getAllDSRTeams() {
        try {
            List<DSRTeamEntity> dsrTeamList = dsrTeamsRepository.findByStatus(Status.ACTIVE);
            ArrayNode dsrTeamResponseList = mObjectMapper.createArrayNode();
            for (DSRTeamEntity entity : dsrTeamList){
                int teamMembersCount = dsrAccountsRepository.findAllByTeamId(entity.getId()).size();
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",entity.getId());
                node.put("name",entity.getName());
                node.put("loc",entity.getLocation());
                node.put("leadTargetValue",entity.getLeadsTargetValue());
                node.put("campaignTargetValue",entity.getCampaignTargetValue());
                node.put("visitTargetValue",entity.getVisitsTargetValue());
                node.put("onboardingTargetValue",entity.getOnboardTargetValue());
                node.put("status",entity.getStatus().equals(Status.ACTIVE)?"Active":"Inactive");
                if(entity.getCreatedOn() != null) {
                    node.put("dateCreated", dateFormat.format(entity.getCreatedOn()));
                }else{
                    node.put("dateCreated","");
                }
                node.put("members-count",teamMembersCount);
                dsrTeamResponseList.add(node);
            }

            return dsrTeamResponseList;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public ArrayNode getTeamMembersByTeamId(long id) {
        try{
            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(id);
            if(!optionalDSRTeam.isPresent())
                throw new RuntimeException("Team is not present");
            ArrayNode members = mObjectMapper.createArrayNode();
            for (DSRAccountEntity m:
                    dsrAccountsRepository.findAllByTeamId(optionalDSRTeam.get().getId())) {

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",m.getId());
                node.put("name",m.getFullName());
                node.put("email",m.getEmail());
                node.put("phone",m.getPhoneNo());
                node.put("staffNo",m.getStaffNo());
                node.put("salesCode",m.getSalesCode());

                members.add(node);
            }
            return members;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public boolean deleteDSRTeam(long id) {

        try {
            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(id);
            if(!optionalDSRTeam.isPresent())
                throw new RuntimeException("Team is not present");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String userId = userDetails.getUsername();
            DSRTeamEntity dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(Status.INACTIVE);
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamsRepository.save(dsrTeam);
//            responseObject.put("status", "success");
//            responseObject.put("message", "DSR team "
//                    +dsrTeam.getName()+" successfully deleted");
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return false;
    }

    @Override
    public boolean addDSR(AddDSRAccountRequest dsrRequest) {
        try {
            if (dsrRequest == null) {
                throw new Exception("Bad request");
            }

//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//            if (userDetails == null)throw new RuntimeException("Service error");
//            String createdBy = userDetails.getUsername();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String createdBy = authentication.getName();

            DSRTeamEntity optionalDSRTeam =
                    dsrTeamsRepository.findById(dsrRequest.getTeamId()).orElse(null);
            if(optionalDSRTeam != null &&
                    !dsrAccountsRepository.findByStaffNo(dsrRequest.getStaffNo()).isPresent()){
                //
                DSRAccountEntity dsrDetails =  DSRAccountEntity.builder()
                        .email(dsrRequest.getEmail())
                        .phoneNo(dsrRequest.getPhoneNo())
                        .status(Status.ACTIVE)
                        .fullName(dsrRequest.getFullName())
                        .staffNo(dsrRequest.getStaffNo())
                        .teamId(optionalDSRTeam.getId())
                        .salesCode(dsrRequest.getSalesCode())
                        .createdBy(createdBy)
                        .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
                        .build();
                //
                if(dsrRequest.getExpiry() != null){
                    dsrDetails.setExpiryDate(dsrRequest.getExpiry());
                }
                //
                dsrAccountsRepository.save(dsrDetails);

//                if(!dsrRequest.getProfiles().isEmpty()){
//
//                    for (Long profileId:
//                         dsrRequest.getProfiles()) {
//
//                        UserProfileEntity userProfile = userProfilesRepository.findById(profileId).orElse(null)
//
//                                if(userProfile != null){
//
//                                    ProfileAndUserEntity profileAndUserEntity = new ProfileAndUserEntity();
//                                    profileAndUserEntity.setUserId();
//                                }
//
//                    }
//                }
                return true;
            }
            //Team not found or dsr already exists
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return false;

    }

    @Override
    public ObjectNode loadTeamDetails(TeamDetailsRequest model) {

        try{

            Optional<DSRTeamEntity> optionalTeam = dsrTeamsRepository.findById(model.getTeamId());

            if(optionalTeam.isPresent()){

                DSRTeamEntity team = optionalTeam.get();

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",team.getId());
                node.put("name",team.getName());
                node.put("loc",team.getLocation());

                DSRRegionEntity regionEntity = dsrRegionsRepository.getById(team.getRegionId());
                node.put("region",regionEntity.getName());
                node.put("bounds", regionEntity.getGeoJsonBounds());
                node.put("status",regionEntity.getStatus().toString());
                node.put("dateCreated",dateFormat.format(regionEntity.getDateCreated()));

                return node;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public boolean attemptActivateTeam(long teamId) {

        try{

            Optional<DSRTeamEntity> optionalTeam = dsrTeamsRepository.findById(teamId);

            if(optionalTeam.isPresent()){

                DSRTeamEntity account = optionalTeam.get();

                if(account.getStatus() != Status.ACTIVE) {
                    account.setStatus(Status.ACTIVE);
                    account.setUpdatedOn(Calendar.getInstance().getTime());

                    dsrTeamsRepository.save(account);

                    return true;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean attemptDeactivateTeam(long teamId) {

        try{

            Optional<DSRTeamEntity> optionalTeam = dsrTeamsRepository.findById(teamId);

            if(optionalTeam.isPresent()){

                DSRTeamEntity account = optionalTeam.get();

                if(account.getStatus() != Status.INACTIVE) {
                    account.setStatus(Status.INACTIVE);
                    account.setUpdatedOn(Calendar.getInstance().getTime());

                    dsrTeamsRepository.save(account);

                    return true;
                }
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return false;
    }

    @Override
    public boolean attemptAddTeamMember(AddTeamMemberRequest model) {

        try{

            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(model.getTeamId());
            Optional<DSRAccountEntity> optionalDSRAccount = dsrAccountsRepository.findByStaffNo(model.getStaffNo());
            if(optionalDSRTeam.isPresent() && optionalDSRAccount.isPresent()){

                optionalDSRTeam.get().getMembers().add(optionalDSRAccount.get());
                dsrTeamsRepository.save(optionalDSRTeam.get());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public boolean attemptRemoveTeamMember(RemoveTeamMemberRequest model) {

        try {
            DSRTeamEntity teamEntity = dsrTeamsRepository.findById(model.getTeamId()).orElse(null);

            DSRAccountEntity dsrAccount = dsrAccountsRepository.findByStaffNo(model.getStaffNo()).orElse(null);

            if (teamEntity != null && dsrAccount != null) {
                //
                Set<DSRAccountEntity> teamMembers = (Set<DSRAccountEntity>) teamEntity.getMembers();
                teamMembers.removeIf(x -> Objects.equals(x.getId(), dsrAccount.getId()));
                teamEntity.setMembers(teamMembers);
                dsrTeamsRepository.save(teamEntity);
                //

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean attemptUpdateTeamMembers(UpdateTeamMembersRequest model) {

        try{

            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(model.getTeamId());

            if(optionalDSRTeam.isPresent()){



                //
                Set<DSRAccountEntity> teamMembers = (Set<DSRAccountEntity>) optionalDSRTeam.get().getMembers();

                //Removal
                for (DSRAccountEntity acc:  optionalDSRTeam.get().getMembers()) {
                    //
                    boolean found = false;
                    for (long userId: model.getMembers()) {

                        if(userId == acc.getId()){
                            found = true;
                            break;
                        }
                    }
                    //Not found in new list
                    if(!found){
                        teamMembers.removeIf(x -> Objects.equals(x.getId(), acc.getId()));
                    }
                }

                //Additions
                for (long userId: model.getMembers()) {

                    //
                    boolean found = false;
                    for (DSRAccountEntity acc:  optionalDSRTeam.get().getMembers()) {
                        if (userId == acc.getId()) {
                            found = true;
                            break;
                        }
                    }
                    //Not found in existing list
                    if(!found){
                        Optional<DSRAccountEntity> optionalDSRAccount = dsrAccountsRepository.findById(userId);
                        if(optionalDSRAccount.isPresent()){

                            teamMembers.add(optionalDSRAccount.get());
                        }
                    }
                }
                //
                optionalDSRTeam.get().setMembers(teamMembers);
                dsrTeamsRepository.save(optionalDSRTeam.get());
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return false;
    }

    @Override
    public ArrayNode getAllDSRAccounts() {

        try{

            ArrayNode list = mObjectMapper.createArrayNode();

            for (DSRAccountEntity entity: dsrAccountsRepository.findAll()) {
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",entity.getId());
                node.put("name",entity.getFullName());
                node.put("email",entity.getEmail());
                node.put("phone",entity.getPhoneNo());
                node.put("staffNo",entity.getStaffNo());
                node.put("status",entity.getStatus().toString());
                node.put("salesCode",entity.getSalesCode());
                //
                Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(entity.getTeamId());
                if(optionalDSRTeam.isPresent()){
                    node.put("teamName",optionalDSRTeam.get().getName());
                    node.put("teamLoc",optionalDSRTeam.get().getLocation());
                }else{
                    node.put("teamName","");
                    node.put("teamLoc","");
                }
                //
                if(entity.getBranchId() != null) {
                    BranchEntity branchEntity = branchesRepository.findById(entity.getBranchId()).orElse(null);
                    if(branchEntity!=null){
                        node.put("branchName",branchEntity.getName());
                    }else{
                        node.put("branchName","");
                    }
                }else{
                    //
                    node.put("branchName","");
                }
                //
                if(entity.getExpiryDate() != null) {
                    node.put("expiry", dateFormat.format(entity.getExpiryDate()));
                }else{
                    node.put("expiry","");
                }
                //
                list.add(node);
            }
            return list;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public boolean deleteDSRById(long id) {

        try{
            Optional<DSRAccountEntity> optionalDSRAccountEntity = dsrAccountsRepository.findById(id);
            DSRAccountEntity dsrDetails = optionalDSRAccountEntity.get();
            //
            excelService.deleteSystemUser(dsrDetails.getId());

            dsrDetails.setStatus(Status.DELETED);
            dsrAccountsRepository.save(dsrDetails);
//            responseObject.put("status", "success");
//            responseObject.put("message", "DSR "
//                    +dsrDetails.getFullName()+" successfully deleted");
            return true;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return false;
    }

//    @Override
//    public ResponseEntity<?> editDSRById(UserAppDto dsrRequest, HttpServletRequest httpServletRequest) {
//        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
//        try {
//            logger.info("Step 0");
//            if (dsrRequest == null) throw new Exception("Bad request");
//            logger.info("Step 1");
//            if(!Utility.validateGender(dsrRequest.getGender()))
//                throw new RuntimeException("Gender is invalid");
//            logger.info("Step 2");
//            if(!Utility.validateStatus(dsrRequest.getStatus()))
//                throw new RuntimeException("Status is invalid");
//            logger.info("Step 3");
//            JsonObject userObject = excelService.updateUser(,httpServletRequest);
//
//            Optional<DSRTeam> optionalDSRTeam =
//                    dsrTeamRepository.findById(dsrRequest.getTeamId());
//            Optional<DSRAccountEntity> optionalDSRAccountEntity =
//                    dsrDetailsRepository.findById(dsrRequest.getDsrId());
//            DSRAccountEntity dsrDetails = optionalDSRAccountEntity.get();
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
//            if (userDetails == null)throw new RuntimeException("Service error");
//            String updatedBy = userDetails.getUsername();
//            dsrDetails.setEmail(dsrRequest.getEmail());
//            dsrDetails.setMobileNo(dsrRequest.getMobileNo());
//            dsrDetails.setMobileNo(dsrRequest.getMobileNo());
//            dsrDetails.setStatus(Status.ACTIVE);
//            dsrDetails.setFirstName(dsrRequest.getFirstName());
//            dsrDetails.setLastName(dsrRequest.getLastName());
//            dsrDetails.setOtherName(dsrRequest.getOtherName());
//            dsrDetails.setLocation(dsrRequest.getLocation());
//            dsrDetails.setGender(dsrRequest.getGender().trim());
//            dsrDetails.setIdNumber(dsrRequest.getIdNumber());
//            dsrDetails.setDsrTeam(optionalDSRTeam.get());
//            dsrDetails.setUpdatedBy(updatedBy);
//            dsrDetails.setUpdatedOn(Utilities.getMYSQLCurrentTimeStampForInsert());
//            dsrDetailsRepository.save(dsrDetails);
//            responseObject.put("status", "success");
//            responseObject.put("message", "DSR "
//                    +dsrDetails.getFirstName()+" successfully updated");
//            return ResponseEntity.ok().body(responseObject);
//        }catch (Exception e){
//            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
//        }


    @Override
    public ObjectNode getDSRProfile(String  staffNo) {

        try {

            Optional<DSRAccountEntity> optionalDSRAccountEntity =
                    dsrAccountsRepository.findByStaffNo(staffNo);

            if (optionalDSRAccountEntity.isPresent()){

                DSRAccountEntity entity = optionalDSRAccountEntity.get();

                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",entity.getId());
                node.put("name",entity.getFullName());
                node.put("email",entity.getEmail());
                node.put("phone",entity.getPhoneNo());
                node.put("staffNo",entity.getStaffNo());
                node.put("salesCode",entity.getSalesCode());
                node.put("status",entity.getStatus().toString());
                node.put("dateCreated",dateFormat.format(entity.getCreatedOn()));
                if(entity.getExpiryDate() != null) {
                    node.put("expiry", dateFormat.format(entity.getExpiryDate()));
                }else{
                    node.put("expiry", "");
                }
                return node;
            }

            //Not found

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

        return null;
    }

    @Override
    public ObjectNode attemptImportAccounts(MultipartFile importFile) {
        try{

            DSRsExcelImportResult results = ExcelHelper.excelToDSRAccounts(importFile.getInputStream());

            int imported = 0;
            for (DSRAccountEntity account: results.getAccounts()) {
                //
                if(!dsrAccountsRepository.findByStaffNo(account.getStaffNo()).isPresent()){

                    //
                    dsrAccountsRepository.save(account);
                    //
                    imported ++;
                }else{
                    results.getErrors().add(new ExcelImportError(0,0,"An DSR Account with Staff No '"+account.getStaffNo()+"' already exists"));
                }
            }
            //
            if(!results.getErrors().isEmpty()){
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("imported",imported);
                node.putPOJO("import-errors",mObjectMapper.convertValue(results.getErrors(),ArrayNode.class));
                //
                return node;
            }else{
                //
                return mObjectMapper.createObjectNode();
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    public boolean lockAccount(String staffNo) {

        try{

            Optional<DSRAccountEntity> dsrAccountEntity = dsrAccountsRepository.findByStaffNo(staffNo);

            if(dsrAccountEntity.isPresent()){
                //
                dsrAccountEntity.get().setLocked(true);
                dsrAccountEntity.get().setDateLocked(Calendar.getInstance().getTime());
                //
                dsrAccountsRepository.save(dsrAccountEntity.get());

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean unlockAccount(String staffNo) {
        try{

            Optional<DSRAccountEntity> dsrAccountEntity = dsrAccountsRepository.findByStaffNo(staffNo);

            if(dsrAccountEntity.isPresent()){
                //
                dsrAccountEntity.get().setLocked(false);
                dsrAccountEntity.get().setDateUnlocked(Calendar.getInstance().getTime());
                //
                dsrAccountsRepository.save(dsrAccountEntity.get());

                return true;
            }
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return false;
    }

    @Override
    public boolean resetPIN(String staffNo) {

        ResetDSRPINRequest model= new ResetDSRPINRequest();
        model.setStaffNo(staffNo);
        return authService.resetDSRPIN(model);
    }


//    @Override
//    public ResponseEntity<?> assignTarget(Long dsrId, Long targetId) {
//        LinkedHashMap<String,Object>responseObject=new LinkedHashMap<>();
//        LinkedHashMap<String,Object>responseParams=new LinkedHashMap<>();
//
//        try {
//          //assign target to dsr
//            DSR dsr=dsrRepository.findById(dsrId).orElseThrow(()->new ResourceNotFoundException("DSR","id",dsrId));
//            Target target=targetRepository.findById(targetId).orElseThrow(()->new ResourceNotFoundException("Target","id",targetId));
//            dsr.setTarget(target);
//            dsrRepository.save(dsr);
//            responseObject.put("status",SUCCESS);
//            responseObject.put("message","Target assigned successfully");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok().body(responseObject);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
//        }
//    }
//
//    @Override
//    public ResponseEntity<?> unassignTarget(Long dsrId, Long targetId) {
//        LinkedHashMap<String,Object>responseObject=new LinkedHashMap<>();
//        LinkedHashMap<String,Object>responseParams=new LinkedHashMap<>();
//
//        try {
//            //unassign target from dsr
//            DSR dsr=dsrRepository.findById(dsrId).orElseThrow(()->new ResourceNotFoundException("DSR","id",dsrId));
//            Target target=targetRepository.findById(targetId).orElseThrow(()->new ResourceNotFoundException("Target","id",targetId));
//            dsr.setTarget(null);
//            dsrRepository.save(dsr);
//            responseObject.put("status",SUCCESS);
//            responseObject.put("message","Target unassigned successfully");
//            responseObject.put("data",responseParams);
//            return ResponseEntity.ok().body(responseObject);
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
//        }
//    }
//
//    //convert entity to dto
//    private DSRDto mapToDto(DSR dsr) {
//
//        return modelMapper.map(dsr, DSRDto .class);
//
//    }
//    //convert dto to entity
//    private DSR mapToEntity(DSRDto dsrDto) {
//
//        return modelMapper.map(dsrDto, DSR.class);
//
//    }
}
