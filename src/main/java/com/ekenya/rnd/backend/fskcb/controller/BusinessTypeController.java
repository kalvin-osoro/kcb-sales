package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.services.BusinessTypeService;
import com.ekenya.rnd.backend.fskcb.utils.AppConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController()
@CrossOrigin(origins = "*")
@RequestMapping(path = "/config/business-types")
public class BusinessTypeController {
   private final BusinessTypeService businessTypeService;

    public BusinessTypeController(BusinessTypeService businessTypeService) {
        this.businessTypeService = businessTypeService;
    }


    //create account type
    @ApiOperation(value = "Create Business Type Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAccountType(@Valid @RequestBody BusinessTypeDto businessTypeDto){
        return  new ResponseEntity<>(businessTypeService.createBusinessType(businessTypeDto), HttpStatus.CREATED);
    }
    //get All account types
    @ApiOperation(value = "Fetching all Business Type  Api")
    @GetMapping
    public ResponseEntity<?> getAllBusinessTypes(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return businessTypeService.getAllBusinessTypes(pageNo,pageSize,sortBy,sortDir);
    }
    //get account type by id
    @ApiOperation(value = "Fetching  Business Type  Api by Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBusinessTypeById(@PathVariable Long id){
       return new ResponseEntity<>(businessTypeService.getBusinessTypesById(id),HttpStatus.OK);
    }
    //update account type
    @ApiOperation(value = "Update Business Type Api")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBusinessType(@Valid @RequestBody BusinessTypeDto businessTypeDto,@PathVariable Long id){
        return businessTypeService.updateBusinessTypes(businessTypeDto,id);
    }
    //delete account type
    @ApiOperation(value = "Delete Business Type Api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteBusinessTypeById(@PathVariable Long id){
        businessTypeService.deleteBusinessTypeById(id);
    }
}

