package com.ekenya.rnd.backend.fskcb.DSRModule.service;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.DeleteWrapper;
import com.ekenya.rnd.backend.fskcb.UserManagement.models.EditWrapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    ObjectNode loadTeamDetails(TeamDetailsRequest model);
    boolean attemptActivateTeam(long teamId);
    boolean attemptDeactivateTeam(long teamId);

    boolean attemptAddTeamMember(AddTeamMemberRequest model);
    boolean attemptRemoveTeamMember(RemoveTeamMemberRequest model);

    boolean attemptUpdateTeamMembers(UpdateTeamMembersRequest model);

    ArrayNode getAllDSRAccounts(DSRAccountsRequest model);
    boolean deleteDSRById(long id);
//    ResponseEntity<?> editDSRById(DSRRequest dsrRequest, HttpServletRequest httpServletRequest);
    ObjectNode getDSRProfile(String staffNo);
//    ResponseEntity<?>assignTarget(Long dsrId, Long targetId);
//    ResponseEntity<?>unassignTarget(Long dsrId, Long targetId);

    ObjectNode attemptImportAccounts(MultipartFile file,String profileCode);

    boolean lockAccount(String staffNo);
    boolean unlockAccount(String staffNo);
    boolean resetPIN(String staffNo);

    List<ObjectNode> getAllDSRAccountsV1(DSRAccountsRequest model);

    boolean addSector(SectorRequest model);

    List<ObjectNode> getAllSectors();

    boolean editSector(SectorRequest model);

    boolean deleteSector(SectorRequest model);

    boolean attemptDeleteTeam(Long teamId);

    boolean attemptEditUser(EditWrapper request);

    boolean attemptDeleteUser(DeleteWrapper request);
}
