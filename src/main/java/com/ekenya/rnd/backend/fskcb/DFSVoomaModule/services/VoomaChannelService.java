package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetFilesEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetFileRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetRepository;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBJustificationEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBLeadEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBJustificationRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.fskcb.exception.ResourceNotFoundException;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class VoomaChannelService implements IVoomaChannelService {

    private final DFSVoomaCustomerVisitRepository dfsVoomaCustomerVisitRepository;
    private  final QuestionRepository questionRepository;
    private  final QuestionnaireRepository questionnaireRepository;
    private  final QuestionResponseRepository questionResponseRepository;
    private final DFSVoomaMerchantOnboardV1Repository dfsVoomaMerchantOnboardV1Repository;
    private final DFSVoomaAssetFilesRepository dfsVoomaAssetFilesRepository;
    private final DFSVoomaAgentOnboardV1Repository dfsVoomaAgentOnboardV1Repository;
    private final DFSVoomaAgentOwnerDetailsRepository dfsVoomaAgentOwnerDetailsRepository;
    private final DFSVoomaAgentContactDetailsRepository dfsVoomaAgentContactDetailsRepository;
    private final DFSVoomaOwnerDetailsRepository dfsVoomaOwnerDetailsRepository;
    private final DFSVoomaContactDetailsRepository dfsVoomaContactDetailsRepository;
    private final DFSVoomaLeadRepository dfsVoomaLeadRepository;
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

//    @Override
//    public Object onboardNewMerchant(String merchDetails, MultipartFile frontID, MultipartFile backID, MultipartFile kraPinCertificate,   MultipartFile shopPhoto,   MultipartFile signatureDoc, MultipartFile businessPermitDoc) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            DFSVoomaOnboardRequest onboardMerchantRequest = mapper.readValue(
//                    merchDetails, DFSVoomaOnboardRequest.class);
//            if (onboardMerchantRequest == null) throw new RuntimeException("Bad request");
//            DFSVoomaOnboardEntity dfsVoomaOnboardEntity = new DFSVoomaOnboardEntity();
//            //merchant type
//            dfsVoomaOnboardEntity.setMerchantType(onboardMerchantRequest.getMerchantType());
//            //company profile
//            dfsVoomaOnboardEntity.setBusinessType(onboardMerchantRequest.getBusinessType());
//            dfsVoomaOnboardEntity.setTradingName(onboardMerchantRequest.getTradingName());
//            dfsVoomaOnboardEntity.setNatureOfTheBusiness(onboardMerchantRequest.getNatureOfTheBusiness());
//            //owner details
//            dfsVoomaOnboardEntity.setMerchantName(onboardMerchantRequest.getMerchantName());
//            dfsVoomaOnboardEntity.setMerchantEmail(onboardMerchantRequest.getMerchantEmail());
//            dfsVoomaOnboardEntity.setMerchantPhone(onboardMerchantRequest.getMerchantPhone());
//            dfsVoomaOnboardEntity.setMerchantIdNumber(onboardMerchantRequest.getMerchantIdNumber());
//            dfsVoomaOnboardEntity.setKRApin(onboardMerchantRequest.getKRApin());
//            //business details
//            dfsVoomaOnboardEntity.setBusinessName(onboardMerchantRequest.getBusinessName());
//            dfsVoomaOnboardEntity.setBusinessEmail(onboardMerchantRequest.getBusinessEmail());
//            dfsVoomaOnboardEntity.setOutletPhoneNo(onboardMerchantRequest.getOutletPhoneNo());
//            dfsVoomaOnboardEntity.setBusinessKRAPin(onboardMerchantRequest.getBusinessKRAPin());
//            dfsVoomaOnboardEntity.setWantVoomaPaybillNumber(onboardMerchantRequest.isWantVoomaPaybillNumber());
//            dfsVoomaOnboardEntity.setWantVoomaTillNumber(onboardMerchantRequest.isWantVoomaTillNumber());
//            dfsVoomaOnboardEntity.setExchangeForeign(onboardMerchantRequest.isExchangeForeign());
//            //next of kin
//            dfsVoomaOnboardEntity.setNextOfKinFullName(onboardMerchantRequest.getNextOfKinFullName());
//            dfsVoomaOnboardEntity.setNextOfKinPhoneNumber(onboardMerchantRequest.getNextOfKinPhoneNumber());
//            dfsVoomaOnboardEntity.setNextOfKinIdNumber(onboardMerchantRequest.getNextOfKinIdNumber());
//            //physical address
//            dfsVoomaOnboardEntity.setMerchantPbox(onboardMerchantRequest.getMerchantPbox());
//            dfsVoomaOnboardEntity.setMerchantPostalCode(onboardMerchantRequest.getMerchantPostalCode());
//            dfsVoomaOnboardEntity.setCounty(onboardMerchantRequest.getCounty());
//            dfsVoomaOnboardEntity.setCity(onboardMerchantRequest.getCity());
//            dfsVoomaOnboardEntity.setStreetName(onboardMerchantRequest.getStreetName());
//            dfsVoomaOnboardEntity.setNearbyLandMark(onboardMerchantRequest.getNearbyLandMark());
//            dfsVoomaOnboardEntity.setLongitude(onboardMerchantRequest.getLongitude());
//            dfsVoomaOnboardEntity.setLatitude(onboardMerchantRequest.getLatitude());
//            dfsVoomaOnboardEntity.setStatus(OnboardingStatus.PENDING);
//            //save merchant details
//            DFSVoomaOnboardEntity merchDtls = dfsVoomaOnboardRepository.save(dfsVoomaOnboardEntity);
//            //subdirectory name generateSubDirectory
//            String folderName = "voomaOnboardingMerchant";
//
//            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "frontID_" + merchDtls.getId() + ".PNG", frontID,folderName);
//            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "backID_" + merchDtls.getId() + ".PNG", backID,folderName);
//            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "kraPinCertificate_" + merchDtls.getId() + ".PNG", kraPinCertificate,folderName);
//
//            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "shopPhoto_" + merchDtls.getId() + ".PNG", shopPhoto,folderName);
//            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "signatureDoc_" + merchDtls.getId() + ".PNG", signatureDoc,folderName);
//            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
//                    "businessPermitDoc_" + merchDtls.getId() + ".PNG", businessPermitDoc,folderName);
//            //save file paths to db
//            ArrayList<String> filePathList = new ArrayList<>();
//            filePathList.add(frontIDPath);
//            filePathList.add(backIDPath);
//            filePathList.add(kraPinCertificatePath);
//            filePathList.add(shopPhotoPath);
//            filePathList.add(signatureDocPath);
//            filePathList.add(businessPermitDocPath);
//            filePathList.forEach(filePath -> {
//                DFSVoomaOnboardingKYCentity agentKYC = new DFSVoomaOnboardingKYCentity();
//                agentKYC.setFilePath(filePath);
//                agentKYC.setDfsVoomaOnboardEntity(merchDtls);
//                dfsVoomaOnboardingKYRepository.save(agentKYC);
//            });
//            return true;
//
//
//        } catch (Exception e) {
//            log.error("Error occurred while onboarding merchant", e);
//        }
//        return false;
//    }

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
            //upload files to directory and create download url based on environment and save to db
            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + agentData.getId() + ".PNG", frontID, folderName);
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + agentData.getId() + ".PNG", backID, folderName);
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + agentData.getId() + ".PNG", kraPinCertificate, folderName);
            String businessCertificateOfRegistrationPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessCertificateOfRegistration_" + agentData.getId() + ".PNG", businessCertificateOfRegistration, folderName);
            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + agentData.getId() + ".PNG", shopPhoto, folderName);
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "signatureDoc_" + agentData.getId() + ".PNG", signatureDoc, folderName);
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + agentData.getId() + ".PNG", businessPermitDoc, folderName);
            //convert path to download by ServletUriComponentsBuilder and save to db
            List<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(businessCertificateOfRegistrationPath);
            filePathList.add(shopPhotoPath);
            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/downloadFile/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db

            }

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
            if (model == null) {
                return false;
            }
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber((model.getSerialNumber())).orElse(null);
            if ( dfsVoomaAssetEntity == null) {
                return false;
            }
            dfsVoomaAssetEntity.setMerchantAccNo(model.getAccountNumber());
            dfsVoomaAssetEntity.setDateAssigned(Utility.getPostgresCurrentTimeStampForInsert());
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
            if (model == null) {
                return false;
            }
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber((model.getSerialNumber())).orElse(null);
            if (  dfsVoomaAssetEntity == null) {
                return false;
            }
            dfsVoomaAssetEntity.setAgentAccNumber(model.getAccountNumber());
            dfsVoomaAssetEntity.setDateAssigned(Utility.getPostgresCurrentTimeStampForInsert());
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
            if (model == null) {
                return null;
            }
            //get all assets for merchant
            List<DFSVoomaAssetEntity> dfsVoomaAssetEntityList = dfsVoomaAssetRepository.findByMerchantAccNo(model.getAccNo());
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            dfsVoomaAssetEntityList.forEach(dfsVoomaAssetEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("assetId", dfsVoomaAssetEntity.getId());
                objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
                objectNode.put("assetNumber",dfsVoomaAssetEntity.getAssetNumber());
                objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().toString());
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
            if (model == null) {
                return false;
            }
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber(model.getSerialNumber()).orElse(null);
            if (dfsVoomaAssetEntity == null) {
                return false;
            }
            dfsVoomaAssetEntity.setDfsVoomaAgentOnboardingEntity(null);
            dfsVoomaAssetEntity.setMerchantAccNo(null);
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
            if (model == null) {
                return false;
            }
            DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = new DFSVoomaCustomerVisitEntity();
            dfsVoomaCustomerVisitEntity.setReasonForVisit(model.getReasonForVisit());
            dfsVoomaCustomerVisitEntity.setActionPlan(model.getActionPlan());
            dfsVoomaCustomerVisitEntity.setHighlights(model.getHighlights());
//            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            String username = userDetails.getUsername();
//            dfsVoomaCustomerVisitEntity.setDsrName(username);
            dfsVoomaCustomerVisitEntity.setAttendance(model.getAttendance());
            dfsVoomaCustomerVisitEntity.setVisitType(model.getVisitType());
            dfsVoomaCustomerVisitEntity.setEntityBrief(model.getEntityBrief());
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
            if (model == null) {
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
                objectNode.put("customerName", dfsVoomaCustomerVisitEntity.getCustomerName());
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
            dfsVoomaMerchantOnboardV1.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());

//            UserDetails userDetails=(UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
//            String username=userDetails.getUsername();
//            dfsVoomaMerchantOnboardV1.setDsrName(username);

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
                dfsVoomaOwnerDetailsEntity.setMerchantId(merchantDetails1.getId());
                dfsVoomaOwnerDetailsRepository.save(dfsVoomaOwnerDetailsEntity);
            }
            List<DFSVoomaContactDetailsRequest> detailsRequestList = dfsVoomaMerchantOnboardV1Request.getDfsVoomaContactDetailsRequests();
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
                dfsVoomaContactDetailsEntity.setMerchantId(merchantDetails1.getId());
                dfsVoomaContactDetailsRepository.save(dfsVoomaContactDetailsEntity);
            }
            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_frontID_" + merchantDetails1.getId() + ".PNG", frontID, Utility.getSubFolder());
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_backID_" + merchantDetails1.getId() + ".PNG", backID, Utility.getSubFolder());
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_kraPinCertificate_" + merchantDetails1.getId() + ".PNG", kraPinCertificate, Utility.getSubFolder());

            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_shopPhoto_" + merchantDetails1.getId() + ".PNG", shopPhoto, Utility.getSubFolder());
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_signatureDoc_" + merchantDetails1.getId() + ".PNG", signatureDoc, Utility.getSubFolder());
            //save download url from file storage service.loadFileAsResource
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "vooma_merchant_businessPermitDoc_" + merchantDetails1.getId() + ".PNG", businessPermitDoc, Utility.getSubFolder());
            //save
            List<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(shopPhotoPath);
            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/" + Utility.getSubFolder() + "/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                DFSVoomaOnboardingKYCentity dfsVoomaMerchantDocumentsEntity = new DFSVoomaOnboardingKYCentity();
                dfsVoomaMerchantDocumentsEntity.setFilePath(downloadUrl);
                dfsVoomaMerchantDocumentsEntity.setDfsVoomaMerchantOnboardV1(merchantDetails1);
                dfsVoomaMerchantDocumentsEntity.setFileName(filePath);
                dfsVoomaMerchantDocumentsEntity.setMerchantId(merchantDetails1.getId());
                dfsVoomaOnboardingKYRepository.save(dfsVoomaMerchantDocumentsEntity);

            }
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
//            dfsVoomaAgentOnboardV1.setKRAPin(dfsVoomaAgentOnboardV1Request.getKRAPin());
            dfsVoomaAgentOnboardV1.setVATNumber(dfsVoomaAgentOnboardV1Request.getVATNumber());
            dfsVoomaAgentOnboardV1.setDealingWithForeignExchange(dfsVoomaAgentOnboardV1Request.getDealingWithForeignExchange());
            //settlement details
            dfsVoomaAgentOnboardV1.setBranchName(dfsVoomaAgentOnboardV1Request.getBranchName());
            dfsVoomaAgentOnboardV1.setAccountName(dfsVoomaAgentOnboardV1Request.getAccountName());
            dfsVoomaAgentOnboardV1.setAccountNumber(dfsVoomaAgentOnboardV1Request.getAccountNumber());
            dfsVoomaAgentOnboardV1.setDate(dfsVoomaAgentOnboardV1Request.getDate());
            dfsVoomaAgentOnboardV1.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
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
                DFSVoomaAgentOwnerDetailsEntity ownerDetailsEntity = dfsVoomaAgentOwnerDetailsRepository.save(dfsVoomaAgentOwnerDetailsEntity);

            }
            List<DFSVoomaAgentContactDetailsRequest> detailsRequestList = dfsVoomaAgentOnboardV1Request.getDfsVoomaAgentContactDetailsRequests();
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

            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "frontID_" + agentOnboardV1.getId() + ".PNG", frontID, Utility.getSubFolder());
            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "backID_" + agentOnboardV1.getId() + ".PNG", backID, Utility.getSubFolder());
            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "kraPinCertificate_" + agentOnboardV1.getId() + ".PNG", kraPinCertificate, Utility.getSubFolder());
            String businessCertificateOfRegistrationPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessCertificateOfRegistration_" + agentOnboardV1.getId() + ".PNG", businessCertificateOfRegistration, Utility.getSubFolder());
            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "shopPhoto_" + agentOnboardV1.getId() + ".PNG", shopPhoto, Utility.getSubFolder());
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "businessPermitDoc_" + agentOnboardV1.getId() + ".PNG", businessPermitDoc, Utility.getSubFolder());
            //save file paths to db
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(businessCertificateOfRegistrationPath);
            filePathList.add(shopPhotoPath);
//            filePathList.add(signatureDocPath);
            filePathList.add(businessPermitDocPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/" + Utility.getSubFolder() + "/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                DFSVoomaAgentOnboardingKYCEntity dfsVoomaAgentOnboardingKYCEntity = new DFSVoomaAgentOnboardingKYCEntity();
                dfsVoomaAgentOnboardingKYCEntity.setFilePath(downloadUrl);
                dfsVoomaAgentOnboardingKYCEntity.setDfsVoomaAgentOnboardV1(agentOnboardV1);
                dfsVoomaAgentOnboardingKYCEntity.setFileName(filePath);
                dfsVoomaAgentOnboardingKYCRepository.save(dfsVoomaAgentOnboardingKYCEntity);

            }
            return true;
        } catch (Exception e) {
            log.error("Error occurred while Onboarding Agent", e);
        }
        return false;
    }

    @Override
    public ObjectNode getCustomerDetails(CustomerRequest model) {
        try {
            if (model == null) {
                return null;
            }
            if (model.getAccountNumber().equals("KCB001")) {
                ObjectNode addProperty = JsonNodeFactory.instance.objectNode();
                addProperty.put("accountNumber", "KCB001");
                addProperty.put("accountNumber", "142876543266");
                addProperty.put("accountName", "Obadia Lusengeri");
                addProperty.put("bankName", "Kenya Commercial Bank");
                addProperty.put("wantTillNumber", "true");
                addProperty.put("wantPaybillNumber", "true");
                addProperty.put("dealingWithForeignExchange", "false");
                addProperty.put("customerName", "Obadia Lusengeri");
                addProperty.put("customerPhoneNumber", "0712345678");
                addProperty.put("customerEmailAddress", "obadia@gmail.com");
                addProperty.put("customerIDNumber", "35189713");
                addProperty.put("customerIDType", "ID");
                addProperty.put("customerAddress", "Nairobi");
                addProperty.put("customerDOB", "1990-01-01");
                addProperty.put("customerGender", "Male");
                addProperty.put("customerMaritalStatus", "Single");
                addProperty.put("customerOccupation", "Developer");
                addProperty.put("customerEmployer", "Ecclectics Int");
                addProperty.put("customerEmployerAddress", "Nairobi");
                addProperty.put("customerEmployerPhoneNumber", "0712345678");
                addProperty.put("customerEmployerEmailAddress", "info@eclectics.io");
                addProperty.put("customerEmployerContactPerson", "Jane Mwangi");
                addProperty.put("customerEmployerContactPersonPhoneNumber", "0712345678");
                addProperty.put("customerEmployerContactPersonEmailAddress", "mwagi.jane@eclectics.io");
                //customer next of kin details
                addProperty.put("customerNextOfKinName", "Patricia Inzagi");
                addProperty.put("customerNextOfKinPhoneNumber", "0712345678");
                addProperty.put("customerNextOfKinEmailAddress", "p.inzagi@gmail.com");
                addProperty.put("VATNumber", "765433");
                addProperty.put("postalCode", "001001");
                addProperty.put("city", "Nairobi");
                addProperty.put("nearestLandMark", "Serena Hotel");
                //paymentDetails
                addProperty.put("settlmentType", "OnDemand");
                addProperty.put("merchantType", "GENERAL");
                addProperty.put("dateRegistered", "15-12-2022");
                //if not matched return empty object
                return addProperty;


            }
        } catch (Exception e) {
            log.error("Error occurred while getting customer details", e);
        }
        return JsonNodeFactory.instance.objectNode();

    }

    @Override
    public boolean attemptCreateLead(TreasuryAddLeadRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DFSVoomaLeadEntity dfsVoomaLeadEntity = new DFSVoomaLeadEntity();
            dfsVoomaLeadEntity.setCustomerName(model.getCustomerName());
            dfsVoomaLeadEntity.setBusinessUnit(model.getBusinessUnit());
            dfsVoomaLeadEntity.setEmail(model.getEmail());
            dfsVoomaLeadEntity.setPhoneNumber(model.getPhoneNumber());
            dfsVoomaLeadEntity.setProduct(model.getProduct());
            dfsVoomaLeadEntity.setPriority(model.getPriority());
            dfsVoomaLeadEntity.setDsrId(model.getDsrId());
            dfsVoomaLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            dfsVoomaLeadEntity.setTopic(model.getTopic());
            dfsVoomaLeadEntity.setLeadStatus(LeadStatus.OPEN);
            dfsVoomaLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaLeadRepository.findAllByDsrIdAndAssigned(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", dfsVoomaLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", dfsVoomaLeadEntity.getPriority().toString());
                node.put("businessUnit", dfsVoomaLeadEntity.getBusinessUnit());
                node.put("leadId", dfsVoomaLeadEntity.getId());
                node.put("leadStatus", dfsVoomaLeadEntity.getLeadStatus().toString());
                node.put("createdOn", dfsVoomaLeadEntity.getCreatedOn().getTime());
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
            if (model == null) {
                return null;
            }
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (DFSVoomaLeadEntity dfsVoomaLeadEntity : dfsVoomaLeadRepository.findAllAssignedLeadByDSRId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", dfsVoomaLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", dfsVoomaLeadEntity.getPriority().toString());
                node.put("businessUnit", dfsVoomaLeadEntity.getBusinessUnit());
                node.put("leadId", dfsVoomaLeadEntity.getId());
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
            if (model == null) {
                return false;
            }
            DFSVoomaLeadEntity dfsVoomaLeadEntity = dfsVoomaLeadRepository.findById(model.getLeadId()).orElse(null);
            dfsVoomaLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            dfsVoomaLeadEntity.setLeadStatus(model.getLeadStatus());
            dfsVoomaLeadRepository.save(dfsVoomaLeadEntity);
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
            short commission = 0;
            short targetAchieved = 0;
            objectNode.put("commission", commission);
            //get total number of dsr visits by dsr id
            int totalVisits = dfsVoomaCustomerVisitRepository.countTotalVisits(model.getDsrId());
            objectNode.put("customer-visits", totalVisits);
            //if null hard code visits for now
            if (totalVisits == 0) {
                objectNode.put("customer-visits", 0);
            }
            //get total number of dsr assigned leads by dsr id
            int totalAssignedLeads = dfsVoomaLeadRepository.countTotalAssignedLeads(model.getDsrId());
            objectNode.put("assigned-leads", totalAssignedLeads);
            //if null hard code assigned leads for now
            if (totalAssignedLeads == 0) {
                objectNode.put("assigned-leads", 0);
            }
//    //get total number of dsr targets achieved by dsr
            objectNode.put("targetAchieved", targetAchieved);
            arrayNode.add(objectNode);
            return arrayNode;
        } catch (Exception e) {
            log.error("Error occurred while getting dsr summary", e);
        }
        return null;
    }

    @Override
    public Object getAssetById(AssetByIdRequest model) {
        try {
            if (model.getAssetId() == null) {
                log.error("Asset id is null");
                return null;
            }
            //get merchant by id
            DFSVoomaAssetEntity acquiringOnboardEntity = dfsVoomaAssetRepository.findById(model.getAssetId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("condition", acquiringOnboardEntity.getAssetCondition().toString());
            asset.put("serialNo", acquiringOnboardEntity.getSerialNumber());
            asset.put("dateIssued", acquiringOnboardEntity.getDateAssigned().getTime());
            asset.put("dsrId", acquiringOnboardEntity.getDsrId());
            asset.put("terminalId", acquiringOnboardEntity.getTerminalId());
            asset.put("assetNumber", acquiringOnboardEntity.getAssetNumber());
            asset.put("visitDate", acquiringOnboardEntity.getVisitDate());
            asset.put("totalTransaction", 0);
            asset.put("location", acquiringOnboardEntity.getLocation());
            asset.put("merchantName", acquiringOnboardEntity.getMerchantName());
            asset.put("status", acquiringOnboardEntity.getStatus().toString());
            List<DFSVoomaAssetFilesEntity> dfsVoomaFileUploadEntities = dfsVoomaAssetFilesRepository.findByIdAsset(model.getAssetId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (DFSVoomaAssetFilesEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                String[] fileName = dfsVoomaFileUploadEntity.getFileName().split("/");
                fileUpload.put("fileName", fileName[fileName.length - 1]);
                fileUploads.add(fileUpload);
            }
            asset.put("fileUploads", fileUploads);
            return asset;
        } catch (Exception e) {
            log.error("Error occurred while fetching merchant by id", e);
        }
        return null;
    }

//    @Override
//    public List<ObjectNode> getAllAgentAsset(CustomerAssetsRequest model) {
//        try {
//            if (model == null) {
//                return null;
//            }
//            //get all assets for merchant
//            List<DFSVoomaAssetEntity> dfsVoomaAssetEntityList = dfsVoomaAssetRepository.findByAgentAccNo(model.getAccNo());
//            List<ObjectNode> objectNodeList = new ArrayList<>();
//            ObjectMapper objectMapper = new ObjectMapper();
//            dfsVoomaAssetEntityList.forEach(dfsVoomaAssetEntity -> {
//                ObjectNode objectNode = objectMapper.createObjectNode();
//                objectNode.put("assetId", dfsVoomaAssetEntity.getId());
//                objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
//                objectNode.put("assetNumber",dfsVoomaAssetEntity.getAssetNumber());
//                objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().toString());
//                objectNode.put("totalTransaction", totalTransaction);
//                objectNodeList.add(objectNode);
//            });
//
//            return objectNodeList;
//        } catch (Exception e) {
//            log.error("Error occurred while getting all agent merchant assets", e);
//        }
//        return null;
//    }

    @Override
    public boolean recollectAgentAsset(VoomaCollectAssetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            DFSVoomaAssetEntity dfsVoomaAssetEntity = (DFSVoomaAssetEntity) dfsVoomaAssetRepository.findBySerialNumber(model.getSerialNumber()).orElse(null);
            if (dfsVoomaAssetEntity == null) {
                return false;
            }
            dfsVoomaAssetEntity.setDfsVoomaAgentOnboardingEntity(null);
            dfsVoomaAssetEntity.setAgentAccNumber(null);
//            dfsVoomaAssetEntity.setDfsVoomaOnboardEntity(null);
            dfsVoomaAssetEntity.setAssigned(false);
            dfsVoomaAssetRepository.save(dfsVoomaAssetEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while recollecting asset", e);
        }
        return false;
    }

    @Override
    public Object getAgentAssetById(AssetByIdRequest model) {
        try {
            if (model.getAssetId() == null) {
                log.error("Asset id is null");
                return null;
            }
            //get merchant by id
            DFSVoomaAssetEntity acquiringOnboardEntity = dfsVoomaAssetRepository.findById(model.getAssetId()).get();
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode asset = mapper.createObjectNode();
            asset.put("id", acquiringOnboardEntity.getId());
            asset.put("condition", acquiringOnboardEntity.getAssetCondition().toString());
            asset.put("serialNo", acquiringOnboardEntity.getSerialNumber());
            asset.put("dateIssued", acquiringOnboardEntity.getDateAssigned().getTime());
            asset.put("dsrId", acquiringOnboardEntity.getDsrId());
            asset.put("terminalId", acquiringOnboardEntity.getTerminalId());
            asset.put("assetNumber", acquiringOnboardEntity.getAssetNumber());
            asset.put("visitDate", acquiringOnboardEntity.getVisitDate());
            asset.put("totalTransaction", 0);
            asset.put("location", acquiringOnboardEntity.getLocation());
            asset.put("AgentName", acquiringOnboardEntity.getMerchantName());
            asset.put("status", acquiringOnboardEntity.getStatus().toString());
            List<DFSVoomaAssetFilesEntity> dfsVoomaFileUploadEntities = dfsVoomaAssetFilesRepository.findByIdAsset(model.getAssetId());
            ArrayNode fileUploads = mapper.createArrayNode();
            for (DFSVoomaAssetFilesEntity dfsVoomaFileUploadEntity : dfsVoomaFileUploadEntities) {
                ObjectNode fileUpload = mapper.createObjectNode();
                String[] fileName = dfsVoomaFileUploadEntity.getFileName().split("/");
                fileUpload.put("fileName", fileName[fileName.length - 1]);
                fileUploads.add(fileUpload);
            }
            asset.put("fileUploads", fileUploads);
            return asset;
        } catch (Exception e) {
            log.error("Error occurred while fetching merchant by id", e);
        }
        return null;
    }

    @Override
    public boolean updateMerchant(VoomaAddAssetReportRequest model) {
        try {
            if (model == null){
                return false;
            }
            DFSVoomaAssetEntity acquiringOnboardEntity = dfsVoomaAssetRepository.findById(model.getAssetId()).get();
            if (acquiringOnboardEntity == null){
                return false;
            }
            acquiringOnboardEntity.setAssetCondition(model.getAssetCondition());
            acquiringOnboardEntity.setRemarks(model.getRemarks());
            //save
            dfsVoomaAssetRepository.save(acquiringOnboardEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while updating merchant", e);
        }
        return false;
    }

    @Override
    public boolean createCustomerResponses(QuestionResponseRequest model) {
        try {
            if (model == null){
                return false;
            }

            List<QuestionsAndResponses> questionRequestList = model.getQuestionsAndResponses();
            for (QuestionsAndResponses questionRequest : questionRequestList) {

                DFSVOOMAQuestionerResponseEntity questionerResponseEntity = new DFSVOOMAQuestionerResponseEntity();
                questionerResponseEntity.setResponse(questionRequest.getResponse());
                questionerResponseEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
                questionerResponseEntity.setQuestionId(questionRequest.getQuestionId());
                QuestionEntity questionEntity =questionRepository.findById(questionRequest.getQuestionId()).get();
                questionerResponseEntity.setQuestion(questionEntity.getQuestion());
                questionerResponseEntity.setCustomerName(model.getCustomerName());
                questionerResponseEntity.setAccountNo(model.getAccountNo());
                questionerResponseEntity.setNationalId(model.getNationalId());
                questionerResponseEntity.setComment(questionRequest.getComment());
                questionerResponseEntity.setQuestionnaireId(model.getQuestionnaireId());
                questionResponseRepository.save(questionerResponseEntity);
            }


            return  true;


        } catch (Exception e) {
            log.error("something went wrong,please try again later");
        }

        return false;
    }

    @Override
    public List<ObjectNode> getAllQuestion(GetQuestionRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (QuestionnaireEntity questionnaireEntity : questionnaireRepository.findByQuestionnaireTypeAndProfileCodeAndStatus(model.getQuestionnaireType(),model.getProfileCode(), Status.ACTIVE)) {
                ObjectNode objectNode = mapper.createObjectNode();
                objectNode.put("questionnaireTitle", questionnaireEntity.getQuestionnaireTitle());
                objectNode.put("createdOn", questionnaireEntity.getCreatedOn().getTime());

                List<QuestionEntity> questionEntityList = questionnaireEntity.getQuestionEntitySet();
                ArrayNode arrayNode = mapper.createArrayNode();
                for (QuestionEntity questionEntity : questionEntityList) {
                    ObjectNode node = mapper.createObjectNode();
                    node.put("id",questionEntity.getId());
                    node.put("question", questionEntity.getQuestion());
                    node.put("questionType", questionEntity.getQuestionType());
                    arrayNode.add(node);
                }
                objectNode.put("questions", arrayNode);
                list.add(objectNode);

            }
            return list;
        } catch (Exception e) {
            log.error("Error occurred while getting all assets", e);
        }

        return null;
    }


}
