package com.deltacode.kcb.controller;

import com.deltacode.kcb.DSRModule.payload.request.DSRTeamRequest;
import com.deltacode.kcb.payload.DSRDto;
import com.deltacode.kcb.payload.DSRResponse;
import com.deltacode.kcb.DSRModule.service.DSRService;
import com.deltacode.kcb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "DSR Rest Api")
@RestController()

@RequestMapping(path = "/api/v1")
public class DSRController {
    private final DSRService dsrService;

    public DSRController(DSRService dsrService) {
        this.dsrService = dsrService;
    }

    @RequestMapping(value = "/add-dsr-team", method = RequestMethod.POST)
    public ResponseEntity<?> addDSRTeam(@RequestBody DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest) {
        return dsrService.addDSRTeam(dsrTeamRequest, httpServletRequest);
    }

    @RequestMapping(value = "/edit-dsr-team", method = RequestMethod.POST)
    public ResponseEntity<?> editDSRTeam(@RequestBody DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest) {
        return dsrService.editDSRTeam(dsrTeamRequest, httpServletRequest);
    }

    @RequestMapping(value = "/get-all-dsr-teams", method = RequestMethod.GET)
    public ResponseEntity<?> getAllDSRTeams() {
        return dsrService.getAllDSRTeams();
    }

    @RequestMapping(value = "/get-team-members/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTeamMembersByTeamId(@PathVariable long id) {
        return dsrService.getTeamMembersByTeamId(id);
    }

    @RequestMapping(value = "/delete-dsr-team/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteDSRTeam(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return dsrService.deleteDSRTeam(id, httpServletRequest);
    }

}
