package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardingKYCentity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringOnboardRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.PSBankingOnboardingFileEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingOnboardingFileRepository;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.PSBankingOnboardingRepossitory;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBDSROnboardingRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PSBankingOnboardingRequest;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PBChannelService implements IPBChannelService {
    private final PSBankingOnboardingFileRepository psBankingOnboardingFileRepository;
    private final PSBankingOnboardingRepossitory psBankingOnboardingRepossitory;
    private final FileStorageService fileStorageService;


    @Override
    public Object onboardNewCustomer(String customerDetails,
                                     MultipartFile signature,
                                     MultipartFile customerPhoto,
                                     MultipartFile kraPin,
                                     MultipartFile crbReport,
                                     MultipartFile frontID,
                                     MultipartFile backID) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PSBankingOnboardingRequest psBankingOnboardingRequest = mapper.readValue(
                    customerDetails, PSBankingOnboardingRequest.class);
            if (psBankingOnboardingRequest == null) throw new RuntimeException("Bad request");
            PSBankingOnboardingEntity psBankingOnboardingEntity = new PSBankingOnboardingEntity();
            psBankingOnboardingEntity.setCustomerTitle(psBankingOnboardingRequest.getCustomerTitle());
            psBankingOnboardingEntity.setSurname(psBankingOnboardingRequest.getSurname());
            psBankingOnboardingEntity.setOtherNames(psBankingOnboardingRequest.getOtherNames());
            psBankingOnboardingEntity.setCustomerIdNumber(psBankingOnboardingRequest.getCustomerIdNumber());
            psBankingOnboardingEntity.setCustomerDob(psBankingOnboardingRequest.getCustomerDob());
            psBankingOnboardingEntity.setGender(psBankingOnboardingRequest.getGender());
            psBankingOnboardingEntity.setNationality(psBankingOnboardingRequest.getNationality());
            psBankingOnboardingEntity.setMaritalStatus(psBankingOnboardingRequest.getMaritalStatus());
            psBankingOnboardingEntity.setCountryOfResidence(psBankingOnboardingRequest.getCountryOfResidence());
            psBankingOnboardingEntity.setIsCustomerHasDualCitizenship(psBankingOnboardingRequest.getIsCustomerHasDualCitizenship());
            psBankingOnboardingEntity.setOtherNationality(psBankingOnboardingRequest.getOtherNationality());
            psBankingOnboardingEntity.setPassportNumber(psBankingOnboardingRequest.getPassportNumber());
            //contact details
            psBankingOnboardingEntity.setResidentialAddress(psBankingOnboardingRequest.getResidentialAddress());
            psBankingOnboardingEntity.setPostalAddress(psBankingOnboardingRequest.getPostalAddress());
            psBankingOnboardingEntity.setPostalCode(psBankingOnboardingRequest.getPostalCode());
            psBankingOnboardingEntity.setCellPhoneNumber(psBankingOnboardingRequest.getCellPhoneNumber());
            //employment details
            psBankingOnboardingEntity.setNameOfEmployer(psBankingOnboardingRequest.getNameOfEmployer());
            psBankingOnboardingEntity.setTermOfEmployment(psBankingOnboardingRequest.getTermOfEmployment());
            psBankingOnboardingEntity.setExpiryOfContract(psBankingOnboardingRequest.getExpiryOfContract());
            psBankingOnboardingEntity.setEstimatedMonthlyIncome(psBankingOnboardingRequest.getEstimatedMonthlyIncome());
            psBankingOnboardingEntity.setIsCustomerReceiveFundInForeignCurrency(psBankingOnboardingRequest.getIsCustomerReceiveFundInForeignCurrency());
            psBankingOnboardingEntity.setPreferredCurrency(psBankingOnboardingRequest.getPreferredCurrency());
            psBankingOnboardingEntity.setSourceOfFunds(psBankingOnboardingRequest.getSourceOfFunds());
            psBankingOnboardingEntity.setCountry(psBankingOnboardingRequest.getCountry());
            //additional details
            psBankingOnboardingEntity.setIsCustomerStudent(psBankingOnboardingRequest.getIsCustomerStudent());
            psBankingOnboardingEntity.setNameofUniversityOrCollege(psBankingOnboardingRequest.getNameofUniversityOrCollege());
            psBankingOnboardingEntity.setGraduationDate(psBankingOnboardingRequest.getGraduationDate());
            psBankingOnboardingEntity.setIsCustomerMinor(psBankingOnboardingRequest.getIsCustomerMinor());
            psBankingOnboardingEntity.setSurnameOfParentOrGuardian(psBankingOnboardingRequest.getSurnameOfParentOrGuardian());
            psBankingOnboardingEntity.setOtherNamesOfParentOrGuardian(psBankingOnboardingRequest.getOtherNamesOfParentOrGuardian());
            psBankingOnboardingEntity.setGenderOfParentOrGuardian(psBankingOnboardingRequest.getGenderOfParentOrGuardian());
            psBankingOnboardingEntity.setRelationshipToCustomer(psBankingOnboardingRequest.getRelationshipToCustomer());
            //mandate details
            psBankingOnboardingEntity.setIsCustomerWantDebitCard(psBankingOnboardingRequest.getIsCustomerWantDebitCard());
            psBankingOnboardingEntity.setIsCustomerWantChequeBook(psBankingOnboardingRequest.getIsCustomerWantChequeBook());
            psBankingOnboardingEntity.setIsCustomerWantToReceiveStatements(psBankingOnboardingRequest.getIsCustomerWantToReceiveStatements());
            psBankingOnboardingEntity.setCycleOfStatements(psBankingOnboardingRequest.getCycleOfStatements());
            psBankingOnboardingEntity.setSendVia(psBankingOnboardingRequest.getSendVia());
            psBankingOnboardingEntity.setIsCustomerWantToUseMobileBanking(psBankingOnboardingRequest.getIsCustomerWantToUseMobileBanking());
            psBankingOnboardingEntity.setIsCustomerMakeForeignExchangeWithBusiness(psBankingOnboardingRequest.getIsCustomerMakeForeignExchangeWithBusiness());
            psBankingOnboardingEntity.setIsCustomerwantToReceiveSMSAlerts(psBankingOnboardingRequest.getIsCustomerwantToReceiveSMSAlerts());
            psBankingOnboardingEntity.setSalaryAlerts(psBankingOnboardingRequest.getSalaryAlerts());
            psBankingOnboardingEntity.setAllCredit(psBankingOnboardingRequest.getAllCredit());
            psBankingOnboardingEntity.setAllDebit(psBankingOnboardingRequest.getAllDebit());
            psBankingOnboardingEntity.setIsCustomerWantToGetCreditCard(psBankingOnboardingRequest.getIsCustomerWantToGetCreditCard());
            psBankingOnboardingEntity.setKCBcreditCardType(psBankingOnboardingRequest.getKCBcreditCardType());
            psBankingOnboardingEntity.setIsCustomerWantToRegisterInternetBanking(psBankingOnboardingRequest.getIsCustomerWantToRegisterInternetBanking());
            psBankingOnboardingEntity.setReceiveTransactionAuthorizationVia(psBankingOnboardingRequest.getReceiveTransactionAuthorizationVia());
            PSBankingOnboardingEntity savedCustomerDetails = psBankingOnboardingRepossitory.save(psBankingOnboardingEntity);
            if (savedCustomerDetails == null) throw new RuntimeException("Failed to save customer details");
            //save file
            String frontIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "frontID_" + savedCustomerDetails.getId() + ".PNG", frontID);

            String backIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "backID_" + savedCustomerDetails.getId() + ".PNG", backID);
            String kraPath = fileStorageService.saveFileWithSpecificFileName(
                    "kra_" + savedCustomerDetails.getId() + ".PNG", kraPin);
            String signaturePath = fileStorageService.saveFileWithSpecificFileName(
                    "signature_" + savedCustomerDetails.getId() + ".PNG", signature);
            String crbReportPath = fileStorageService.saveFileWithSpecificFileName(
                    "crbReport_" + savedCustomerDetails.getId() + ".PNG", crbReport);
            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "customerPhoto_" + savedCustomerDetails.getId() + ".PNG", customerPhoto);

            //save file path
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPath);
            filePathList.add(signaturePath);
            filePathList.add(crbReportPath);
            filePathList.add(customerPhotoPath);
            filePathList.forEach(filePath -> {
                PSBankingOnboardingFileEntity customerKYC = new PSBankingOnboardingFileEntity();
                customerKYC.setFilePath(filePath);
                customerKYC.setPsBankingOnboardingEntity(savedCustomerDetails);
                psBankingOnboardingFileRepository.save(customerKYC);
            });
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllOnboardings(PBDSROnboardingRequest model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> allOnboardings = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            List<PSBankingOnboardingEntity> allOnboardingsByDsr = psBankingOnboardingRepossitory.findAllByDsrId(model.getDsrId());
            allOnboardingsByDsr.forEach(psBankingOnboardingEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("id", psBankingOnboardingEntity.getId());
                objectNode.put("surname", psBankingOnboardingEntity.getSurname());
                objectNode.put("otherNames", psBankingOnboardingEntity.getOtherNames());
                objectNode.put("idNumber", psBankingOnboardingEntity.getCustomerIdNumber());
                objectNode.put("phoneNumber", psBankingOnboardingEntity.getCustomerPhone());
                objectNode.put("email", psBankingOnboardingEntity.getCustomerEmail());
                objectNode.put("dateOfBirth", psBankingOnboardingEntity.getCustomerDob());
                objectNode.put("Gender", psBankingOnboardingEntity.getGender());
                //add to list
                allOnboardings.add(objectNode);

            });
            return allOnboardings;
        } catch (Exception e) {
            log.error("Error occurred while getting all onboarding requests by dsr", e);
        }
        return null;
    }
}
