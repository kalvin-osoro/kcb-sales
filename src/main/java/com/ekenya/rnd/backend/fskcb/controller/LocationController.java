package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.service.LocationService;
import com.ekenya.rnd.backend.fskcb.utils.AppConstants;
import com.ekenya.rnd.backend.fskcb.payload.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@Api(value = "Town Controller Rest Api")
@RestController()
@RequestMapping(path = "/location/v1")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @RequestMapping(value = "/towns", method = RequestMethod.GET)
    public ResponseEntity<?> getTown() {
        return locationService.getAllTowns();
    }
    @ApiOperation(value = "Create Ward REST API")
    @PostMapping("/constituencies/{constituencyId}/ward")
    public ResponseEntity<WardDto> createWard(@PathVariable(value = "constituencyId") long constituencyId,
                                              @Valid @RequestBody WardDto wardDto) {
        return new ResponseEntity<>(locationService.createWard(constituencyId, wardDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Fetching all Wards  Api")
    @GetMapping("/wards")
    public WardResponse getAllWards(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return locationService.getAllWards(pageNo,pageSize,sortBy,sortDir);
    }

    @ApiOperation(value = "Get All Ward By Constituency ID REST API")
    @GetMapping("/constituencies/{constituencyId}/ward")
    public List<WardDto> getWardByConstituencyId(@PathVariable(value = "constituencyId") Long constituencyId) {
        return locationService.getWardByConstituencyId(constituencyId);
    }

    @ApiOperation(value = "Get Single Ward By ID REST API")
    @GetMapping("/constituencies/{constituencyId}/ward/{id}")
    public ResponseEntity<WardDto> getWardById(@PathVariable(value = "constituencyId") Long constituencyId,
                                               @PathVariable(value = "id") Long wardId){
        WardDto wardDto = locationService.getWardById(constituencyId, wardId);
        return new ResponseEntity<>(wardDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Ward By ID REST API")
    @PutMapping("/constituencies/{constituencyId}/ward/{id}")
    public ResponseEntity<WardDto> updateWard(@PathVariable(value = "constituencyId") Long constituencyId,
                                              @PathVariable(value = "id") Long wardId,
                                              @Valid @RequestBody WardDto wardDto){
        WardDto updatedWard = locationService.updateWard(constituencyId, wardId, wardDto);
        return new ResponseEntity<>(updatedWard, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Ward By ID REST API")
    @DeleteMapping("/constituency/{constituencyId}/ward/{id}")
    public ResponseEntity<String> deleteWard(@PathVariable(value = "constituencyId") Long constituencyId,
                                             @PathVariable(value = "id") Long wardId){
        locationService.deleteWard(constituencyId, wardId);
        return new ResponseEntity<>("Ward deleted successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "Create Constituency REST API")
    @PostMapping("/counties/{countyId}/constituency")
    public ResponseEntity<ConstituencyDto> createConstituency(@PathVariable(value = "countyId") long countyId,
                                                              @Valid @RequestBody ConstituencyDto constituencyDto) {
        return new ResponseEntity<>(locationService.createConstituency(countyId, constituencyDto), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Fetching all Constituency   Api")
    @GetMapping("/constituencies")
    public ConstituencyResponse getAllConstituency(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return locationService.getAllConstituency(pageNo,pageSize,sortBy,sortDir);
    }

    @ApiOperation(value = "Get All Constituency By County ID REST API")
    @GetMapping("/counties/{countyId}/constituency")
    public List<ConstituencyDto> getConstituencyByCountyId(@PathVariable(value = "countyId") Long countyId) {
        return locationService.getConstituencyByCountyId(countyId);
    }

    @ApiOperation(value = "Get Single Constituency By ID REST API")
    @GetMapping("/counties/{countyId}/constituency/{id}")
    public ResponseEntity<ConstituencyDto> getConstituencyById(@PathVariable(value = "countyId") Long countyId,
                                                               @PathVariable(value = "id") Long constituencyId){
        ConstituencyDto constituencyDto = locationService.getConstituencyById(countyId, constituencyId);
        return new ResponseEntity<>(constituencyDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Constituency By ID REST API")
    @PutMapping("/counties/{countyId}/constituency/{id}")
    public ResponseEntity<ConstituencyDto> updateConstituency(@PathVariable(value = "countyId") Long countyId,
                                                              @PathVariable(value = "id") Long constituencyId,
                                                              @Valid @RequestBody ConstituencyDto constituencyDto){
        ConstituencyDto updatedConstituency = locationService.updateConstituency(countyId, constituencyId, constituencyDto);
        return new ResponseEntity<>(updatedConstituency, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Constituency By ID REST API")
    @DeleteMapping("/counties/{countyId}/constituency/{id}")
    public ResponseEntity<String> deleteConstituency(@PathVariable(value = "countyId") Long countyId,
                                                     @PathVariable(value = "id") Long constituencyId){
        locationService.deleteConstituency(countyId, constituencyId);
        return new ResponseEntity<>("Constituency deleted successfully", HttpStatus.OK);
    }
    @ApiOperation(value = "Create County Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CountyDto> createCounty(@Valid @RequestBody CountyDto countyDto){
        return  new ResponseEntity<>(locationService.createCounty(countyDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Fetching all County  Api")
    @GetMapping
    public CountyResponse getAllCounties(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return locationService.getAllCounties(pageNo,pageSize,sortBy,sortDir);
    }

    @ApiOperation(value = "Fetching  County  Api by Id")
    @GetMapping("/{id}")
    public CountyDto getCountyById(@PathVariable Long id){
        return locationService.getCountyById(id);
    }

    @ApiOperation(value = "Update Bank Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCounty(@Valid @RequestBody CountyDto countyDto,@PathVariable Long id){
        return locationService.updateCounty(countyDto,id);
    }

    @ApiOperation(value = "Delete County Api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCounty(@PathVariable Long id){
        locationService.deleteCountyById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

