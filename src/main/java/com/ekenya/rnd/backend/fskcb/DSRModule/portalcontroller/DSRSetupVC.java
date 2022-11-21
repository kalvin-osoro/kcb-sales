package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@Api(value = "DSR Rest Api")
@RestController
@RequestMapping(path = "/api/v1")
public class DSRSetupVC {
    private final IDSRPortalService dsrService;

    public DSRSetupVC(IDSRPortalService dsrService) {
        this.dsrService = dsrService;
    }


}
