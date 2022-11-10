package com.deltacode.kcb.DSRModule.service;

import com.deltacode.kcb.DSRModule.models.DSRDetails;
import com.deltacode.kcb.DSRModule.models.DSRTeam;
import com.deltacode.kcb.DSRModule.payload.request.DSRRequest;
import com.deltacode.kcb.DSRModule.payload.request.DSRTeamRequest;
import com.deltacode.kcb.DSRModule.payload.response.DSRDetailsResponse;
import com.deltacode.kcb.DSRModule.payload.response.DSRTeamResponse;
import com.deltacode.kcb.DSRModule.repository.DSRDetailsRepository;
import com.deltacode.kcb.DSRModule.repository.DSRTeamRepository;
import com.deltacode.kcb.DSRModule.repository.ZoneCoordinatesRepository;
import com.deltacode.kcb.UserManagement.service.ExcelService;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.repository.CustomerDetailsRepository;
import com.deltacode.kcb.repository.MerchantDetailsRepository;
import com.deltacode.kcb.repository.ZoneRepository;
import com.deltacode.kcb.utils.Status;
import com.deltacode.kcb.utils.Utility;
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
import java.util.stream.Collectors;
@Slf4j

@Service
public class DSRServiceImpl implements DSRService {

    @Autowired
    private DSRTeamRepository dsrTeamRepository;

    @Autowired
    private DSRDetailsRepository dsrDetailsRepository;

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @Autowired
    private MerchantDetailsRepository merchantDetailsRepository;



    @Autowired
    private ZoneCoordinatesRepository zoneCoordinatesRepository;

    @Autowired
    private ZoneRepository zoneRepository;
    @Autowired
    private ExcelService excelService;



    private final static java.util.logging.Logger logger = Logger.getLogger(DSRServiceImpl.class.getName());

    @Override
    public ResponseEntity<?> addDSRTeam(DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if(dsrTeamRequest == null) throw new RuntimeException("Bad request");
            if (dsrTeamRepository.existsByTeamName(dsrTeamRequest.getTeamName())) {
                return ResponseEntity
                        .ok()
                        .body(new MessageResponse("Error: DSR team is already taken!", "failed"));
            }
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null)throw new RuntimeException("Please login to add Dsr");
            String userId= userDetails.getUsername();
            logger.info("adding team step 2");


            DSRTeam dsrTeam = new DSRTeam();
            dsrTeam.setTeamName(dsrTeamRequest.getTeamName());
            dsrTeam.setTeamLocation(dsrTeamRequest.getTeamLocation());
            dsrTeam.setCreatedBy(userId);
            dsrTeam.setStatus(Status.ACTIVE);
            dsrTeam.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeamRepository.save(dsrTeam);
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
            Optional<DSRTeam> optionalDSRTeam =
                    dsrTeamRepository.findById(dsrTeamRequest.getId());
            DSRTeam dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(dsrTeamRequest.getStatus());
            dsrTeam.setTeamName(dsrTeamRequest.getTeamName());
            dsrTeam.setTeamLocation(dsrTeamRequest.getTeamLocation());
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamRepository.save(dsrTeam);
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
            List<DSRTeam> dsrTeamList = dsrTeamRepository.findDSRTeamByStatus(Status.ACTIVE);
            List<DSRTeamResponse> dsrTeamResponseList = new ArrayList<>();
            DSRTeamResponse dsrDsrTeamResponse;
            for (int i= 0; i<dsrTeamList.size(); i++){
                int teamMembersCount = dsrDetailsRepository.findAllByDsrTeam(dsrTeamList.get(i)).size();
                dsrDsrTeamResponse = new DSRTeamResponse(
                        dsrTeamList.get(i).getId(),
                        dsrTeamList.get(i).getTeamName(),
                        dsrTeamList.get(i).getTeamLocation(),
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
            Optional<DSRTeam> optionalDSRTeam = dsrTeamRepository.findById(id);
            if(!optionalDSRTeam.isPresent()) throw new RuntimeException("Team is not present");
            List<DSRDetails> dsrDetailsList =
                    dsrDetailsRepository.findAllByDsrTeam(optionalDSRTeam.get());
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
            Optional<DSRTeam> optionalDSRTeam = dsrTeamRepository.findById(id);
            if(!optionalDSRTeam.isPresent()) throw new RuntimeException("Team is not present");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String userId = userDetails.getUsername();
            DSRTeam dsrTeam = optionalDSRTeam.get();
            dsrTeam.setStatus(Status.INACTIVE);
            dsrTeam.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dsrTeam.setUpdatedBy(userId);
            dsrTeamRepository.save(dsrTeam);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR team "
                    +dsrTeam.getTeamName()+" successfully deleted");
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

            DSRTeam optionalDSRTeam =
                    dsrTeamRepository.findById(dsrRequest.getTeamId()).orElse(null);
            DSRDetails dsrDetails =  DSRDetails.builder()
                    .email(dsrRequest.getEmail())
                    .username(dsrRequest.getUsername())
                    .mobileNo(dsrRequest.getMobileNo())
                    .status(Status.ACTIVE)
                    .firstName(dsrRequest.getFirstName())
                    .lastName(dsrRequest.getLastName())
                    .otherName(dsrRequest.getOtherName())
                    .location(dsrRequest.getLocation())
                    .gender(dsrRequest.getGender().trim())
                    .idNumber(dsrRequest.getIdNumber())
                    .systemUserId(systemUserId)
                    .dsrTeam(optionalDSRTeam)
                    .createdBy(createdBy)
                    .createdOn(Utility.getPostgresCurrentTimeStampForInsert())
                    .build();
            dsrDetailsRepository.save(dsrDetails);
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
        try{
            int customerCont = 0;
            int merchantCount = 0;
            int agentCount =0;
            List<DSRDetails> dsrDetailsList =
                    dsrDetailsRepository.findDSRDetailsByStatus(Status.ACTIVE);
            List<DSRDetailsResponse> dsrDetailsResponseList = new ArrayList<>();
            DSRDetailsResponse dsrDetailsResponse = new DSRDetailsResponse();;
            for(int i =0; i<dsrDetailsList.size(); i++){
                customerCont = customerDetailsRepository.
                        findCustomerDetailsByCreatedBy(dsrDetailsList.get(i).getSystemUserId()).size();
                merchantCount = merchantDetailsRepository.
                        findMerchantDetailsByCreatedBy(dsrDetailsList.get(i).getSystemUserId())
                        .stream().filter(m -> m.getMerchAgentAccountType()
                                .getUserAccType().getUserAccTypeName()
                                .equalsIgnoreCase("Merchant")).collect(Collectors.toList()).size();
                agentCount = merchantDetailsRepository.
                        findMerchantDetailsByCreatedBy(dsrDetailsList.get(i).getSystemUserId())
                        .stream().filter(m -> m.getMerchAgentAccountType()
                                .getUserAccType().getUserAccTypeName()
                                .equalsIgnoreCase("Agent")).collect(Collectors.toList()).size();
                dsrDetailsResponse = new DSRDetailsResponse(
                        dsrDetailsList.get(i).getId(),
                        dsrDetailsList.get(i).getEmail()!=null?dsrDetailsList.get(i).getEmail():null,
                        dsrDetailsList.get(i).getMobileNo()!=null?dsrDetailsList.get(i).getMobileNo():null,
                        dsrDetailsList.get(i).getFirstName()!=null?dsrDetailsList.get(i).getFirstName():null,
                        dsrDetailsList.get(i).getLastName()!=null?dsrDetailsList.get(i).getLastName():null,
                        dsrDetailsList.get(i).getOtherName()!=null?dsrDetailsList.get(i).getOtherName():null,
                        dsrDetailsList.get(i).getLocation()!=null?dsrDetailsList.get(i).getLocation():null,
                        dsrDetailsList.get(i).getGender()!=null?dsrDetailsList.get(i).getGender():null,
                        dsrDetailsList.get(i).getIdNumber()!=null?dsrDetailsList.get(i).getIdNumber():null,
                        dsrDetailsList.get(i).getSystemUserId(),
                        dsrDetailsList.get(i).getDsrTeam(),
                        dsrDetailsList.get(i).getCreatedBy(),
                        dsrDetailsList.get(i).getCreatedOn(),
                        dsrDetailsList.get(i).getUpdatedOn()!=null?dsrDetailsList.get(i).getUpdatedOn():null,
                        dsrDetailsList.get(i).getUpdatedBy()!=null?dsrDetailsList.get(i).getUpdatedBy():null,
                        customerCont, merchantCount, agentCount,
                        dsrDetailsList.get(i).getUsername()!=null?dsrDetailsList.get(i).getUsername():null,
                        dsrDetailsList.get(i).getStaffNo()!=null?dsrDetailsList.get(i).getStaffNo():null
                );
                dsrDetailsResponseList.add(dsrDetailsResponse);
            }
            if (dsrDetailsResponseList.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No DSRs available");
                responseParams.put("dsrDetailsList",dsrDetailsResponseList);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "DSRs available");
                responseParams.put("dsrDetailsList",dsrDetailsResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> deleteDSRById(long id, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try{
            Optional<DSRDetails> optionalDSRDetails = dsrDetailsRepository.findById(id);
            DSRDetails dsrDetails = optionalDSRDetails.get();
            excelService.deleteSystemUser(dsrDetails.getSystemUserId(),httpServletRequest);

            dsrDetails.setStatus(Status.DELETED);
            dsrDetailsRepository.save(dsrDetails);
            responseObject.put("status", "success");
            responseObject.put("message", "DSR "
                    +dsrDetails.getFirstName()+" successfully deleted");
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
//            Optional<DSRDetails> optionalDSRDetails =
//                    dsrDetailsRepository.findById(dsrRequest.getDsrId());
//            DSRDetails dsrDetails = optionalDSRDetails.get();
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
            String username = userDetailsObject.getUsername();
            Optional<DSRDetails> optionalDSRDetails =
                    dsrDetailsRepository.findDSRDetailsByUsername(username);
            if (!optionalDSRDetails.isPresent())
                throw new RuntimeException("User does not exist");

            responseObject.put("status", "success");
            responseObject.put("message", "User profile");
            responseParams.put("dsrProfile",optionalDSRDetails.get());
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
