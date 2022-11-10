package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.BusinessTypeService;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.LiquidationTypeService;
import com.ekenya.rnd.backend.fskcb.UserManagement.services.BankService;
import com.ekenya.rnd.backend.fskcb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/dfs/vooma-merchant")
public class VoomaMerchantOnboardingVC {
    @Autowired
    private MerchantService merchantDetailsService;

    @Autowired
    private BankService bankService;


    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LiquidationTypeService liquidationTypeService;



    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/create-vooma-merchant-account")
    public ResponseEntity<?> onboardNewMerchant(@RequestParam("merchDetails") String merchDetails,
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
        return merchantDetailsService.addMerchant(frontID, backID, kraPinCertificate,certificateOFGoodConduct,
                businessLicense, fieldApplicationForm, merchDetails,
                shopPhoto, customerPhoto,
                companyRegistrationDoc, signatureDoc, businessPermitDoc, httpServletRequest);
    }
//    @RequestMapping(value = "/get-merchant-details/{merchantid}", method = RequestMethod.POST)
//    public  ResponseEntity<?> getMerchantDetails(@PathVariable String merchantid) {
//        return merchantDetailsService.findMerchantByAccountNumber(merchantid);
//    }
    //TODO: add the rest of the endpoints
    //onboarded merchants

//    @RequestMapping(value = "/get-merchant-detail-geo-map-details/{merchantid}", method = RequestMethod.POST)
//    public  ResponseEntity<?> getMerchantGeomapDetails(@PathVariable String merchantid) {
//        return merchantDetailsService.findMerchantGeoMapDetails(merchantid);
//    }

//    @RequestMapping(value = "/get-agent-merch-account", method = RequestMethod.GET)
//    public ResponseEntity<?> getUserTypes() {
//        return userTypeService.getAllAgentMerchantUserType();
//    }

    @GetMapping("fileupload/dfs-merchant/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println("filename "+filename);
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @GetMapping("fileupload2/dfs-merchant/{filename:.+}")
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
}
