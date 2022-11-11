package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VoomaAssetsVC {

    private final AssetService assetService;

    public VoomaAssetsVC(AssetService assetService) {
        this.assetService = assetService;
    }

    @RequestMapping(value = "/vooma-get-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAssets() {
        return assetService.getAllAssets();
    }

    @RequestMapping(value = "/vooma-create-asset", method = RequestMethod.POST)
    public  ResponseEntity<?> createAssetManagement( @RequestParam("assetDetails") String assetDetails,
                                                     @RequestParam("assetFiles") MultipartFile[] assetFiles,
                                                     HttpServletRequest httpServletRequest) {
        return assetService.addToAssetManagement(assetDetails, assetFiles, httpServletRequest);
    }
}
