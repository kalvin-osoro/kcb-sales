package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRTeamRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface DSRService {
    ResponseEntity<?> addDSRTeam(DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest);
    ResponseEntity<?> editDSRTeam(DSRTeamRequest dsrTeamRequest, HttpServletRequest httpServletRequest);
    ResponseEntity<?> getAllDSRTeams();
    ResponseEntity<?> getTeamMembersByTeamId(long id);
    ResponseEntity<?> deleteDSRTeam(long id, HttpServletRequest httpServletRequest);
    ResponseEntity<?> addDSR(DSRRequest dsrRequest, HttpServletRequest httpServletRequest);
    ResponseEntity<?> getAllDSRs(HttpServletRequest httpServletRequest);
    ResponseEntity<?> deleteDSRById(long id, HttpServletRequest httpServletRequest);
//    ResponseEntity<?> editDSRById(DSRRequest dsrRequest, HttpServletRequest httpServletRequest);
    ResponseEntity<?> getDSRProfile(HttpServletRequest httpServletRequest);
//    ResponseEntity<?>assignTarget(Long dsrId, Long targetId);
//    ResponseEntity<?>unassignTarget(Long dsrId, Long targetId);

}
