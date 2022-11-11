package com.ekenya.rnd.backend.fskcb.controller;

import com.ekenya.rnd.backend.fskcb.payload.LiquidationResponse;
import com.ekenya.rnd.backend.fskcb.payload.LiquidationTypeDto;
import com.ekenya.rnd.backend.fskcb.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@Api(value = "LiquidationType API")
@RestController()

@RequestMapping(path = "/config/liquidationTypes")
public class LiquidationTypeController {

}
