package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.payload.request.DSRTeamRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface IDSRPortalService {
    boolean addDSRTeam(DSRTeamRequest dsrTeamRequest);
    boolean editDSRTeam(DSRTeamRequest dsrTeamRequest);
    ArrayNode getAllDSRTeams();
    ArrayNode getTeamMembersByTeamId(long id);
    boolean deleteDSRTeam(long id);
    boolean addDSR(AddDSRAccountRequest dsrRequest);
    ArrayNode getAllDSRs();
    boolean deleteDSRById(long id);
//    ResponseEntity<?> editDSRById(DSRRequest dsrRequest, HttpServletRequest httpServletRequest);
    ObjectNode getDSRProfile(String staffNo);
//    ResponseEntity<?>assignTarget(Long dsrId, Long targetId);
//    ResponseEntity<?>unassignTarget(Long dsrId, Long targetId);

    ObjectNode attemptImportAccounts(MultipartFile file);

    boolean lockAccount(String staffNo);
    boolean unlockAccount(String staffNo);
    boolean resetPIN(String staffNo);
}
