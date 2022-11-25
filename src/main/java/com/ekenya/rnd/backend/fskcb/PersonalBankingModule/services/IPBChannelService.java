package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.services;

import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBAddLeadRequest;
import com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs.PBDSROnboardingRequest;
import com.ekenya.rnd.backend.fskcb.payload.PersonalAccountTypeRequest;
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

    boolean createLead(PBAddLeadRequest request);

    List<ObjectNode> getAllLeadsByDsrId(PBAddLeadRequest model);


//    ResponseEntity<?> getAllAccountTypes();
//    ResponseEntity<?> createPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
//    ResponseEntity<?> editPersonalAccountType( PersonalAccountTypeRequest personalAccountTypeRequest);
//    ResponseEntity<?> deletePersonalAccountType( long id);

}
