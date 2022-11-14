package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddTargetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringDSRsInTargetRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAcquiringPortalService {

    //Leads
    boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model);

    List<ObjectNode> loadTargets();
    boolean addNewTarget(AcquiringAddTargetRequest acquiringAddTargetRequest);

    //Assets
    boolean addAsset(String assetDetails, MultipartFile[] assetFiles);

      //get all assets
      List<ObjectNode> getAllAssets();

      ObjectNode getAssetById(Long id);

      boolean deleteAssetById(Long id);

    //Visits
    List<ObjectNode> loadQuestionnaires();

    List<ObjectNode> loadDSRsInTarget(AcquiringDSRsInTargetRequest model);
}
