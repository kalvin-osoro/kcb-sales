package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRTeamEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.DSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRTeamRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.response.DSRTeamResponse;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.DSRTeamsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.ZoneCoordinatesRepository;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.ExcelService;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j

@Service
public class DSRPortalService implements IDSRPortalService {

    @Autowired
    private DSRTeamsRepository dsrTeamsRepository;

    @Autowired
    private DSRAccountsRepository dsrAccountsRepository;



    @Autowired
    private ZoneCoordinatesRepository zoneCoordinatesRepository;
    @Autowired
    private ExcelService excelService;



    private final static java.util.logging.Logger logger = Logger.getLogger(DSRPortalService.class.getName());

    @Override
    public ResponseEntity<?> addDSRTeam(DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if(dsrTeamRequest == null) throw new RuntimeException("Bad request");
            if (dsrTeamsRepository.existsByName(dsrTeamRequest.getTeamName())) {
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse("Error: DSR team is already taken!", "failed"));
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null)throw new RuntimeException("Please login to add Dsr");
            String userId= userDetails.getUsername();
            logger.info("adding team step 2");


            DSRTeamEntity dsrTeam = new DSRTeamEntity();
            dsrTeam.setName(dsrTeamRequest.getTeamName());
            dsrTeam.setLocation(dsrTeamRequest.getTeamLocation());
            dsrTeam.setCreatedBy(userId);
            dsrTeam.setStatus(Status.ACTIVE);
            dsrTeam.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeamsRepository.save(dsrTeam);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR team "
                    +dsrTeamRequest.getTeamName()+" successfully created");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> editDSRTeam(DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();

        try {
            if (dsrTeamRequest == null)throw new RuntimeException("Bad request");
            if(!Utility.validateStatus(dsrTeamRequest.getStatus()))
                throw new RuntimeException("Status is invalid");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String userId = userDetails.getUsername();
            Optional<DSRTeamEntity> optionalDSRTeam =
                    dsrTeamsRepository.findById(dsrTeamRequest.getId());
            DSRTeamEntity dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(dsrTeamRequest.getStatus());
            dsrTeam.setName(dsrTeamRequest.getTeamName());
            dsrTeam.setLocation(dsrTeamRequest.getTeamLocation());
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamsRepository.save(dsrTeam);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR team "
                    +dsrTeamRequest.getTeamName()+" successfully updated");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> getAllDSRTeams() {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<DSRTeamEntity> dsrTeamList = dsrTeamsRepository.findByStatus(Status.ACTIVE);
            List<DSRTeamResponse> dsrTeamResponseList = new ArrayList<>();
            DSRTeamResponse dsrDsrTeamResponse;
            for (int i= 0; i<dsrTeamList.size(); i++){
                int teamMembersCount = dsrAccountsRepository.findAllByTeamId(dsrTeamList.get(i).getId()).size();
                dsrDsrTeamResponse = new DSRTeamResponse(
                        dsrTeamList.get(i).getId(),
                        dsrTeamList.get(i).getName(),
                        dsrTeamList.get(i).getLocation(),
                        dsrTeamList.get(i).getCreatedBy()  != null? dsrTeamList.get(i).getCreatedBy():null,
                        dsrTeamList.get(i).getStatus().equals(Status.ACTIVE)?"Active":"Inactive",

                        dsrTeamList.get(i).getCreatedOn()  != null? dsrTeamList.get(i).getCreatedOn():null,
                        dsrTeamList.get(i).getUpdatedOn() != null? dsrTeamList.get(i).getUpdatedOn():null,
                        dsrTeamList.get(i).getUpdatedBy()  != null? dsrTeamList.get(i).getUpdatedBy():null,
                        teamMembersCount
                );
                dsrTeamResponseList.add(dsrDsrTeamResponse);
            }


            if (dsrTeamResponseList.isEmpty()){
                responseObject.put("status", "success");
                responseObject.put("message", "No DSR team available");
                responseParams.put("dsrTeamList",dsrTeamResponseList);
                responseObject.put("data", responseParams);
            }else{
                responseObject.put("status", "success");
                responseObject.put("message", "DSR team available");
                responseParams.put("dsrTeamList",dsrTeamResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> getTeamMembersByTeamId(long id) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try{
            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(id);
            if(!optionalDSRTeam.isPresent()) throw new RuntimeException("Team is not present");
            List<DSRAccountEntity> dsrDetailsList =
                    dsrAccountsRepository.findAllByTeamId(optionalDSRTeam.get().getId());
            if (dsrDetailsList.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No members registered to this team");
                responseParams.put("dsrDetailsList",dsrDetailsList);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "Members registered to this team");
                responseParams.put("dsrDetailsList",dsrDetailsList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(), "failed"));
        }
    }

    @Override
    public ResponseEntity<?> deleteDSRTeam(long id, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            Optional<DSRTeamEntity> optionalDSRTeam = dsrTeamsRepository.findById(id);
            if(!optionalDSRTeam.isPresent()) throw new RuntimeException("Team is not present");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String userId = userDetails.getUsername();
            DSRTeamEntity dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(Status.INACTIVE);
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamsRepository.save(dsrTeam);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR team "
                    +dsrTeam.getName()+" successfully deleted");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }

    }

    @Override
    public ResponseEntity<?> addDSR(DSRRequest dsrRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if (dsrRequest == null) throw new Exception("Bad request");
            if(!Utility.validateGender(dsrRequest.getGender()))
                throw new RuntimeException("Gender is invalid");

            JsonObject registerUserObj =//register user
                    new JsonObject();
            if(registerUserObj == null )
                throw  new RuntimeException("User registration failed");

            if(!registerUserObj.get("status").getAsString().equals("success")) {
                String message = registerUserObj.get("message").getAsString();
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse(message, "failed"));
            }
            long systemUserId = registerUserObj.get("systemuserid").getAsLong();
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (userDetails == null)throw new RuntimeException("Service error");
            String createdBy = userDetails.getUsername();

            DSRTeamEntity optionalDSRTeam =
                    dsrTeamsRepository.findById(dsrRequest.getTeamId()).orElse(null);
            DSRAccountEntity dsrDetails =  DSRAccountEntity.builder()
                    .email(dsrRequest.getEmail())
                    .phoneNo(dsrRequest.getMobileNo())
                    .status(Status.ACTIVE)
                    .fullName(dsrRequest.getFirstName())
                    .location(dsrRequest.getLocation())
                    .gender(dsrRequest.getGender().trim())
                    .idNumber(dsrRequest.getIdNumber())
                    .teamId(optionalDSRTeam.getId())
                    .createdBy(createdBy)
                    .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
                    .build();
            dsrAccountsRepository.save(dsrDetails);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR successfully added");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }

    }

    @Override
    public ResponseEntity<?> getAllDSRs(HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();


        return ResponseEntity.ok("ok");
    }

    @Override
    public ResponseEntity<?> deleteDSRById(long id, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try{
            Optional<DSRAccountEntity> optionalDSRAccountEntity = dsrAccountsRepository.findById(id);
            DSRAccountEntity dsrDetails = optionalDSRAccountEntity.get();
            //
            excelService.deleteSystemUser(dsrDetails.getId(),httpServletRequest);

            dsrDetails.setStatus(Status.DELETED);
            dsrAccountsRepository.save(dsrDetails);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR "
                    +dsrDetails.getFullName()+" successfully deleted");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }

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
    public ResponseEntity<?> getDSRProfile(HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            UserDetails userDetailsObject =  (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            //logger.info("userDetailsObject "+userDetailsObject);
            if (userDetailsObject == null)throw new RuntimeException("Service error");
            String staffNo = userDetailsObject.getUsername();
            Optional<DSRAccountEntity> optionalDSRAccountEntity =
                    dsrAccountsRepository.findByStaffNo(staffNo);
            if (!optionalDSRAccountEntity.isPresent())
                throw new RuntimeException("User does not exist");

            responseObject.put("status", "success");
            responseObject.put("message", "User profile");
            responseParams.put("dsrProfile",optionalDSRAccountEntity.get());
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);

        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
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
