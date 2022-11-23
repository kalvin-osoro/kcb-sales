package com.ekenya.rnd.backend.fskcb;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRRegionsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.repositories.*;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class DbInitializer {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserProfilesRepository profilesRepository;
    @Autowired
    IDSRAccountsRepository dSRAccountsRepository;
    @Autowired
    IDSRTeamsRepository dSRTeamsRepository;
    @Autowired
    ProfilesAndRolesRepository profilesAndRolesRepository;

    @Autowired
    IDSRRegionsRepository dsrRegionsRepository;

    @Autowired
    IDSRTeamsRepository dsrTeamsRepository;
    @Autowired
    ProfilesAndUsersRepository profilesAndUsersRepository;

    private String DEFAULT_USER_PIN = "1234";
    private String DEFAULT_USER_PASS = "12345";
    //


    @EventListener(ApplicationReadyEvent.class)
    private void init() {
        log.info("DB initializer; Seeding db ..");

        //1. Seed system roles
        createSystemRoles();

        //2. Seed system profiles
        createDefaultProfiles();

        //3. Seed user admin account
        createAdminUser();

        //4. Seed demo DSRs
        createTestRegionsAndTeams();

        //4. Seed demo DSRs
        createTestDSRs();
    }


    private void createAdminUser(){
        //
        String adminName = "System Admin";
        String adminUserName = "Admin";
        String adminEmail = "admin@kcbfieldsales.co.ke";
        String adminPass = "Admin@4321";
        try{
            //check if user already exists by username or email
            if(userRepository.existsByEmail(adminEmail)){
                log.warn("Admin user already exists, skiping ..");
                return;
            }

            //create new user object
            UserAccountEntity userApp = new UserAccountEntity();
            userApp.setStaffNo(adminUserName);
            userApp.setEmail(adminEmail);
            userApp.setFullName(adminName);
            userApp.setPassword(passwordEncoder.encode(adminPass));
            userApp.setPhoneNumber("");
            userApp.setAccountType(AccountType.ADMIN);
            userRepository.save(userApp);//save user to db


            //
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.SYS_ADMIN).get();//get role from db
            userApp.setRoles(Collections.singleton(userRole));//set role to user
            userRepository.save(userApp);//save user to db

            //
            for (UserProfileEntity profile: profilesRepository.findAll()) {
                //Add user to profile

            }
            //
            log.info("Admin Account registered successfully");

        }catch (Exception exception){

            log.error("Register Admin Account failed."+exception.getMessage(), exception);
        }
    }

    private void createSystemRoles(){

        //SystemRoles.SYS_ADMIN
        if(!roleRepository.findByName(SystemRoles.SYS_ADMIN).isPresent()){
            //
            UserRoleEntity role = new UserRoleEntity();
            role.setName(SystemRoles.SYS_ADMIN);
            role.setCreatedOn(Calendar.getInstance().getTime());
            role.setType(RoleType.SYSTEM);
            //
            roleRepository.save(role);
        }else{
            //
            log.info(SystemRoles.SYS_ADMIN +" already exists,");
        }

        if(!roleRepository.findByName(SystemRoles.ADMIN).isPresent()){
            //
            UserRoleEntity role = new UserRoleEntity();
            role.setName(SystemRoles.ADMIN);
            role.setCreatedOn(Calendar.getInstance().getTime());
            role.setType(RoleType.SYSTEM);
            //
            roleRepository.save(role);
        }else{
            //
            log.info(SystemRoles.ADMIN +" already exists,");
        }
        //
        if(!roleRepository.findByName(SystemRoles.DSR).isPresent()){
            //
            UserRoleEntity role = new UserRoleEntity();
            role.setName(SystemRoles.DSR);
            role.setCreatedOn(Calendar.getInstance().getTime());
            role.setType(RoleType.SYSTEM);
            //
            roleRepository.save(role);
        }else{
            //
            log.info(SystemRoles.DSR +" already exists,");
        }

        //
        if(!roleRepository.findByName(SystemRoles.USER).isPresent()){
            //
            UserRoleEntity role = new UserRoleEntity();
            role.setName(SystemRoles.USER);
            role.setCreatedOn(Calendar.getInstance().getTime());
            role.setType(RoleType.SYSTEM);
            //
            roleRepository.save(role);
        }else{
            //
            log.info(SystemRoles.USER +" already exists,");
        }
    }

    private void createDefaultProfiles(){

        //Vooma
        String DSR_VOOMA_PROFILE_NAME = "DSR Vooma";

        if(!profilesRepository.findByName(DSR_VOOMA_PROFILE_NAME).isPresent()) {

            String DSR_VOOMA_PROFILE_CODE = "dsrVooma";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(DSR_VOOMA_PROFILE_NAME);
            userProfile.setCode(DSR_VOOMA_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //Agency
        String DSR_AGENCY_PROFILE_NAME = "DSR AGENCY";

        if(!profilesRepository.findByName(DSR_AGENCY_PROFILE_NAME).isPresent()) {

            String DSR_AGENCY_PROFILE_CODE = "dsrAgency";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(DSR_AGENCY_PROFILE_NAME);
            userProfile.setCode(DSR_AGENCY_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //Acquiring
        String DSR_ACQUIRING_PROFILE_NAME = "DSR ACQUIRING";

        if(!profilesRepository.findByName(DSR_ACQUIRING_PROFILE_NAME).isPresent()) {

            String DSR_ACQUIRING_PROFILE_CODE = "dsrAcquiring";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(DSR_ACQUIRING_PROFILE_NAME);
            userProfile.setCode(DSR_ACQUIRING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //Personal Banking
        String PERSONAL_BANKING_PROFILE_NAME = "PERSONAL BANKING";

        if(!profilesRepository.findByName(PERSONAL_BANKING_PROFILE_NAME).isPresent()) {

            String PERSONAL_BANKING_PROFILE_CODE = "personalBanking";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(PERSONAL_BANKING_PROFILE_NAME);
            userProfile.setCode(PERSONAL_BANKING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //retail Banking
        String RETAIL_BANKING_PROFILE_NAME = "RETAIL BANKING";

        if(!profilesRepository.findByName(RETAIL_BANKING_PROFILE_NAME).isPresent()) {

            String RETAIL_BANKING_PROFILE_CODE = "retailBanking";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(RETAIL_BANKING_PROFILE_NAME);
            userProfile.setCode(RETAIL_BANKING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //premium Banking
        String PREMIUM_BANKING_PROFILE_NAME = "PREMIUM BANKING";
        //
        if(!profilesRepository.findByName(PREMIUM_BANKING_PROFILE_NAME).isPresent()) {

            String PREMIUM_BANKING_PROFILE_CODE = "premiumBanking";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(PREMIUM_BANKING_PROFILE_NAME);
            userProfile.setCode(PREMIUM_BANKING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }


        //Cooporate Banking
        String COOPORATE_BANKING_PROFILE_NAME = "PREMIUM BANKING";
        //
        if(!profilesRepository.findByName(COOPORATE_BANKING_PROFILE_NAME).isPresent()) {

            String COOPORATE_BANKING_PROFILE_CODE = "cooporateBanking";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(COOPORATE_BANKING_PROFILE_NAME);
            userProfile.setCode(COOPORATE_BANKING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);

            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }

        //Treasury
        String TREASURY_BANKING_PROFILE_NAME = "TREASURY";
        //
        if(!profilesRepository.findByName(TREASURY_BANKING_PROFILE_NAME).isPresent()) {

            String TREASURY_BANKING_PROFILE_CODE = "treasuryBanking";
            //
            UserProfileEntity userProfile = new UserProfileEntity();
            userProfile.setName(TREASURY_BANKING_PROFILE_NAME);
            userProfile.setCode(TREASURY_BANKING_PROFILE_CODE);

            //
            profilesRepository.save(userProfile);


            //map to role
            UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db

            ProfileAndRoleEntity profileRole = new ProfileAndRoleEntity();
            profileRole.setProfileId(userProfile.getId());
            profileRole.setRoleId(userRole.getId());
            //
            profilesAndRolesRepository.save(profileRole);
        }
    }


    private void createTestDSRs(){

        //
        try{
            String nairobiTeam = "Nairobi Team";

            if(!dSRTeamsRepository.existsByName(nairobiTeam)){

                DSRTeamEntity dsrTeam = new DSRTeamEntity();
                dsrTeam.setName(nairobiTeam);
                dsrTeam.setLocation("Nairobi");
                dsrTeam.setCreatedBy("0");
                dsrTeam.setStatus(Status.ACTIVE);
                dsrTeam.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                dSRTeamsRepository.save(dsrTeam);
            }
            DSRTeamEntity team = dSRTeamsRepository.findByName(nairobiTeam).get();

            //
//            int index = 10;
//            DecimalFormat decFormat = new DecimalFormat("000");
//            long seed = System.currentTimeMillis();
//            Random random = new Random(seed);
//            int max = 99999999, min = 10000000;
//            for (UserProfile profile: profilesRepository.findAll()) {
//                //Add user to profile
//
//                String staffNo = "KCB"+decFormat.format(index);
//                String email = "email"+index+"@kcbfieldsales.co.ke";
//                if(!dSRAccountsRepository.findByStaffNo(staffNo).isPresent()){
//
//                    //Capture info
//                    DSRAccountEntity dsrDetails =  DSRAccountEntity.builder()
//                            .email(email)
//                            .staffNo(staffNo)
//                            .phoneNo("2547"+(random.nextInt((max + 1)-min)+min))
//                            .status(Status.ACTIVE)
//                            .fullName(profile.getName()+" "+decFormat.format(index))
//                            .location(team.getLocation())
//                            .gender("other")
//                            .idNumber(""+(random.nextInt((max + 1)-min)+min))
//                            .teamId(team.getId())
//                            .createdBy("0")
//                            .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
//                            .build();
//
//                    //Create login profile
//                    UserAccount userApp = new UserAccount();
//                    userApp.setStaffNo(dsrDetails.getStaffNo());
//                    userApp.setEmail(dsrDetails.getEmail());
//                    userApp.setFullName(dsrDetails.getFullName());
//                    userApp.setPassword(passwordEncoder.encode(DEFAULT_USER_PIN));
//                    userApp.setPhoneNumber(dsrDetails.getPhoneNo());
//                    userApp.setAccountType(AccountType.DSR);
//                    userRepository.save(userApp);//save user to db
//                    //Add to role
//                    UserRole userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db
//                    //
//                    userApp.setRoles(Collections.singleton(userRole));//set role to user
//                    userRepository.save(userApp);//save user to db
//
//                    //Save DSR
//                    dSRAccountsRepository.save(dsrDetails);
//
//                    //Add to profile this profile
//                    ProfileUserEntity profileUser = new ProfileUserEntity();
//                    profileUser.setUserId(userApp.getId());
//                    profileUser.setProfileId(profileUser.getId());
//                    profilesAndUsersRepository.save(profileUser);
//
//                    //
//                    index++;
//                }
//            }

            String staffNo = "KCB100";
            String email = "dsr34@kcbfieldsales.co.ke";
            if(!dSRAccountsRepository.findByStaffNo(staffNo).isPresent()) {

                //Capture info
                DSRAccountEntity dsrDetails = DSRAccountEntity.builder()
                        .email(email)
                        .staffNo(staffNo)
                        .phoneNo("254734406645")
                        .status(Status.ACTIVE)
                        .fullName("TEST DSR")
                        .location(team.getLocation())
                        .gender("other")
                        .idNumber("")
                        .teamId(team.getId())
                        .createdBy("0")
                        .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
                        .build();

                //Create login profile
                UserAccountEntity userApp = new UserAccountEntity();
                userApp.setStaffNo(dsrDetails.getStaffNo());
                userApp.setEmail(dsrDetails.getEmail());
                userApp.setFullName(dsrDetails.getFullName());
                userApp.setPassword(passwordEncoder.encode(DEFAULT_USER_PIN));
                userApp.setPhoneNumber(dsrDetails.getPhoneNo());
                userApp.setAccountType(AccountType.DSR);
                userRepository.save(userApp);//save user to db
                //Add to role
                UserRoleEntity userRole = roleRepository.findByName(SystemRoles.DSR).get();//get role from db
                //
                userApp.setRoles(Collections.singleton(userRole));//set role to user
                userRepository.save(userApp);//save user to db

                //Save DSR
                dSRAccountsRepository.save(dsrDetails);

                //Add to profile this profile
                ProfileAndUserEntity profileUser = new ProfileAndUserEntity();
                profileUser.setUserId(userApp.getId());
                profileUser.setProfileId(profileUser.getId());
                profilesAndUsersRepository.save(profileUser);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    protected void createTestRegionsAndTeams(){

        try{
            //
            String regionName = "Nairobi";
            String regionCode = "R001";

            Optional<DSRRegionEntity> optionalDSRRegion = dsrRegionsRepository.findByName(regionName);
            if(!optionalDSRRegion.isPresent()) {
                DSRRegionEntity region = new DSRRegionEntity();
                region.setCode(regionCode);
                region.setName(regionName);
                dsrRegionsRepository.save(region);
                //
                optionalDSRRegion = dsrRegionsRepository.findByName(regionName);
            }
            String teamName = "DFS KASARANI";
            String teamCode = "6756";

            if(!dsrTeamsRepository.findByName(teamName).isPresent()){
                DSRTeamEntity team = new DSRTeamEntity();
                team.setName(teamName);
                team.setRegionId(optionalDSRRegion.get().getId());
                dsrTeamsRepository.save(team);
            }
        }catch (Exception ex){
            //
        }
    }
}
