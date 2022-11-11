package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.service.DSRService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@Api(value = "DSR Rest Api")
@RestController()

@RequestMapping
public class DSRSetupVC {
    private final DSRService dsrService;

    public DSRSetupVC(DSRService dsrService) {
        this.dsrService = dsrService;
    }


    @RequestMapping(value = "/dsr-setups-add-dsr", method = RequestMethod.POST)
    public ResponseEntity<?> addDSR(@RequestBody DSRRequest dsrRequest, HttpServletRequest httpServletRequest) {
        return dsrService.addDSR(dsrRequest, httpServletRequest);
    }
    @RequestMapping(value = "/dsr-setups-get-all-dsrs", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDSRs(HttpServletRequest httpServletRequest) {
        return dsrService.getAllDSRs(httpServletRequest);
    }
    @RequestMapping(value = "/dsr-setups-delete-dsr/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteDSRById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return dsrService.deleteDSRById(id, httpServletRequest);
    }


    @RequestMapping(value = "/dsr-setups-get-dsr-profile", method = RequestMethod.GET)
    public  ResponseEntity<?>getDSRProfile(HttpServletRequest httpServletRequest){
        return dsrService.getDSRProfile(httpServletRequest);
    }
}
