package com.deltacode.kcb.DSRModule.controller;

import com.deltacode.kcb.DSRModule.payload.request.DSRRequest;
import com.deltacode.kcb.payload.DSRDto;
import com.deltacode.kcb.payload.DSRResponse;
import com.deltacode.kcb.DSRModule.service.DSRService;
import com.deltacode.kcb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "DSR Rest Api")
@RestController()

@RequestMapping(path = "dsr-setup/v1")
public class DSRSetupController {
    private final DSRService dsrService;

    public DSRSetupController(DSRService dsrService) {
        this.dsrService = dsrService;
    }


    @RequestMapping(value = "/add-dsr", method = RequestMethod.POST)
    public ResponseEntity<?> addDSR(@RequestBody DSRRequest dsrRequest, HttpServletRequest httpServletRequest) {
        return dsrService.addDSR(dsrRequest, httpServletRequest);
    }
    @RequestMapping(value = "/get-all-dsrs", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDSRs(HttpServletRequest httpServletRequest) {
        return dsrService.getAllDSRs(httpServletRequest);
    }
    @RequestMapping(value = "/delete-dsr/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteDSRById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return dsrService.deleteDSRById(id, httpServletRequest);
    }


    @RequestMapping(value = "/get-dsr-profile", method = RequestMethod.GET)
    public  ResponseEntity<?>getDSRProfile(HttpServletRequest httpServletRequest){
        return dsrService.getDSRProfile(httpServletRequest);
    }
}
