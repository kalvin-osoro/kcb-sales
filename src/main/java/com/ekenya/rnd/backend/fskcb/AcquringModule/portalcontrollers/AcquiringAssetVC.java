package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringPortalService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class AcquiringAssetVC {
        @Autowired
     private IAcquiringPortalService acquiringService;

    @PostMapping("/acquiring-create-asset")
    public ResponseEntity<?> createAsset(@RequestParam("assetDetails") String assetDetails,
                                         @RequestParam("assetFiles") MultipartFile[] assetFiles) {


        //
        boolean success = acquiringService.addAsset(assetDetails, assetFiles);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping(value = "/acquiring-get-all-assets")
    public ResponseEntity<?> getAllAsset() {

        List<?> acquiringAssetResponse = acquiringService.getAllAssets();


        boolean success = acquiringAssetResponse == null;//

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,acquiringAssetResponse,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }




    @PostMapping("/acquiring-sync-crm-asset")
    public ResponseEntity<?> syncCRMAssets() {

        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    // Expected output =>

    //{
    //    "serialNo":"",
    //    "status":"",
    //    "dateIssued":"dd-MMM-yyyy",
    //    "dateAdded":"dd-MMM-yyyy",
    //    "issuedTo":"AgentID",//MerchantID
    //    "visitReports":[{
    //        "merchantID":"",
    //        "merchantName":"",
    //        "locationName":"",
    //        "DSRID":"",
    //        "visitDate":""
    //    }],
    //    "photos":[{"name":"","link":"/jjj.png"}]
    //}
    @PostMapping("/acquiring-get-asset-by-id")
    public ResponseEntity<?> getDFSVoomaById(@RequestBody AssetByIdRequest model  ) {
        Object asset = acquiringService.getAssetById(model);
        boolean success = asset  != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
            node.putPOJO("assets",asset);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
