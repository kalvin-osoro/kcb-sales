package com.deltacode.kcb.service.impl;

import com.deltacode.kcb.payload.TargetResponse;
import com.deltacode.kcb.repository.TargetRepository;
import com.deltacode.kcb.service.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class TargetServiceImpl implements TargetService {
    @Autowired
    private TargetRepository targetRepository;


    @Override
    public ResponseEntity<?> getAllTarget() {
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();
        try {
            //hardcoded for now targets for now
            responseParams.put("onboardingTarget", 100);
            responseParams.put("onboardingAchieved", 50);
            responseParams.put("onboardingPercentage", 50);
            responseParams.put("campaignTarget", 100);
            responseParams.put("campaignAchieved", 50);
            responseParams.put("campaignPercentage", 50);
            responseParams.put("visitsTarget", 100);
            responseParams.put("visitAchieved", 50);
            responseParams.put("visitPercentage", 50);

            responseObject.put("status", "success");
            responseObject.put("message", "Target fetched successfully");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);

        } catch (Exception e) {
            responseObject.put("status", "error");
            responseObject.put("message", "Error fetching target");
            responseObject.put("data", responseParams);
        }
        return ResponseEntity.ok().body(responseObject);

    }
}
