package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaMerchantOnboardV1;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaMerchantDetailsRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.IVoomaPortalService;
import com.ekenya.rnd.backend.fskcb.files.FileStorageService;
import com.ekenya.rnd.backend.responses.BaseAppResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(path = "/api/v1")
public class VoomaMerchantsVC {

    @Autowired
    IVoomaPortalService acquiringService;
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/vooma-get-merchant-by-id")
    public ResponseEntity<?> getMerchantById(@RequestBody VoomaMerchantDetailsRequest model) {
        Object merchant = acquiringService.getMerchantById(model);
        boolean success = merchant != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("merchant").add(objectMapper.valueToTree(merchant));

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }


    }

    @PostMapping("/vooma-get-agent-by-id")
    public ResponseEntity<?> getAgentById(@RequestBody VoomaMerchantDetailsRequest model) {
        Object agent = acquiringService.agentById(model);
        boolean success = agent != null;


        //Response
        ObjectMapper objectMapper = new ObjectMapper();
        if (success) {
            //return merchant object
            ObjectNode node = objectMapper.createObjectNode();
            node.putArray("merchant").add(objectMapper.valueToTree(agent));

            return ResponseEntity.ok(new BaseAppResponse(1, node, "Request Processed Successfully"));
        } else {

            //Response
            return ResponseEntity.ok(new BaseAppResponse(0, objectMapper.createObjectNode(), "Request could NOT be processed. Please try again later"));
        }
    }

//    @PostMapping(value = "/vooma-get-all-merchants")
//    public ResponseEntity<?> getAllMerchantOnboarded() {
//
//
//        //TODO;
//        boolean success = false;//acquiringService..(model);
//
//        //Response
//        ObjectMapper objectMapper = new ObjectMapper();
//        if(success){
//            //Object
//            ArrayNode node = objectMapper.createArrayNode();
////          node.put("id",0);
//
//            return ResponseEntity.ok(new AppResponse(1,node,"Request Processed Successfully"));
//        }else{
//
//            //Response
//            return ResponseEntity.ok(new AppResponse(0,objectMapper.createArrayNode(),"Request could NOT be processed. Please try again later"));
//        }
//    }

//    @GetMapping("fileupload/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//        System.out.println("filename " + filename);
//        Resource file = fileStorageService.load(filename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }
//
//    @GetMapping("fileupload2/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> getFile2(@PathVariable String filename) {
//        System.out.println("filename " + filename);
//        Resource file = fileStorageService.load(filename);
//        MimeType mimeType = (file.getFilename().endsWith("PNG")) ? MimeTypeUtils.IMAGE_PNG : MimeTypeUtils.IMAGE_JPEG;
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
//                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(mimeType))
//                .body(file);
//    }

    @GetMapping("/dwldFile")
    public ResponseEntity<Resource> getFileByName(@RequestParam (value="fileName")String fileName)
    {
        try {
            Resource file = fileStorageService.loadFileAsResourceByName(fileName);
            MimeType mimeType = (file.getFilename().endsWith("PNG")) ? MimeTypeUtils.IMAGE_PNG : MimeTypeUtils.IMAGE_JPEG;
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, String.valueOf(mimeType))
                    .body(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





}
