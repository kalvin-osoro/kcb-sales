package com.ekenya.rnd.backend.fskcb.PersonalBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_pb_onboarding")
@DynamicUpdate
@DynamicInsert
public class PSBankingOnboardingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String customerTitle;

    @Lob
    private String surname;

    @Lob
    private String otherNames;

    @Lob
    private String gender;

    @Lob
    private String customerName;
    @Lob
    private String customerCode;

    @Lob
    private String region;

    @Lob
    private  String customerIdNumber;

    @Lob
    private String customerEmail;
    @Column(name="customer_phone_number")
    @Lob
    private String customerPhone;

    @Lob
    @Column(name = "customer_dob")
    private String customerDob;

    private Long dsrId;
    private Date createdOn;
    private Boolean isApproved=false;
    @Enumerated(EnumType.STRING)
    private OnboardingStatus status;
    @Lob
    private String nationality;
    @Lob
    private String maritalStatus;
    @Lob
    private String countryOfResidence;
    @Lob
    private String IsCustomerMinor;
    @Lob
    private String IsCustomerHasDualCitizenship;

    @Lob
    private String otherNationality;
    @Lob
    private String passportNumber;
    //contact details
    @Lob
    private String residentialAddress;
    @Lob
    private String postalAddress;

    @Lob
    private String postalCode;

    @Lob
    private String cellPhoneNumber;
    //AccountType Details

    @Lob
    private String preferredCurrency;

    @Lob
    private String typeOfAccount;
    @Lob
    private String accountCategory;
    @Lob
    private String accountType;
    //Employment Details
    @Lob
    private String nameOfEmployer;
    @Lob
    private String termOfEmployment;
    @Lob
    private String expiryOfContract;
    @Lob
    private String estimatedMonthlyIncome;
    @Lob
    private String isCustomerReceiveFundInForeignCurrency;
    @Lob
    private String sourceOfFunds;
    @Lob
    private String country;

    //Additional Details
    @Lob
    private String isCustomerStudent;
    @Lob
    private String nameofUniversityOrCollege;
    @Lob
    private String graduationDate;
    @Lob
    private String isCustomerMinorChild;
    @Lob
    private String  surnameOfParentOrGuardian;
    @Lob
    private String otherNamesOfParentOrGuardian;
    @Lob
    private String genderOfParentOrGuardian;
    @Lob
    private String relationshipToCustomer;
    //mandate Details
    @Lob
    private String idType;
    @Lob
    private String mandateCondition;
    //Account operating Tools
    @Lob
    private String isCustomerWantDebitCard;
    @Lob
    private String isCustomerWantChequeBook;
    @Lob
    private String isCustomerWantToReceiveStatements;
    @Lob
    private String cycleOfStatements;
    @Lob
    private String sendVia;
    @Lob
    private String isCustomerWantToUseMobileBanking;
    @Lob
    private String mobileBankingPhoneNumber;
    @Lob
    private String isCustomerMakeForeignExchangeWithBusiness;
    @Lob
    private String isCustomerwantToReceiveSMSAlerts;
    @Lob
    private String salaryAlerts;
    @Lob
    private String allCredit;
    @Lob
    private String allDebit;
    @Lob
    private String isCustomerWantToGetCreditCard;
    @Lob
    private String KCBcreditCardType;
    @Lob
    private String isCustomerWantToRegisterInternetBanking;
    @Lob
    private String receiveTransactionAuthorizationVia;

}
