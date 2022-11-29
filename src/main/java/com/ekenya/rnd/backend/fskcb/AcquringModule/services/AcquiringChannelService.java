package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquringSummaryRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringNearbyCustomersRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringOnboardRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.AcquiringPrincipalInfoRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.resp.AcquiringCustomerLookupResponse;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CrmAdapter.ICRMService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaOnboardEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DFSVoomaOnboardRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRRegionsRepository;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.geolatte.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ekenya.rnd.backend.utils.Utility.isPointInPolygon;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcquiringChannelService implements IAcquiringChannelService {
    private final AcquiringOnboardingKYCRepository acquiringOnboardingKYCRepository;
    private final IAcquiringOnboardingsRepository acquiringOnboardingsRepository;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final FileStorageService fileStorageService;
    private final IAcquiringTargetsRepository acquiringTargetsRepository;
    private final IDSRRegionsRepository dsrRegionsRepository;

    private final IAcquiringLeadsRepository acquiringLeadsRepository;
    private final AcquiringPrincipalInfoRepository acquiringPrincipalInfoRepository;
    private final AcquiringCustomerVisitRepository acquiringCustomerVisitRepository;

    private final int totalTransactions = Utility.generateRandomNumber(1000, 100000);


    @Override
    public JsonObject findCustomerByAccNo(String accNo) {
        return null;
    }


    @Override
    public List<ObjectNode> getAllOnboardings() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.findAll()) {

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id", acquiringOnboardEntity.getId());
                asset.put("businessName", acquiringOnboardEntity.getBusinessName());
                asset.put("clientName", acquiringOnboardEntity.getClientLegalName());
                asset.put("businessEmail", acquiringOnboardEntity.getBusinessEmail());
                asset.put("businessPhone", acquiringOnboardEntity.getBusinessPhoneNumber());
                asset.put("outletContactPerson", acquiringOnboardEntity.getOutletContactPerson());
                asset.put("outletPhoneNumber", acquiringOnboardEntity.getOutletPhone());
                asset.put("numberOfOutlets", acquiringOnboardEntity.getNumberOfOutlet());
                asset.put("typeOfGoodsAndServices", acquiringOnboardEntity.getTypeOfGoodAndServices());
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
            acquiringLeadsRepository.save(acquiringLeadEntity);

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
                asset.put("businessName", acquiringOnboardEntity.getBusinessName());
                asset.put("clientName", acquiringOnboardEntity.getClientLegalName());
                asset.put("businessEmail", acquiringOnboardEntity.getBusinessEmail());
                asset.put("businessPhone", acquiringOnboardEntity.getBusinessPhoneNumber());
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
//        try {
//            double longitude = model.getLongitude();
//           double latitude = model.getLatitude();
//            Coordinate coordinate = new Coordinate(longitude, latitude);
//            List<ObjectNode> list = new ArrayList<>();
//             ObjectMapper mapper = new ObjectMapper();
//             for(DSRRegionEntity acquiringDSRRegionEntity : dsrRegionsRepository.findAll()){
//                 ObjectNode asset = mapper.createObjectNode();
//                 asset.put("id", acquiringDSRRegionEntity.getId());
//                 asset.put("name", acquiringDSRRegionEntity.getName());
//                    asset.put("bounds", acquiringDSRRegionEntity.getGeoJsonBounds());
//                    //convert bounds to  coordinate
//                    GeometryFactory geometryFactory = new GeometryFactory();
//                    WKTReader reader = new WKTReader(geometryFactory);
//                    Geometry geometry = reader.read(acquiringDSRRegionEntity.getGeoJsonBounds());
//                 System.out.println("bounds: "+acquiringDSRRegionEntity.getGeoJsonBounds());
//                 list.add(asset);
//                 //check if login user latitute and longitude is inside any region bounds
//                 if(isPointInPolygon(geometry.getCoordinate(),coordinate)){
//                     System.out.println("inside a region");
//                     //get all merchant in that region within 5km radius and get region name
//                        for(AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.getNearbyCustomers(coordinate,acquiringDSRRegionEntity.getName())) {
//                            ObjectNode asset1 = mapper.createObjectNode();
//                            asset1.put("id", acquiringOnboardEntity.getId());
//                            asset1.put("businessType", acquiringOnboardEntity.getBusinessType());
//                            asset1.put("merchantName", acquiringOnboardEntity.getMerchantName());
//                            asset1.put("merchantEmail", acquiringOnboardEntity.getMerchantEmail());
//                            asset1.put("merchantPhone", acquiringOnboardEntity.getMerchantPhone());
//                            asset1.put("merchantIdNumber", acquiringOnboardEntity.getMerchantIdNumber());
//                            asset1.put("businessName", acquiringOnboardEntity.getBusinessName());
//                            asset1.put("businessEmail", acquiringOnboardEntity.getBusinessEmail());
//                            asset1.put("region", acquiringDSRRegionEntity.getName());
//                            list.add(asset1);
//                        }
//                        return list;
//                 }
//                 return null;
//             }
//        } catch (Exception e) {
//            log.error("Error occurred while getting nearby customers", e);
//        }

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

            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", acquiringTargetEntity.getTargetAchievement());
                node.put("target", acquiringTargetEntity.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", acquiringTargetEntity.getTargetAchievement());
                node.put("target", acquiringTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", acquiringTargetEntity.getTargetAchievement());
                node.put("target", acquiringTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (AcquiringTargetEntity acquiringTargetEntity : acquiringTargetsRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", acquiringTargetEntity.getTargetAchievement());
                node.put("target", acquiringTargetEntity.getTargetValue());
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
    public boolean createCustomerVisit(AcquiringCustomerVisitsRequest model) {
        try {
            if (model == null) {
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
            if (assetId == null || agentId == null) {
                return false;
            }
            AcquiringAssetEntity acquiringAssetEntity = acquiringAssetRepository.findById(assetId).get();
            if (acquiringAssetEntity == null) {
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

    @Override
    public Object onboardNewMerchant(String merchDetails, MultipartFile[] signatureDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AcquiringOnboardRequest acquiringOnboardRequest = mapper.readValue(
                    merchDetails, AcquiringOnboardRequest.class);
            if (acquiringOnboardRequest == null) throw new RuntimeException("Bad request");
            AcquiringOnboardEntity acquiringOnboardEntity = new AcquiringOnboardEntity();
            acquiringOnboardEntity.setBusinessName(acquiringOnboardRequest.getBusinessName());
            acquiringOnboardEntity.setClientLegalName(acquiringOnboardRequest.getClientLegalName());
            acquiringOnboardEntity.setBusinessPhoneNumber(acquiringOnboardRequest.getBusinessPhoneNumber());
            acquiringOnboardEntity.setBusinessEmail(acquiringOnboardRequest.getBusinessEmail());
            acquiringOnboardEntity.setBusinessWebsite(acquiringOnboardRequest.getBusinessWebsite());
            acquiringOnboardEntity.setOutletContactPerson(acquiringOnboardRequest.getOutletContactPerson());
            acquiringOnboardEntity.setOutletPhone(acquiringOnboardRequest.getOutletPhone());
            acquiringOnboardEntity.setNumberOfOutlet(acquiringOnboardRequest.getNumberOfOutlet());
            acquiringOnboardEntity.setTypeOfGoodAndServices(acquiringOnboardRequest.getTypeOfGoodAndServices());
            //save acquiringOnboardEntity
            AcquiringOnboardEntity acquiringOnboard = acquiringOnboardingsRepository.save(acquiringOnboardEntity);
            acquiringOnboardEntity.setBankName(acquiringOnboardRequest.getBankName());
            acquiringOnboardEntity.setAccountName(acquiringOnboardRequest.getAccountName());
            acquiringOnboardEntity.setAccountNumber(acquiringOnboardRequest.getAccountNumber());
            acquiringOnboardEntity.setBranchName(acquiringOnboardRequest.getBranchName());
            acquiringOnboardEntity.setFeesAndCommission(acquiringOnboardRequest.getFeesAndCommission());
            List<AcquiringPrincipalInfoEntity> acquiringPrincipalInfoEntities = new ArrayList<>();
            for (AcquiringPrincipalInfoEntity acquiringPrincipalInfoRequest : acquiringOnboardRequest.getAcquiringPrincipalInfoEntities()) {
                AcquiringPrincipalInfoEntity acquiringPrincipalInfoEntity = new AcquiringPrincipalInfoEntity();
                acquiringPrincipalInfoEntity.setNameOfDirectorOrPrincipalOrPartner(acquiringPrincipalInfoRequest.getNameOfDirectorOrPrincipalOrPartner());
                acquiringPrincipalInfoEntity.setDirectorOrPrincipalOrPartnerPhoneNumber(acquiringPrincipalInfoRequest.getDirectorOrPrincipalOrPartnerPhoneNumber());
                acquiringPrincipalInfoEntity.setDirectorOrPrincipalOrPartnerEmail(acquiringPrincipalInfoRequest.getDirectorOrPrincipalOrPartnerEmail());
                //add to list
                acquiringPrincipalInfoEntities.add(acquiringPrincipalInfoEntity);
                acquiringPrincipalInfoRepository.save(acquiringPrincipalInfoEntity);

            }
            //allow several signatures to be uploaded to uploadDir
            for (MultipartFile file : signatureDoc) {
                if (file.isEmpty()) {
                    return "Please no signature uploaded";
                }
                String fileName = file.getOriginalFilename();
                String filePath = FileStorageService.uploadDirectory + File.separator + fileName;
                File dest = new File(filePath);
                file.transferTo(dest);
                AcquiringOnboardingKYCentity acquiringSignatureEntity = new AcquiringOnboardingKYCentity();
                acquiringSignatureEntity.setFilePath(filePath);
                acquiringSignatureEntity.setAcquiringOnboardEntity(acquiringOnboard);
                acquiringOnboardingKYCRepository.save(acquiringSignatureEntity);
                return null;


            }
        } catch (Exception e) {
            log.error("Error occurred while onboarding new merchant", e);
        }
        return null;
    }
}

