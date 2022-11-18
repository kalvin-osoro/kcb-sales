package com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.services;

import com.ekenya.rnd.backend.fskcb.PremiumSegmentModule.models.reps.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface IPSPortalService {
    boolean addTrackedCovenant(PSAddConvenantRequest model);

    List<ObjectNode> getAllTrackedCovenants();

    boolean assignLead(PSAddLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean scheduleCustomerVisit(PSCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(PSCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getCustomerVisitQuestionnaireResponses(PSCustomerVisitQuestionnaireRequest model);
}
