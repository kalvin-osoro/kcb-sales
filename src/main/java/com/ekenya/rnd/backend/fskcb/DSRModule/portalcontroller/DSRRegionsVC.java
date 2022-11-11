package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/dsr-regions")
public class DSRRegionsVC {
    //TODO: implement the reports controller for all required dfs reports
    @PostMapping("/dsr-regions-create")
    public ResponseEntity<?> createRegion(@RequestBody AddRegionRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/dsr-regions--get-all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRegions() {
        return null;
    }
    @PostMapping("/dsr-regions-import")
    public ResponseEntity<?> importRegions(@RequestBody ImportRegionsRequest leadRequest ) {
        return null;
    }
    @PostMapping("/dsr-regions-export")
    public ResponseEntity<?> exportRegions(@RequestBody ExportDSRRegionsRequest leadRequest ) {
        return null;
    }
}
