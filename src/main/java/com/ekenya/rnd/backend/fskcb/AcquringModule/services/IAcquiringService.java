package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetResponse;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssetResponse;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IAcquiringService {

    //Leads
    boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model);

    List<ObjectNode> loadTargets();



    //Assets
    ResponseEntity<?> addAsset(String assetDetails, MultipartFile[] assetFiles);

  //get all assets
  ResponseEntity<?> getAllAssets();

  ResponseEntity<?>getAssetById(Long id);

  ResponseEntity<?>deleteAssetById(Long id);

    //Visits
    List<ObjectNode> loadQuestionnaires();
}
