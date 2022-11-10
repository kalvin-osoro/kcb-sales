package com.deltacode.kcb.controller;

import com.deltacode.kcb.payload.TargetResponse;
import com.deltacode.kcb.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/target")
public class TargetController {
    @Autowired
   private TargetService targetService;
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    ResponseEntity<?> getAllTarget( ) {
        return targetService.getAllTarget();

    }
}
