package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetFileRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class VoomaChannelService implements IVoomaChannelService {

    private  final DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;
    private  final DFSVoomaLeadRepository dfsVoomaLeadRepository;
    private final DFSVoomaTargetRepository dfsVoomaTargetRepository;
    private final DFSVoomaOnboardingKYRepository dfsVoomaOnboardingKYRepository;
    private final DFSVoomaOnboardRepository dfsVoomaOnboardRepository;
    private final DFSVoomaAgentOnboardingRepository dfsVoomaAgentOnboardingRepositor;
    private final DFSVoomaAgentOnboardingKYCRepository dfsVoomaAgentOnboardingKYCRepository;
    private final DFSVoomaAssetRepository dfsVoomaAssetRepository;

    private final AcquiringAssetRepository acquiringAssetRepository;

    private final AcquiringAssetFileRepository acquiringAssetFileRepository;
    
    private final FileStorageService fileStorageService;

    private final int totalTransaction = (int) (Math.random() * 1000000);

    @Override
    public boolean createLead(VoomaAddLeadRequest model) {
        try {
            DFSVoomaLeadEntity dfsVoomaLeadEntity = new DFSVoomaLeadEntity();
            dfsVoomaLeadEntity.setCustomerName(model.getCustomerName());
            dfsVoomaLeadEntity.setBusinessUnit(model.getBusinessUnit());
            dfsVoomaLeadEntity.setPriority(model.getPriority());
            dfsVoomaLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            dfsVoomaLeadEntity.setTopic(model.getTopic());
            dfsVoomaLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeadsByDsrId(VoomaAddLeadRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaLeadRepository.findAllByDsrId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerId", dfsVoomaLeadEntity.getCustomerId());
                node.put("businessUnit", dfsVoomaLeadEntity.getBusinessUnit());
                node.put("leadStatus", String.valueOf(dfsVoomaLeadEntity.getLeadStatus()));
                node.put("topic", dfsVoomaLeadEntity.getTopic());
                node.put("priority", dfsVoomaLeadEntity.getPriority().ordinal());
                node.put("dsrId", dfsVoomaLeadEntity.getDsrId());
                //add to list
                list.add(node);
            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while loading questionnaires", e);
        }
        return null;
    }

    @Override
    public ArrayList<ObjectNode> getTargetsSummary() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaTargetEntity dfsVoomaTargetEntity : dfsVoomaTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", dfsVoomaTargetEntity.getTargetAchievement());
                node.put("target", dfsVoomaTargetEntity.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (DFSVoomaTargetEntity dfsVoomaTargetEntity : dfsVoomaTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", dfsVoomaTargetEntity.getTargetAchievement());
                node.put("target", dfsVoomaTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (DFSVoomaTargetEntity dfsVoomaTargetEntity : dfsVoomaTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", dfsVoomaTargetEntity.getTargetAchievement());
                node.put("target", dfsVoomaTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (DFSVoomaTargetEntity dfsVoomaTargetEntity : dfsVoomaTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", dfsVoomaTargetEntity.getTargetAchievement());
                node.put("target", dfsVoomaTargetEntity.getTargetValue());
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
    public Object onboardNewMerchant(String merchDetails, MultipartFile frontID, MultipartFile backID, MultipartFile kraPinCertificate,   MultipartFile shopPhoto,   MultipartFile signatureDoc, MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DFSVoomaOnboardRequest onboardMerchantRequest = mapper.readValue(
                    merchDetails, DFSVoomaOnboardRequest.class);
            if (onboardMerchantRequest == null) throw new RuntimeException("Bad request");
            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = new DFSVoomaOnboardEntity();
            //merchant type
            dfsVoomaOnboardEntity.setMerchantType(onboardMerchantRequest.getMerchantType());
            //company profile
            dfsVoomaOnboardEntity.setBusinessType(onboardMerchantRequest.getBusinessType());
            dfsVoomaOnboardEntity.setTradingName(onboardMerchantRequest.getTradingName());
            dfsVoomaOnboardEntity.setNatureOfTheBusiness(onboardMerchantRequest.getNatureOfTheBusiness());
            //owner details
            dfsVoomaOnboardEntity.setMerchantName(onboardMerchantRequest.getMerchantName());
            dfsVoomaOnboardEntity.setMerchantEmail(onboardMerchantRequest.getMerchantEmail());
            dfsVoomaOnboardEntity.setMerchantPhone(onboardMerchantRequest.getMerchantPhone());
            dfsVoomaOnboardEntity.setMerchantIdNumber(onboardMerchantRequest.getMerchantIdNumber());
            dfsVoomaOnboardEntity.setKRApin(onboardMerchantRequest.getKRApin());
            //business details
            dfsVoomaOnboardEntity.setBusinessName(onboardMerchantRequest.getBusinessName());
            dfsVoomaOnboardEntity.setBusinessEmail(onboardMerchantRequest.getBusinessEmail());
            dfsVoomaOnboardEntity.setOutletPhoneNo(onboardMerchantRequest.getOutletPhoneNo());
            dfsVoomaOnboardEntity.setBusinessKRAPin(onboardMerchantRequest.getBusinessKRAPin());
            dfsVoomaOnboardEntity.setWantVoomaPaybillNumber(onboardMerchantRequest.isWantVoomaPaybillNumber());
            dfsVoomaOnboardEntity.setWantVoomaTillNumber(onboardMerchantRequest.isWantVoomaTillNumber());
            dfsVoomaOnboardEntity.setExchangeForeign(onboardMerchantRequest.isExchangeForeign());
            //next of kin
            dfsVoomaOnboardEntity.setNextOfKinFullName(onboardMerchantRequest.getNextOfKinFullName());
            dfsVoomaOnboardEntity.setNextOfKinPhoneNumber(onboardMerchantRequest.getNextOfKinPhoneNumber());
            dfsVoomaOnboardEntity.setNextOfKinIdNumber(onboardMerchantRequest.getNextOfKinIdNumber());
            //physical address
            dfsVoomaOnboardEntity.setMerchantPbox(onboardMerchantRequest.getMerchantPbox());
            dfsVoomaOnboardEntity.setMerchantPostalCode(onboardMerchantRequest.getMerchantPostalCode());
            dfsVoomaOnboardEntity.setCounty(onboardMerchantRequest.getCounty());
            dfsVoomaOnboardEntity.setCity(onboardMerchantRequest.getCity());
            dfsVoomaOnboardEntity.setStreetName(onboardMerchantRequest.getStreetName());
            dfsVoomaOnboardEntity.setNearbyLandMark(onboardMerchantRequest.getNearbyLandMark());
            dfsVoomaOnboardEntity.setLongitude(onboardMerchantRequest.getLongitude());
            dfsVoomaOnboardEntity.setLatitude(onboardMerchantRequest.getLatitude());
            //save merchant details
            DFSVoomaOnboardEntity merchDtls = dfsVoomaOnboardRepository.save(dfsVoomaOnboardEntity);
            //subdirectory name generateSubDirectory
            String folderName = "voomaOnboardingMerchant";

            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + merchDtls.getId() + ".PNG", frontID,folderName);
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + merchDtls.getId() + ".PNG", backID,folderName);
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + merchDtls.getId() + ".PNG", kraPinCertificate,folderName);

            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + merchDtls.getId() + ".PNG", shopPhoto,folderName);
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "signatureDoc_" + merchDtls.getId() + ".PNG", signatureDoc,folderName);
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + merchDtls.getId() + ".PNG", businessPermitDoc,folderName);
            //save file paths to db
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(shopPhotoPath);
            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            filePathList.forEach(filePath -> {
                DFSVoomaOnboardingKYCentity agentKYC = new DFSVoomaOnboardingKYCentity();
                agentKYC.setFilePath(filePath);
                agentKYC.setDfsVoomaOnboardEntity(merchDtls);
                dfsVoomaOnboardingKYRepository.save(agentKYC);
            });
            return true;


        } catch (Exception e) {
            log.error("Error occurred while onboarding merchant", e);
        }
        return false;
    }

    @Override
    public Object onboardNewAgent(String agentDetails,
                                  MultipartFile frontID,
                                  MultipartFile backID,
                                  MultipartFile kraPinCertificate,
                                  MultipartFile businessCertificateOfRegistration,
                                  MultipartFile shopPhoto,
                                  MultipartFile signatureDoc,
                                  MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DFSVoomaAgentOnboardRequest voomaAgentOnboardRequest = mapper.readValue(
                    agentDetails, DFSVoomaAgentOnboardRequest.class);
            if (voomaAgentOnboardRequest == null) throw new RuntimeException("Bad request");
            DFSVoomaAgentOnboardingEntity dfsVoomaAgentOnboardEntity = new DFSVoomaAgentOnboardingEntity();
            //outlet personal details
            dfsVoomaAgentOnboardEntity.setNameOfContractSignatory(voomaAgentOnboardRequest.getNameOfContractSignatory());
            dfsVoomaAgentOnboardEntity.setContractSignatoryPhoneNumber(voomaAgentOnboardRequest.getContractSignatoryPhoneNumber());
            dfsVoomaAgentOnboardEntity.setContractSignatoryEmail(voomaAgentOnboardRequest.getContractSignatoryEmail());
            dfsVoomaAgentOnboardEntity.setKeyContactName(voomaAgentOnboardRequest.getKeyContactName());
            dfsVoomaAgentOnboardEntity.setKeyContactPhoneNumber(voomaAgentOnboardRequest.getKeyContactPhoneNumber());
            dfsVoomaAgentOnboardEntity.setKeyContactEmail(voomaAgentOnboardRequest.getKeyContactEmail());
            dfsVoomaAgentOnboardEntity.setKeyFinanceContactName(voomaAgentOnboardRequest.getKeyFinanceContactName());
            dfsVoomaAgentOnboardEntity.setKeyFinanceContactPhoneNumber(voomaAgentOnboardRequest.getKeyFinanceContactPhoneNumber());
            dfsVoomaAgentOnboardEntity.setKeyFinanceContactEmail(voomaAgentOnboardRequest.getKeyFinanceContactEmail());
            //company details
            dfsVoomaAgentOnboardEntity.setBusinessType(voomaAgentOnboardRequest.getBusinessType());
            dfsVoomaAgentOnboardEntity.setBusinessPhoneNumber(voomaAgentOnboardRequest.getBusinessPhoneNumber());
            dfsVoomaAgentOnboardEntity.setBusinessEmail(voomaAgentOnboardRequest.getBusinessEmail());
            dfsVoomaAgentOnboardEntity.setFaxNumber(voomaAgentOnboardRequest.getFaxNumber());
            dfsVoomaAgentOnboardEntity.setKCBAgent(voomaAgentOnboardRequest.isKCBAgent());
            dfsVoomaAgentOnboardEntity.setNumberOfOutlets(voomaAgentOnboardRequest.getNumberOfOutlets());
            dfsVoomaAgentOnboardEntity.setKRAPin(voomaAgentOnboardRequest.getKRAPin());
            dfsVoomaAgentOnboardEntity.setVATNumber(voomaAgentOnboardRequest.getVATNumber());
            //physical address
            dfsVoomaAgentOnboardEntity.setPostalAddress(voomaAgentOnboardRequest.getPostalAddress());
            dfsVoomaAgentOnboardEntity.setPostalCode(voomaAgentOnboardRequest.getPostalCode());
            dfsVoomaAgentOnboardEntity.setTown(voomaAgentOnboardRequest.getTown());
            dfsVoomaAgentOnboardEntity.setNearestLandmark(voomaAgentOnboardRequest.getNearestLandmark());
//            dfsVoomaAgentOnboardEntity.setStaffId(voomaAgentOnboardRequest.getStaffId());
            //save to db
            DFSVoomaAgentOnboardingEntity agentData = dfsVoomaAgentOnboardingRepositor.save(dfsVoomaAgentOnboardEntity);
            //save files to server
            String folderName = "voomaAgentOnboarding";
            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + agentData.getId() + ".PNG", frontID,folderName);
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + agentData.getId() + ".PNG", backID,folderName);
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + agentData.getId() + ".PNG", kraPinCertificate,folderName);
            String businessCertificateOfRegistrationPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessCertificateOfRegistration_" + agentData.getId() + ".PNG", businessCertificateOfRegistration,folderName);
            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + agentData.getId() + ".PNG", shopPhoto,folderName);
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "signatureDoc_" + agentData.getId() + ".PNG", signatureDoc,folderName);
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + agentData.getId() + ".PNG", businessPermitDoc,folderName);
            //save file paths to db
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(businessCertificateOfRegistrationPath);
            filePathList.add(shopPhotoPath);
            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            filePathList.forEach(filePath -> {
                DFSVoomaAgentOnboardingKYCEntity agentKYC = new DFSVoomaAgentOnboardingKYCEntity();
                agentKYC.setFilePath(filePath);
                agentKYC.setDfsVoomaAgentOnboardingEntity(agentData);
                dfsVoomaAgentOnboardingKYCRepository.save(agentKYC);
            });
            return true;
        } catch (Exception e) {
            log.error("Error occurred while Onboarding Agent", e);
        }
        return false;
    }

    @Override
    public boolean assignAssetToMerchant(VoomaAssignAssetRequest model) {
        //assign asset to merchant
        try {
            if (model ==null){
                return false;
            }
            DFSVoomaOnboardEntity dfsVoomaMerchantOnboardingEntity = dfsVoomaOnboardRepository.findById(model.getCustomerId()).orElse(null);
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber((model.getSerialNumber())).orElse(null);
            if (dfsVoomaMerchantOnboardingEntity == null || dfsVoomaAssetEntity == null){
                return false;
            }
            dfsVoomaAssetEntity.setDfsVoomaOnboardEntity(dfsVoomaMerchantOnboardingEntity);
            dfsVoomaAssetEntity.setAssigned(true);
            dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning asset to agent", e);
        }
        return false;
    }

    @Override
    public boolean assignAssetToAgent(VoomaAssignAssetRequest model) {
        try {
            if (model ==null){
                return false;
            }
            DFSVoomaAgentOnboardingEntity dfsVoomaAgentOnboardingEntity = dfsVoomaAgentOnboardingRepositor.findById(model.getCustomerId()).orElse(null);
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber((model.getSerialNumber())).orElse(null);
            if (dfsVoomaAgentOnboardingEntity == null || dfsVoomaAssetEntity == null){
                return false;
            }
            dfsVoomaAssetEntity.setDfsVoomaAgentOnboardingEntity(dfsVoomaAgentOnboardingEntity);
            dfsVoomaAssetEntity.setAssigned(true);
            dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning asset to agent", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllAgentMerchantAssets(CustomerAssetsRequest model) {
        try {
            if (model == null){
                return null;
            }
            //get all assets for merchant
            List<DFSVoomaAssetEntity> dfsVoomaAssetEntityList = dfsVoomaAssetRepository.findAllByDfsVoomaOnboardingEntityId(model.getCustomerId());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            dfsVoomaAssetEntityList.forEach(dfsVoomaAssetEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("assetId", dfsVoomaAssetEntity.getId());
                objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
                objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().ordinal());
                objectNode.put("totalTransaction", totalTransaction);
                objectNodeList.add(objectNode);
            });

            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all agent merchant assets", e);
        }
        return null;
    }

    @Override
    public boolean recollectAsset(VoomaCollectAssetRequest model) {
        try {
            if (model ==null){
                return false;
            }
            DFSVoomaAssetEntity dfsVoomaAssetEntity =dfsVoomaAssetRepository.findById(model.getAssetId()).orElse(null);
            if (dfsVoomaAssetEntity == null){
                return false;
            }
            dfsVoomaAssetEntity.setDfsVoomaAgentOnboardingEntity(null);
            dfsVoomaAssetEntity.setDfsVoomaOnboardEntity(null);
            dfsVoomaAssetEntity.setAssigned(false);
            dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while recollecting asset", e);
        }
        return false;
    }

    @Override
    public boolean createCustomerVisit(VoomaCustomerVisitsRequest model) {
        try {
            if (model==null){
                return false;
            }
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = new DFSVoomaCustomerVisitEntity();
            dfsVoomaCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            dfsVoomaCustomerVisitEntity.setActionPlan(model.getActionPlan());
            dfsVoomaCustomerVisitEntity.setHighlights(model.getHighlights());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
            dfsVoomaCustomerVisitEntity.setDsrName("test");
            dfsVoomaCustomerVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save customer visit
            dfsVoomaCustomerVisitRepository.save(dfsVoomaCustomerVisitEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while creating customer visit", e);
        }
        return false;
    }

    @Override
    public boolean updateCustomerVisit(VoomaCustomerVisitsRequest request) {
        return false;
    }

    @Override
    public List<ObjectNode> getAllCustomerVisitsByDSR(VoomaCustomerVisitsRequest model) {
        try {
            if (model==null){
                return null;
            }
            List<DFSVoomaCustomerVisitEntity> dfsVoomaCustomerVisitEntityList = dfsVoomaCustomerVisitRepository.findAllByDsrId(model.getDsrId());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            dfsVoomaCustomerVisitEntityList.forEach(dfsVoomaCustomerVisitEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("visitId", dfsVoomaCustomerVisitEntity.getId());
                objectNode.put("reasonForVisit", dfsVoomaCustomerVisitEntity.getReasonForVisit());
                objectNode.put("actionPlan", dfsVoomaCustomerVisitEntity.getActionPlan());
                objectNode.put("highlights", dfsVoomaCustomerVisitEntity.getHighlights());
                objectNode.put("dsrName", dfsVoomaCustomerVisitEntity.getDsrName());
                objectNode.put("customerName",dfsVoomaCustomerVisitEntity.getCustomerName());
                objectNode.put("createdOn", dfsVoomaCustomerVisitEntity.getCreatedOn().toString());
                objectNodeList.add(objectNode);
            });
            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all customer visits by DSR", e);
        }
        return null;
    }

    @Override
    public Object getSummary(DSRSummaryRequest model) {
       return null;
    }

}