package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.models.reqs;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSBankingOnboardingRequest {
    private String customerTitle;
    private String surname;
    private String otherNames;
    private String gender;
    private String customerName;
    private String customerCode;
    private String region;
    private  String customerIdNumber;
    private String customerEmail;
    @Column(name="customer_phone_number")
    private String customerPhone;
    @Column(name = "customer_dob")
    private String customerDob;
    private Long dsrId;
    private Date createdOn;
    private Boolean isApproved=false;
    @Enumerated(EnumType.STRING)
    private OnboardingStatus status;
    private String nationality;
    private String maritalStatus;
    private String countryOfResidence;
    private String IsCustomerMinor;
    private String IsCustomerHasDualCitizenship;
    private String otherNationality;
    private String passportNumber;
    //contact details
    private String residentialAddress;
    private String postalAddress;
    private String postalCode;
    private String cellPhoneNumber;
    //AccountType Details
    private String preferredCurrency;
    private String typeOfAccount;
    private String accountCategory;
    private String accountType;
    //Employment Details
    private String nameOfEmployer;
    private String termOfEmployment;
    private String expiryOfContract;
    private String estimatedMonthlyIncome;
    private String isCustomerReceiveFundInForeignCurrency;
    private String sourceOfFunds;
    private String country;

    //Additional Details
    private String isCustomerStudent;
    private String nameofUniversityOrCollege;
    private String graduationDate;
    private String isCustomerMinorChild;
    private String  surnameOfParentOrGuardian;
    private String otherNamesOfParentOrGuardian;
    private String genderOfParentOrGuardian;
    private String relationshipToCustomer;
    //mandate Details
    private String idType;
    private String mandateCondition;
    //Account operating Tools
    private String isCustomerWantDebitCard;
    private String isCustomerWantChequeBook;
    private String isCustomerWantToReceiveStatements;
    private String cycleOfStatements;
    private String sendVia;
    private String isCustomerWantToUseMobileBanking;
    private String mobileBankingPhoneNumber;
    private String isCustomerMakeForeignExchangeWithBusiness;
    private String isCustomerwantToReceiveSMSAlerts;
    private String salaryAlerts;
    private String allCredit;
    private String allDebit;
    private String isCustomerWantToGetCreditCard;
    private String KCBcreditCardType;
    private String isCustomerWantToRegisterInternetBanking;
    private String receiveTransactionAuthorizationVia;
}
