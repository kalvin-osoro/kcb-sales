package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBAssignLeadRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PSBankingAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPBPortalService {




    List<ObjectNode> getAllLeads();

    boolean assignLead(PBAssignLeadRequest model);

    boolean scheduleCustomerVisit(PBCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(PBCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getCustomerVisitQuestionnaireResponses(PBCustomerVisitQuestionnaireRequest assetManagementRequest);

    boolean createQuestionnaire(PSBankingAddQuestionnaireRequest model);

    List<ObjectNode> getAllQuestionnaires();
}
