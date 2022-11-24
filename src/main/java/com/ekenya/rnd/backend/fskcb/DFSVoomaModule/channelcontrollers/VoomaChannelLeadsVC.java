package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaAddLeadRequest;
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
public class VoomaChannelLeadsVC {
    @Autowired
    private IVoomaChannelService voomaService;

    //Create new lead
    @PostMapping("/vooma-create-lead")
    public ResponseEntity<?> createVoomaLead(@RequestBody VoomaAddLeadRequest model) {
        boolean success = voomaService.createLead(model);
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
    @PostMapping(value = "/vooma-get-all-leadsByDsrid")
    public ResponseEntity<?> getAllLeads(@RequestBody VoomaAddLeadRequest model) {
        List<?> leads = voomaService.getAllLeadsByDsrId(model);
        boolean success = leads != null;
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)leads);
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
