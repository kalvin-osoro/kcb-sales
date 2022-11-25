package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsBYDSRRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsByDsrId;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.PSGetDSRLeadsRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

public interface IPSChannelService {
  

    boolean createCustomerVisits(PSCustomerVisitsRequest model);

    List<ObjectNode> getAllDsrVisits(PSCustomerVisitsByDsrId model);

    boolean createLead(PSAddLeadRequest model);

    List<ObjectNode> getAllLeads(PSGetDSRLeadsRequest model);

    ArrayList<ObjectNode> getTargetSytem();
}
