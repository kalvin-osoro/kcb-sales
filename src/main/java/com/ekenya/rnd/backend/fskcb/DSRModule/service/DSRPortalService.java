package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.ISmsService;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.SMSService;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBCustomerVisitEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.JsonLatLng;
import com.ekenya.rnd.backend.fskcb.AuthModule.models.reqs.ResetDSRPINRequest;
import com.ekenya.rnd.backend.fskcb.AuthModule.services.IAuthService;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.RegionsExcelImportResult;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.IUserAccountsRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.ProfilesAndUsersRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.UserProfilesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.helper.ExcelHelper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.ExcelImportError;
import com.ekenya.rnd.backend.fskcb.UserManagement.payload.AddAdminUserRequest;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.IUsersService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.UsersService;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j

@Service
public class DSRPortalService implements IDSRPortalService {


//    @Value("ECLECTICS")
    @Value("CMCOM")
    private String SMS_SENDER_ID;
    @Value("ECLECTICS")
    private String SMS_SENDER_ID1;

    @Value("5094")
    private String client_id;
    @Value("https://testgateway.ekenya.co.ke:8443/ServiceLayer/pgsms/send")
    private String SMS_GATEWAY_URL;
    @Value("janty")
    private String SMS_USER_NAME;
    @Value("b0c95e2144bdd4c86b94501a814f9bbd9d025651d8497df04b7b7f318fe5172088c491906756a67727f6ea964e9caf1c034bf9bb267b821e6b43cb3dcc569d0f")
    private String SMS_PASSWORD;
    @Value("30")
    private String otpExpiresIn;
    //

    @Autowired
    private IDSRTeamsRepository dsrTeamsRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    ISmsService smsService;

    @Autowired
    private IDSRRegionsRepository dsrRegionsRepository;

    @Autowired
    private ISectorRepository sectorRepository;
    @Autowired
    private IDSRAccountsRepository dsrAccountsRepository;
    @Autowired
    IBranchesRepository branchesRepository;

    @Autowired
    UserProfilesRepository userProfilesRepository;
    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;

    @Autowired
    IUserAccountsRepository userAccountsRepository;

    @Autowired
    IUsersService usersService;
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
            dsrTeam.setProfileCode(addTeamRequest.getProfileCode());
            dsrTeam.setLocation(addTeamRequest.getTeamLocation());
            dsrTeam.setCreatedBy(userId);
            dsrTeam.setCreatedBy(userId);
            dsrTeam.setRegionId(addTeamRequest.getZoneId());
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
                DSRRegionEntity regionEntity =dsrRegionsRepository.findById(entity.getRegionId()).get();
                node.put("region",regionEntity.getName());
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
//principal
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String createdBy = authentication.getName();

            //Get profile ..
            UserProfileEntity profileEntity =
                    userProfilesRepository.findByCode(dsrRequest.getProfileCode()).orElse(null);
            //
            DSRTeamEntity optionalDSRTeam =
                    dsrTeamsRepository.findById(dsrRequest.getTeamId()).orElse(null);
            //

            if(optionalDSRTeam != null &&
                    !dsrAccountsRepository.findByStaffNo(dsrRequest.getStaffNo()).isPresent()){
                //
                DSRAccountEntity dsrDetails =  DSRAccountEntity.builder()
                        .email(dsrRequest.getEmail())
                        .phoneNo(dsrRequest.getPhoneNo())
                        .profileCode(profileEntity.getCode())
                        .status(Status.ACTIVE)
                        .fullName(dsrRequest.getFullName())
                        .staffNo(dsrRequest.getStaffNo())
                        .teamId(optionalDSRTeam.getId())
                        .isRm(dsrRequest.getIsRM())
                        .salesCode(dsrRequest.getSalesCode())
                        .createdBy(createdBy)
                        .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
                        .build();
                //
                if(dsrRequest.getExpiry() != null){
                    dsrDetails.setExpiryDate(dsrRequest.getExpiry());
                }

                //Create inactive user account ..
                AddAdminUserRequest req = new AddAdminUserRequest();
                req.setEmail(dsrDetails.getEmail());
                req.setFullName(dsrDetails.getFullName());
                req.setPhoneNo(dsrDetails.getPhoneNo());
                req.setStaffNo(dsrDetails.getStaffNo());
                req.setIsRm(dsrDetails.getIsRm());

                //Save DSR Account ..

                //
                if(userAccountsRepository.existsByStaffNo(dsrDetails.getStaffNo())){

                    //
                    UserAccountEntity userAccount =
                            userAccountsRepository.findByStaffNo(dsrDetails.getStaffNo()).get();
                    //
                    //check existing profileId and userId
                    //TODO check existing profileId and userId
                    ProfileAndUserEntity profileAndUserEntity = new ProfileAndUserEntity();
                    profileAndUserEntity.setProfileId(profileEntity.getId());
                    profileAndUserEntity.setUserId(userAccount.getId());
                    profileAndUserEntity.setStatus(Status.ACTIVE);

                    //
                    dsrAccountsRepository.save(dsrDetails);
                    //send email if user saved successfully


                    //Add DSR to profile ..
                    profilesAndUsersRepository.save(profileAndUserEntity);

                }else if(usersService.attemptCreateUser(req,AccountType.DSR,false)) {
                    //
                    UserAccountEntity userAccount =
                            userAccountsRepository.findByStaffNo(dsrDetails.getStaffNo()).get();
                    //
                    ProfileAndUserEntity profileAndUserEntity = new ProfileAndUserEntity();
                    profileAndUserEntity.setProfileId(profileEntity.getId());
                    profileAndUserEntity.setUserId(userAccount.getId());
                    profileAndUserEntity.setStatus(Status.ACTIVE);

                    //
                    dsrAccountsRepository.save(dsrDetails);
                    //send email if user saved successfully
                    if (dsrDetails.getExpiryDate() != null) {
                        String subject = "DSR Account Created";
                        String message = "Dear " + dsrDetails.getFullName() + ",\n\n" +
                                "Your DSR account has been created successfully.\n" +
                                "Your account details are as follows:\n" +
                                "Staff No: " + dsrDetails.getStaffNo() + "\n" +
                                "Phone No: " + dsrDetails.getPhoneNo() + "\n" +
                                "Sales Code: " + dsrDetails.getSalesCode() + "\n" +
                                "Expiry Date: " + dsrDetails.getExpiryDate() + "\n\n" +
                                "Please use this link to download the app:\n" +
                                "https://play.google.com/apps/internaltest/4701657927919684045\n\n" +
                                "Thank you.";
                        sendEmail(dsrDetails.getEmail(), subject, message);
                    }

                    //Add DSR to profile ..
                    profilesAndUsersRepository.save(profileAndUserEntity);
                }else{
                    //User Account not found ..
                    //Creation also failed,,
                }
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

    private void sendEmail(String email, String subject, String message) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(email);
            msg.setSubject(subject);
            msg.setText(message);
            javaMailSender.send(msg);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
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
    public ArrayNode getAllDSRAccounts(DSRAccountsRequest model) {
//        System.out.println("model.getProfileCode()");
        System.out.println(model.getProfileCode());
        try{

            ArrayNode list = mObjectMapper.createArrayNode();
            //
            List<DSRAccountEntity> dsrAccountEntities = new ArrayList<>();
            if(model.getProfileCode() != null) {
                //Get profile ..
                UserProfileEntity profileEntity = userProfilesRepository.findByCode(model.getProfileCode()).orElse(null);

                //
                if(profileEntity != null) {
                    //Each user in profile ..
                    for (ProfileAndUserEntity pu :
                            profilesAndUsersRepository.findAllByProfileId(profileEntity.getId())) {
                        //Get User Account ..
                        UserAccountEntity userAccount = userAccountsRepository
                                .findByIdAndStatus(pu.getProfileId(),Status.ACTIVE).orElse(null);
                        if(userAccount != null){
                            //Get Linked DSR Account
                            DSRAccountEntity dsrAccount = dsrAccountsRepository.findByStaffNo(userAccount.getStaffNo()).orElse(null);
                            //
                            if(dsrAccount != null){
                                //Add this one ..
                                dsrAccountEntities.add(dsrAccount);
                            }
                        }
                    }
                }
            }else {
                dsrAccountEntities = dsrAccountsRepository.findAll();
            }
            //Add them to response ..
            for (DSRAccountEntity entity:dsrAccountEntities) {
                //
                ObjectNode node = mObjectMapper.createObjectNode();
                node.put("id",entity.getId());
                node.put("name",entity.getFullName());
                node.put("email",entity.getEmail());
                node.put("phone",entity.getPhoneNo());
                node.put("staffNo",entity.getStaffNo());
                node.put("status",entity.getStatus().toString());
                node.put("salesCode",entity.getSalesCode());
                node.put("profileCode",entity.getProfileCode());
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
                node.put("teamId",entity.getTeamId());
                DSRTeamEntity dsrTeamEntity = dsrTeamsRepository.findById(entity.getTeamId()).get();
                node.put("regionId",dsrTeamEntity.getRegionId());
                DSRRegionEntity dsrRegionEntity = dsrRegionsRepository.findById(dsrTeamEntity.getRegionId()).get();
                node.put("regionBounds",dsrRegionEntity.getGeoJsonBounds());
                node.put("regionName",dsrRegionEntity.getName());
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
    public ObjectNode attemptImportAccounts(MultipartFile importFile, String profileCode) {
        try{
            //
            UserProfileEntity profileEntity = userProfilesRepository.findByCode(profileCode).get();
            //
            DSRsExcelImportResult results = ExcelHelper.excelToDSRAccounts(importFile.getInputStream(),dsrTeamsRepository);
            //
            int imported = 0;
            for (DSRAccountEntity account: results.getAccounts()) {
                //
                if(!dsrAccountsRepository.findByEmail(account.getEmail()).isPresent()){

                    //Create inactive user account ..
                    AddAdminUserRequest req = new AddAdminUserRequest();
                    req.setEmail(account.getEmail());
                    req.setFullName(account.getFullName());
                    req.setPhoneNo(account.getPhoneNo());
                    req.setStaffNo(account.getStaffNo());
                    req.setIsRm(account.getIsRm());

                    //
                    if(usersService.attemptCreateUser(req,AccountType.DSR,false)) {
                        //
                        UserAccountEntity userAccount =
                                userAccountsRepository.findByStaffNo(account.getStaffNo()).get();
                        //
                        ProfileAndUserEntity profileAndUserEntity = new ProfileAndUserEntity();
                        profileAndUserEntity.setProfileId(profileEntity.getId());
                        profileAndUserEntity.setUserId(userAccount.getId());
                        profileAndUserEntity.setStatus(Status.ACTIVE);

                        //

                        //Save DSR Account ..
                        account.setProfileCode(profileEntity.getCode());

                        DSRAccountEntity account1 = dsrAccountsRepository.save(account);
                        //send email

                        if (account1.getEmail() != null && !account1.getEmail().isEmpty()) {
                            String subject = "DSR Account Created";
                            String message = "Dear " + account1.getFullName() + ",\n\n" +
                                    "Your DSR account has been created successfully.\n" +
                                    "Your account details are as follows:\n" +
                                    "Staff No: " + account1.getStaffNo() + "\n" +
                                    "Phone No: " + account1.getPhoneNo() + "\n" +
                                    "Sales Code: " + account1.getSalesCode() + "\n" +
                                    "Expiry Date: " + account1.getExpiryDate() + "\n\n" +
                                    "Please use this link to download the app:\n" +
                                    "https://play.google.com/apps/internaltest/4701657927919684045\n\n" +
                                    "Thank you.";
                            sendEmail(account1.getEmail(), subject, message);
                        }
                        //send sms
                        if (account1.getPhoneNo() != null && !account.getPhoneNo().isEmpty()) {
                            String message = "Dear " + account1.getFullName() + ",\n\n" +
                                    "Your DSR account has been created successfully.\n" +
                                    "Your account details are as follows:\n" +
                                    "Staff No: " + account1.getStaffNo() + "\n" +
                                    "Phone No: " + account1.getPhoneNo() + "\n" +
                                    "Sales Code: " + account1.getSalesCode() + "\n" +
                                    "Expiry Date: " + account1.getExpiryDate() + "\n\n" +
                                    "Please use this link to download the app:\n" +
                                    "https://play.google.com/apps/internaltest/4701657927919684045\n\n" +
                                    "Thank you.";
                            sendSMS(account1.getPhoneNo(), message);

                        }

                        //Add DSR to profile ..
                        profilesAndUsersRepository.save(profileAndUserEntity);
                    }
                    //
                    imported ++;
                }else{
                    results.getErrors().add(new ExcelImportError(0,0,"An DSR Account with Email '"+account.getEmail()+"' already exists"));
                }
            }
            //
            ObjectNode node = mObjectMapper.createObjectNode();
            node.put("imported",imported);
            //
            if(!results.getErrors().isEmpty()){
                //
                node.putPOJO("import-errors",mObjectMapper.convertValue(results.getErrors(),ArrayNode.class));
                //
            }else{
                //
                node.putPOJO("import-errors",mObjectMapper.createArrayNode());
            }
            return node;
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }
    @Scheduled(fixedRate = 1)
    private JsonObject sendSMS(String phoneNo, String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
////        String url = environment.getProperty("sms_gateway.url");
//        String client_id=
//        String username =
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        int max = 99999999, min = 10000000;
//        String password =
        JsonObject jsonObjectBody = new JsonObject();
        jsonObjectBody.addProperty("to", phoneNo);
        jsonObjectBody.addProperty("message", message);

        if (LocalTime.now().isBefore(LocalTime.of(18, 0))) {
            jsonObjectBody.addProperty("from", SMS_SENDER_ID1);
        } else {
            jsonObjectBody.addProperty("from", SMS_SENDER_ID);
        }
//        jsonObjectBody.addProperty("from", SMS_SENDER_ID);
        jsonObjectBody.addProperty("transactionID", "FS"+(random.nextInt((max + 1)-min)+min));
        jsonObjectBody.addProperty("clientid", client_id);
        jsonObjectBody.addProperty("username", SMS_USER_NAME);
        jsonObjectBody.addProperty("password", SMS_PASSWORD);
        logger.info("Message body is: " + jsonObjectBody);
        HttpEntity<String> entity = new HttpEntity<>(jsonObjectBody.toString(), headers);
        ResponseEntity<String> responseEntity = null;

        try {
            logger.info("Sending sms");
            responseEntity = restTemplate.postForEntity(SMS_GATEWAY_URL,
                    entity, String.class);
            logger.info("After Sending sms");
            JsonObject payload = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();
            return payload;
        } catch (Exception e) {
            logger.info("Send SMS failed. " + e.getMessage());
        }
        return null;

    }

//    private JsonObject sendEmail(String message, String receiverEmail) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
//            helper.setTo(receiverEmail);
//            helper.setSubject("Account Created");
//            helper.setText(message);
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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

    @Override
    public List<ObjectNode> getAllDSRAccountsV1(DSRAccountsRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            List<DSRAccountEntity> dsrAccountEntities = dsrAccountsRepository.findByProfileCode(model.getProfileCode());
            for (DSRAccountEntity dsrAccountEntity : dsrAccountEntities) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", dsrAccountEntity.getId());
                objectNode.put("name", dsrAccountEntity.getFullName());
                objectNode.put("email", dsrAccountEntity.getEmail());
                objectNode.put("phone", dsrAccountEntity.getEmail());
                objectNode.put("staffNo", dsrAccountEntity.getStaffNo());
                objectNode.put("isRM", dsrAccountEntity.getIsRm());
                objectNode.put("profileCode", dsrAccountEntity.getProfileCode());
                objectNode.put("status", dsrAccountEntity.getStatus().toString());
                objectNode.put("salesCode", dsrAccountEntity.getSalesCode());
                objectNode.put("teamId", dsrAccountEntity.getTeamId());
                DSRTeamEntity dsrTeamEntity = dsrTeamsRepository.findById(dsrAccountEntity.getTeamId()).get();
                objectNode.put("teamName", dsrTeamEntity.getName());
                objectNode.put("teamLoc", dsrTeamEntity.getLocation());
                objectNode.put("expiry", dsrAccountEntity.getExpiryDate() ==null ? null :dsrAccountEntity.getExpiryDate().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all opportunities", e);
        }
        return null;
    }

    @Override
    public boolean addSector(SectorRequest model) {
        try {
            if (model == null){
                return false;
            }
            SectorEntity sectorEntity = new SectorEntity();
            sectorEntity.setSectorDesc(model.getSectorDesc());
            sectorEntity.setSectorName(model.getSectorName());
            sectorEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            sectorRepository.save(sectorEntity);

        } catch (Exception e) {
            log.error("something went wrong,try again later");
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllSectors() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (SectorEntity sectorEntity : sectorRepository.findAll()) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("id", sectorEntity.getId());
                objectNode.put("sectorName", sectorEntity.getSectorName());
                objectNode.put("sectorDesc", sectorEntity.getSectorDesc());
                objectNode.put("createdOn", sectorEntity.getCreatedOn().getTime());
                list.add(objectNode);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }

    @Override
    public boolean editSector(SectorRequest model) {
        try {
            if (model == null){
                return false;
            }
            SectorEntity sectorEntity = sectorRepository.findById(model.getSectorId()).orElseThrow(()-> new ResourceNotFoundException("sector","id",model.getSectorId() ));
            sectorEntity.setSectorDesc(model.getSectorDesc());
            sectorEntity.setSectorName(model.getSectorName());
            sectorRepository.save(sectorEntity);
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Error occurred while editing sector", e);
        }
        return false;
    }

    @Override
    public boolean deleteSector(SectorRequest model) {
        try {
            if (model == null){
                return false;
            }
            SectorEntity sectorEntity = sectorRepository.findById(model.getSectorId()).orElseThrow(()-> new ResourceNotFoundException("sector","id",model.getSectorId() ));
            sectorRepository.delete(sectorEntity);
            return true;
        } catch (ResourceNotFoundException e) {
            log.error("Error occurred while deleting sector", e);
        }
        return false;
    }

}
