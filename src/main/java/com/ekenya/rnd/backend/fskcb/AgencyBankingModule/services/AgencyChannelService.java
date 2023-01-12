package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.LeadStatus;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.datasource.entities.TreasuryLeadEntity;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public boolean recollectAsset(AssetRecollectRequest model) {
        try {
            if (model == null) {
                return false;
            }
            AgencyAssetEntity agencyAssetEntity = agencyAssetRepository.findById(model.getAssetId()).orElse(null);
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
    public boolean createLead(AgencyAddLeadRequest model) {
        try {
            if (model==null){
                return false;
            }
            AgencyBankingLeadEntity agencyBankingLeadEntity = new AgencyBankingLeadEntity();
            agencyBankingLeadEntity.setCustomerName(model.getCustomerName());
            agencyBankingLeadEntity.setBusinessUnit(model.getBusinessUnit());
            agencyBankingLeadEntity.setEmail(model.getEmail());
            agencyBankingLeadEntity.setPhoneNumber(model.getPhoneNumber());
            agencyBankingLeadEntity.setProduct(model.getProduct());
            agencyBankingLeadEntity.setPriority(model.getPriority());
            agencyBankingLeadEntity.setDsrId(model.getDsrId());
            agencyBankingLeadEntity.setCustomerAccountNumber(model.getCustomerAccountNumber());
            agencyBankingLeadEntity.setTopic(model.getTopic());
            agencyBankingLeadEntity.setLeadStatus(LeadStatus.OPEN);
            agencyBankingLeadEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
            return true;

        } catch (Exception e) {
            log.error("Error occurred while creating lead", e);
        }
        return false;
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

                                     MultipartFile certificateOFGoodConduct,
                                     MultipartFile shopPhoto,
                                     MultipartFile financialStatement,
                                     MultipartFile cv,
                                     MultipartFile customerPhoto,
                                     MultipartFile crbReport) {
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
            agencyOnboardingEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //yes or no questions
            agencyOnboardingEntity.setIsFundsObtainedFromIllegalSources(agencyOnboardingRequest.getIsFundsObtainedFromIllegalSources());
            agencyOnboardingEntity.setIsAgentConvictedOfAnyOffence(agencyOnboardingRequest.getIsAgentConvictedOfAnyOffence());
            agencyOnboardingEntity.setIsAgentHeldLiableInAnyCourtOfLaw(agencyOnboardingRequest.getIsAgentHeldLiableInAnyCourtOfLaw());
            agencyOnboardingEntity.setIsAgentEverBeenDismissedFromEmployment(agencyOnboardingRequest.getIsAgentEverBeenDismissedFromEmployment());
            agencyOnboardingEntity.setIsAgentExchangeForeignCurrency(agencyOnboardingRequest.getIsAgentExchangeForeignCurrency());
            agencyOnboardingEntity.setStatus(OnboardingStatus.PENDING);
            agencyOnboardingEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save the onboarding request
            AgencyOnboardingEntity agentInfo = agencyOnboardingRepository.save(agencyOnboardingEntity);
            if (agentInfo == null) throw new RuntimeException("Error occurred while saving agent info");

            //upload documents
            String frontIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_frontID_" + agentInfo.getId() + ".PNG", frontID,Utility.getSubFolder() );

            String backIDPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_backID_" + agentInfo.getId() + ".PNG", backID,Utility.getSubFolder() );


            String certificateOFGoodConductPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_certificateOFGoodConduct_" + agentInfo.getId() + ".PNG", certificateOFGoodConduct,Utility.getSubFolder() );

            String shopPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_shopPhoto_" + agentInfo.getId() + ".PNG", shopPhoto,Utility.getSubFolder() );
            String financialStatementPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_financialStatement_" + agentInfo.getId() + ".PNG", financialStatement,Utility.getSubFolder() );
            String cvPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_cv_" + agentInfo.getId() + ".PNG", cv,Utility.getSubFolder() );
            String customerPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_customerPhoto_" + agentInfo.getId() + ".PNG", customerPhoto ,Utility.getSubFolder());
            String crbReportPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "agency_crbReportPhoto_" + agentInfo.getId() + ".PNG", crbReport,Utility.getSubFolder() );
            //check if all the documents were uploaded successfully if not throw an exception
            if (frontIDPath == null || backIDPath == null || certificateOFGoodConductPath == null
                    || shopPhotoPath == null || financialStatementPath == null || cvPath == null
                    || customerPhotoPath == null || crbReportPath == null) {
                throw new RuntimeException("Error occurred while uploading documents");
            }

            List<String> filePathList = new ArrayList<>();
            filePathList.add(frontIDPath);
            filePathList.add(backIDPath);
            filePathList.add(certificateOFGoodConductPath);
            filePathList.add(financialStatementPath);
            filePathList.add(customerPhotoPath);
            filePathList.add(cvPath);
            filePathList.add(crbReportPath);
            filePathList.add(shopPhotoPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/"+Utility.getSubFolder()+"/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                AgencyOnboardingKYCEntity agencyOnboardingKYCEntity = new AgencyOnboardingKYCEntity();
                agencyOnboardingKYCEntity.setFilPath(downloadUrl);
                agencyOnboardingKYCEntity.setAgencyOnboardingEntity(agentInfo);
                agencyOnboardingKYCEntity.setFilename(filePath);
                agencyOnboardingKYCEntity.setAgentId(agentInfo.getId());
                agencyOnboardingKYCRepository.save(agencyOnboardingKYCEntity);

            }
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
    public boolean createCustomerVisit(String visitDetails, MultipartFile premiseInsidePhoto,MultipartFile tariffPhoto, MultipartFile premiseOutsidePhoto, MultipartFile cashRegisterPhoto) {

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
            agencyBankingVisitEntity.setStatus(Status.ACTIVE);
            agencyBankingVisitEntity.setHasMaterials(agencyCustomerVisitsRequest.getHasMaterials());
            agencyBankingVisitEntity.setAssetNumber(agencyCustomerVisitsRequest.getAssetNumber());
            agencyBankingVisitEntity.setAssetCondition(agencyCustomerVisitsRequest.getAssetCondition());
            agencyBankingVisitEntity.setSerialNumber(agencyCustomerVisitsRequest.getSerialNumber());
            agencyBankingVisitEntity.setSerialNumber(agencyCustomerVisitsRequest.getLongitude());
            agencyBankingVisitEntity.setSerialNumber(agencyCustomerVisitsRequest.getLatitude());
            agencyBankingVisitEntity.setTerminalId(agencyCustomerVisitsRequest.getTerminalId());
          AgencyBankingVisitEntity  visitInfo = agencyBankingVisitRepository.save(agencyBankingVisitEntity);

            String premiseInsidePhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "premiseInsidePhoto_" + visitInfo.getId() + ".PNG", premiseInsidePhoto,Utility.getSubFolder() );
            String premiseOutsidePhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "premiseOutsidePhoto_" + visitInfo.getId() + ".PNG", premiseOutsidePhoto ,Utility.getSubFolder());
            String cashRegisterPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "cashRegisterPhoto_" + visitInfo.getId() + ".PNG", cashRegisterPhoto,Utility.getSubFolder() );
            String tariffPhotoPath = fileStorageService.saveFileWithSpecificFileNameV(
                    "tariffPhoto_" + visitInfo.getId() + ".PNG", tariffPhoto,Utility.getSubFolder() );
            //save the document paths
            ArrayList<String> filePathList = new ArrayList<>();
            filePathList.add(premiseInsidePhotoPath);
            filePathList.add(premiseOutsidePhotoPath);
            filePathList.add(cashRegisterPhotoPath);
            filePathList.add(tariffPhotoPath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/upload/" + Utility.getSubFolder() + "/")
                        .path(filePath)
                        .toUriString();
                downloadUrlList.add(downloadUrl);
                //save to db
                AgencyBankingVisitFileEntity agencyBankingVisitFileEntity = new AgencyBankingVisitFileEntity();
                agencyBankingVisitFileEntity.setFilePath(downloadUrl);
                agencyBankingVisitFileEntity.setAgencyBankingVisitEntity(visitInfo);
                agencyBankingVisitFileEntity.setFileName(filePath);
                agencyBankingVisitFileEntity.setIdVisit(visitInfo.getId());
                agencyBankingVisitFilesRepository.save(agencyBankingVisitFileEntity);

            }
            return true;
        } catch (Exception e) {
            log.error("Error occurred while scheduling customer visit", e);
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
            for (AgencyBankingLeadEntity agencyBankingLeadEntity : agencyBankingLeadRepository.findAllByDsrIdAndAssigned(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", agencyBankingLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", agencyBankingLeadEntity.getPriority().toString());
                node.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                node.put("leadId", agencyBankingLeadEntity.getId());
                node.put("leadStatus", agencyBankingLeadEntity.getLeadStatus().ordinal());
                node.put("createdOn", agencyBankingLeadEntity.getCreatedOn().getTime());
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
            for (AgencyBankingLeadEntity agencyBankingLeadEntity : agencyBankingLeadRepository.findAllAssignedLeadByDSRId(model.getDsrId())) {
                ObjectNode node = mapper.createObjectNode();
                node.put("customerName", agencyBankingLeadEntity.getCustomerName());
//                node.put("customerID", treasuryLeadEntity.getCustomerId());
                node.put("priority", agencyBankingLeadEntity.getPriority().toString());
                node.put("businessUnit", agencyBankingLeadEntity.getBusinessUnit());
                node.put("leadId", agencyBankingLeadEntity.getId());
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
            AgencyBankingLeadEntity agencyBankingLeadEntity = agencyBankingLeadRepository.findById(model.getLeadId()).orElse(null);
            agencyBankingLeadEntity.setOutcomeOfTheVisit(model.getOutcomeOfTheVisit());
            agencyBankingLeadEntity.setLeadStatus(model.getLeadStatus());
            agencyBankingLeadRepository.save(agencyBankingLeadEntity);
            return true;
        } catch (Exception e) {
            log.error("Error occurred while updating lead", e);
        }
        return false;
    }

}



