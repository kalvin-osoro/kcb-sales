package com.deltacode.kcb.DFSModule.controller;

import com.deltacode.kcb.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/api/portal/dfs-assets")
public class DFSAssetManagementController {

    private final AssetService assetService;

    public DFSAssetManagementController(AssetService assetService) {
        this.assetService = assetService;
    }

    @RequestMapping(value = "/get-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAssets() {
        return assetService.getAllAssets();
    }

    @RequestMapping(value = "/create-asset-management", method = RequestMethod.POST)
    public  ResponseEntity<?> createAssetManagement( @RequestParam("assetDetails") String assetDetails,
                                                     @RequestParam("assetFiles") MultipartFile[] assetFiles,
                                                     HttpServletRequest httpServletRequest) {
        return assetService.addToAssetManagement(assetDetails, assetFiles, httpServletRequest);
    }
}
