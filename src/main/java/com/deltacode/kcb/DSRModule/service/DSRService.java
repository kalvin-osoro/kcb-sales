package com.deltacode.kcb.DSRModule.service;

import com.deltacode.kcb.payload.CommissionsRequest;
import com.deltacode.kcb.payload.DSRDto;
import com.deltacode.kcb.payload.DSRResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DSRService {
    DSRDto createDSR(long teamId, DSRDto dsrDto);

    List<DSRDto> getDSRByTeamId(long teamId);

    DSRResponse getAllDSRs(int pageNo, int pageSize, String sortBy, String sortDir );

    DSRDto getDSRById(Long teamId, Long dsrId);

    DSRDto updateDSR(Long teamId, long dsrId, DSRDto dsrDto);

    void deleteDSR(Long teamId, Long dsrId);
    ResponseEntity<?>getCommissionByDSRId(Long teamId, Long dsrId, CommissionsRequest commissionsRequest);
    ResponseEntity<?>assignTarget(Long dsrId, Long targetId);
    ResponseEntity<?>unassignTarget(Long dsrId, Long targetId);

}
