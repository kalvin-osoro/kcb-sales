package com.deltacode.kcb.DFSModule.controller;

import com.deltacode.kcb.payload.GetLeadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/dfs-lead")
public class DFSLeadController {
    @PostMapping("/create-lead")
    public ResponseEntity<?> createLead(@RequestBody GetLeadRequest leadRequest ) {
        return null;
    }
    @RequestMapping(value = "/get-all-dfs-leads", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLeads() {
        return null;
    }

}
