package com.ekenya.rnd.backend.fskcb.AcquringModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddAssetRequest;
import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAssignLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IAcquiringService {

    //Leads
    boolean assigneLeadtoDSR(AcquiringAssignLeadRequest model);

    List<ObjectNode> loadTargets();


    //Assets
    boolean addAsset(AcquiringAddAssetRequest model);

    List<ObjectNode> loadAssets();

    //Visits
    List<ObjectNode> loadQuestionnaires();
}
