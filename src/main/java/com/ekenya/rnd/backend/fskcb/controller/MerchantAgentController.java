package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.fskcb.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/merchant")
@CrossOrigin(origins = "*")
public class MerchantAgentController {
    @Autowired
    MerchantService merchantService;
    @Autowired
    FileStorageService fileStorageService;

    @PostMapping("/create-merchant_agent-account")
    public ResponseEntity<?> createMerchantAccount(@RequestParam("merchDetails") String merchDetails,
                                                   @RequestParam("frontID") MultipartFile frontID,
                                                   @RequestParam("backID") MultipartFile backID,
                                                   @RequestParam("kraPinCertificate") MultipartFile kraPinCertificate,
                                                   @RequestParam("certificateOFGoodConduct") MultipartFile certificateOFGoodConduct,
                                                   @RequestParam("businessLicense") MultipartFile businessLicense,
                                                   @RequestParam("fieldApplicationForm") MultipartFile fieldApplicationForm,
                                                   @RequestParam("shopPhoto") MultipartFile shopPhoto,
                                                   @RequestParam("customerPhoto") MultipartFile customerPhoto,
                                                   @RequestParam("companyRegistrationDoc") MultipartFile companyRegistrationDoc,
                                                   @RequestParam("signatureDoc") MultipartFile signatureDoc,
                                                   @RequestParam("businessPermitDoc") MultipartFile businessPermitDoc,
                                                   HttpServletRequest httpServletRequest){
        return merchantService.addMerchant(frontID, backID, kraPinCertificate,certificateOFGoodConduct,
                businessLicense, fieldApplicationForm, merchDetails,
                shopPhoto, customerPhoto,
                companyRegistrationDoc, signatureDoc, businessPermitDoc, httpServletRequest);
    }
    @RequestMapping(value = "/get-merchant-details/{merchantid}", method = RequestMethod.POST)
    public  ResponseEntity<?> getMerchantDetails(@PathVariable String merchantid) {
        return merchantService.findMerchantByAccountNumber(merchantid);
    }

    @RequestMapping(value = "/get-merchant-detail-geo-map-details/{merchantid}", method = RequestMethod.POST)
    public  ResponseEntity<?> getMerchantGeomapDetails(@PathVariable String merchantid) {
        return merchantService.findMerchantGeoMapDetails(merchantid);
    }

    @RequestMapping(value = "/get-merchants-by-sys-user-id/{sys_user_id}", method = RequestMethod.POST)
    public ResponseEntity<?> getMerchantsByDSRId(@PathVariable  long sys_user_id) {
        return merchantService.getAllMerchantDetailByAgentId(sys_user_id);
    }
    @RequestMapping(value = "/get-merch-agent-account-types", method = RequestMethod.GET)
    public ResponseEntity<?> getMerchantAccountType() {
        return merchantService.getAllMerchantAccountTypes();
    }

    @GetMapping("fileupload/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println("filename "+filename);
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @GetMapping("fileupload2/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile2(@PathVariable String filename) {
        System.out.println("filename "+filename);
        Resource file = fileStorageService.load(filename);
        MimeType mimeType = (file.getFilename().endsWith("PNG")) ? MimeTypeUtils.IMAGE_PNG : MimeTypeUtils.IMAGE_JPEG;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(mimeType))
                .body(file);
    }

    @RequestMapping(value = "/get-agents-by-sys-user-id/{sys_user_id}", method = RequestMethod.POST)
    public ResponseEntity<?> getAgentsByDSRId(@PathVariable  long sys_user_id) {
        return merchantService.getAllAgentsByAgentId(sys_user_id);

    }
}
