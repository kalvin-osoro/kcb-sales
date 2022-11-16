package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.IDSRPortalService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@Api(value = "DSR Rest Api")
@RestController()

@RequestMapping
public class DSRSetupVC {
    private final IDSRPortalService dsrService;

    public DSRSetupVC(IDSRPortalService dsrService) {
        this.dsrService = dsrService;
    }


    @PostMapping(value = "/dsr-setups-add-dsr")
    public ResponseEntity<?> addDSR(@RequestBody DSRRequest dsrRequest, HttpServletRequest httpServletRequest) {
        return dsrService.addDSR(dsrRequest, httpServletRequest);
    }
    @PostMapping(value = "/dsr-setups-get-all-dsrs")
    public ResponseEntity<?> getAllDSRs(HttpServletRequest httpServletRequest) {
        return dsrService.getAllDSRs(httpServletRequest);
    }
    @PostMapping(value = "/dsr-setups-delete-dsr/{id}")
    public ResponseEntity<?> deleteDSRById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return dsrService.deleteDSRById(id, httpServletRequest);
    }


    @PostMapping(value = "/dsr-setups-get-dsr-profile")
    public  ResponseEntity<?>getDSRProfile(HttpServletRequest httpServletRequest){
        return dsrService.getDSRProfile(httpServletRequest);
    }
}
