package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyAssignAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCollectAssetRequest;
import com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs.AgencyCustomerVisitsRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public interface IAgencyChannelService {

    boolean assignAsset(AgencyAssignAssetRequest model);

    List <ObjectNode>getAllAgentAssets(Long agentId);

    boolean recollectAsset(Long assetId);

    boolean createLead(AgencyAddLeadRequest request);

    List<ObjectNode> getAllLeadsByDsrId(Long dsrId);

    ArrayList<ObjectNode> getTargetsSummary();

    boolean createCustomerVisit(AgencyCustomerVisitsRequest model);
}
