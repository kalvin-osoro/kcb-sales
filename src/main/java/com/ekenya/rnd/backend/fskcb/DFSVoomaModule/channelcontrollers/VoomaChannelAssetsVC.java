package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AssetByIdRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaChannelService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class VoomaChannelAssetsVC {
    @Autowired
    IVoomaChannelService voomaChannelService;


    @PostMapping("/vooma-assign-asset-merchant")
    public ResponseEntity<?> assignAssetMerchant(@RequestBody VoomaAssignAssetRequest model) {
            boolean success =voomaChannelService.assignAssetToMerchant(model);

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
    @PostMapping("/vooma-assign-asset-agent")
    public ResponseEntity<?> assignAssetToAgent(@RequestBody VoomaAssignAssetRequest model) {
        boolean success =voomaChannelService.assignAssetToAgent(model);



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

    @PostMapping(value = "/vooma-get-all-merchant-assets")
    public ResponseEntity<?> getAllMerchantAssets(@RequestBody CustomerAssetsRequest model) {
        List<?> assets = voomaChannelService.getAllAgentMerchantAssets(model);
        boolean success = assets != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(assets));

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/vooma-create-asset-report")
    public ResponseEntity<?> createAssetReport(@RequestBody VoomaAddAssetReportRequest request) {

        boolean success = false;//acquiringService..(model);
        //TODO: Implement this

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


    @PostMapping("/vooma-recollect-asset")
    public ResponseEntity<?> recollectAsset(@RequestBody VoomaCollectAssetRequest model) {
        boolean success = voomaChannelService.recollectAsset(model);
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
    @PostMapping("/vooma-get-asset-by-id")
    public ResponseEntity<?> viewVoomaAsset(@RequestBody AssetByIdRequest model  ) {
        Object asset = voomaChannelService.getAssetById(model);
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

//    @PostMapping(value = "/vooma-get-all-agent-assets")
//    public ResponseEntity<?> getAllAgentAssets(@RequestBody CustomerAssetsRequest model) {
//        List<?> assets = voomaChannelService.getAllAgentAsset(model);
//        boolean success = assets != null;
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ArrayNode node = objectMapper.createArrayNode();
//            node.addAll((ArrayNode) objectMapper.valueToTree(assets));
//
//            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }

    @PostMapping("/vooma-recollect-agent-asset")
    public ResponseEntity<?> recollectAgentAsset(@RequestBody VoomaCollectAssetRequest model) {
        boolean success = voomaChannelService.recollectAgentAsset(model);
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

    @PostMapping("/vooma-get-agent-asset-by-id")
    public ResponseEntity<?> viewVoomaAgentAsset(@RequestBody AssetByIdRequest model  ) {
        Object asset = voomaChannelService.getAgentAssetById(model);
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
