package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface IRetailChannelService {


    boolean createLead(RetailAddLeadRequest model);

    ObjectNode loadDSRAnalytics(String staffNo);
}
