package com.ekenya.rnd.backend.fskcb.AcquringModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssetResponse;
import com.ekenya.rnd.backend.fskcb.AcquringModule.services.IAcquiringService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

@RestController()
public class AcquiringAssetVC {

     private final IAcquiringService acquiringService;

    public AcquiringAssetVC(IAcquiringService acquiringService) {
        this.acquiringService = acquiringService;
    }

    @PostMapping("/acquiring-create-asset")
    public ResponseEntity<?> createAsset(@RequestParam("assetDetails") String assetDetails,
                                         @RequestParam("assetFiles") MultipartFile[] assetFiles) {
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String,Object> responseObj=new LinkedHashMap<>();
        try {
            AcquiringAddAssetRequest model = new ObjectMapper().readValue(assetDetails, AcquiringAddAssetRequest.class);
            ResponseEntity<?> response = acquiringService.addAsset(assetDetails, assetFiles);
            if (true) {
                responseObj.put("asset", response.getBody());
                responseParams.put("status", 1);
                responseParams.put("message", "Asset created successfully");
                responseParams.put("data", responseObj);
                return AppResponse.build(responseParams);
            } else {
                responseParams.put("status", 0);
                responseParams.put("message", "Asset creation failed");
                responseParams.put("data", responseObj);
                return AppResponse.build(responseParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseParams.put("status", "error");
            responseParams.put("message", "Asset creation failed");
            responseParams.put("data", responseObj);
            return AppResponse.build(responseParams);
        }

    }

    @RequestMapping(value = "/acquiring-get-all-assets", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAsset() {
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
      try {
                List<AcquiringAssetResponse> acquiringAssetResponse = (List<AcquiringAssetResponse>) acquiringService.getAllAssets();
                responseParams.put("assets", acquiringAssetResponse);
                responseObject.put("status", 1);
                responseObject.put("message", "Assets fetched successfully");
                responseObject.put("data", responseParams);
                return AppResponse.build(responseObject);
            } catch (Exception e) {
                e.printStackTrace();
                responseObject.put("status", 0);
                responseObject.put("message", "Assets fetching failed");
                responseObject.put("data", responseParams);
                return AppResponse.build(responseObject);
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

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
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
    public ResponseEntity<?> getAssetById(@RequestParam long id) {
        LinkedHashMap<String,Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String,Object> responseObject = new LinkedHashMap<>();

        try {
            ResponseEntity<?> acquiringAssetResponse = acquiringService.getAssetById(id);
            responseParams.put("asset",acquiringAssetResponse);
            responseObject.put("status",1);
            responseObject.put("message","Asset fetched successfully");
            responseObject.put("data",responseParams);
            return AppResponse.build(responseObject);
        } catch (Exception e) {
            e.printStackTrace();
            responseObject.put("status",0);
            responseObject.put("message","Asset fetching failed");
            responseObject.put("data",responseParams);
            return AppResponse.build(responseObject);
        }





    }
}
