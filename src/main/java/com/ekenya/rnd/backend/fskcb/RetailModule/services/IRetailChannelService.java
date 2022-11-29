package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailGetDSRLead;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IRetailChannelService {


    boolean createLead(RetailAddLeadRequest model);

    ObjectNode loadDSRAnalytics(String staffNo);

    List<?> getAllLeads(RetailGetDSRLead model);
}
