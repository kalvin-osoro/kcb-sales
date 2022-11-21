package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddDSRAccountRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddRegionRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.UpdateRegionRequest;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.AddTeamRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

public interface IDSRPortalService {
    boolean createRegion(AddRegionRequest model);
    boolean updateRegion(UpdateRegionRequest model);
    ObjectNode attemptImportRegions(MultipartFile importFile);

    ArrayNode getAllRegions();

    boolean addDSRTeam(AddTeamRequest addTeamRequest);
    boolean editDSRTeam(AddTeamRequest addTeamRequest);
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
