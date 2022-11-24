package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAddAssetReportRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAssignAssetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaCollectAssetRequest;
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
    public ResponseEntity<?> getAllMerchantAssets(@RequestBody Long merchantId) {
        List<?> assets = voomaChannelService.getAllAgentMerchantAssets(merchantId);
        boolean success = assets != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((ArrayNode) objectMapper.valueToTree(assets));
//          node.put("id",0);

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
}
