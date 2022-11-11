package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.portalcontrollers;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services.ICBService;
import com.ekenya.rnd.backend.fskcb.service.CustomerDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/v1")
public class CBOnboardingVC {

    private final ICBService cbService;

    public CBOnboardingVC(ICBService service) {
        this.cbService = service;
    }

}
