package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsBYDSRRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IPSChannelService {
    boolean createCustomerVisit(PBCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisitsByDSR(PBCustomerVisitsBYDSRRequest model);
}
