package com.ekenya.rnd.backend.fskcb.AdminModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AdminModule.AddBranchRequest;
import com.ekenya.rnd.backend.fskcb.AdminModule.services.ISettingsService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class SettingsVC {

    @Autowired
    ObjectMapper mObjectMapper;
    @Autowired
    ISettingsService settingsServices;

    @PostMapping("/settings-create-branch")
    public ResponseEntity<?> createBranch(@RequestBody AddBranchRequest request){
        //
        //
        boolean success = settingsServices.createBranch(request);

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
    @PostMapping(value = "/settings-get-all-branches")
    public ResponseEntity<?> getAllBranches() {


        //
        ArrayNode resp = settingsServices.loadAllBranches();

        //Response
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    //creating email endpoint
}
