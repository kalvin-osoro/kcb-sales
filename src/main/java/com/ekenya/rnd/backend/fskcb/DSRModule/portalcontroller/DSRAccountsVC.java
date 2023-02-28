package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.DSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ExportDSRAccountsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.SystemRoles;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.reps.*;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
@PreAuthorize("hasAuthority('"+ SystemRoles.SYS_ADMIN+"') or hasAuthority('"+SystemRoles.ADMIN+"')")
public class DSRAccountsVC {
    private final  ObjectMapper mObjectMapper;
    //
   private  final  IDSRPortalService dsrPortalService;
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
    public ResponseEntity<?> getAllAccounts(@RequestBody DSRAccountsRequest request) {

        //INSIDE SERVICE
        ArrayNode list = dsrPortalService.getAllDSRAccounts(request);

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
    public ResponseEntity<?> importAccounts(@RequestParam("file")  MultipartFile file,
                                            @RequestParam("profileCode") String  profileCode ) {

        //INSIDE SERVICE
        ObjectNode resp = dsrPortalService.attemptImportAccounts(file,profileCode);

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
    public ResponseEntity<?> getDSRDetails(@RequestBody DSRDetailsRequest request ) {


        //INSIDE SERVICE
        ObjectNode info = dsrPortalService.getDSRProfile(request.getStaffNo());

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
    public ResponseEntity<?> getDSRAuditTrail(@RequestBody DSRAuditTrailRequest request) {


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
    public ResponseEntity<?> unlockAccount(@RequestBody UnlockDSRAccountRequest request ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.unlockAccount(request.getStaffNo());

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
    public ResponseEntity<?> lockAccount(@RequestBody LockDSRAccountRequest request ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.lockAccount(request.getStaffNo());

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
    public ResponseEntity<?> resetPIN(@RequestBody ResetDSRPINRequest request ) {


        //INSIDE SERVICE
        boolean success = dsrPortalService.resetPIN(request.getStaffNo());

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


    @PostMapping(value = "/dsr-get-accounts-allV1")
    public ResponseEntity<?> getAllAccountsV1(@RequestBody DSRAccountsRequest model) {

        //INSIDE SERVICE
        List<?> visits = dsrPortalService.getAllDSRAccountsV1(model);
        boolean success = visits != null;

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List) visits);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createArrayNode(), "Request could NOT be processed. Please try again later"));
        }
    }
}
