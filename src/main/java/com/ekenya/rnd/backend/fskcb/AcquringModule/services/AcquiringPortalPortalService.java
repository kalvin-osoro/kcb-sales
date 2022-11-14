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
import com.ekenya.rnd.backend.fskcb.files.IFileStorageService;
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
    private final AcquiringAssetRepository acquiringAssetRepository;
    private final ModelMapper modelMapper;
    private final AcqAssetRepository acqAssetRepository;
    private final IFileStorageService IFileStorageService;
    private final AcquiringAssetFileRepository acquiringAssetFileRepository;

    public AcquiringPortalPortalService(IAcquiringLeadsRepository mLeadsRepo,
                                        AcquiringAssetRepository acquiringAssetRepository,
                                        ModelMapper modelMapper,
                                        AcqAssetRepository acqAssetRepository,
                                        IFileStorageService IFileStorageService,
                                        AcquiringAssetFileRepository acquiringAssetFileRepository) {
        this.mLeadsRepo = mLeadsRepo;
        this.acquiringAssetRepository = acquiringAssetRepository;
        this.modelMapper = modelMapper;
        this.acqAssetRepository = acqAssetRepository;
        this.IFileStorageService = IFileStorageService;
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
            filePathList = IFileStorageService.saveMultipleFileWithSpecificFileName("Asset_",assetFiles);
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



}
