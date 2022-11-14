package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcqAsset;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetFilesEntity;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcqAssetRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetFileRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.AcquiringAssetRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.repositories.IAcquiringLeadsRepository;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
public class AcquiringService implements IAcquiringService{

    private final  IAcquiringLeadsRepository mLeadsRepo;
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final ModelMapper modelMapper;
    private final AcqAssetRepository acqAssetRepository;
    private final FileStorageService fileStorageService;
    private final AcquiringAssetFileRepository acquiringAssetFileRepository;

    public AcquiringService(IAcquiringLeadsRepository mLeadsRepo,
                            AcquiringAssetRepository acquiringAssetRepository,
                            ModelMapper modelMapper,
                            AcqAssetRepository acqAssetRepository,
                            FileStorageService fileStorageService,
                            AcquiringAssetFileRepository acquiringAssetFileRepository) {
        this.mLeadsRepo = mLeadsRepo;
        this.acquiringAssetRepository = acquiringAssetRepository;
        this.modelMapper = modelMapper;
        this.acqAssetRepository = acqAssetRepository;
        this.fileStorageService = fileStorageService;
        this.acquiringAssetFileRepository = acquiringAssetFileRepository;
    }


    @Override
    public boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model) {
        return false;
    }

    @Override
    public List<ObjectNode> loadTargets() {
        return null;
    }

    @Override
    public ResponseEntity<?> addAsset(String assetDetails, MultipartFile[] assetFiles) {
        try {
            if (assetDetails == null) {
                return ResponseEntity.badRequest().body("Asset details are required");
            }
            List<String> filePathList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            AcquiringAddAssetRequest acquiringAddAssetRequest =
                    mapper.readValue(assetDetails,AcquiringAddAssetRequest.class);
            Optional<AcqAsset> optionalAsset = acqAssetRepository.findById(
                    acquiringAddAssetRequest.getDeviceId());
            AcquiringAssetEntity acquiringAssetEntity = new AcquiringAssetEntity();
            acquiringAssetEntity.setAssetType(optionalAsset.get());
            acquiringAssetEntity.setSerialNumber(acquiringAddAssetRequest.getSerialNumber());
            acquiringAssetEntity.setCondition(acquiringAddAssetRequest.getCondition());

            AcquiringAssetEntity savedAsset = acquiringAssetRepository.save(acquiringAssetEntity);
            //save files
            filePathList =fileStorageService.saveMultipleFileWithSpecificFileName(
                    "Asset_",assetFiles);
            filePathList.forEach(filePath ->{
                AcquiringAssetFilesEntity assetFilesEntity = new AcquiringAssetFilesEntity();
                assetFilesEntity.setAcquiringAssetEntity(savedAsset);
                assetFilesEntity.setFilePath(filePath);
                acquiringAssetFileRepository.save(assetFilesEntity);
            });

        } catch (JsonMappingException e) {
            return ResponseEntity.badRequest().body("Invalid asset details");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public ResponseEntity<?> getAllAssets() {
        try {
            List<AcquiringAssetEntity> acquiringAssetEntityList=acquiringAssetRepository.findAll();
        } catch (Exception e) {
            log.error("Error occurred while fetching all assets",e);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getAssetById(Long id) {
        try {
            AcquiringAssetEntity acquiringAssetEntity=acquiringAssetRepository.findById(id).get();
        } catch (Exception e) {
            log.error("Error occurred while fetching asset by id",e);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> deleteAssetById(Long id) {
        try {
            acquiringAssetRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error occurred while deleting asset by id",e);
        }
        return null;
    }


    @Override
    public List<ObjectNode> loadQuestionnaires() {
        return null;
    }



}
