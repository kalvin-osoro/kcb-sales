package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.channelcontrollers;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.QuestionRequetDto;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyChannelService;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/ch")
public class AgencyChannelCustomerVisitsVC {

    @Autowired
    IAgencyChannelService agencyChannelService;
    @PostMapping("/agency-create-customer-visit")
    public ResponseEntity<?> createCustomerVisit(@RequestParam("visitDetails") String visitDetails,
                                                 @RequestParam("premiseInsidePhoto") MultipartFile premiseInsidePhoto,
                                                 @RequestParam("premiseOutsidePhoto") MultipartFile premiseOutsidePhoto,
                                                 @RequestParam("tariffPhoto") MultipartFile tariffPhoto,
                                                 @RequestParam("cashRegisterPhoto") MultipartFile cashRegisterPhoto){
        boolean success= agencyChannelService.createCustomerVisit(visitDetails,premiseInsidePhoto,premiseOutsidePhoto,tariffPhoto,cashRegisterPhoto);
        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//            node.put("message","Customer Visit Created Successfully");
//          node.put("id",0);

            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }

    @PostMapping("/agency-update-customer-visit")
    public ResponseEntity<?> updateCustomerVisit(@RequestBody AgencyCustomerVisitsRequest request) {


        //TODO; INSIDE SERVICE
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

    @PostMapping(value = "/agency-get-all-customer-visits")
    public ResponseEntity<?> getAllCustomerVisitsByDSR(@RequestBody int dsrId) {


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

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

//    @PostMapping(value = "/get-all-question")
//    public ResponseEntity<?> getAllQuestion(@RequestBody QuestionRequetDto model) {
//        List<?> list = agencyChannelService.getAllQuestion(model);
//        boolean success = list != null;
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ArrayNode node = objectMapper.createArrayNode();
//            node.addAll((ArrayNode) objectMapper.valueToTree(list));
////          node.put("id",0);
//
//            return ResponseEntity.ok(new BaseAppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new BaseAppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }

}
