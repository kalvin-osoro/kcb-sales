package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.entity.*;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.payload.*;
import com.ekenya.rnd.backend.fskcb.repository.*;
import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Slf4j
@Service
public class VoomaService implements IVoomaService{

    @Autowired
    MerchantDetailsRepository merchantDetailsRepository;
    @Autowired
    MerchantAccountTypeRepository merchantAccountTypeRepository;
    @Autowired
    BusinessTypeRepository businessTypeRepository;
    @Autowired
    LiquidationRepository liquidationRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    ConstituencyRepository constituencyRepository;
    @Autowired
    WardRepository wardRepository;
    @Autowired
    UserAccTypeRepository userAccTypeRepository;
    @Autowired
    MerchantKYCRepository merchantKYCRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    ModelMapper modelMapper;
    private final Logger logger = Logger.getLogger(VoomaService.class.getName());
    @Override
    public ResponseEntity<?> addMerchant(MultipartFile frontID,
                                         MultipartFile backID,
                                         MultipartFile kraPinCertificate,
                                         MultipartFile certificateOFGoodConduct,
                                         MultipartFile businessLicense,
                                         MultipartFile fieldApplicationForm,
                                         String merchDetails,
                                         MultipartFile shopPhoto,
                                         MultipartFile customerPhoto,
                                         MultipartFile companyRegistrationDoc,
                                         MultipartFile signatureDoc, MultipartFile businessPermitDoc,
                                         HttpServletRequest httpServletRequest) {
        try {
            logger.info("Adding Merchant Started");
            ObjectMapper mapper = new ObjectMapper();
            OnboardMerchantRequest onboardMerchantRequest = mapper.readValue(merchDetails, OnboardMerchantRequest.class);
            if (onboardMerchantRequest==null)throw new RuntimeException("Invalid Request");
            JsonObject userObjectDetails = (JsonObject) httpServletRequest.getAttribute("userObjectDetails");
            Long userId = userObjectDetails.get("id").getAsLong();
            MerchantDetails merchantDetails = new MerchantDetails();
            logger.info("get Merchant Account Type");
            Optional<MerchantAccountType> optionalMerchAgentAccountType = merchantAccountTypeRepository.findById(
                    onboardMerchantRequest.getMerchantAccountTypeId());
            logger.info("getting Merchant County");
            Optional<County> county = countyRepository.findById(
                    onboardMerchantRequest.getCountyCode());
            logger.info("getting Merchant Buiness Details");
            Optional<BusinessType> businessType = businessTypeRepository.findById(
                    onboardMerchantRequest.getBusinessTpeId());
            logger.info("getting Merchant Liquidation Details");
            Optional<LiquidationType> liquidationType = liquidationRepository.findById(
                    onboardMerchantRequest.getLiquidationTypeId());
            logger.info("getting Merchant Branch Details");
            Optional<Branch> branch =branchRepository.findByBranchCode(onboardMerchantRequest.getBranchCode());

            logger.info("step 1");
            merchantDetails.setBusinessName(onboardMerchantRequest.getBusinessName());
            merchantDetails.setPhoneNo(onboardMerchantRequest.getMobileNumber());
            merchantDetails.setEmail(onboardMerchantRequest.getEmail());
            merchantDetails.setBusinessType(businessType.get());
            logger.info("step 2");
            merchantDetails.setLiquidationType(liquidationType.get());
            merchantDetails.setNatureBusiness(onboardMerchantRequest.getBusinessNature());
            logger.info("step 3");
            merchantDetails.setBankBranch(branch.get());
            merchantDetails.setLiquidationRate(onboardMerchantRequest.getLiquidationRate());
            merchantDetails.setAccountName(onboardMerchantRequest.getAccountName());
            merchantDetails.setAccountNumber(onboardMerchantRequest.getAccountNumber());
            merchantDetails.setCounty(county.get());
            merchantDetails.setTown(onboardMerchantRequest.getTownName());

            logger.info("step 4");
            merchantDetails.setMerchAgentAccountType(optionalMerchAgentAccountType.get());
            merchantDetails.setStreetName(onboardMerchantRequest.getStreetName());
            merchantDetails.setBuildingName(onboardMerchantRequest.getBuldingName());
            merchantDetails.setRoomNumber(onboardMerchantRequest.getRoomNo());
            merchantDetails.setLatitude(onboardMerchantRequest.getLatitude());
            merchantDetails.setLongitude(onboardMerchantRequest.getLongitude());
            merchantDetails.setCreatedBy(userId);
            merchantDetails.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

            //Please note this is a temporary account number
            String tmpAccountId = Utility.merchantTempAccountId();
            merchantDetails.setAccountId(tmpAccountId);
            merchantDetails.setMerchantIDNumber(onboardMerchantRequest.getMerchantIDNumber());
            merchantDetails.setMerchantSurname(onboardMerchantRequest.getMerchantSurname());
            merchantDetails.setMerchantFirstName(onboardMerchantRequest.getMerchantFirstName());
            merchantDetails.setMerchantLastName(onboardMerchantRequest.getMerchantLastName());
            merchantDetails.setMerchantDob(onboardMerchantRequest.getMerchantDob());
            merchantDetails.setMerchantGender(onboardMerchantRequest.getMerchantGender());

            //merchantAdminDetails
            MerchantDetails merchDtls= merchantDetailsRepository.save(merchantDetails);

            String frontIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "frontID_"+merchDtls.getId()+".PNG", frontID);

            String backIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "backID_"+merchDtls.getId()+".PNG", backID);

            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileName(
                    "kraPinCertificate_"+merchDtls.getId()+".PNG", kraPinCertificate);

            String certificateOFGoodConductPath = fileStorageService.saveFileWithSpecificFileName(
                    "certificateOFGoodConduct_"+merchDtls.getId()+".PNG", certificateOFGoodConduct);

            String businessLicensePath = fileStorageService.saveFileWithSpecificFileName(
                    "businessLicense_"+merchDtls.getId()+".PNG", businessLicense);

            String fieldApplicationFormPath = fileStorageService.saveFileWithSpecificFileName(
                    "fieldApplicationForm_"+merchDtls.getId()+".PNG", fieldApplicationForm);

            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "shopPhoto_"+merchDtls.getId()+".PNG", shopPhoto);

            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "customerPhoto_"+merchDtls.getId()+".PNG", customerPhoto);



            String companyRegistrationDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "companyRegistrationDoc_"+merchDtls.getId()+".PNG", companyRegistrationDoc);

            String signatureDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "signatureDocDoc_"+merchDtls.getId()+".PNG", signatureDoc);

            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "businessPermitDoc_"+merchDtls.getId()+".PNG", businessPermitDoc);

            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath); filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath); filePathList.add(certificateOFGoodConductPath);
            filePathList.add(fieldApplicationFormPath); filePathList.add(shopPhotoPath);
            filePathList.add(companyRegistrationDocPath); filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath); filePathList.add(businessLicensePath);
            filePathList.forEach(filePath -> {
                MerchantKYC merchantKYC = new MerchantKYC();
                merchantKYC.setFilePath(filePath);
                merchantKYC.setMerchantDetails(merchDtls);
                merchantKYCRepository.save(merchantKYC);
            });
            return ResponseEntity.ok().body(new OnboardingResponse(
                    optionalMerchAgentAccountType.get().getUserAccType().getUserAccTypeName()+
                            " onboarded successfully!", "success", Long.parseLong(tmpAccountId)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new MessageResponse("Registration failed", "failed"));
        }

    }


    @Override
    public ResponseEntity<?> findMerchantByAccountNumber(String accountId) {
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            Optional<MerchantDetails> optionalMerchantDetails=
                    merchantDetailsRepository.findMerchantDetailsByAccountId(accountId);
            MerchantDetails merchantDetails = optionalMerchantDetails.get();
            List<MerchantKYC> merchantKYCList =
                    merchantDetails.getMerchantKYCList();
            MerchartDetailResponse merchantDetailResponse =
                    new MerchartDetailResponse();
            merchantDetailResponse.setId(merchantDetails.getId());
            merchantDetailResponse.setMerchAgentAccountTypeName(
                    merchantDetails.getMerchAgentAccountType().getName());
            merchantDetailResponse.setBusinessName(
                    merchantDetails.getBusinessName());
            merchantDetailResponse.setBusinessName(
                    merchantDetails.getPhoneNo());
            merchantDetailResponse.setEmail(
                    merchantDetails.getEmail());
            merchantDetailResponse.setBusinessTypeName(
                    merchantDetails.getBusinessType().getBusinessTypeName());
            merchantDetailResponse.setNatureBusiness(
                    merchantDetails.getNatureBusiness());
            merchantDetailResponse.setBusinessName(
                    merchantDetails.getBusinessName());
            merchantDetailResponse.setLiquidationType(
                    merchantDetails.getLiquidationType().getLiquidationTypeName());
            merchantDetailResponse.setLiquidationRate(
                    merchantDetails.getLiquidationRate());
            merchantDetailResponse.setBranchName(
                    merchantDetails.getBankBranch().getBranchName()
            );
            merchantDetailResponse.setBranchCode(
                    merchantDetails.getBankBranch().getBranchCode());
            merchantDetailResponse.setBankCode(
                    merchantDetails.getBankBranch().getBank().getBankCode());
            merchantDetailResponse.setBankName(
                    merchantDetails.getBankBranch().getBank().getBankName());
            merchantDetailResponse.setAccountNumber(
                    merchantDetails.getAccountNumber());
            merchantDetailResponse.setAccountName(
                    merchantDetails.getAccountName());
            merchantDetailResponse.setCountyCode(
                    merchantDetails.getCounty().getCountyCode());
            merchantDetailResponse.setCountyName(
                    merchantDetails.getCounty().getCountyName());
            merchantDetailResponse.setTown(merchantDetails.getTown());
            merchantDetailResponse.setStreetName(merchantDetails.getStreetName());
            merchantDetailResponse.setBuildingName(merchantDetails.getBuildingName());
            merchantDetailResponse.setRoomNumber( merchantDetails.getRoomNumber());

            List<Map<String, Object>> fileNameList = new ArrayList<>();
            Map<String, Object> fileMap = new HashMap<>();
            for (int i=0; i<merchantKYCList.size(); i++){
                fileMap.put(new File(
                                merchantKYCList.get(i).getFilePath()).getName().split("_")[0],
                        new File( merchantKYCList.get(i).getFilePath()).getName());
            }
            fileNameList.add(fileMap);
            merchantDetailResponse.setMerchantKYCList(fileNameList);

            merchantDetailResponse.setLatitude( merchantDetails.getLatitude());
            merchantDetailResponse.setLongitude( merchantDetails.getLongitude());
            merchantDetailResponse.setLongitude( merchantDetails.getLongitude());
            merchantDetailResponse.setPhoneNo( merchantDetails.getPhoneNo());
			/*merchantDetailResponse.setCompanyRegistration(
					new File(merchantKYC.getCompanyRegistration()).getName());*/
            merchantDetailResponse.setAccountId( merchantDetails.getAccountId());

            merchantDetailResponse.setCreatedBy( merchantDetails.getCreatedBy());
            if(merchantDetails.getUpdatedBy()!=null)
                merchantDetailResponse.setUpdatedBy( merchantDetails.getUpdatedBy());

            responseParams.put("merchantdetails", merchantDetailResponse);
            responseObject.put("status", "success");
            responseObject.put("message", "Merchant Details for account number "+accountId);
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            logger.info("Error is "+e.getMessage());
            return ResponseEntity.ok().body(new MessageResponse("Account does not exist","failed"));
        }
    }

    @Override
    public ResponseEntity<?> findMerchantGeoMapDetails(String accountId) {
        //TODO Auto-generated method stub

        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {

            Optional<MerchantDetails> optionalMerchantDetails =
                    merchantDetailsRepository.findMerchantDetailsByAccountId(accountId);



            Optional<MerchAgentGeoMapResponse> merchAgentGeoMapResponseOptional =
                    optionalMerchantDetails.map(res -> new MerchAgentGeoMapResponse(
                            res.getLatitude() != null?res.getLatitude():null,
                            res.getLongitude() != null?res.getLongitude():null,
                            res.getBusinessName() != null?res.getBusinessName():null,
                            res.getPhoneNo() != null?res.getPhoneNo():null,
                            res.getCounty().getCountyName(),
                            res.getTown() != null?res.getTown():null,
                            res.getStreetName() != null?res.getStreetName():null,
                            res.getBuildingName() != null?res.getBuildingName():null,
                            res.getRoomNumber() != null?res.getRoomNumber():null
                    ));
            if(merchAgentGeoMapResponseOptional.isPresent()){
                responseObject.put("status", "success");
                responseObject.put("message" ,"Merchant or agent account number is correct");
                responseParams.put("merchantdetails",merchAgentGeoMapResponseOptional.get());
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "failed");
                responseObject.put("message" ,"Merchant  account number is incorrect");
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", "Account number does not exist");
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> getAllMerchantDetailByAgentId(long agentId) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<MerchantDetails> merchantDetailsList =
                    merchantDetailsRepository.findMerchantDetailsByCreatedBy(agentId)
                            .stream().filter(m -> m.getMerchAgentAccountType()
                                    .getUserAccType().getUserAccTypeName()
                                    .equalsIgnoreCase("Merchant")).collect(Collectors.toList());
            List<MerchartDetailResponse> merchantDetailResponseList = merchantDetailsList.stream().
                    map( res -> new MerchartDetailResponse(
                            res.getId(),
                            res.getMerchAgentAccountType().getName(),
                            res.getBusinessName() != null?res.getBusinessName():null,
                            res.getPhoneNo()  != null?res.getPhoneNo():null,
                            res.getEmail()  != null? res.getEmail():null,
                            res.getBusinessType().getBusinessTypeName(),
                            res.getNatureBusiness()  != null?res.getNatureBusiness():null,
                            res.getLiquidationType().getLiquidationTypeName(),
                            res.getLiquidationRate(),
                            res.getBankBranch().getBranchCode(),
                            res.getBankBranch().getBranchName(),
                            res.getBankBranch().getBank().getBankCode(),
                            res.getBankBranch().getBank().getBankName(),
                            res.getAccountName() != null?res.getAccountName():null,
                            res.getAccountNumber()  != null?res.getAccountNumber():null,
                            res.getCounty().getCountyCode(),
                            res.getCounty().getCountyName(),
                            res.getTown()  != null ? res.getTown():null,
                            res.getStreetName()  != null?res.getStreetName():null,
                            res.getBuildingName()  != null?res.getBuildingName():null,
                            res.getRoomNumber()  != null?res.getRoomNumber():null,
                            res.getMerchantKYCList().stream()
                                    .map(x ->new File( x.getFilePath()).getName())
                                    .map(x->new HashMap<String, Object>(){{
                                        put("label",x.split("_")[0]);
                                        put("name", x);
                                    }
                                    }).collect(Collectors.toList()),
                            res.getLatitude()  != null?res.getLatitude():null,
                            res.getLongitude()  != null?res.getLongitude():null,
                            res.getAccountId()  != null?res.getAccountId():null,
                            res.getCreatedBy()  != null?res.getCreatedBy():null,
                            res.getUpdatedBy()  != null?res.getUpdatedBy():null,
                            res.getMerchantIDNumber()  != null?res.getMerchantIDNumber():null,
                            res.getMerchantSurname()  != null?res.getMerchantSurname():null,
                            res.getMerchantFirstName()  != null?res.getMerchantFirstName():null,
                            res.getMerchantLastName()  != null?res.getMerchantLastName():null,
                            res.getMerchantGender()  != null?res.getMerchantGender():null,
                            res.getMerchantDob()  != null?res.getMerchantDob():null,
                            res.getMerchAgentAccountType().getUserAccType().getUserAccTypeName()
                    )).collect(Collectors.toList());
            if(merchantDetailResponseList.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No merchants available");
                responseParams.put("merchantDetailsList",merchantDetailResponseList);
                responseObject.put("data", responseParams);
            }else{
                responseObject.put("status", "success");
                responseObject.put("message", "Merchants available");
                responseParams.put("merchantDetailsList",merchantDetailResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> getAllAgentsByAgentId(long agentId) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<MerchantDetails> merchantDetailsList =
                    merchantDetailsRepository.findMerchantDetailsByCreatedBy(agentId)
                            .stream().filter(m -> m.getMerchAgentAccountType()
                                    .getUserAccType().getUserAccTypeName()
                                    .equalsIgnoreCase("Agent")).collect(Collectors.toList());
            List<MerchartDetailResponse> merchantDetailResponseList = merchantDetailsList.stream().
                    map( res -> new MerchartDetailResponse(
                            res.getId(),
                            res.getMerchAgentAccountType().getName(),
                            res.getBusinessName() != null?res.getBusinessName():null,
                            res.getPhoneNo()  != null?res.getPhoneNo():null,
                            res.getEmail()  != null? res.getEmail():null,
                            res.getBusinessType().getBusinessTypeName(),
                            res.getNatureBusiness()  != null?res.getNatureBusiness():null,
                            res.getLiquidationType().getLiquidationTypeName(),
                            res.getLiquidationRate(),
                            res.getBankBranch().getBranchCode(),
                            res.getBankBranch().getBranchName(),
                            res.getBankBranch().getBank().getBankCode(),
                            res.getBankBranch().getBank().getBankName(),
                            res.getAccountName() != null?res.getAccountName():null,
                            res.getAccountNumber()  != null?res.getAccountNumber():null,
                            res.getCounty().getCountyCode(),
                            res.getCounty().getCountyName(),
                            res.getTown()  != null ? res.getTown():null,
                            res.getStreetName()  != null?res.getStreetName():null,
                            res.getBuildingName()  != null?res.getBuildingName():null,
                            res.getRoomNumber()  != null?res.getRoomNumber():null,
                            res.getMerchantKYCList().stream()
                                    .map(x ->new File( x.getFilePath()).getName())
                                    .map(x->new HashMap<String, Object>(){{
                                        put(x.split("_")[0], x);
                                    }
                                    }).collect(Collectors.toList()),
                            res.getLatitude()  != null?res.getLatitude():null,
                            res.getLongitude()  != null?res.getLongitude():null,
                            res.getAccountId()  != null?res.getAccountId():null,
                            res.getCreatedBy()  != null?res.getCreatedBy():null,
                            res.getUpdatedBy()  != null?res.getUpdatedBy():null,
                            res.getMerchantIDNumber()  != null?res.getMerchantIDNumber():null,
                            res.getMerchantSurname()  != null?res.getMerchantSurname():null,
                            res.getMerchantFirstName()  != null?res.getMerchantFirstName():null,
                            res.getMerchantLastName()  != null?res.getMerchantLastName():null,
                            res.getMerchantGender()  != null?res.getMerchantGender():null,
                            res.getMerchantDob()  != null?res.getMerchantDob():null,
                            res.getMerchAgentAccountType().getUserAccType().getUserAccTypeName()
                    )).collect(Collectors.toList());
            if(merchantDetailResponseList.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No merchants available");
                responseParams.put("merchantDetailsList",merchantDetailResponseList);
                responseObject.put("data", responseParams);
            }else{
                responseObject.put("status", "success");
                responseObject.put("message", "Merchants available");
                responseParams.put("merchantDetailsList",merchantDetailResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> getAllMerchantAccountTypes() {
        // TODO
        return null;
    }


    @Override
    public ResponseEntity<?> createLiquidationType(LiquidationTypeDto liquidationTypeDto) {
        ConcurrentHashMap<String,Object> responseObject=new ConcurrentHashMap<>();
        ConcurrentHashMap<String,Object> responseParams=new ConcurrentHashMap<>();
        try {
            logger.info("Creating liquidation type");
            //convert Dto to entity
            LiquidationType liquidationType = mapToEntity(liquidationTypeDto);
            LiquidationType newLiquidationType = liquidationRepository.save(liquidationType);
//            //convert entity to Dto
//            return mapToDto(newLiquidationType);
            responseObject.put("status", "success");
            responseObject.put("message", "Liquidation type "
                    +liquidationTypeDto.getLiquidationTypeName()+" successfully created");
            responseObject.put("data", responseParams);
            //convert entity to Dto
            mapToDto(newLiquidationType);
            return ResponseEntity.ok(responseObject);


        } catch (
                Exception e) {
            log.error("Error creating liquidation type", e);
            responseObject.put("status", "error");
            responseObject.put("message", "Error creating liquidation type");
            responseObject.put("data", responseParams);
            return null;
        }


    }

    @Override
    public LiquidationResponse getAllLiquidationType(int pageNo, int pageSize, String sortBy, String sortDir) {
        logger.info("Getting all liquidation types");
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = org.springframework.data.domain.PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<LiquidationType> liquidationTypes = liquidationRepository.findAll(pageable);
        //get content for page object
        List<LiquidationType> listOfLiquidationType = liquidationTypes.getContent();
        List<LiquidationTypeDto> content = listOfLiquidationType.stream().map(liquidationType -> mapToDto(liquidationType)).collect(Collectors.toList());
        LiquidationResponse liquidationResponse = new LiquidationResponse();
        liquidationResponse.setContent(content);
        liquidationResponse.setPageNo(liquidationTypes.getNumber());
        liquidationResponse.setPageSize(liquidationTypes.getSize());
        liquidationResponse.setTotalElements(liquidationTypes.getNumberOfElements());
        liquidationResponse.setTotalPages(liquidationTypes.getTotalPages());
        liquidationResponse.setLast(liquidationTypes.isLast());
        return liquidationResponse;
    }

    @Override
    public ResponseEntity<?> getLiquidationTypeById(Long id) {
        log.info("Getting liquidation type by id = {}", id);
        ConcurrentHashMap<String, Object> responseObject = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Object> responseParams = new ConcurrentHashMap<>();
        try {
            LiquidationType liquidationType = liquidationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LiquidationType", "id", id));

            responseObject.put("status", "success");
            responseObject.put("message", "Liquidation type found" + liquidationType.getLiquidationTypeName());
            responseParams.put("liquidationType", liquidationType);
            responseObject.put("params", responseParams);
            modelMapper.map(liquidationType, LiquidationTypeDto.class);
            return ResponseEntity.ok(responseObject);


        } catch (
                ResourceNotFoundException e) {
            responseObject.put("status", "error");
            responseObject.put("message", e.getMessage());
            responseParams.put("liquidationType", null);
            responseObject.put("params", responseParams);
            modelMapper.map(responseObject, LiquidationTypeDto.class);
            return ResponseEntity.ok(responseObject);
        }


    }

    @Override
    public ResponseEntity<?> updateLiquidationType(LiquidationTypeDto liquidationTypeDto ) {
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            log.info("Updating liquidation type with id = {}", liquidationTypeDto.getId());
            Optional<LiquidationType>  optionalLiquidationType= Optional.ofNullable(liquidationRepository
                    .findById(liquidationTypeDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("LiquidationType", "id", liquidationTypeDto.getId())));//get the liquidation type to be updated

            LiquidationType liquidationType = optionalLiquidationType.get();//get the liquidation type to be updated
            //update the liquidation type
            liquidationType.setLiquidationTypeName(liquidationTypeDto.getLiquidationTypeName());
            liquidationType.setStatus(Status.ACTIVE);
            LiquidationType updatedLiquidationType = liquidationRepository.save(liquidationType);

            responseObject.put("status", "success");
            responseObject.put("message", "Liquidation type "
                    +liquidationTypeDto.getLiquidationTypeName()+" successfully updated");
            responseObject.put("data", responseParams);
            //convert entity to Dto
            mapToDto(updatedLiquidationType);
            return ResponseEntity.ok(responseObject);

        } catch (
                ResourceNotFoundException e) {
            responseObject.put("status", "error");
            responseObject.put("message", e.getMessage());
            responseParams.put("liquidationType", null);
            responseObject.put("params", responseParams);
            modelMapper.map(responseObject, LiquidationTypeDto.class);
            return ResponseEntity.ok(responseObject);
        }

    }
//update liquidation type with id = {}




    @Override
    public ResponseEntity<?> deleteLiquidationTypeById(Long id) {
        log.info("Deleting liquidation type by id = {}", id);
        ConcurrentHashMap<String, Object> responseObject = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Object> responseParams = new ConcurrentHashMap<>();
        try {
            LiquidationType liquidationType = liquidationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LiquidationType", "id", id));
            liquidationRepository.delete(liquidationType);
            responseObject.put("status", "success");
            responseObject.put("message", "Liquidation type "
                    +liquidationType.getLiquidationTypeName()+" successfully deleted");
            responseObject.put("data", responseParams);
            modelMapper.map(liquidationType, LiquidationTypeDto.class);
            return ResponseEntity.ok(responseObject);
        } catch (ResourceNotFoundException e) {
            responseObject.put("status", "error");
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            modelMapper.map(responseObject, LiquidationTypeDto.class);
            return ResponseEntity.ok(responseObject);
        }

    }
    //convert entity to dto
    private LiquidationTypeDto mapToDto(LiquidationType liquidationType) {

        return modelMapper.map(liquidationType, LiquidationTypeDto .class);

    }
    //convert dto to entity
    private LiquidationType mapToEntity(LiquidationTypeDto liquidationTypeDto) {

        return modelMapper.map(liquidationTypeDto, LiquidationType.class);

    }


////

    @Override
    public ResponseEntity<?> createBusinessType(BusinessTypeDto businessTypeDto) {
        log.info("Creating business type");
        BusinessType businessType = modelMapper.map(businessTypeDto, BusinessType.class);
        businessTypeRepository.save(businessType);
        modelMapper.map(businessType, BusinessTypeDto.class);
        return ResponseEntity.ok(businessType);

    }

    @Override
    public ResponseEntity<?> getAllBusinessTypes(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("Getting all business types");
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=org.springframework.data.domain.PageRequest.of(pageNo, pageSize, sort);
        //create a pageable instance
        Page<BusinessType> businessTypes=businessTypeRepository.findAll(pageable);
        //get content for page object
        List<BusinessType> businessTypeList=businessTypes.getContent();
        List<BusinessTypeDto> content=businessTypeList.stream().map(businessType -> modelMapper.map(businessType, BusinessTypeDto.class)).collect(Collectors.toList());
        BusinessTypeResponse businessTypeResponse=new BusinessTypeResponse();
        businessTypeResponse.setContent(content);
        businessTypeResponse.setPageNo(businessTypes.getNumber());
        businessTypeResponse.setPageSize(businessTypes.getSize());
        businessTypeResponse.setTotalElements((int) businessTypes.getTotalElements());
        businessTypeResponse.setTotalPages(businessTypes.getTotalPages());
        businessTypeResponse.setLast(businessTypes.isLast());
        return ResponseEntity.ok(businessTypeResponse);

    }

    @Override
    public BusinessTypeDto getBusinessTypesById(Long id) {
        log.info("Getting business type by id {}", id);
        BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Business Type not found"));
        return modelMapper.map(businessType, BusinessTypeDto.class);
    }

    @Override
    public ResponseEntity<?> updateBusinessTypes(BusinessTypeDto businessTypeDto, Long id) {
        log.info("Updating business type by id {}", id);
        HashMap<String, Object> responseObject = new HashMap<>();
        HashMap<String, Object> responseParams = new HashMap<>();
        try {
            log.info("Updating liquidation type with id = {}", id);
            BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("LiquidationType", "id", id));
            //convert Dto to entity
            BusinessType updatedBusinessType = mapToEntity(businessTypeDto);
            BusinessType newBusinessType = businessTypeRepository.save(updatedBusinessType);
            responseObject.put("status", "success");
            responseObject.put("message", "Business type "
                    +businessTypeDto.getBusinessTypeName()+" successfully updated");
            responseObject.put("data", responseParams);
            //convert entity to Dto
            mapToDto(newBusinessType);
            return ResponseEntity.ok(responseObject);
        } catch (ResourceNotFoundException e) {
            log.error("Error updating business type", e);
            responseObject.put("status", "error");
            responseObject.put("message", e.getMessage());
            responseParams.put("businessType", null);
            responseObject.put("params", responseParams);
            modelMapper.map(responseObject, BusinessTypeDto.class);
            return ResponseEntity.ok(responseObject);
        }
    }

    @Override
    public void deleteBusinessTypeById(Long id) {
        log.info("Deleting business type by id {}", id);
        BusinessType businessType = businessTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("Business Type not found"));
        businessTypeRepository.delete(businessType);

    }
    private BusinessTypeDto mapToDto(BusinessType businessType) {

        return modelMapper.map(businessType, BusinessTypeDto .class);

    }
    //convert dto to entity
    private BusinessType mapToEntity(BusinessTypeDto businessTypeDto) {

        return modelMapper.map(businessTypeDto, BusinessType.class);

    }
}
