package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.DSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.SectorRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
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
@RequestMapping(path = "/api/v1")
public class SectorVC {
    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    IDSRPortalService dsrPortalService;

    @PostMapping("/create-sector")
    public ResponseEntity<?> createAccount(@RequestBody SectorRequest model ) {

        //INSIDE SERVICE
        boolean success = dsrPortalService.addSector(model);

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

    @PostMapping(value = "/get-all-sector")
    public ResponseEntity<?> getAllSectors() {

        //INSIDE SERVICE
        List<?>list = dsrPortalService.getAllSectors();

        //Response
        if(list != null){
            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
