package com.ekenya.rnd.backend.fskcb.service;

import com.ekenya.rnd.backend.fskcb.payload.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocationService {
    CountyDto createCounty(CountyDto countyDto);
    CountyResponse getAllCounties(int pageNo, int pageSize, String sortBy, String sortDir );

    CountyDto getCountyById(Long id);
    ResponseEntity<?> updateCounty(CountyDto countyDto, Long id);
    void deleteCountyById(Long id);
    ConstituencyDto createConstituency(long countyId, ConstituencyDto constituencyDto);

    List<ConstituencyDto> getConstituencyByCountyId(long countyId);

    ConstituencyDto getConstituencyById(Long countyId, Long constituencyId);

    ConstituencyDto updateConstituency(Long countyId, long constituencyId, ConstituencyDto constituencyDto);
    ConstituencyResponse getAllConstituency(int pageNo, int pageSize, String sortBy, String sortDir );

    void deleteConstituency(Long countyId, Long constituencyId);
    WardDto createWard(long constituencyId, WardDto wardDto);

    List<WardDto> getWardByConstituencyId(long constituencyId);
    WardResponse getAllWards(int pageNo, int pageSize, String sortBy, String sortDir );

    WardDto getWardById(Long constituencyId, Long wardId);

    WardDto updateWard(Long constituencyId, long wardId, WardDto wardDto);

    void deleteWard(Long constituencyId, Long wardId);
    ResponseEntity<?> getAllTowns();
}
