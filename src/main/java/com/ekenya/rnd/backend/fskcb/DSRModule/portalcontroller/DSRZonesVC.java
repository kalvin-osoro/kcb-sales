package com.ekenya.rnd.backend.fskcb.DSRModule.portalcontroller;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.ZoneRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ExportZonesRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.ImportZonesRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/dsr-zones")
public class DSRZonesVC {
    //
    @PostMapping("/dsr-zones-create")
    public ResponseEntity<?> createZone(@RequestBody ZoneRequest leadRequest ) {
        return null;
    }
    @PostMapping(value = "/dsr-zones-get-all")
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
