package com.ekenya.rnd.backend.fskcb.service.impl;

import com.ekenya.rnd.backend.fskcb.entity.Asset;
import com.ekenya.rnd.backend.fskcb.entity.AssetFiles;
import com.ekenya.rnd.backend.fskcb.entity.AssetManagement;
import com.ekenya.rnd.backend.fskcb.entity.UserAccType;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import com.ekenya.rnd.backend.fskcb.payload.AssetManagementRequest;
import com.ekenya.rnd.backend.fskcb.repository.AssetFilesRepository;
import com.ekenya.rnd.backend.fskcb.repository.AssetManagementRepository;
import com.ekenya.rnd.backend.fskcb.repository.AssetRepository;
import com.ekenya.rnd.backend.fskcb.repository.UserAccTypeRepository;
import com.ekenya.rnd.backend.fskcb.service.AssetService;
import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetManagementRepository assetManagementRepository;

    @Autowired
    private UserAccTypeRepository userAccountTypeRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AssetFilesRepository assetFilesRepository;


    @Override
    public ResponseEntity<?> getAllAssets() {
        ConcurrentHashMap<String, Object> responseObject = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Object> responseParams = new ConcurrentHashMap<>();
        try {
            List<Asset> listAsset = assetRepository.findAssetByStatus(Status.ACTIVE);
            if (listAsset.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No assets available");
                responseParams.put("assets",listAsset);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "Assets available");
                responseParams.put("assets",listAsset);
                responseObject.put("data", responseParams);
            }
           return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }
    @Override
    public ResponseEntity<?> addToAssetManagement(
            String assetDetails, MultipartFile[] assetFiles,
            HttpServletRequest httpServletRequest) {
        ConcurrentHashMap<String, Object> responseObject = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Object> responseParams = new ConcurrentHashMap<>();
        try {
            if (assetDetails == null)throw new RuntimeException("Bad request");
            UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String userId=userDetails.getUsername();
            List<String> filePathList = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            AssetManagementRequest assetManagementRequest =
                    mapper.readValue(assetDetails,AssetManagementRequest.class);
            Optional<Asset> optionalAsset = assetRepository.findById(
                   assetManagementRequest.getAssetId());
            Optional<UserAccType> optionalUserAccountType = userAccountTypeRepository.findById(
                    assetManagementRequest.getUserAccountType());
            AssetManagement assetManagement = new AssetManagement();
            assetManagement.setAsset(optionalAsset.get());
            assetManagement.setUserAccountType(optionalUserAccountType.get());
            assetManagement.setSerialNumber(assetManagementRequest.getSerialNumber());
            assetManagement.setAccountNo(assetManagementRequest.getAccountNo());
            assetManagement.setCreatedBy(userId);
            assetManagement.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            assetManagement.setStatus(Status.ACTIVE);
            AssetManagement assetManagement1 = assetManagementRepository.save(assetManagement);
            filePathList =fileStorageService.saveMultipleFileWithSpecificFileName(
                    "Asset_",assetFiles);
            filePathList.forEach(filePath ->{
                AssetFiles assetFilesEntity = new AssetFiles();
                assetFilesEntity.setAssetManagement(assetManagement1);
                assetFilesEntity.setFilePath(filePath);
                assetFilesRepository.save(assetFilesEntity);
            });
            responseObject.put("status", "success");
            responseObject.put("message", "Created successfully");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }
}
