package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services;

import com.ekenya.rnd.backend.fskcb.payload.LiquidationResponse;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationTypeDto;
import org.springframework.http.ResponseEntity;

public interface LiquidationTypeService {
    ResponseEntity<?> createLiquidationType(LiquidationTypeDto liquidationTypeDto);
    LiquidationResponse getAllLiquidationType(int pageNo, int pageSize, String sortBy, String sortDir );
    ResponseEntity<?> getLiquidationTypeById(Long id);
    ResponseEntity<?> updateLiquidationType(LiquidationTypeDto liquidationTypeDto);
    ResponseEntity<?> deleteLiquidationTypeById(Long id);
}
