package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyProductRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services.IAgencyPortalService;
import com.ekenya.rnd.backend.responses.AppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class AgencySetupVC {

    @Autowired
    IAgencyPortalService agencyService;

    @RequestMapping(value = "/agency-setups-add-product",method = RequestMethod.POST)
    public ResponseEntity<?> addDFSProduct(@RequestBody AgencyProductRequest productRequest){


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }


        //Response
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
    @RequestMapping(value = "agency-setups-get-all-products",method = RequestMethod.GET)
    public ResponseEntity<?>getAllProducts(){

        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ArrayNode node = objectMapper.createArrayNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }
        //Response
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));

    }
    //edit product
    @RequestMapping(value = "/agency-setups-edit-product",method = RequestMethod.PUT)
    public ResponseEntity<?>editProduct(@RequestBody AgencyProductRequest productRequest){


        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }


        //Response
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }
    //delete product
    @RequestMapping(value = "/agency-setups-delete-product",method = RequestMethod.DELETE)
    public ResponseEntity<?>deleteProduct(@RequestBody AgencyProductRequest productRequest){

        //TODO; INSIDE SERVICE
        boolean success = false;//acquiringService..(model);

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(success){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }


        //Response
        return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));

    }




    @PostMapping("/agency-create-questionnaire")
    public ResponseEntity<?> createQuestionnaire(@RequestBody AcquiringAddQuestionnaireRequest assetManagementRequest) {


        //TODO;
        boolean success = false;//acquiringService..(model);

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

    @RequestMapping(value = "/agency-get-all-questionnaires", method = RequestMethod.GET)
    public ResponseEntity<?> getAllQuestionnaires() {

        //
        List<ObjectNode> list = null;//agencyService.loadQuestionnaires();

        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if(list != null ){
            //Object
            ObjectNode node = objectMapper.createObjectNode();
//          node.put("id",0);

            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
        }else{

            //Response
            return ResponseEntity.ok(new AppResponse(0,objectMapper.createObjectNode(),"Request could NOT be processed. Please try again later"));
        }
    }
}
