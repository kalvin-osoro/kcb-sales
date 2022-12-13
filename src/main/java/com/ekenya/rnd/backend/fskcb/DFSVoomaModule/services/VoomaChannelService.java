package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetFilesEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetFileRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBJustificationEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBJustificationRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    private final DFSVoomaMerchantOnboardV1Repository dfsVoomaMerchantOnboardV1Repository;
    private final DFSVoomaAgentOnboardV1Repository dfsVoomaAgentOnboardV1Repository;
    private final DFSVoomaAgentOwnerDetailsRepository dfsVoomaAgentOwnerDetailsRepository;
    private final DFSVoomaAgentContactDetailsRepository dfsVoomaAgentContactDetailsRepository;
    private final DFSVoomaOwnerDetailsRepository dfsVoomaOwnerDetailsRepository;
    private final DFSVoomaContactDetailsRepository dfsVoomaContactDetailsRepository;
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
            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.PENDING);
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
            dfsVoomaAgentOnboardEntity.setStatus(OnboardingStatus.PENDING);
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

    @Override
    public Object onboardNewMerchantV1(String merchDetails,
                                       MultipartFile frontID,
                                       MultipartFile backID,
                                       MultipartFile kraPinCertificate,
                                       MultipartFile shopPhoto,
                                       MultipartFile signatureDoc,
                                       MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DFSVoomaMerchantOnboardV1Request dfsVoomaMerchantOnboardV1Request = mapper.readValue(
                    merchDetails, DFSVoomaMerchantOnboardV1Request.class);
            if (dfsVoomaMerchantOnboardV1Request == null) throw new RuntimeException("Bad request");
            DFSVoomaMerchantOnboardV1 dfsVoomaMerchantOnboardV1 = new DFSVoomaMerchantOnboardV1();
            //organisations details
            dfsVoomaMerchantOnboardV1.setBusinessType(dfsVoomaMerchantOnboardV1Request.getBusinessType());
            dfsVoomaMerchantOnboardV1.setTradingName(dfsVoomaMerchantOnboardV1Request.getTradingName());
            dfsVoomaMerchantOnboardV1.setNatureOfBusiness(dfsVoomaMerchantOnboardV1Request.getNatureOfBusiness());
            dfsVoomaMerchantOnboardV1.setMerchantType(dfsVoomaMerchantOnboardV1Request.getMerchantType());
            dfsVoomaMerchantOnboardV1.setSettlmentType(dfsVoomaMerchantOnboardV1Request.getSettlmentType());
            //business details
            dfsVoomaMerchantOnboardV1.setBusinessName(dfsVoomaMerchantOnboardV1Request.getBusinessName());
            dfsVoomaMerchantOnboardV1.setOutletPhoneNumber(dfsVoomaMerchantOnboardV1Request.getOutletPhoneNumber());
            dfsVoomaMerchantOnboardV1.setBusinessEmailAddress(dfsVoomaMerchantOnboardV1Request.getBusinessEmailAddress());
            dfsVoomaMerchantOnboardV1.setBusinessKraPin(dfsVoomaMerchantOnboardV1Request.getBusinessKraPin());
            dfsVoomaMerchantOnboardV1.setWantTillNumber(dfsVoomaMerchantOnboardV1Request.getWantTillNumber());
            dfsVoomaMerchantOnboardV1.setWantPaybillNumber(dfsVoomaMerchantOnboardV1Request.getWantPaybillNumber());
            dfsVoomaMerchantOnboardV1.setDealingWithForeignExchange(dfsVoomaMerchantOnboardV1Request.getDealingWithForeignExchange());
            //settlement details
            dfsVoomaMerchantOnboardV1.setBranchName(dfsVoomaMerchantOnboardV1Request.getBranchName());
            dfsVoomaMerchantOnboardV1.setAccountName(dfsVoomaMerchantOnboardV1Request.getAccountName());
            dfsVoomaMerchantOnboardV1.setAccountNumber(dfsVoomaMerchantOnboardV1Request.getAccountNumber());
            dfsVoomaMerchantOnboardV1.setDate(dfsVoomaMerchantOnboardV1Request.getDate());
            //physical address
            dfsVoomaMerchantOnboardV1.setPostalAddress(dfsVoomaMerchantOnboardV1Request.getPostalAddress());
            dfsVoomaMerchantOnboardV1.setPostalCode(dfsVoomaMerchantOnboardV1Request.getPostalCode());
            dfsVoomaMerchantOnboardV1.setCityOrTown(dfsVoomaMerchantOnboardV1Request.getCityOrTown());
            dfsVoomaMerchantOnboardV1.setNearestLandmark(dfsVoomaMerchantOnboardV1Request.getNearestLandmark());
            dfsVoomaMerchantOnboardV1.setLatitude(dfsVoomaMerchantOnboardV1Request.getLatitude());
            dfsVoomaMerchantOnboardV1.setLongitude(dfsVoomaMerchantOnboardV1Request.getLongitude());
            dfsVoomaMerchantOnboardV1.setOnboardingStatus(OnboardingStatus.PENDING);
            //save merchant
            DFSVoomaMerchantOnboardV1 merchantDetails1 = dfsVoomaMerchantOnboardV1Repository.save(dfsVoomaMerchantOnboardV1);

            //save Owner details can be multiple
            List<DFSVoomaOwnerDetailsRequest> ownerDetailsRequests = dfsVoomaMerchantOnboardV1Request.getDfsVoomaOwnerDetailsRequests();
            for (DFSVoomaOwnerDetailsRequest dfsVoomaOwnerDetailsRequest : ownerDetailsRequests) {
                DFSVoomaOwnerDetailsEntity dfsVoomaOwnerDetailsEntity = new DFSVoomaOwnerDetailsEntity();
                dfsVoomaOwnerDetailsEntity.setFullName(dfsVoomaOwnerDetailsRequest.getFullName());
                dfsVoomaOwnerDetailsEntity.setIdNumber(dfsVoomaOwnerDetailsRequest.getIdNumber());
                dfsVoomaOwnerDetailsEntity.setPhoneNumber(dfsVoomaOwnerDetailsRequest.getPhoneNumber());
                dfsVoomaOwnerDetailsEntity.setEmailAddress(dfsVoomaOwnerDetailsRequest.getEmailAddress());
                dfsVoomaOwnerDetailsEntity.setIdType(dfsVoomaOwnerDetailsRequest.getIdType());
                dfsVoomaOwnerDetailsEntity.setDfsVoomaMerchantOnboardV1(merchantDetails1);
                dfsVoomaOwnerDetailsRepository.save(dfsVoomaOwnerDetailsEntity);
            }
           List<DFSVoomaContactDetailsRequest> detailsRequestList =dfsVoomaMerchantOnboardV1Request.getDfsVoomaContactDetailsRequests();
            for (DFSVoomaContactDetailsRequest dfsVoomaContactDetailsRequest : detailsRequestList) {
                DFSVoomaContactDetailsEntity dfsVoomaContactDetailsEntity = new DFSVoomaContactDetailsEntity();
                dfsVoomaContactDetailsEntity.setContactPersonName(dfsVoomaContactDetailsRequest.getContactPersonName());
                dfsVoomaContactDetailsEntity.setContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getContactPersonEmailAddress());
                dfsVoomaContactDetailsEntity.setContactPersonIdType(dfsVoomaContactDetailsRequest.getContactPersonIdType());
                dfsVoomaContactDetailsEntity.setContactPersonIdNumber(dfsVoomaContactDetailsRequest.getContactPersonIdNumber());
                //financial details
                dfsVoomaContactDetailsEntity.setFinancialContactPersonIdType(dfsVoomaContactDetailsRequest.getFinancialContactPersonIdType());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonIdNumber(dfsVoomaContactDetailsRequest.getFinancialContactPersonIdNumber());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonName(dfsVoomaContactDetailsRequest.getFinancialContactPersonName());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getFinancialContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getFinancialContactPersonEmailAddress());
                //administrative details
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonIdType(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonIdType());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonIdNumber(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonIdNumber());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonName(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonName());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonEmailAddress());
                dfsVoomaContactDetailsEntity.setDfsVoomaMerchantOnboardV1(merchantDetails1);
                dfsVoomaContactDetailsRepository.save(dfsVoomaContactDetailsEntity);
            }
            //upload documents
            String folderName = "voomaOnboardingMerchant";

            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + merchantDetails1.getId() + ".PNG", frontID,folderName);
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + merchantDetails1.getId() + ".PNG", backID,folderName);
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + merchantDetails1.getId() + ".PNG", kraPinCertificate,folderName);

            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + merchantDetails1.getId() + ".PNG", shopPhoto,folderName);
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "signatureDoc_" + merchantDetails1.getId() + ".PNG", signatureDoc,folderName);
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + merchantDetails1.getId() + ".PNG", businessPermitDoc,folderName);
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
                agentKYC.setDfsVoomaMerchantOnboardV1(merchantDetails1);
                dfsVoomaOnboardingKYRepository.save(agentKYC);
            });
            return true;


        } catch (Exception e) {
            log.error("Error occurred while onboarding merchant", e);
        }
        return false;
    }

    @Override
    public Object onboardNewAgentV1(String agentDetails,
                                    MultipartFile frontID,
                                    MultipartFile backID,
                                    MultipartFile kraPinCertificate,
                                    MultipartFile businessCertificateOfRegistration,
                                    MultipartFile shopPhoto,
                                    MultipartFile[] signatureDoc,
                                    MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            DFSVoomaAgentOnboardV1Request dfsVoomaAgentOnboardV1Request = mapper.readValue(
                    agentDetails, DFSVoomaAgentOnboardV1Request.class);
            if (dfsVoomaAgentOnboardV1Request == null) throw new RuntimeException("Bad request");
            DFSVoomaAgentOnboardV1 dfsVoomaAgentOnboardV1 = new DFSVoomaAgentOnboardV1();
            //organisations details
            dfsVoomaAgentOnboardV1.setOrganisationName(dfsVoomaAgentOnboardV1Request.getOrganisationName());

            //business details
            dfsVoomaAgentOnboardV1.setBusinessCategory(dfsVoomaAgentOnboardV1Request.getBusinessCategory());
            dfsVoomaAgentOnboardV1.setBusinessPhoneNumber(dfsVoomaAgentOnboardV1Request.getBusinessPhoneNumber());
            dfsVoomaAgentOnboardV1.setBusinessEmail(dfsVoomaAgentOnboardV1Request.getBusinessEmail());
            dfsVoomaAgentOnboardV1.setIsKCBAgent(dfsVoomaAgentOnboardV1Request.getIsKCBAgent());
            dfsVoomaAgentOnboardV1.setNumberOfOutlets(dfsVoomaAgentOnboardV1Request.getNumberOfOutlets());
            dfsVoomaAgentOnboardV1.setKRAPin(dfsVoomaAgentOnboardV1Request.getKRAPin());
            dfsVoomaAgentOnboardV1.setVATNumber(dfsVoomaAgentOnboardV1Request.getVATNumber());
            dfsVoomaAgentOnboardV1.setDealingWithForeignExchange(dfsVoomaAgentOnboardV1Request.getDealingWithForeignExchange());
            //settlement details
            dfsVoomaAgentOnboardV1.setBranchName(dfsVoomaAgentOnboardV1Request.getBranchName());
            dfsVoomaAgentOnboardV1.setAccountName(dfsVoomaAgentOnboardV1Request.getAccountName());
            dfsVoomaAgentOnboardV1.setAccountNumber(dfsVoomaAgentOnboardV1Request.getAccountNumber());
            dfsVoomaAgentOnboardV1.setDate(dfsVoomaAgentOnboardV1Request.getDate());
            //physical address
            dfsVoomaAgentOnboardV1.setPostalAddress(dfsVoomaAgentOnboardV1Request.getPostalAddress());
            dfsVoomaAgentOnboardV1.setPostalCode(dfsVoomaAgentOnboardV1Request.getPostalCode());
            dfsVoomaAgentOnboardV1.setCityOrTown(dfsVoomaAgentOnboardV1Request.getCityOrTown());
            dfsVoomaAgentOnboardV1.setNearestLandmark(dfsVoomaAgentOnboardV1Request.getNearestLandmark());
            dfsVoomaAgentOnboardV1.setLatitude(dfsVoomaAgentOnboardV1Request.getLatitude());
            dfsVoomaAgentOnboardV1.setLongitude(dfsVoomaAgentOnboardV1Request.getLongitude());
            dfsVoomaAgentOnboardV1.setOnboardingStatus(OnboardingStatus.PENDING);
            //save merchant
            DFSVoomaAgentOnboardV1 agentOnboardV1 = dfsVoomaAgentOnboardV1Repository.save(dfsVoomaAgentOnboardV1);

            //save Owner details can be multiple
            List<DFSVoomaAgentOwnerDetailsRequest> ownerDetailsRequests = dfsVoomaAgentOnboardV1Request.getDfsVoomaAgentOwnerDetailsRequests();
            for (DFSVoomaAgentOwnerDetailsRequest dfsVoomaAgentOwnerDetailsRequest : ownerDetailsRequests) {
                DFSVoomaAgentOwnerDetailsEntity dfsVoomaAgentOwnerDetailsEntity = new DFSVoomaAgentOwnerDetailsEntity();
                dfsVoomaAgentOwnerDetailsEntity.setFullName(dfsVoomaAgentOwnerDetailsRequest.getFullName());
                dfsVoomaAgentOwnerDetailsEntity.setIdNumber(dfsVoomaAgentOwnerDetailsRequest.getIdNumber());
                dfsVoomaAgentOwnerDetailsEntity.setPhoneNumber(dfsVoomaAgentOwnerDetailsRequest.getPhoneNumber());
                dfsVoomaAgentOwnerDetailsEntity.setEmailAddress(dfsVoomaAgentOwnerDetailsRequest.getEmailAddress());
                dfsVoomaAgentOwnerDetailsEntity.setIdType(dfsVoomaAgentOwnerDetailsRequest.getIdType());
                dfsVoomaAgentOwnerDetailsEntity.setDob(dfsVoomaAgentOwnerDetailsRequest.getDob());
                dfsVoomaAgentOwnerDetailsEntity.setDfsVoomaAgentOnboardV1(agentOnboardV1);
                DFSVoomaAgentOwnerDetailsEntity ownerDetailsEntity=dfsVoomaAgentOwnerDetailsRepository.save(dfsVoomaAgentOwnerDetailsEntity);

            }
            List<DFSVoomaAgentContactDetailsRequest> detailsRequestList =dfsVoomaAgentOnboardV1Request.getDfsVoomaAgentContactDetailsRequests();
            for (DFSVoomaAgentContactDetailsRequest dfsVoomaContactDetailsRequest : detailsRequestList) {
                DFSVoomaAgentContactDetailsEntity dfsVoomaContactDetailsEntity = new DFSVoomaAgentContactDetailsEntity();
                dfsVoomaContactDetailsEntity.setContactPersonName(dfsVoomaContactDetailsRequest.getContactPersonName());
                dfsVoomaContactDetailsEntity.setContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getContactPersonEmailAddress());
                dfsVoomaContactDetailsEntity.setContactPersonIdType(dfsVoomaContactDetailsRequest.getContactPersonIdType());
                dfsVoomaContactDetailsEntity.setContactPersonIdNumber(dfsVoomaContactDetailsRequest.getContactPersonIdNumber());
                //financial details
                dfsVoomaContactDetailsEntity.setFinancialContactPersonIdType(dfsVoomaContactDetailsRequest.getFinancialContactPersonIdType());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonIdNumber(dfsVoomaContactDetailsRequest.getFinancialContactPersonIdNumber());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonName(dfsVoomaContactDetailsRequest.getFinancialContactPersonName());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getFinancialContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setFinancialContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getFinancialContactPersonEmailAddress());
                //administrative details
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonIdType(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonIdType());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonIdNumber(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonIdNumber());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonName(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonName());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonPhoneNumber(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonPhoneNumber());
                dfsVoomaContactDetailsEntity.setAdministrativeContactPersonEmailAddress(dfsVoomaContactDetailsRequest.getAdministrativeContactPersonEmailAddress());
                dfsVoomaContactDetailsEntity.setDfsVoomaAgentOnboardV1(agentOnboardV1);
                dfsVoomaAgentContactDetailsRepository.save(dfsVoomaContactDetailsEntity);
            }

            String folderName = "voomaAgentOnboarding";
            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + agentOnboardV1.getId() + ".PNG", frontID,folderName);
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + agentOnboardV1.getId() + ".PNG", backID,folderName);
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + agentOnboardV1.getId() + ".PNG", kraPinCertificate,folderName);
            String businessCertificateOfRegistrationPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessCertificateOfRegistration_" + agentOnboardV1.getId() + ".PNG", businessCertificateOfRegistration,folderName);
            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + agentOnboardV1.getId() + ".PNG", shopPhoto,folderName);
//            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "signatureDoc_" + agentOnboardV1.getId() + ".PNG", signatureDoc,folderName);
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + agentOnboardV1.getId() + ".PNG", businessPermitDoc,folderName);
            //save file paths to db
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(businessCertificateOfRegistrationPath);
            filePathList.add(shopPhotoPath);
//            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            filePathList.forEach(filePath -> {
                DFSVoomaAgentOnboardingKYCEntity agentKYC = new DFSVoomaAgentOnboardingKYCEntity();
                agentKYC.setFilePath(filePath);
                agentKYC.setDfsVoomaAgentOnboardV1(agentOnboardV1);
                dfsVoomaAgentOnboardingKYCRepository.save(agentKYC);
            });
            List<String> filePathList1 = new ArrayList<>();
            //save files

            filePathList1 = fileStorageService.saveMultipleFileWithSpecificFileName("Signature_"+agentOnboardV1.getId(), signatureDoc);
            //save file paths to db
            filePathList1.forEach(filePath -> {
                DFSVoomaAgentOnboardingKYCEntity signatureFiles = new DFSVoomaAgentOnboardingKYCEntity();
                signatureFiles.setDfsVoomaAgentOnboardV1(agentOnboardV1);
                signatureFiles.setFilePath(filePath);
                dfsVoomaAgentOnboardingKYCRepository.save(signatureFiles);
            });;
            return true;
        } catch (Exception e) {
            log.error("Error occurred while Onboarding Agent", e);
        }
        return false;
    }

}