package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.entity.CustomerDetails;
import com.deltacode.kcb.entity.CustomerKYC;
import com.deltacode.kcb.entity.EmploymentType;
import com.deltacode.kcb.entity.PersonalAccountType;
import com.deltacode.kcb.exception.MessageResponse;
import com.deltacode.kcb.payload.GetCustomerRequest;
import com.deltacode.kcb.payload.OnboardCustomerRequest;
import com.deltacode.kcb.payload.OnboardingResponse;
import com.deltacode.kcb.repository.*;
import com.deltacode.kcb.service.CustomerDetailsService;
import com.deltacode.kcb.service.FileStorageService;
import com.deltacode.kcb.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class CustomerDetailsImpl implements CustomerDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;
    private final CustomerKYCRepository customerKYCRepository;
    private final UserAccTypeRepository userAccTypeRepository;
    private  final PersonalAccountTypeRepository personalAccountTypeRepository;
    private final CountyRepository countyRepository;
    private final EmploymentTypeRepository employmentTypeRepository;
    private final FileStorageService fileStorageService;

    public CustomerDetailsImpl(CustomerDetailsRepository customerDetailsRepository,
                               CustomerKYCRepository customerKYCRepository,
                               UserAccTypeRepository userAccTypeRepository,
                               PersonalAccountTypeRepository personalAccountTypeRepository,
                               CountyRepository countyRepository, EmploymentTypeRepository employmentTypeRepository,
                               FileStorageService fileStorageService) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.customerKYCRepository = customerKYCRepository;
        this.userAccTypeRepository = userAccTypeRepository;
        this.personalAccountTypeRepository = personalAccountTypeRepository;
        this.countyRepository = countyRepository;
        this.employmentTypeRepository = employmentTypeRepository;
        this.fileStorageService = fileStorageService;
    }
    private Logger logger = Logger.getLogger(CustomerDetailsImpl.class.getName());

    @Override
    public List<CustomerDetails> getAllCustomerDetails() {

//TODO: Implement this method
        return null;
    }

    @Override
    public ResponseEntity<?> getCustomerDetails(GetCustomerRequest getCustomerRequest) {
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addCustomer(MultipartFile frontIdCapture, MultipartFile backIdCapture,
                                         MultipartFile passportPhotoCapture, String customerData,  HttpServletRequest httpServletRequest) {
        try {
            if(customerData==null) throw new RuntimeException("Bad request");
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = userDetails.getUsername();
            ObjectMapper mapper = new ObjectMapper();
            OnboardCustomerRequest onboardCustomerRequest =mapper.readValue(
                    customerData, OnboardCustomerRequest.class);
            Optional<PersonalAccountType > personalAccountType = personalAccountTypeRepository.findById(
                    onboardCustomerRequest.getPersonalAccountTypeId());

            Optional<EmploymentType> employmentType  = employmentTypeRepository.findById(
                    onboardCustomerRequest.getEmploymentType());
            log.info("Step 0");
            CustomerDetails customerDetails = new CustomerDetails();


            customerDetails.setSurname(customerDetails.getSurname());
            customerDetails.setFirstName(onboardCustomerRequest.getFirstName());
            customerDetails.setPhoneNo(onboardCustomerRequest.getPhoneNo());
            customerDetails.setDob(onboardCustomerRequest.getDob());
            customerDetails.setGender(onboardCustomerRequest.getGender());
            customerDetails.setLastName(onboardCustomerRequest.getLastName());

            customerDetails.setIncome(onboardCustomerRequest.getIncome());
            customerDetails.setAccountOpeningPurpose(customerDetails.getAccountOpeningPurpose());

            log.info("Step 1");
            customerDetails.setAccountType(personalAccountType.orElse(null));
            customerDetails.setCompanyYouWorkFor(onboardCustomerRequest.getCompanyYouWorkFor());
            log.info("Step 2");
            customerDetails.setEmploymentType(employmentType.orElse(null));
            customerDetails.setLatitude(onboardCustomerRequest.getLatitude());
            customerDetails.setLongitude(onboardCustomerRequest.getLongitude());
            customerDetails.setCreatedBy(userId);
            customerDetails.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

            JsonObject createCustomerJsonObject = new JsonObject();
            createCustomerJsonObject.addProperty("name",onboardCustomerRequest.getFirstName()
                    +" "+onboardCustomerRequest.getSurname());
            createCustomerJsonObject.addProperty("telephone1",onboardCustomerRequest.getPhoneNo());
            createCustomerJsonObject.addProperty("websiteurl","");

            JsonObject createLeadJsonObject = new JsonObject();
            createLeadJsonObject.addProperty("firstname",onboardCustomerRequest.getFirstName());
            createLeadJsonObject.addProperty("lastname",onboardCustomerRequest.getLastName());
            createLeadJsonObject.addProperty("companyname","Test Company");

            createLeadJsonObject.addProperty("emailaddress1",onboardCustomerRequest.getFirstName()+"@gmail.com");
            createLeadJsonObject.addProperty("telephone1",onboardCustomerRequest.getPhoneNo());

//            JsonObject createLeadResultJson = CcrmIntegrations.createLead(createLeadJsonObject);
//            JsonObject creaCustomerResultJson = CcrmIntegrations.createCustomer(createCustomerJsonObject);
//            System.out.println("createLeadResultJson "+createLeadResultJson);
//            System.out.println("creaCustomerResultJson "+creaCustomerResultJson);
//            long accountNo = creaCustomerResultJson.get("new_nmb_account_number").getAsLong();
//            String leadId = createLeadResultJson.get("leadid").getAsString();
//            customerDetails.setCrmAccountNo(accountNo);
//            customerDetails.setCrmLeadId(leadId);


            CustomerDetails customer = customerDetailsRepository.save(customerDetails);
            String frontIdCapturePath = fileStorageService.saveFileWithSpecificFileName(
                    "frontIdCapture_"+customer.getId()+".PNG", frontIdCapture);
            String backIdCapturePath = fileStorageService.saveFileWithSpecificFileName(
                    "backIdCapture_"+customer.getId()+".PNG", backIdCapture);
            String passportPhotoCapturePath = fileStorageService.saveFileWithSpecificFileName(
                    "passportPhotoCapture_"+customer.getId()+".PNG", backIdCapture);

            List<String> filePathList = new ArrayList<>();
            filePathList.add(frontIdCapturePath);
            filePathList.add(backIdCapturePath);
            filePathList.add(passportPhotoCapturePath);
            filePathList.forEach(filePath -> {
                CustomerKYC customerKYC = new CustomerKYC();
                customerKYC.setFilePath(filePath);
                customerKYC.setCustomerDetails(customer);
                customerKYCRepository.save(customerKYC);
            });




            return ResponseEntity.ok().body(new OnboardingResponse(
                    "Customer onboarded successfully!", "success", customer.getId()));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(), "failed"));
        }
    }

    @Override
    public ResponseEntity<?> getCustomerKYCDetails(GetCustomerRequest getCustomerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAccountDetails(GetCustomerRequest getCustomerRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllCustomerByDsrId(Long dsrId) {
        return null;
    }
}
