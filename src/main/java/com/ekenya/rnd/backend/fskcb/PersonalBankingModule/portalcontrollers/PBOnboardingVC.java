package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.service.FileStorageService;
import com.ekenya.rnd.backend.fskcb.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController("/personal-banking-onboarding")
public class PBOnboardingVC {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    MerchantService merchantDetailsService;

    @PostMapping("/create-personal-banking-merchant-account")
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
}
