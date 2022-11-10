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
    //Hardcoded values for testing purposes
    private static final int onboardingTarget = (int) (Math.random() * 100);
    private static final int onboardingAchieved = (int) (Math.random() * 100);
    private static final int onboardingCommission = (int) (Math.random() * 100);
    private static final int campaignTarget = (int) (Math.random() * 100);
    private static final int campaignAchieved = (int) (Math.random() * 100);
    private static final int campaignCommission = (int) (Math.random() * 100);
    private static final int visitsTarget = (int) (Math.random() * 100);
    private static final int visitsAchieved = (int) (Math.random() * 100);
    private static final int visitsCommission = (int) (Math.random() * 100);


    @Override
    public ResponseEntity<?> getAllTarget() {
        LinkedHashMap<String, Object> responseParams = new LinkedHashMap<>();
        LinkedHashMap<String, Object> responseObject = new LinkedHashMap<>();

        try {
            //hardcoded for now targets for now
            responseParams.put("onboardingTarget", onboardingTarget);
            responseParams.put("onboardingAchieved", onboardingAchieved);
            responseParams.put("onboardingCommission", onboardingCommission + "Ksh");
            responseParams.put("campaignTarget", campaignTarget);
            responseParams.put("campaignAchieved", campaignAchieved);
            responseParams.put("campaignCommission", campaignCommission + "Ksh");
            responseParams.put("visitsTarget", visitsTarget);
            responseParams.put("visitAchieved", visitsAchieved);
            responseParams.put("visitsCommission", visitsCommission + "Ksh");

            responseObject.put("status", 1);
            responseObject.put("message", "Target fetched successfully");
            responseObject.put("data", responseParams);
            return ResponseEntity.ok().body(responseObject);

        } catch (Exception e) {
            responseObject.put("status", 0);
            responseObject.put("message", "Error fetching target");
            responseObject.put("data", responseParams);
        }
        return ResponseEntity.ok().body(responseObject);




    }
}
