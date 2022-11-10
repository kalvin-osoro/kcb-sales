package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.ZoneRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface ZoneService {
    ResponseEntity<?> addZone(ZoneRequest zoneRequest, HttpServletRequest httpServletRequest);
    ResponseEntity<?> getAllZones(HttpServletRequest httpServletRequest);
    ResponseEntity<?> getAllZoneById(long id, HttpServletRequest httpServletRequest);
    ResponseEntity<?> deleteZone(long id, HttpServletRequest httpServletRequest);
    ResponseEntity<?> editZone(ZoneRequest zoneRequest, HttpServletRequest httpServletRequest);
}
