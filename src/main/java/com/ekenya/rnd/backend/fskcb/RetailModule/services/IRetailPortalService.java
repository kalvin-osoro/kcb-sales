package com.ekenya.rnd.backend.fskcb.RetailModule.services;

import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBAddConvenantRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBApproveConcessionRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.CBConcessionRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAssignLeadRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IRetailPortalService {

    boolean assignLead(TreasuryAssignLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean addConcession(CBConcessionRequest model);

    List<ObjectNode> getAllConcessions();

    boolean addTrackedCovenant(CBAddConvenantRequest model);

    boolean setTrackedCovenantStatus(CBAddConvenantRequest model);

    List<ObjectNode> getAllTrackedCovenants();

    boolean approveCBConcession(CBApproveConcessionRequest model);

    boolean sendEmailForApproval(CBApproveConcessionRequest model);

    boolean rejectCBConcession(CBApproveConcessionRequest model);
}
