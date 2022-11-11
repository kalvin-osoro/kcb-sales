package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.payload.BusinessTypeDto;
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

}

