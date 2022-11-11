package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.AddTeamRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.ExportTeamsRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.ImportTeamsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
public class DSRTeamsVC {
    //TODO: implement the reports controller for all required dfs reports
    @PostMapping("/dsr-regions-create")
    public ResponseEntity<?> createTeam(@RequestBody AddTeamRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/dsr-regions-get-all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeams() {
        return null;
    }
    @PostMapping("/dsr-regions-import")
    public ResponseEntity<?> importTeams(@RequestBody ImportTeamsRequest leadRequest ) {
        return null;
    }
    @PostMapping("/dsr-regions-export")
    public ResponseEntity<?> exportTeams(@RequestBody ExportTeamsRequest leadRequest ) {
        return null;
    }
}
