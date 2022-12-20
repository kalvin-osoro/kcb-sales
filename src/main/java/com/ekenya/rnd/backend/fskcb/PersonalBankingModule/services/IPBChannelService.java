package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.models.reqs.DSRSummaryRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsBYDSRRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBCustomerVisitsRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBDSROnboardingRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryGetDSRLeads;
import com.ekenya.rnd.backend.fskcb.TreasuryModule.models.reqs.TreasuryUpdateLeadRequest;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public interface IPBChannelService {
    Object onboardNewCustomer(String customerDetails,
                              MultipartFile signature,
                              MultipartFile customerPhoto,
                              MultipartFile kraPin,
                              MultipartFile crbReport,
                              MultipartFile frontID,
                              MultipartFile backID);

    List<ObjectNode> getAllOnboardings(PBDSROnboardingRequest model);

    ArrayList<ObjectNode> getTargetsSummary();
    boolean createCustomerVisit(PBCustomerVisitsRequest model);

    List<ObjectNode> getAllCustomerVisitsByDSR(PBCustomerVisitsBYDSRRequest model);

    boolean attemptCreateLead(TreasuryAddLeadRequest model);


    List<ObjectNode> loadDSRLead(TreasuryGetDSRLeads model);

    List<ObjectNode> loadAssignedDSRLead(TreasuryGetDSRLeads model);

    boolean attemptUpdateLead(TreasuryUpdateLeadRequest model);

    ArrayNode getDSRSummary(DSRSummaryRequest model);
}
