package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailGetDSRLead;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public interface IRetailChannelService {


    boolean createLead(RetailAddLeadRequest model);

    ObjectNode loadDSRAnalytics(String staffNo);

    List<?> getAllLeads(RetailGetDSRLead model);

    ArrayList<ObjectNode> loadDSRSummary();

    boolean attemptCreateMicroLead(TreasuryAddLeadRequest model);

    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model);

    boolean attemptUpdateLead(TreasuryUpdateLeadRequest model);
}
