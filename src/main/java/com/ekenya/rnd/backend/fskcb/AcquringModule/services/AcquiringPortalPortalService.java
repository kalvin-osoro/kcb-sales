package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.*;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.*;
import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class AcquiringPortalPortalService implements IAcquiringPortalService {

    private final  IAcquiringLeadsRepository mLeadsRepo;
    private final IAcquiringTargetsRepository iAcquiringTargetsRepository;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final ModelMapper modelMapper;
    private final AcqAssetRepository acqAssetRepository;
    private final FileStorageService fileStorageService;
    private final AcquiringAssetFileRepository acquiringAssetFileRepository;

    private final IAcquiringDSRsInTargetRepository iAcquiringDSRsInTargetRepository;

    public AcquiringPortalPortalService(IAcquiringLeadsRepository mLeadsRepo,
                                        IAcquiringTargetsRepository iAcquiringTargetsRepository, AcquiringAssetRepository acquiringAssetRepository,
                                        ModelMapper modelMapper,
                                        AcqAssetRepository acqAssetRepository,
                                        FileStorageService fileStorageService,
                                        AcquiringAssetFileRepository acquiringAssetFileRepository, IAcquiringDSRsInTargetRepository iAcquiringDSRsInTargetRepository) {
        this.mLeadsRepo = mLeadsRepo;
        this.iAcquiringTargetsRepository = iAcquiringTargetsRepository;
        this.acquiringAssetRepository = acquiringAssetRepository;
        this.modelMapper = modelMapper;
        this.acqAssetRepository = acqAssetRepository;
        this.fileStorageService = fileStorageService;
        this.acquiringAssetFileRepository = acquiringAssetFileRepository;
        this.iAcquiringDSRsInTargetRepository = iAcquiringDSRsInTargetRepository;
    }


    @Override
    public boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model) {
        return false;
    }

    @Override
    public List<ObjectNode> loadTargets() {
        try {
            List<ObjectNode> targets = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for (AcquiringTargetEntity target : iAcquiringTargetsRepository.findAll()) {
                ObjectNode node = mapper.createObjectNode();
                node.put("id", target.getId());
                node.put("name", target.getTargetName());
                node.put("description", target.getTargetDesc());
                node.put("status", target.getStatus().toString());
                node.put("value", target.getTargetValue());
                node.put("achievement", target.getTargetAchievement());
                node.put("source", target.getTargetSource());
                node.put("startDate", target.getStartDate().toString());
                node.put("endDate", target.getEndDate().toString());
                node.put("type", target.getAquiringTargetType().toString());
                targets.add(node);
            }
            return targets;
        } catch (Exception e) {
            log.error("Error loading targets", e);
        }
        return null;
    }

    @Override
    public boolean addNewTarget(AcquiringAddTargetRequest acquiringAddTargetRequest) {
        try {
            if (acquiringAddTargetRequest == null) {
                return false;
            }
            ObjectMapper mapper = new ObjectMapper();

            AcquiringTargetEntity acquiringTargetEntity=new AcquiringTargetEntity();
            acquiringTargetEntity.setTargetName(acquiringAddTargetRequest.getTargetName());
            acquiringTargetEntity.setTargetSource(acquiringAddTargetRequest.getTargetSource());
            acquiringTargetEntity.setAquiringTargetType(acquiringAddTargetRequest.getAquiringTargetType());
            acquiringTargetEntity.setTargetDesc(acquiringAddTargetRequest.getTargetDesc());
            acquiringTargetEntity.setStatus(Status.ACTIVE);
            acquiringTargetEntity.setTargetValue(acquiringAddTargetRequest.getTargetValue());
            acquiringTargetEntity.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            //save
            iAcquiringTargetsRepository.save(acquiringTargetEntity);
            return true;

            //save
        } catch (Exception e) {
            log.error("Error while adding new target", e);
        }
        return false;
    }

    @Override
    public boolean addAsset(String assetDetails, MultipartFile[] assetFiles) {
        try {
            if (assetDetails == null) {
                return false;
                //return ResponseEntity.badRequest().body("Asset details are required");
            }
            //

            ObjectMapper mapper = new ObjectMapper();

            AcquiringAddAssetRequest acquiringAddAssetRequest =
                    mapper.readValue(assetDetails,AcquiringAddAssetRequest.class);

            Optional<AcqAsset> optionalAsset = acqAssetRepository.findById( acquiringAddAssetRequest.getDeviceId());

            AcquiringAssetEntity acquiringAssetEntity = new AcquiringAssetEntity();
            acquiringAssetEntity.setAssetType(optionalAsset.get());
            //
            acquiringAssetEntity.setSerialNumber(acquiringAddAssetRequest.getSerialNumber());
            //
            acquiringAssetEntity.setCondition(acquiringAddAssetRequest.getCondition());
            //
            AcquiringAssetEntity savedAsset = acquiringAssetRepository.save(acquiringAssetEntity);
            //

            List<String> filePathList = new ArrayList<>();
            //save files
            filePathList =fileStorageService.saveMultipleFileWithSpecificFileName("Asset_",assetFiles);
            //
            filePathList.forEach(filePath ->{
                AcquiringAssetFilesEntity assetFilesEntity = new AcquiringAssetFilesEntity();
                assetFilesEntity.setAcquiringAssetEntity(savedAsset);
                assetFilesEntity.setFilePath(filePath);
                acquiringAssetFileRepository.save(assetFilesEntity);
            });

        } catch (JsonMappingException e) {
            //return ResponseEntity.badRequest().body("Invalid asset details");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public List<ObjectNode> getAllAssets() {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for(AcquiringAssetEntity  acquiringAssetEntity : acquiringAssetRepository.findAll()){

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id",acquiringAssetEntity.getId());
                asset.put("condition",acquiringAssetEntity.getCondition());
                asset.put("sno",acquiringAssetEntity.getSerialNumber());
                //asset.put("type",acquiringAssetEntity.getAssetType());
                //
                ArrayNode images = mapper.createArrayNode();

                //"http://10.20.2.12:8484/"

                // "/files/acquiring/asset-23-324767234.png;/files/acquiring/asset-23-3247672ewqee8.png"
                for (String path: acquiringAssetEntity.getImages().split(";")) {
                    images.add(path);
                }

                asset.put("images",images);


                //
                list.add(asset);
            }


            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all assets",e);
        }
        return null;
    }

    @Override
    public ObjectNode getAssetById(Long id) {
        try {
            AcquiringAssetEntity acquiringAssetEntity=acquiringAssetRepository.findById(id).get();


        } catch (Exception e) {
            log.error("Error occurred while fetching asset by id",e);
        }
        return null;
    }

    @Override
    public boolean deleteAssetById(Long id) {
        try {
            acquiringAssetRepository.deleteById(id);
            //
            return true;
        } catch (Exception e) {
            log.error("Error occurred while deleting asset by id",e);
        }
        return false;
    }


    @Override
    public List<ObjectNode> loadQuestionnaires() {
        return null;
    }

    @Override
    public List<ObjectNode> loadDSRsInTarget(AcquiringDSRsInTargetRequest model) {
        try {
            List<ObjectNode> list = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            for(AcquiringDSRsInTargetEntity acquiringDSRsInTargetEntity : iAcquiringDSRsInTargetRepository.findAll()){

                ObjectNode asset = mapper.createObjectNode();
                asset.put("id",acquiringDSRsInTargetEntity.getId());
                asset.put("dsrName",acquiringDSRsInTargetEntity.getDsrName());
                asset.put("targetName",acquiringDSRsInTargetEntity.getTargetName());
                asset.put("setTarget",acquiringDSRsInTargetEntity.getSetTarget());
                asset.put("achievedTarget",acquiringDSRsInTargetEntity.getAchievedTarget());
                list.add(asset);
            }
            return list;

        } catch (Exception e) {
            log.error("Error occurred while fetching all assets",e);
        }
        return null;
    }


}
