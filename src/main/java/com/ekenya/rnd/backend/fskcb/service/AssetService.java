package com.ekenya.rnd.backend.fskcb.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface AssetService {
    ResponseEntity<?> getAllAssets();
    ResponseEntity<?> addToAssetManagement(String assetDetails, MultipartFile[] assetFiles,
                                           HttpServletRequest httpServletRequest);
}
