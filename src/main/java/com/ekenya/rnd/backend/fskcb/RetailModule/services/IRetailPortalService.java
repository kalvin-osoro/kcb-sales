package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IRetailPortalService {
    boolean assignLead(RetailAssignLeadRequest model);

    List<ObjectNode> getAllLeads();
}
