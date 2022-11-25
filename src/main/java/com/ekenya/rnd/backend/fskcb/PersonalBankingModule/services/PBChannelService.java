package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringOnboardingKYCentity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringOnboardRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.AgencyOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.ekenya.rnd.backend.utils.Utility;
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
    private final PSBankingLeadRepository psBankingLeadRepository;

    private final PSBankingTargetRepository psBankingTargetRepository;


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
//            psBankingOnboardingEntity.setIsCustomerReceiveFundInForeignCurrency(psBankingOnboardingRequest.getIsCustomerReceiveFundInForeignCurrency());
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
//            psBankingOnboardingEntity.setSendVia(psBankingOnboardingRequest.getSendVia());
            psBankingOnboardingEntity.setIsCustomerWantToUseMobileBanking(psBankingOnboardingRequest.getIsCustomerWantToUseMobileBanking());
            psBankingOnboardingEntity.setIsCustomerMakeForeignExchangeWithBusiness(psBankingOnboardingRequest.getIsCustomerMakeForeignExchangeWithBusiness());
            psBankingOnboardingEntity.setIsCustomerwantToReceiveSMSAlerts(psBankingOnboardingRequest.getIsCustomerwantToReceiveSMSAlerts());
            psBankingOnboardingEntity.setSalaryAlerts(psBankingOnboardingRequest.getSalaryAlerts());
            psBankingOnboardingEntity.setAllCredit(psBankingOnboardingRequest.getAllCredit());
            psBankingOnboardingEntity.setAllDebit(psBankingOnboardingRequest.getAllDebit());
            psBankingOnboardingEntity.setIsCustomerWantToGetCreditCard(psBankingOnboardingRequest.getIsCustomerWantToGetCreditCard());
            psBankingOnboardingEntity.setKCBcreditCardType(psBankingOnboardingRequest.getKCBcreditCardType());
//            psBankingOnboardingEntity.setIsCustomerWantToRegisterInternetBanking(psBankingOnboardingRequest.getIsCustomerWantToRegisterInternetBanking());
//            psBankingOnboardingEntity.setReceiveTransactionAuthorizationVia(psBankingOnboardingRequest.getReceiveTransactionAuthorizationVia());
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

    @Override
    public ArrayList<ObjectNode> getTargetsSummary() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (PSBankingTargetEntity psBankingTargetEntity : psBankingTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", psBankingTargetEntity.getTargetAchievement());
                node.put("target", psBankingTargetEntity.getTargetValue());
                onboardingNode.set("onboarding", node);
                list.add(onboardingNode);
            }
            //add to the list hard coded values for commission
            ObjectNode node = mapper.createObjectNode();
            ObjectNode commissionNode = mapper.createObjectNode();
            node.put("current-commission", 0);
            node.put("previous-commision", 0);
            commissionNode.set("commission", node);
            list.add(commissionNode);
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;

    }

    @Override
    public boolean createLead(PBAddLeadRequest request) {
        try {
            PSBankingLeadEntity psBankingLeadEntity = new PSBankingLeadEntity();
            psBankingLeadEntity.setCustomerName(request.getCustomerName());
            psBankingLeadEntity.setBusinessUnit(request.getBusinessUnit());
            psBankingLeadEntity.setPriority(request.getPriority());
            psBankingLeadEntity.setCustomerAccountNumber(request.getCustomerAccountNumber());
            psBankingLeadEntity.setTopic(request.getTopic());
            psBankingLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psBankingLeadRepository.save(psBankingLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeadsByDsrId(PBAddLeadRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingLeadEntity psBankingLeadEntity : psBankingLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerId", psBankingLeadEntity.getCustomerId());
                node.put("businessUnit", psBankingLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(psBankingLeadEntity.getLeadStatus()));
                node.put("topic", psBankingLeadEntity.getTopic());
                node.put("priority", psBankingLeadEntity.getPriority().ordinal());
                node.put("dsrId", psBankingLeadEntity.getDsrId());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }
    private final PSBankingCustomerVisitRepository psBankingCustomerVisitRepository;

    @Override
    public boolean createCustomerVisit(PBCustomerVisitsRequest model) {
        try {
            if (model==null){
                return false;
            }
            PSBankingVisitEntity psBankingVisitEntity = new PSBankingVisitEntity();
            psBankingVisitEntity.setReasonForVisit(model.getReasonForVisit());
            psBankingVisitEntity.setTypeOfVisit(model.getTypeOfVisit());
            psBankingVisitEntity.setChannelUsed(model.getChannelUsed());
            psBankingVisitEntity.setActionplan(model.getActionplan());
            psBankingVisitEntity.setHighlightOfVisit(model.getHighlightOfVisit());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
            psBankingVisitEntity.setDsrName("test");
            psBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save customer visit
            psBankingCustomerVisitRepository.save(psBankingVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisitsByDSR(PBCustomerVisitsBYDSRRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<PSBankingVisitEntity> psBankingVisitEntities = psBankingCustomerVisitRepository.findAllByDsrId(model.getDsrId());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            psBankingVisitEntities.forEach(psBankingVisitEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("visitId", psBankingVisitEntity.getId());
                objectNode.put("reasonForVisit", psBankingVisitEntity.getReasonForVisit());
                objectNode.put("actionPlan", psBankingVisitEntity.getActionplan());
                objectNode.put("highlights", psBankingVisitEntity.getHighlightOfVisit());
                objectNode.put("dsrName", psBankingVisitEntity.getDsrName());
                objectNode.put("customerName",psBankingVisitEntity.getCustomerName());
                objectNode.put("createdOn", psBankingVisitEntity.getCreatedOn().toString());
                objectNodeList.add(objectNode);
            });
            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits by DSR", e);
        }
        return null;
    }
}
