package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.TargetType;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBJustificationEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.datasource.entities.CBRevenueLineEntity;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBJustificationRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBRevenueLineRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRAccountEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities.DSRRegionEntity;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRAccountsRepository;
import com.ekenya.rnd.backend.fskcb.DSRModule.datasource.repositories.IDSRRegionsRepository;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ekenya.rnd.backend.utils.Utility.isPointInPolygon;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcquiringChannelService implements IAcquiringChannelService {
    private final AcquiringOnboardingKYCRepository acquiringOnboardingKYCRepository;
    private final IAcquiringOnboardingsRepository acquiringOnboardingsRepository;
    private final AcquiringPrincipalRepository acquiringPrincipalRepository;
    private final IDSRAccountsRepository dsrAccountsRepository;
    private final CustomerFeedBackRepository customerFeedBackRepository;
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
    public List<ObjectNode> searchCustomers(SearchKeyWordRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringOnboardEntity acquiringOnboardEntity : acquiringOnboardingsRepository.searchCustomers(model.getKeyword())) {

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
        try {
            double longitude = model.getLongitude();
           double latitude = model.getLatitude();
            Coordinate coordinate = new Coordinate(longitude, latitude);
            List<ObjectNode> list = new ArrayList<>();
             ObjectMapper mapper = new ObjectMapper();
             for(DSRRegionEntity acquiringDSRRegionEntity : dsrRegionsRepository.findAll()){
                 ObjectNode asset = mapper.createObjectNode();
                 asset.put("id", acquiringDSRRegionEntity.getId());
                 asset.put("name", acquiringDSRRegionEntity.getName());
                    asset.put("bounds", acquiringDSRRegionEntity.getGeoJsonBounds());
                    //convert bounds to  coordinate
                    GeometryFactory geometryFactory = new GeometryFactory();
                    WKTReader reader = new WKTReader(geometryFactory);
                    Geometry geometry = reader.read(acquiringDSRRegionEntity.getGeoJsonBounds());
                 System.out.println("bounds: "+acquiringDSRRegionEntity.getGeoJsonBounds());
                 list.add(asset);
                 //check if login user latitute and longitude is inside any region bounds
                 if(isPointInPolygon(geometry.getCoordinate(),coordinate)){
                     System.out.println("inside a region");


                        return list;
                 }
                 return null;
             }
        } catch (Exception e) {
            log.error("Error occurred while getting nearby customers", e);
        }

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
            acquiringCustomerVisitsEntity.setVisitType(model.getVisitType());
            acquiringCustomerVisitsEntity.setEntityBrief(model.getEntityBrief());
            acquiringCustomerVisitsEntity.setLatitude(model.getLatitude());
            acquiringCustomerVisitsEntity.setLongitude(model.getLongitude());
            acquiringCustomerVisitsEntity.setAttendance(model.getAttendance());
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
            acquiringOnboardEntity.setRegion(acquiringOnboardRequest.getRegion());
            acquiringOnboardEntity.setLatitude(acquiringOnboardRequest.getLatitude());
            acquiringOnboardEntity.setLongitude(acquiringOnboardRequest.getLongitude());
            acquiringOnboardEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            acquiringOnboardEntity.setStatus(OnboardingStatus.PENDING);
            acquiringOnboardEntity.setTypeOfGoodAndServices(acquiringOnboardRequest.getTypeOfGoodAndServices());
            acquiringOnboardEntity.setBankName(acquiringOnboardRequest.getBankName());
            acquiringOnboardEntity.setAccountName(acquiringOnboardRequest.getAccountName());
            acquiringOnboardEntity.setAccountNumberInKES(acquiringOnboardRequest.getAccountNumberInKES());
            acquiringOnboardEntity.setAccountNumberInUSD(acquiringOnboardRequest.getAccountNumberInUSD());
            acquiringOnboardEntity.setBranchName(acquiringOnboardRequest.getBranchName());
            acquiringOnboardEntity.setFeesAndCommission(acquiringOnboardRequest.getFeesAndCommission());
            AcquiringOnboardEntity acquiringOnboard = acquiringOnboardingsRepository.save(acquiringOnboardEntity);
            //
            List<AcquiringPrincipalInfoRequest> acquiringPrincipalInfoRequestList =  acquiringOnboardRequest.getAcquiringPrincipalInfoRequests();
            for (AcquiringPrincipalInfoRequest acquiringPrincipalInfoRequest : acquiringPrincipalInfoRequestList) {
                AcquiringPrincipalInfoEntity acquiringPrincipalInfoEntity = new AcquiringPrincipalInfoEntity();
                acquiringPrincipalInfoEntity.setNameOfDirectorOrPrincipalOrPartner(acquiringPrincipalInfoRequest.getNameOfDirectorOrPrincipalOrPartner());
                acquiringPrincipalInfoEntity.setDirectorOrPrincipalOrPartnerPhoneNumber(acquiringPrincipalInfoRequest.getDirectorOrPrincipalOrPartnerPhoneNumber());
                acquiringPrincipalInfoEntity.setDirectorOrPrincipalOrPartnerEmail(acquiringPrincipalInfoRequest.getDirectorOrPrincipalOrPartnerEmail());
                acquiringPrincipalInfoEntity.setAcquiringOnboardEntity(acquiringOnboardEntity);//
                acquiringPrincipalInfoRepository.save(acquiringPrincipalInfoEntity);
            }
            //
            List<AcquiringPrincipalRequest> acquiringPrincipalRequestList = acquiringOnboardRequest.getAcquiringPrincipalRequests();
            for (AcquiringPrincipalRequest acquiringPrincipalRequest : acquiringPrincipalRequestList) {
                AcquiringPrincipalEntity acquiringPrincipalEntity = new AcquiringPrincipalEntity();
                acquiringPrincipalEntity.setName(acquiringPrincipalRequest.getPrincipalName());
                acquiringPrincipalEntity.setAcquiringOnboardEntity(acquiringOnboardEntity);
                acquiringPrincipalRepository.save(acquiringPrincipalEntity);
            }
            //allow several signatures to be uploaded to uploadDir
            List<String> filePathList = new ArrayList<>();
            //
            //save files

            filePathList = fileStorageService.saveMultipleFileWithSpecificFileName("SignatoriesSignatures_", signatureDoc);
            //save file paths to db
            filePathList.forEach(filePath -> {
                AcquiringOnboardingKYCentity signatureFilesEntity = new AcquiringOnboardingKYCentity();
                signatureFilesEntity.setAcquiringOnboardEntity(acquiringOnboard);
                signatureFilesEntity.setFilePath(filePath);
                acquiringOnboardingKYCRepository.save(signatureFilesEntity);
            });
            return true;

        } catch (Exception e) {
            log.error("Error occurred while onboarding new merchant", e);
        }
        return null;
    }

    @Override
    public boolean createCustomerFeedback(CustomerFeedbackRequest model) {
        try {
            if (model == null) {
                return false;
            }
            CustomerFeedBack customerFeedBack=new CustomerFeedBack();
            customerFeedBack.setDescribeTheService(model.getDescribeTheService());
            customerFeedBack.setWhyWouldYouChange(model.getWhyWouldYouChange());
            customerFeedBack.setWhatWouldYouChange(model.getWhatWouldYouChange());
            customerFeedBackRepository.save(customerFeedBack);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating customer feedback", e);
        }
        return false;
    }

    @Override
    public ArrayNode getDSRSummary(DSRSummaryRequest model) {

try {
    if (model == null) {
        return null;
    }
    //hard commission for now
    ArrayNode arrayNode = new ObjectMapper().createArrayNode();
    ObjectNode objectNode = new ObjectMapper().createObjectNode();
    short commission=120;
    short targetAchieved=300;
    objectNode.put("commission", commission);
    //get total number of dsr visits by dsr id
    int totalVisits = acquiringCustomerVisitRepository.countTotalVisits(model.getDsrId());
    objectNode.put("customer-visits", totalVisits);
    //if null hard code visits for now
    if (totalVisits == 0) {
        objectNode.put("customer-visits", 100);
    }
    //get total number of dsr assigned leads by dsr id
    int totalAssignedLeads = acquiringLeadsRepository.countTotalAssignedLeads(model.getDsrId());
    objectNode.put("assigned-leads", totalAssignedLeads);
    //if null hard code assigned leads for now
    if (totalAssignedLeads == 0) {
        objectNode.put("assigned-leads", 100);
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

