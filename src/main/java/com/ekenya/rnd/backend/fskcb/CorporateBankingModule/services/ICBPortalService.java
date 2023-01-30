package com.ekenya.rnd.backend.fskcb.CorporateBankingModule.services;

import com.ekenya.rnd.backend.fskcb.AcquringModule.models.AcquiringAddQuestionnaireRequest;
import com.ekenya.rnd.backend.fskcb.CorporateBankingModule.models.reqs.*;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.TeamTAssignTargetRequest;
import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.VoomaTargetByIdRequest;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.ChangeConvenantStatus;
import com.ekenya.rnd.backend.fskcb.RetailModule.models.reqs.RetailAddConcessionRequest;
import com.ekenya.rnd.backend.fskcb.payload.CustomerAppointementRequest;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

public interface ICBPortalService {
    boolean assignLead(CBAssignLeadRequest model);

    List<ObjectNode> getAllLeads();

    boolean createQuestionnaire(AcquiringAddQuestionnaireRequest model);

    List<ObjectNode> getAllQuestionnaires();

    boolean scheduleCustomerVisit(CBCustomerVisitsRequest model);

    boolean rescheduleCustomerVisit(CBRescheduleRequest model);

    List<ObjectNode> getAllCustomerVisits();

    List<ObjectNode> getAllCustomerFeedbacks();

    Object getCustomerFeedbackResponses(CBBankingFeedBackRequest model);

    boolean createCampain(CBCampaignsRequest model);

    List<ObjectNode> getAllCampaigns();

    

    List<ObjectNode> getAllConcessions();

    boolean addTrackedCovenant(CBAddConvenantRequest model);

    boolean setTrackedCovenantStatus(ChangeConvenantStatus model);

    List<ObjectNode> getAllTrackedCovenants();

    List<ObjectNode> getOnboardingSummary();

    boolean createCBTarget(CBAddTargetRequest model);

    List<ObjectNode> getAllTargets();

    boolean assignTargetToDSR(DSRTAssignTargetRequest model);

    boolean assignTargetToTeam(TeamTAssignTargetRequest model);

    Object getTargetById(VoomaTargetByIdRequest model);
    
    boolean addOpportunity(CBAddOpportunityRequest model);

    List<ObjectNode> getAllOpportunities( );

    Object getOpportunityById(CBGetOppByIdRequest model);

    boolean createQuestionnareType(QuestionTypeRequest model);

    List<ObjectNode> getAllQuestionnareTypes();

    boolean addQuestionnareQuestions(UQuestionnaireQuestionRequest model);

    boolean addQuestionnareResponse(QuestionResponseRequest model);

    boolean addConcession(CBConcessionRequest model);

    boolean updateOpportunity(CBUpdateOpportunity model);

    boolean approveCBConcession(CBApproveConcessionRequest model);

    boolean sendEmailForApproval(CBApproveConcessionRequest model);

    boolean rejectCBConcession(CBApproveConcessionRequest model);

    boolean reAssignLead(CBAssignLeadRequest model);

    boolean updateTarget(UpdateTargetRequest model);
}
