package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddRegionRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.UpdateRegionRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1")
public class DSRRegionsVC {
    //
    @Autowired
    ObjectMapper mObjectMapper;

    @Autowired
    IDSRPortalService dsrPortalService;
    @PostMapping("/dsr-create-region")
    public ResponseEntity<?> createRegion(@RequestBody AddRegionRequest request ) {

        //INSIDE SERVICE
        boolean success = dsrPortalService.createRegion(request);

        //Response
        if(success){
            //Object
            ObjectNode node = mObjectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping(value = "/dsr-get-all-regions")
    public ResponseEntity<?> getAllRegions() {
        //TODO; INSIDE SERVICE
        ArrayNode list = dsrPortalService.getAllRegions();

        //Response
        if(list != null){
            //Object
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/dsr-import-regions")
    public ResponseEntity<?> importRegion(@RequestBody MultipartFile file) {
        //TODO; INSIDE SERVICE
        ObjectNode result = dsrPortalService.attemptImportRegions(file);

        //Response
        if(result != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,result,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-update-region")
    public ResponseEntity<?> updateRegions(@RequestBody UpdateRegionRequest request ) {

        //TODO; INSIDE SERVICE
        boolean success = dsrPortalService.updateRegion(request);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
