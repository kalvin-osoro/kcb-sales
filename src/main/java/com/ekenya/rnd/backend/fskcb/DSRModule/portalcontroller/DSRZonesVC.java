package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddZoneRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ExportZonesRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ImportZonesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/dsr-zones")
public class DSRZonesVC {
    //
    @PostMapping("/dsr-zones-create")
    public ResponseEntity<?> createZone(@RequestBody AddZoneRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/dsr-zones-get-all", method = RequestMethod.GET)
    public ResponseEntity<?> getAllZones() {
        return null;
    }
    @PostMapping("/dsr-zones-import")
    public ResponseEntity<?> importZones(@RequestBody ImportZonesRequest leadRequest ) {
        return null;
    }
    @PostMapping("/dsr-zones-export")
    public ResponseEntity<?> exportZones(@RequestBody ExportZonesRequest leadRequest ) {
        return null;
    }
}
