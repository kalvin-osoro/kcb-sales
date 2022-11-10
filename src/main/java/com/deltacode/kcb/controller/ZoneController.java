package com.deltacode.kcb.controller;
import com.deltacode.kcb.DSRModule.payload.request.ZoneRequest;
import com.deltacode.kcb.payload.ZoneDto;
import com.deltacode.kcb.service.ZoneService;
import com.deltacode.kcb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@Api(value = "Zone Controller Rest Api")
@RestController()
@RequestMapping(path = "/zone")
public class ZoneController {
    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }
    @ApiOperation(value = "Create Zone Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addZone(@Valid @RequestBody ZoneRequest  zoneRequest, HttpServletRequest request) {
        return new ResponseEntity<>(zoneService.addZone(zoneRequest,request), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Zones Api")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/get-all-zones", method = RequestMethod.GET)
    public ResponseEntity<?> getAllZones(HttpServletRequest request) {
        return zoneService.getAllZones(request);
    }
//    @ApiOperation(value = "Get Zone By Id Api")
//    @PreAuthorize("hasRole('ADMIN')")
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<?> getAllZoneById(@PathVariable long id, HttpServletRequest httpServletRequest) {
//        return zoneService.getAllZones(id,httpServletRequest);
//    }
    @ApiOperation(value = "Delete Zone Api")
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/delete-zone/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteZone(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return zoneService.deleteZone(id, httpServletRequest);
    }
    @ApiOperation(value = "Edit Zone Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/edit-zone/{id}")
    public ResponseEntity<?> editZone(@Valid @RequestBody ZoneRequest zoneRequest, HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(zoneService.editZone(zoneRequest,httpServletRequest), HttpStatus.OK);
    }

}
