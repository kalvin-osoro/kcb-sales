package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaAssetsVC {

    @Autowired
    private IVoomaPortalService voomaService;

    @PostMapping("/vooma-create-asset")
    public ResponseEntity<?> createAsset(@RequestParam("assetDetails") String assetDetails, @RequestParam("assetFiles") MultipartFile[] assetFiles) {
        boolean success = voomaService.createAsset(assetDetails, assetFiles);

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

    @PostMapping(value = "/vooma-get-all-assets")
    public ResponseEntity<?> getAllAsset() {
        List<?> assets = voomaService.getAllAssets();
        boolean success = assets != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)assets);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/vooma-sync-crm-asset")
    public ResponseEntity<?> syncCRMAssets() {

        //TODO; INSIDE SERVICE
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
    @PostMapping("/vooma-get-asset-by-id")
    public ResponseEntity<?> getAssetById(@RequestParam int id) {



        //TODO; INSIDE SERVICE
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

}
