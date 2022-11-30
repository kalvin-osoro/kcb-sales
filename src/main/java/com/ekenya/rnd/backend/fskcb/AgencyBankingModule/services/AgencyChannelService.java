package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAgentOnboardingEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaAssetEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaLeadEntity;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaTargetEntity;
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
@RequiredArgsConstructor

@Service
public class AgencyChannelService implements IAgencyChannelService {
    private final AgencyAssetRepository agencyAssetRepository;
    private final AgencyAssetFilesRepository agencyAssetFilesRepository;
    private final AgencyOnboardingKYCRepository agencyOnboardingKYCRepository;
    private final AgencyOnboardingRepository agencyOnboardingRepository;
    private final AgencyBankingLeadRepository agencyBankingLeadRepository;

    private final AgencyBankingTargetRepository agencyBankingTargetRepository;
    private final AgencyBankingVisitRepository agencyBankingVisitRepository;
    private final AgencyBankingVisitFileRepository agencyBankingVisitFilesRepository;
    private final FileStorageService fileStorageService;
    private final int totalTransaction = (int) (Math.random() * 1000000);

    @Override
    public boolean assignAsset(AgencyAssignAssetRequest model) {
        try {
            if (model == null) {
                return false;
            }
            AgencyOnboardingEntity agencyOnboardingEntity = agencyOnboardingRepository.findById(model.getCustomerId()).orElse(null);
            AgencyAssetEntity agencyAssetEntity = agencyAssetRepository.findById(model.getAssetId()).orElse(null);
            if (agencyOnboardingEntity == null || agencyAssetEntity == null) {
                return false;
            }
            agencyAssetEntity.setAgencyOnboardingEntity(agencyOnboardingEntity);
            agencyAssetEntity.setAssigned(true);
            agencyAssetRepository.save(agencyAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while assigning asset to agent ", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllAgentAssets(Long agentId) {
        try {
            //get all assets for merchant
            List<AgencyAssetEntity> agencyAssetEntities = agencyAssetRepository.findAllByAgentId(agentId);
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            agencyAssetEntities.forEach(dfsVoomaAssetEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("assetId", dfsVoomaAssetEntity.getId());
                objectNode.put("serialNumber", dfsVoomaAssetEntity.getSerialNumber());
                objectNode.put("assetCondition", dfsVoomaAssetEntity.getAssetCondition().ordinal());
                objectNode.put("totalTransaction", totalTransaction);
                objectNodeList.add(objectNode);
            });

            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all agent assets ", e);
        }
        return null;
    }

    @Override
    public boolean recollectAsset(Long assetId) {
        try {
            if (assetId == null) {
                return false;
            }
            AgencyAssetEntity agencyAssetEntity = agencyAssetRepository.findById(assetId).orElse(null);
            if (agencyAssetEntity == null) {
                return false;
            }
            agencyAssetEntity.setAgencyOnboardingEntity(null);
            agencyAssetEntity.setAssigned(false);
            agencyAssetRepository.save(agencyAssetEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while recollecting asset from agent ", e);
        }
        return false;
    }

    @Override
    public boolean createLead(AgencyAddLeadRequest request) {
        try {
            AgencyBankingLeadEntity agencyBankingLeadEntity = new AgencyBankingLeadEntity();
            agencyBankingLeadEntity.setCustomerName(request.getCustomerName());
            agencyBankingLeadEntity.setBusinessUnit(request.getBusinessUnit());
            agencyBankingLeadEntity.setPriority(request.getPriority());
            agencyBankingLeadEntity.setCustomerAccountNumber(request.getCustomerAccountNumber());
            agencyBankingLeadEntity.setTopic(request.getTopic());
            agencyBankingLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllLeadsByDsrId(Long dsrId) {
        try {
            List<AgencyBankingLeadEntity> agencyBankingLeadEntities = agencyBankingLeadRepository.findAllByDsrId(dsrId);
            List<ObjectNode> objectNodeList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            agencyBankingLeadEntities.forEach(agencyBankingLeadEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("leadId", agencyBankingLeadEntity.getId());
                objectNode.put("customerName", agencyBankingLeadEntity.getCustomerName());
                objectNode.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                objectNode.put("priority", agencyBankingLeadEntity.getPriority().ordinal());
                objectNode.put("customerAccountNumber", agencyBankingLeadEntity.getCustomerAccountNumber());
                objectNode.put("topic", agencyBankingLeadEntity.getTopic());
                objectNode.put("createdOn", agencyBankingLeadEntity.getCreatedOn().toString());
                objectNodeList.add(objectNode);
            });

            return objectNodeList;
        } catch (Exception e) {
            log.error("Error occurred while getting all leads for dsr ", e);
        }
        return null;
    }

    @Override
    public ArrayList<ObjectNode> getTargetsSummary() {
        try {
            ArrayList<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AgencyBankingTargetEntity agencyBankingTarget : agencyBankingTargetRepository.findAllByTargetType(TargetType.VISITS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode visitsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTarget.getTargetAchievement());
                node.put("target", agencyBankingTarget.getTargetValue());
                visitsNode.set("visits", node);
                list.add(visitsNode);
            }
            //targetType =Leads
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.LEADS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode leadsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
                leadsNode.set("leads", node);
                list.add(leadsNode);
            }
            //targetType =CAMPAIGNS
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.CAMPAINGS)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode campaignsNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
                campaignsNode.set("campaigns", node);
                list.add(campaignsNode);
            }
            //targetType =ONBOARDING
            for (AgencyBankingTargetEntity agencyBankingTargetEntity : agencyBankingTargetRepository.findAllByTargetType(TargetType.ONBOARDING)) {
                ObjectNode node = mapper.createObjectNode();
                ObjectNode onboardingNode = mapper.createObjectNode();
                node.put("achieved", agencyBankingTargetEntity.getTargetAchievement());
                node.put("target", agencyBankingTargetEntity.getTargetValue());
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
    public Object onboardNewCustomer(String agentDetails,
                                     MultipartFile frontID,
                                     MultipartFile backID,
                                     MultipartFile kraPinCertificate,
                                     MultipartFile certificateOFGoodConduct,
                                     MultipartFile businessLicense,
                                     MultipartFile shopPhoto,
                                     MultipartFile financialStatement,
                                     MultipartFile cv,
                                     MultipartFile customerPhoto,
                                     MultipartFile companyRegistrationDoc,
                                     MultipartFile signatureDoc,
                                     MultipartFile passportPhoto1,
                                     MultipartFile passportPhoto2,
                                     MultipartFile acceptanceLetter,
                                     MultipartFile crbReport,
                                     MultipartFile businessPermitDoc) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            AgencyOnboardingRequest agencyOnboardingRequest = mapper.readValue(
                    agentDetails, AgencyOnboardingRequest.class);
            if (agencyOnboardingRequest == null) throw new RuntimeException("Bad request");
            AgencyOnboardingEntity agencyOnboardingEntity = new AgencyOnboardingEntity();
            agencyOnboardingEntity.setNameOfProposiedAgent(agencyOnboardingRequest.getNameOfProposiedAgent());
            agencyOnboardingEntity.setBusinessType(agencyOnboardingRequest.getBusinessType());
            //personal details
            agencyOnboardingEntity.setSurname(agencyOnboardingRequest.getSurname());
            agencyOnboardingEntity.setOtherNames(agencyOnboardingRequest.getOtherNames());
            agencyOnboardingEntity.setPreviousName(agencyOnboardingRequest.getPreviousName());
            agencyOnboardingEntity.setYearOfBirth(agencyOnboardingRequest.getYearOfBirth());
            agencyOnboardingEntity.setPlaceOfBirth(agencyOnboardingRequest.getPlaceOfBirth());
            agencyOnboardingEntity.setAgentIdNumber(agencyOnboardingRequest.getAgentIdNumber());
            agencyOnboardingEntity.setRelationshipToEntity(agencyOnboardingRequest.getRelationshipToEntity());
            agencyOnboardingEntity.setEducationalQualification(agencyOnboardingRequest.getEducationalQualification());
            //physical address
            agencyOnboardingEntity.setAgentPostalCode(agencyOnboardingRequest.getAgentPostalCode());
            agencyOnboardingEntity.setAgentPbox(agencyOnboardingRequest.getAgentPbox());
            agencyOnboardingEntity.setAgentPhone(agencyOnboardingRequest.getAgentPhone());
            //borrower details
            agencyOnboardingEntity.setNameOfBorrower(agencyOnboardingRequest.getNameOfBorrower());
            agencyOnboardingEntity.setLendingInstitution(agencyOnboardingRequest.getLendingInstitution());
            agencyOnboardingEntity.setTypeOfLoan(agencyOnboardingRequest.getTypeOfLoan());
            agencyOnboardingEntity.setDateOfLoan(agencyOnboardingRequest.getDateOfLoan());
            agencyOnboardingEntity.setPerformanceOfLoan(agencyOnboardingRequest.getPerformanceOfLoan());
            agencyOnboardingEntity.setOtherRemarks(agencyOnboardingRequest.getOtherRemarks());
            //yes or no questions
            agencyOnboardingEntity.setIsFundsObtainedFromIllegalSources(agencyOnboardingRequest.getIsFundsObtainedFromIllegalSources());
            agencyOnboardingEntity.setIsAgentConvictedOfAnyOffence(agencyOnboardingRequest.getIsAgentConvictedOfAnyOffence());
            agencyOnboardingEntity.setIsAgentHeldLiableInAnyCourtOfLaw(agencyOnboardingRequest.getIsAgentHeldLiableInAnyCourtOfLaw());
            agencyOnboardingEntity.setIsAgentEverBeenDismissedFromEmployment(agencyOnboardingRequest.getIsAgentEverBeenDismissedFromEmployment());
            agencyOnboardingEntity.setIsAgentExchangeForeignCurrency(agencyOnboardingRequest.getIsAgentExchangeForeignCurrency());
            //save the onboarding request
            AgencyOnboardingEntity agentInfo = agencyOnboardingRepository.save(agencyOnboardingEntity);

            //upload documents
            String folderName = "onboarding_" + agentInfo.getId();
            String frontIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "frontID_" + agentInfo.getId() + ".PNG", frontID,folderName );

            String backIDPath = fileStorageService.saveFileWithSpecificFileName(
                    "backID_" + agentInfo.getId() + ".PNG", backID,folderName );

            String kraPinCertificatePath = fileStorageService.saveFileWithSpecificFileName(
                    "kraPinCertificate_" + agentInfo.getId() + ".PNG", kraPinCertificate,folderName );
            String certificateOFGoodConductPath = fileStorageService.saveFileWithSpecificFileName(
                    "certificateOFGoodConduct_" + agentInfo.getId() + ".PNG", certificateOFGoodConduct,folderName );
            String businessLicensePath = fileStorageService.saveFileWithSpecificFileName(
                    "businessLicense_" + agentInfo.getId() + ".PNG", businessLicense,folderName );
            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "shopPhoto_" + agentInfo.getId() + ".PNG", shopPhoto,folderName );
            String financialStatementPath = fileStorageService.saveFileWithSpecificFileName(
                    "financialStatement_" + agentInfo.getId() + ".PNG", financialStatement,folderName );
            String cvPath = fileStorageService.saveFileWithSpecificFileName(
                    "cv_" + agentInfo.getId() + ".PNG", cv,folderName );
            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "customerPhoto_" + agentInfo.getId() + ".PNG", customerPhoto,folderName );
            String companyRegistrationDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "companyRegistrationDoc_" + agentInfo.getId() + ".PNG", companyRegistrationDoc,folderName );
            String signatureDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "signatureDoc_" + agentInfo.getId() + ".PNG", signatureDoc,folderName );
            String passportPhoto1Path = fileStorageService.saveFileWithSpecificFileName(
                    "passportPhoto1_" + agentInfo.getId() + ".PNG", passportPhoto1,folderName );
            String passportPhoto2Path = fileStorageService.saveFileWithSpecificFileName(
                    "passportPhoto2_" + agentInfo.getId() + ".PNG", passportPhoto2,folderName );
            String acceptanceLetterPath = fileStorageService.saveFileWithSpecificFileName(
                    "acceptanceLetter_" + agentInfo.getId() + ".PNG", acceptanceLetter,folderName );
            String crbReportPath = fileStorageService.saveFileWithSpecificFileName(
                    "crbReport_" + agentInfo.getId() + ".PNG", crbReport,folderName );
            String businessPermitDocPath = fileStorageService.saveFileWithSpecificFileName(
                    "businessPermitDoc_" + agentInfo.getId() + ".PNG", businessPermitDoc,folderName );
            //save the document paths
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(kraPinCertificatePath);
            filePathList.add(certificateOFGoodConductPath);
            filePathList.add(businessLicensePath);
            filePathList.add(shopPhotoPath);
            filePathList.add(financialStatementPath);
            filePathList.add(cvPath);
            filePathList.add(customerPhotoPath);
            filePathList.add(companyRegistrationDocPath);
            filePathList.add(signatureDocPath);
            filePathList.add(passportPhoto1Path);
            filePathList.add(passportPhoto2Path);
            filePathList.add(acceptanceLetterPath);
            filePathList.add(crbReportPath);
            filePathList.add(businessPermitDocPath);
            filePathList.forEach(filePath -> {
                AgencyOnboardingKYCEntity agentKYC = new AgencyOnboardingKYCEntity();
                agentKYC.setFilPath(filePath);
                agentKYC.setAgencyOnboardingEntity(agentInfo);
                agencyOnboardingKYCRepository.save(agentKYC);
            });
            return true;
        } catch (Exception e) {
            log.error("Error occurred while saving agent onboarding request", e);
        }
        return false;
    }

    @Override
    public List<ObjectNode> getAllOnboardingsByDsr(AgencyAllDSROnboardingsRequest model) {
        try {
            if (model == null) {
                return null;
            }
            List<ObjectNode> allOnboardings = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            List<AgencyOnboardingEntity> allOnboardingsByDsr = agencyOnboardingRepository.findAllByDsrId(model.getDsrId());
            allOnboardingsByDsr.forEach(agencyOnboardingEntity -> {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("id", agencyOnboardingEntity.getId());
                objectNode.put("agentIdNumber", agencyOnboardingEntity.getAgentIdNumber());
                objectNode.put("surname", agencyOnboardingEntity.getSurname());
                objectNode.put("otherNames", agencyOnboardingEntity.getOtherNames());
                objectNode.put("businessType", agencyOnboardingEntity.getBusinessType());
                objectNode.put("proposedAgentName", agencyOnboardingEntity.getNameOfProposiedAgent());
                objectNode.put("agentPhone", agencyOnboardingEntity.getAgentPhone());
                objectNode.put("typeOfLoan", agencyOnboardingEntity.getTypeOfLoan());
                objectNode.put("dateOfLoan", agencyOnboardingEntity.getDateOfLoan());
                objectNode.put("performanceOfLoan", agencyOnboardingEntity.getPerformanceOfLoan());
                objectNode.put("otherRemarks", agencyOnboardingEntity.getOtherRemarks());
                objectNode.put("isAgentExchangeForeignCurrency", agencyOnboardingEntity.getIsAgentExchangeForeignCurrency());
                objectNode.put("status", agencyOnboardingEntity.getStatus().ordinal());
                objectNode.put("createdOn", agencyOnboardingEntity.getCreatedOn().toString());
                allOnboardings.add(objectNode);
            });
            return allOnboardings;
        } catch (Exception e) {
            log.error("Error occurred while getting all onboarding requests by dsr", e);
        }
        return null;
    }

    @Override
    public Object createCustomerVisit(String visitDetails, MultipartFile premiseInsidePhoto, MultipartFile premiseOutsidePhoto, MultipartFile cashRegisterPhoto) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            AgencyCustomerVisitsRequest agencyCustomerVisitsRequest = mapper.readValue(
                    visitDetails, AgencyCustomerVisitsRequest.class);
            if (agencyCustomerVisitsRequest == null) throw new RuntimeException("Bad request");
            AgencyBankingVisitEntity agencyBankingVisitEntity = new AgencyBankingVisitEntity();
            agencyBankingVisitEntity.setDsrName(agencyCustomerVisitsRequest.getDsrName());
            agencyBankingVisitEntity.setAgentName(agencyCustomerVisitsRequest.getAgentName());
            agencyBankingVisitEntity.setReasonForVisit(agencyCustomerVisitsRequest.getReasonForVisit());
            agencyBankingVisitEntity.setVisitDate(agencyCustomerVisitsRequest.getVisitDate());
            agencyBankingVisitEntity.setStatus(agencyCustomerVisitsRequest.getStatus());
            agencyBankingVisitEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingVisitEntity.setUpdatedOn(agencyCustomerVisitsRequest.getUpdatedOn());
            agencyBankingVisitEntity.setScheduled(agencyCustomerVisitsRequest.getScheduled());
            agencyBankingVisitEntity.setLocation(agencyCustomerVisitsRequest.getLocation());
            agencyBankingVisitEntity.setPdqVersionCorrect(agencyCustomerVisitsRequest.getPdqVersionCorrect());
            agencyBankingVisitEntity.setChargesUpfront(agencyCustomerVisitsRequest.getChargesUpfront());
            agencyBankingVisitEntity.setMaintainsOpenedAcctRecords(agencyCustomerVisitsRequest.getMaintainsOpenedAcctRecords());
            agencyBankingVisitEntity.setTrained(agencyCustomerVisitsRequest.getTrained());
            agencyBankingVisitEntity.setUsesManualReceiptBook(agencyCustomerVisitsRequest.getUsesManualReceiptBook());
            agencyBankingVisitEntity.setReconcileFloatAcctStat(agencyCustomerVisitsRequest.getReconcileFloatAcctStat());
            agencyBankingVisitEntity.setMoreThanXTransactions(agencyCustomerVisitsRequest.getMoreThanXTransactions());
            agencyBankingVisitEntity.setBranchCollectsRegisters(agencyCustomerVisitsRequest.getBranchCollectsRegisters());
            agencyBankingVisitEntity.setTariffPosterWellDisplayed(agencyCustomerVisitsRequest.getTariffPosterWellDisplayed());
            agencyBankingVisitEntity.setCustomersSignRegister(agencyCustomerVisitsRequest.getCustomersSignRegister());
            agencyBankingVisitEntity.setRegisterReflected(agencyCustomerVisitsRequest.getRegisterReflected());
            agencyBankingVisitEntity.setOutletWellBranded(agencyCustomerVisitsRequest.getOutletWellBranded());
            agencyBankingVisitEntity.setRegisterCompleted(agencyCustomerVisitsRequest.getRegisterCompleted());
            agencyBankingVisitEntity.setVisitedByStaff(agencyCustomerVisitsRequest.getVisitedByStaff());
            agencyBankingVisitEntity.setLocatedStrategically(agencyCustomerVisitsRequest.getLocatedStrategically());
            agencyBankingVisitEntity.setCsLevel(agencyCustomerVisitsRequest.getCsLevel());
            agencyBankingVisitEntity.setOutletPresentable(agencyCustomerVisitsRequest.getOutletPresentable());
            agencyBankingVisitEntity.setHasFloat(agencyCustomerVisitsRequest.getHasFloat());
            agencyBankingVisitEntity.setHasFloat(agencyCustomerVisitsRequest.getHasFloat());
            agencyBankingVisitEntity.setCustomerInflow(agencyCustomerVisitsRequest.getCustomerInflow());
            agencyBankingVisitEntity.setAgentTrxInForeignCur(agencyCustomerVisitsRequest.getAgentTrxInForeignCur());
            agencyBankingVisitEntity.setComments(agencyCustomerVisitsRequest.getComments());
            agencyBankingVisitEntity.setHasMaterials(agencyCustomerVisitsRequest.getHasMaterials());
            //save visit
          AgencyBankingVisitEntity  visitInfo = agencyBankingVisitRepository.save(agencyBankingVisitEntity);

            String folderName = "agencyBankingVisit_" + visitInfo.getId();
            String premiseInsidePhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "premiseInsidePhoto_" + visitInfo.getId() + ".PNG", premiseInsidePhoto,folderName );
            String premiseOutsidePhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "premiseOutsidePhoto_" + visitInfo.getId() + ".PNG", premiseOutsidePhoto,folderName );
            String cashRegisterPhotoPath = fileStorageService.saveFileWithSpecificFileName(
                    "cashRegisterPhoto_" + visitInfo.getId() + ".PNG", cashRegisterPhoto,folderName );
            //save the document paths
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(premiseInsidePhotoPath);
            filePathList.add(premiseOutsidePhotoPath);
            filePathList.add(cashRegisterPhotoPath);
            filePathList.forEach(filePath -> {
                AgencyBankingVisitFileEntity visitKYC = new AgencyBankingVisitFileEntity();
                visitKYC.setFilePath(filePath);
                visitKYC.setAgencyBankingVisitEntity(visitInfo);
                agencyBankingVisitFilesRepository.save(visitKYC);
            });
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
        }
        return false;
    }
}

