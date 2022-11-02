package com.deltacode.kcb.service;

import com.deltacode.kcb.payload.TargetDto;
import com.deltacode.kcb.payload.TargetResponse;

public interface TargetService {
    TargetDto createTarget(TargetDto targetDto);
    TargetResponse getAllTarget(int pageNo, int pageSize, String sortBy, String sortDir );
}
