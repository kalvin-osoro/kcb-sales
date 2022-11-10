package com.ekenya.rnd.backend.fskcb.service.impl;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.ZoneCoordinates;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.ZoneRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.response.ZoneCoordinatesResponse;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.response.ZoneResponse;
import com.ekenya.rnd.backend.fskcb.DSRModule.repository.ZoneCoordinatesRepository;
import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.fskcb.exception.MessageResponse;
import com.ekenya.rnd.backend.fskcb.repository.ZoneRepository;
import com.ekenya.rnd.backend.fskcb.service.ZoneService;
import com.ekenya.rnd.backend.fskcb.utils.Status;
import com.ekenya.rnd.backend.fskcb.utils.Utility;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;
    private final ZoneCoordinatesRepository zoneCoordinatesRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneCoordinatesRepository zoneCoordinatesRepository) {
        this.zoneRepository = zoneRepository;
        this.zoneCoordinatesRepository = zoneCoordinatesRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> addZone(ZoneRequest zoneRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if(zoneRequest==null) throw new RuntimeException("Bad request");
            Zone zone = new Zone();
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
            if (userDetails == null)
                throw new RuntimeException("Service error");
            String createdBy = userDetails.getUsername();
            zone.setZoneName(zoneRequest.getZoneName());
            zone.setStatus(Status.ACTIVE);
            zone.setCreatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            zone.setCreatedBy(createdBy);
            Zone zone1 = zoneRepository.save(zone);
            ZoneCoordinates zoneCoordinates;
            if(zoneRequest.getCoordinatesList().size()<0)
                throw new RuntimeException("Add zone coordinates");
            List<JsonObject> coordinatesList = zoneRequest.getCoordinatesList();
            for (int i = 0; i<coordinatesList.size(); i++){
                zoneCoordinates = new ZoneCoordinates();
                JsonObject objCoordinate = coordinatesList.get(i);
                BigDecimal latitude = objCoordinate.get("lat").getAsBigDecimal();
                BigDecimal longitude  = objCoordinate.get("lng").getAsBigDecimal();
                zoneCoordinates.setZone(zone1);
                zoneCoordinates.setLatitude(latitude);
                zoneCoordinates.setLongitude(longitude);
                zoneCoordinatesRepository.save(zoneCoordinates);
            }
            responseObject.put("status", "success");
            responseObject.put("message", "Zone saved successfully");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            responseObject.put("status", "failed");
            responseObject.put("message", e.getMessage());
            return ResponseEntity.ok().body(responseObject);
        }
    }

    @Override
    public ResponseEntity<?> getAllZones(HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            List<Zone> zoneList = zoneRepository.findAllByStatus(Status.ACTIVE);
            List<ZoneResponse> zoneResponseList = new ArrayList<>();
            ZoneResponse zoneResponse;
            for (int i=0; i<zoneList.size(); i++){
                zoneResponse = new ZoneResponse();
                zoneResponse.setId(zoneList.get(i).getId());
                zoneResponse.setZoneName(zoneList.get(i).getZoneName());
                zoneResponse.setStatus(zoneList.get(i).getStatus());
                List<ZoneCoordinatesResponse> zoneCoordinatesResponseList
                        = new ArrayList<>();
                ZoneCoordinatesResponse zoneCoordinatesResponse;
                for (int j=0; j<zoneList.get(i).getCoordinatesList().size(); j++){
                    zoneCoordinatesResponse = new ZoneCoordinatesResponse();
                    zoneCoordinatesResponse.setLatitude(
                            zoneList.get(i).getCoordinatesList().get(j).getLatitude()
                    );
                    zoneCoordinatesResponse.setLongitude(
                            zoneList.get(i).getCoordinatesList().get(j).getLongitude()
                    );
                    zoneCoordinatesResponseList.add(zoneCoordinatesResponse);
                }
                zoneResponse.setCoordinatesList(zoneCoordinatesResponseList);
                zoneResponseList.add(zoneResponse);
            }

            if (zoneResponseList.isEmpty()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No zones available");
                responseParams.put("zoneList",zoneResponseList);
                responseObject.put("data", responseParams);
            }else {
                responseObject.put("status", "success");
                responseObject.put("message", "Zones available");
                responseParams.put("zoneList",zoneResponseList);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> getAllZoneById(long id, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        try {
            Optional<Zone> optionalZone = zoneRepository.findById(id);
            if (!optionalZone.isPresent()) {
                responseObject.put("status", "success");
                responseObject.put("message", "No zone available");
                responseParams.put("zone",optionalZone.get());
                responseObject.put("data", responseParams);
            }else {
                ZoneResponse zoneResponse = new ZoneResponse();
                zoneResponse.setId(optionalZone.get().getId());
                zoneResponse.setZoneName(optionalZone.get().getZoneName());
                zoneResponse.setStatus(optionalZone.get().getStatus());

                List<ZoneCoordinatesResponse> zoneCoordinatesResponseList = optionalZone.get().getCoordinatesList().stream()
                        .map(zone -> ZoneCoordinatesResponse.builder()
                                .latitude(zone.getLatitude())
                                .longitude(zone.getLongitude())
                                .build()
                        )
                        .collect(Collectors.toList());

                zoneResponse.setCoordinatesList(zoneCoordinatesResponseList);


                responseObject.put("status", "success");
                responseObject.put("message", "Zone details");
                responseParams.put("zone",zoneResponse);
                responseObject.put("data", responseParams);
            }
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity
                    .ok()
                    .header("X-XSS-Protection","1")
                    .body(new MessageResponse(e.getMessage(),"failed"));
        }
    }

    @Override
    public ResponseEntity<?> deleteZone(long id, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            Optional<Zone> optionalZone = zoneRepository.findById(id);
            Zone zone = optionalZone.get();
            zone.setStatus(Status.DELETED);
            zoneRepository.save(zone);
            responseObject.put("status", "success");
            responseObject.put("message", "Successfully deleted");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }

    }

    @Override
    public ResponseEntity<?> editZone(ZoneRequest zoneRequest, HttpServletRequest httpServletRequest) {
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            if(zoneRequest == null)throw new RuntimeException( "Bad request");
            Optional<Zone> optionalZone = zoneRepository.findById(zoneRequest.getId());
            Zone zone = optionalZone.get();
            UserDetails userDetailsObject =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetailsObject == null)
                throw new RuntimeException("Service error");
            String createdBy = userDetailsObject.getUsername();
            zone.setZoneName(zoneRequest.getZoneName());
            zone.setStatus(zone.getStatus());
            zone.setUpdatedOn(Utility.getPostgresCurrentTimeStampForInsert());
            zone.setUpdatedBy(createdBy);
            zoneRepository.save(zone);
            responseObject.put("status", "success");
            responseObject.put("message", "Successfully edited");
            return ResponseEntity.ok().body(responseObject);
        }catch (Exception e){
            return ResponseEntity.ok().body(new MessageResponse(e.getMessage(),"failed"));
        }
    }
}

