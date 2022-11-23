package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringOnboardRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcquiringChannelService implements IAcquiringChannelService {
    private final AcquiringOnboardingKYCRepository acquiringOnboardingKYCRepository;
    private final IAcquiringOnboardingsRepository acquiringOnboardingsRepository;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final FileStorageService fileStorageService;
    private final IAcquiringTargetsRepository acquiringTargetsRepository;

    private final IAcquiringLeadsRepository acquiringLeadsRepository;
    private final AcquiringCustomerVisitRepository acquiringCustomerVisitRepository;

    private final int totalTransactions = Utility.generateRandomNumber(1000, 100000);



    @Override
    public JsonObject findCustomerByAccNo(String accNo) {
        return null;
    }

    @Override
    public Object onboardNewMerchant(String merchDetails, MultipartFile frontID, MultipartFile backID, MultipartFile kraPinCertificate, MultipartFile certificateOFGoodConduct, MultipartFile businessLicense, MultipartFile shopPhoto, MultipartFile customerPhoto, MultipartFile companyRegistrationDoc, MultipartFile signatureDoc, MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AcquiringOnboardRequest onboardMerchantRequest = mapper.readValue(
                    merchDetails, AcquiringOnboardRequest.class);
            if (onboardMerchantRequest == null) throw new RuntimeException("Bad request");
            AcquiringOnboardEntity acquiringOnboardEntity = new AcquiringOnboardEntity();
            //merchant type
            acquiringOnboardEntity.setMerchantType(onboardMerchantRequest.getMerchantType());
            //company profile
            acquiringOnboardEntity.setBusinessType(onboardMerchantRequest.getBusinessType());
            acquiringOnboardEntity.setTradingName(onboardMerchantRequest.getTradingName());
            acquiringOnboardEntity.setNatureOfTheBusiness(onboardMerchantRequest.getNatureOfTheBusiness());
            //owner details
            acquiringOnboardEntity.setMerchantName(onboardMerchantRequest.getMerchantName());
            acquiringOnboardEntity.setMerchantEmail(onboardMerchantRequest.getMerchantEmail());
            acquiringOnboardEntity.setMerchantPhone(onboardMerchantRequest.getMerchantPhone());
            acquiringOnboardEntity.setMerchantIdNumber(onboardMerchantRequest.getMerchantIdNumber());
            acquiringOnboardEntity.setKRApin(onboardMerchantRequest.getKRApin());
            //business details
            acquiringOnboardEntity.setBusinessName(onboardMerchantRequest.getBusinessName());
            acquiringOnboardEntity.setBusinessEmail(onboardMerchantRequest.getBusinessEmail());
            acquiringOnboardEntity.setOutletPhoneNo(onboardMerchantRequest.getOutletPhoneNo());
            acquiringOnboardEntity.setBusinessKRAPin(onboardMerchantRequest.getBusinessKRAPin());
            acquiringOnboardEntity.setWantVoomaPaybillNumber(onboardMerchantRequest.isWantVoomaPaybillNumber());
            acquiringOnboardEntity.setWantVoomaTillNumber(onboardMerchantRequest.isWantVoomaTillNumber());
            acquiringOnboardEntity.setExchangeForeign(onboardMerchantRequest.isExchangeForeign());
            //next of kin
            acquiringOnboardEntity.setNextOfKinFullName(onboardMerchantRequest.getNextOfKinFullName());
            acquiringOnboardEntity.setNextOfKinPhoneNumber(onboardMerchantRequest.getNextOfKinPhoneNumber());
            acquiringOnboardEntity.setNextOfKinIdNumber(onboardMerchantRequest.getNextOfKinIdNumber());
            //physical address
            acquiringOnboardEntity.setMerchantPbox(onboardMerchantRequest.getMerchantPbox());
            acquiringOnboardEntity.setMerchantPostalCode(onboardMerchantRequest.getMerchantPostalCode());
            acquiringOnboardEntity.setCounty(onboardMerchantRequest.getCounty());
            acquiringOnboardEntity.setCity(onboardMerchantRequest.getCity());
            acquiringOnboardEntity.setStreetName(onboardMerchantRequest.getStreetName());
            acquiringOnboardEntity.setNearbyLandMark(onboardMerchantRequest.getNearbyLandMark());
            acquiringOnboardEntity.setLongitude(onboardMerchantRequest.getLongitude());
            acquiringOnboardEntity.setLatitude(onboardMerchantRequest.getLatitude());
            //save merchant details
            AcquiringOnboardEntity merchDtls = acquiringOnboardingsRepository.save(acquiringOnboardEntity);
            //documents upload
            String frontIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "frontID_" + merchDtls.getId() + ".PNG", frontID);

            String backIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "backID_" + merchDtls.getId() + ".PNG", backID);

            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileName(
                    "kraPinCertificate_" + merchDtls.getId() + ".PNG", kraPinCertificate);

            String certificateOFGoodConductPath = fileStorageService.saveFileWithSpecificFileName(
                    "certificateOFGoodConduct_" + merchDtls.getId() + ".PNG", certificateOFGoodConduct);

            String businessLicensePath = fileStorageService.saveFileWithSpecificFileName(
                    "businessLicense_" + merchDtls.getId() + ".PNG", businessLicense);


            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "shopPhoto_" + merchDtls.getId() + ".PNG", shopPhoto);

            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "customerPhoto_" + merchDtls.getId() + ".PNG", customerPhoto);


            String companyRegistrationDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "companyRegistrationDoc_" + merchDtls.getId() + ".PNG", companyRegistrationDoc);

            String signatureDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "signatureDocDoc_" + merchDtls.getId() + ".PNG", signatureDoc);

            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "businessPermitDoc_" + merchDtls.getId() + ".PNG", businessPermitDoc);
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(certificateOFGoodConductPath);
            filePathList.add(shopPhotoPath);
            filePathList.add(customerPhotoPath);
            filePathList.add(companyRegistrationDocPath);
            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            filePathList.add(businessLicensePath);
            filePathList.forEach(filePath -> {
                AcquiringOnboardingKYCentity merchantKYC = new AcquiringOnboardingKYCentity();
                merchantKYC.setFilePath(filePath);
                merchantKYC.setAcquiringOnboardEntity(merchDtls);
                acquiringOnboardingKYCRepository.save(merchantKYC);
            });

            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllOnboardings() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringOnboardEntity.getId());
                asset.put("merchantType", acquiringOnboardEntity.getMerchantType().ordinal());
                asset.put("businessType", acquiringOnboardEntity.getBusinessType());
                asset.put("tradingName", acquiringOnboardEntity.getTradingName());
                asset.put("natureOfTheBusiness", acquiringOnboardEntity.getNatureOfTheBusiness());
                asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
                asset.put("merchantEmail", acquiringOnboardEntity.getMerchantEmail());
                asset.put("merchantPhone", acquiringOnboardEntity.getMerchantPhone());
                asset.put("merchantIdNumber", acquiringOnboardEntity.getMerchantIdNumber());
                asset.put("kRApin", acquiringOnboardEntity.getKRApin());
                asset.put("businessName", acquiringOnboardEntity.getBusinessName());
                asset.put("businessEmail", acquiringOnboardEntity.getBusinessEmail());
                asset.put("outletPhoneNo", acquiringOnboardEntity.getOutletPhoneNo());
                asset.put("businessKRAPin", acquiringOnboardEntity.getBusinessKRAPin());
                asset.put("wantVoomaPaybillNumber", acquiringOnboardEntity.isWantVoomaPaybillNumber());
                asset.put("wantVoomaTillNumber", acquiringOnboardEntity.isWantVoomaTillNumber());
                asset.put("exchangeForeign", acquiringOnboardEntity.isExchangeForeign());
                asset.put("nextOfKinFullName", acquiringOnboardEntity.getNextOfKinFullName());
                asset.put("nextOfKinPhoneNumber", acquiringOnboardEntity.getNextOfKinPhoneNumber());
                asset.put("nextOfKinIdNumber", acquiringOnboardEntity.getNextOfKinIdNumber());
                asset.put("merchantPbox", acquiringOnboardEntity.getMerchantPbox());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting all onboardings", e);
        }
        return null;
    }

    @Override
    public boolean createLead(AcquiringAddLeadRequest model) {
        try {
            AcquiringLeadEntity acquiringLeadEntity = new AcquiringLeadEntity();
            acquiringLeadEntity.setCustomerName(model.getCustomerName());
            acquiringLeadEntity.setBusinessUnit(model.getBusinessUnit());
            acquiringLeadEntity.setPriority(model.getPriority());
            acquiringLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            acquiringLeadEntity.setTopic(model.getTopic());
            acquiringLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringLeadEntity acquiringLeadEntity : acquiringLeadsRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringLeadEntity.getId());
                asset.put("customerName", acquiringLeadEntity.getCustomerName());
                asset.put("businessUnit", acquiringLeadEntity.getBusinessUnit());
                asset.put("priority", acquiringLeadEntity.getPriority().ordinal());
                asset.put("customerAccountNumber", acquiringLeadEntity.getCustomerAccountNumber());
                asset.put("topic", acquiringLeadEntity.getTopic());
                asset.put("createdOn", acquiringLeadEntity.getCreatedOn().toString());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting all leads", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getAllAssignedLeads() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringLeadEntity acquiringAssignedLeadEntity : acquiringLeadsRepository.fetchAllAssignedLeads()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringAssignedLeadEntity.getId());
                asset.put("customerName", acquiringAssignedLeadEntity.getCustomerName());
                asset.put("businessUnit", acquiringAssignedLeadEntity.getBusinessUnit());
                asset.put("priority", acquiringAssignedLeadEntity.getPriority().ordinal());
                asset.put("customerAccountNumber", acquiringAssignedLeadEntity.getCustomerAccountNumber());
                asset.put("topic", acquiringAssignedLeadEntity.getTopic());
                asset.put("createdOn", acquiringAssignedLeadEntity.getCreatedOn().toString());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting all assigned leads", e);
        }
        return null;
    }

    @Override
    public Object updateLead(AcquiringAddLeadRequest model) {
        //update lead
        try {
            AcquiringLeadEntity acquiringLeadEntity = acquiringLeadsRepository.findById(model.getId()).get();
            acquiringLeadEntity.setLeadStatus(model.getLeadStatus());
            acquiringLeadEntity.setRemarks(model.getRemarks());
            acquiringLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            acquiringLeadsRepository.save(acquiringLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> searchCustomers(String keyword) {
        //search customer by name or phone number from onboarding
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.searchCustomers(keyword)) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringOnboardEntity.getId());
                asset.put("businessType", acquiringOnboardEntity.getBusinessType());
                asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
                asset.put("merchantEmail", acquiringOnboardEntity.getMerchantEmail());
                asset.put("merchantPhone", acquiringOnboardEntity.getMerchantPhone());
                asset.put("merchantIdNumber", acquiringOnboardEntity.getMerchantIdNumber());
                asset.put("businessName", acquiringOnboardEntity.getBusinessName());
                asset.put("businessEmail", acquiringOnboardEntity.getBusinessEmail());

                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while searching customers", e);
        }
        return null;
    }

    @Override
    public List<ObjectNode> getNearbyCustomers(AcquiringNearbyCustomersRequest model) {


        return null;
    }

    @Override
    public List<ObjectNode> getTargetsSummary() {
        //{
        //    "onboarding":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "visits":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "leads":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "campaigns":{
        //        "achieved":67,
        //        "target":100
        //    },
        //    "cur-comission":56000,
        //    "prev-comission":45000
        //}
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.fetchAllOnboardingTarget()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("targetValue", acquiringTargetEntity.getTargetValue());
                asset.put("targetAchieved", acquiringTargetEntity.getTargetAchievement());
                list.add(asset);
            }
            //targetValue and targetAchieved for Target type is VISITS
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.fetchAllVisitsTarget()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("targetValue", acquiringTargetEntity.getTargetValue());
                asset.put("targetAchieved", acquiringTargetEntity.getTargetAchievement());
                list.add(asset);
            }
            //targetValue and targetAchieved for Target type is LEADS
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.fetchAllLeadsTarget()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("targetValue", acquiringTargetEntity.getTargetValue());
                asset.put("targetAchieved", acquiringTargetEntity.getTargetAchievement());
                list.add(asset);
            }
            //targetValue and targetAchieved for Target type is CAMPAIGNS
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.fetchAllCampaignsTarget()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("targetValue", acquiringTargetEntity.getTargetValue());
                asset.put("targetAchieved", acquiringTargetEntity.getTargetAchievement());
                list.add(asset);
            }
            //current commission  hard coded for now
            ObjectNode asset = mapper.createObjectNode();
            asset.put("cur-comission", 56000);
            asset.put("prev-comission", 45000);
            list.add(asset);

            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting targets summary", e);
        }
        return null;
    }

    @Override
    public boolean createCustomerVisit(AcquiringCustomerVisitsRequest model) {
        try {
            if (model==null){
                return false;
            }
            AcquiringCustomerVisitEntity acquiringCustomerVisitsEntity = new AcquiringCustomerVisitEntity();
            acquiringCustomerVisitsEntity.setReasonForVisit(model.getReasonForVisit());
            acquiringCustomerVisitsEntity.setActionPlan(model.getActionPlan());
            acquiringCustomerVisitsEntity.setHighlights(model.getHighlights());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
            acquiringCustomerVisitsEntity.setDsrName("test");
            acquiringCustomerVisitsEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save customer visit
            acquiringCustomerVisitRepository.save(acquiringCustomerVisitsEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public boolean updateCustomerVisit(AcquiringCustomerVisitsRequest model) {
        //TODO update customer visit not implemented
        return false;
    }

    @Override
    public List<?> getAllCustomerVisitsByDSR(int dsrId) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringCustomerVisitEntity acquiringCustomerVisitEntity : acquiringCustomerVisitRepository.getAllCustomerVisitsByDSR(dsrId)) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringCustomerVisitEntity.getId());
                asset.put("reasonForVisit", acquiringCustomerVisitEntity.getReasonForVisit());
                asset.put("actionPlan", acquiringCustomerVisitEntity.getActionPlan());
                asset.put("highlights", acquiringCustomerVisitEntity.getHighlights());
                asset.put("dsrName", acquiringCustomerVisitEntity.getDsrName());
                asset.put("createdOn", acquiringCustomerVisitEntity.getCreatedOn().toString());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits by DSR", e);
        }
        return null;
    }

    @Override
    public boolean assignAssetToMerchant(Long assetId, Long agentId) {
        //assign asset to merchant
        try {
            if (assetId==null || agentId==null){
                return false;
            }
            AcquiringAssetEntity acquiringAssetEntity = acquiringAssetRepository.findById(assetId).get();
            if (acquiringAssetEntity==null){
                return false;
            }
            acquiringAssetEntity.setAgentId(agentId);
            acquiringAssetRepository.save(acquiringAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning asset to merchant", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllAgentsAssets(Long agentId) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringAssetEntity acquiringAssetEntity : acquiringAssetRepository.getAllAgentsAssets(agentId)) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringAssetEntity.getId());
              asset.put("SerialNumber", acquiringAssetEntity.getSerialNumber());
              asset.put("condition", acquiringAssetEntity.getAssetCondition().ordinal());
              //hard code total transactions for now
                asset.put("totalTransactions", totalTransactions);
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while getting all agents assets", e);
        }
        return null;
    }
}
