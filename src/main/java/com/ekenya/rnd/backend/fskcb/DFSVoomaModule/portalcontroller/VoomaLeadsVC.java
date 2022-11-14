package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.VoomaAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.VoomaLeadsListRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaService;
import com.ekenya.rnd.backend.fskcb.payload.GetLeadRequest;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaLeadsVC {
    @Autowired
    private IVoomaService voomaService;
    //Assign lead to a sales person
    @PostMapping("/vooma-assign-lead")
    public ResponseEntity<?> createVoomaAsset(@RequestBody VoomaAssignLeadRequest model) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService.assigneLeadtoDSR(model);

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

    //List all leads
    @RequestMapping(value = "/vooma-get-all-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads(@RequestBody VoomaLeadsListRequest filters) {

        //
        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }

}
