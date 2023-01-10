package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardingKYCentity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private final PSBankingCustomerVisitRepository psBankingCustomerVisitRepository;

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
            psBankingOnboardingEntity.setStatus(OnboardingStatus.PENDING);
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
//            String folderName = "customerDetails" + File.separator + savedCustomerDetails.getId();
            String frontIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "frontID_" + savedCustomerDetails.getId() + ".PNG", frontID);

            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + savedCustomerDetails.getId() + ".PNG", backID,Utility.getSubFolder());
            String kraPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kra_" + savedCustomerDetails.getId() + ".PNG", kraPin,Utility.getSubFolder());
            String signaturePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "signature_" + savedCustomerDetails.getId() + ".PNG", signature,Utility.getSubFolder());
            String crbReportPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "crbReport_" + savedCustomerDetails.getId() + ".PNG", crbReport,Utility.getSubFolder());
            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "customerPhoto_" + savedCustomerDetails.getId() + ".PNG", customerPhoto,Utility.getSubFolder());

            //save file path
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPath);
            filePathList.add(signaturePath);
            filePathList.add(crbReportPath);
            filePathList.add(customerPhotoPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/" + Utility.getSubFolder() + "/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                PSBankingOnboardingFileEntity psBankingOnboardingEntity1 = new PSBankingOnboardingFileEntity();
                psBankingOnboardingEntity1.setFilePath(downloadUrl);
                psBankingOnboardingEntity1.setPsBankingOnboardingEntity(savedCustomerDetails);
                psBankingOnboardingEntity1.setPersonId(savedCustomerDetails.getId());
                psBankingOnboardingEntity1.setFileName(filePath);
                psBankingOnboardingFileRepository.save(psBankingOnboardingEntity1);
                return true;
            }
            } catch(Exception e){
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

    @Override
    public boolean attemptCreateLead(TreasuryAddLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            PSBankingLeadEntity psBankingLeadEntity = new PSBankingLeadEntity();
            psBankingLeadEntity.setCustomerName(model.getCustomerName());
            psBankingLeadEntity.setBusinessUnit(model.getBusinessUnit());
            psBankingLeadEntity.setEmail(model.getEmail());
            psBankingLeadEntity.setPhoneNumber(model.getPhoneNumber());
            psBankingLeadEntity.setProduct(model.getProduct());
            psBankingLeadEntity.setPriority(model.getPriority());
            psBankingLeadEntity.setDsrId(model.getDsrId());
            psBankingLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            psBankingLeadEntity.setTopic(model.getTopic());
            psBankingLeadEntity.setLeadStatus(LeadStatus.OPEN);
            psBankingLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            psBankingLeadRepository.save(psBankingLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingLeadEntity psBankingLeadEntity : psBankingLeadRepository.findAllByDsrIdAndAssigned(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", psBankingLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", psBankingLeadEntity.getPriority().toString());
                node.put("businessUnit", psBankingLeadEntity.getBusinessUnit());
                node.put("leadId", psBankingLeadEntity.getId());
                node.put("leadStatus", psBankingLeadEntity.getLeadStatus().ordinal());
                node.put("createdOn", psBankingLeadEntity.getCreatedOn().getTime());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;

    }


    @Override
    public List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model==null){
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (PSBankingLeadEntity psBankingLeadEntity : psBankingLeadRepository.findAllAssignedLeadByDSRId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", psBankingLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", psBankingLeadEntity.getPriority().toString());
                node.put("businessUnit", psBankingLeadEntity.getBusinessUnit());
                node.put("leadId", psBankingLeadEntity.getId());
                list.add(node);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while loading assigned leads", e);
        }
        return null;
    }

    @Override
    public boolean attemptUpdateLead(TreasuryUpdateLeadRequest model) {
        try {
            if (model==null){
                return false;
            }
            PSBankingLeadEntity psBankingLeadEntity = psBankingLeadRepository.findById(model.getLeadId()).orElse(null);
            psBankingLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            psBankingLeadEntity.setLeadStatus(model.getLeadStatus());
            psBankingLeadRepository.save(psBankingLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return false;
    }

    @Override
    public ArrayNode getDSRSummary(DSRSummaryRequest model) {
        try {
            if (model == null) {
                return null;
            }
            ArrayNode arrayNode = new ObjectMapper().createArrayNode();
            ObjectNode objectNode = new ObjectMapper().createObjectNode();
            short commission=0;
            short targetAchieved=0;
            objectNode.put("commission", commission);
            //get total number of dsr visits by dsr id
            int totalVisits = psBankingCustomerVisitRepository.countTotalVisits(model.getDsrId());
            objectNode.put("customer-visits", totalVisits);
            //if null hard code visits for now
            if (totalVisits == 0) {
                objectNode.put("customer-visits", 0);
            }
            //get total number of dsr assigned leads by dsr id
            int totalAssignedLeads = psBankingLeadRepository.countTotalAssignedLeads(model.getDsrId());
            objectNode.put("assigned-leads", totalAssignedLeads);
            //if null hard code assigned leads for now
            if (totalAssignedLeads == 0) {
                objectNode.put("assigned-leads", 0);
            }
//    //get total number of dsr targets achieved by dsr id
//hard code for now since we dont know metrics to messure target achieved
            objectNode.put("targetAchieved",targetAchieved);
            arrayNode.add(objectNode);
            return arrayNode;
        } catch (Exception e) {
            log.error("Error occurred while getting dsr summary", e);
        }
        return null;
    }
    }

