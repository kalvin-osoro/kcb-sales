package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ExportDSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1")
@PreAuthorize("hasAuthority('"+ SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
public class DSRAccountsVC {
    @Autowired
    ObjectMapper mObjectMapper;
    //
    @Autowired
    IDSRPortalService dsrPortalService;
    //
    @PostMapping("/dsr-accounts-create")
    public ResponseEntity<?> createAccount(@RequestBody AddDSRAccountRequest request ) {

        //INSIDE SERVICE
        boolean success = dsrPortalService.addDSR(request);

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
    @PostMapping(value = "/dsr-get-accounts-all")
    public ResponseEntity<?> getAllAccounts() {

        //INSIDE SERVICE
        ArrayNode list = dsrPortalService.getAllDSRAccounts();

        //Response
        if(list != null){
            //Object
            //ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,list,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,mObjectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-accounts-import")
    public ResponseEntity<?> importAccounts(@RequestBody MultipartFile file ) {

        //INSIDE SERVICE
        ObjectNode resp = dsrPortalService.attemptImportAccounts(file);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(resp != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,resp,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
    @PostMapping("/dsr-accounts-export")
    public ResponseEntity<?> exportAccounts(@RequestBody ExportDSRAccountsRequest leadRequest ) {
        //INSIDE SERVICE
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

    @PostMapping("/dsr-get-details")
    public ResponseEntity<?> getDSRDetails(@RequestBody String staffNo ) {


        //INSIDE SERVICE
        ObjectNode info = dsrPortalService.getDSRProfile(staffNo);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(info != null){
            //Object

            return ResponseEntity.ok(new BaseAppResponse(1,info,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }


    @PostMapping("/dsr-get-audit-trail")
    public ResponseEntity<?> getDSRAuditTrail(@RequestBody int id ) {


        //INSIDE SERVICE
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

    @PostMapping("/dsr-unlock-user")
    public ResponseEntity<?> unlockAccount(@RequestBody String staffNo ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.unlockAccount(staffNo);

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



    @PostMapping("/dsr-lock-account")
    public ResponseEntity<?> lockAccount(@RequestBody String staffNo ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.lockAccount(staffNo);

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

    @PostMapping("/dsr-reset-pin")
    public ResponseEntity<?> resetPIN(@RequestBody String staffNo ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.resetPIN(staffNo);

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
