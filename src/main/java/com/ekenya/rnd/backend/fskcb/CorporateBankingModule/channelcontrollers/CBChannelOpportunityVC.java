package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBChannelOpportunityRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBChannelService;
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

import javax.validation.constraints.NotNull;
import java.util.List;
@RestController
@RequestMapping(path = "/api/v1/ch")
public class CBChannelOpportunityVC {

    @Autowired
    ICBChannelService channelService;


    @PostMapping("/cb-get-all-opportunities")
    ResponseEntity<?> getAllOpportunities( ){
        return getResponseEntity(channelService.getAllOpportunities());
    }

    @NotNull
    public static ResponseEntity<?> getResponseEntity(List<ObjectNode> allOpportunities) {
        List<?> response = allOpportunities;
        boolean success = response != null;
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            ArrayNode node = objectMapper.createArrayNode();
            node.addAll((List)response);
            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

        }
    }
    //update the opportunity
    @PostMapping("/cb-update-opportunity")
    public ResponseEntity<?> updateOpportunity(@RequestBody CBChannelOpportunityRequest request) {
        boolean success = channelService.updateOpportunity(request);
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
