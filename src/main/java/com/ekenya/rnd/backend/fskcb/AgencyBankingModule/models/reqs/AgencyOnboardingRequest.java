package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyOnboardingRequest {
    private Long id;
    private String agentName;
    private String agentCode;
    private String region;
    private  String agentIdNumber;
    private String agentEmail;
    private String agentPhone;
    private String agentDob;
    private String agentPbox;
    private String agentPostalCode;
    private Long dsrId;
    private Date createdOn;
    private String remarks;
    private String latitude;
    private String longitude;
    private String businessName;
    private String town;
    private String streetName;
    private String buildingName;
    private String roomNumber;
    private OnboardingStatus status;
    //agency onboarding details
    private String nameOfProposiedAgent;
    private String businessType;
    //personal details
    private String surname;
    private String otherNames;
    private String previousName;
    private String yearOfBirth;
    private String placeOfBirth;
    private String idNumber;
    private String relationshipToEntity;
    private String educationalQualification;
    //other borrowing details
    private String nameOfBorrower;
    private String lendingInstitution;
    private String typeOfLoan;
    private String dateOfLoan;
    private String performanceOfLoan;
    private String otherRemarks;
    private String sourceOfFunds;
    //yes or no questions
    private String isFundsObtainedFromIllegalSources;
    private String isAgentConvictedOfAnyOffence;
    private String isAgentEverBeenDismissedFromEmployment;
    private String isAgentHeldLiableInAnyCourtOfLaw;
    private String isAgentExchangeForeignCurrency;
}
