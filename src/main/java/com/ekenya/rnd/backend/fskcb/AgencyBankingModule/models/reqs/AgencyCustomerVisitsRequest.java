package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.models.reqs;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgencyCustomerVisitsRequest {
    private  Long dsrId;
    private String dsrName;
    private String agentName;
    private String reasonForVisit;
    private String visitDate;
    private Status status;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    //yes or no question
    private String scheduled;
    private String location;
    private String pdqVersionCorrect;
    //    status
    private String chargesUpfront;
    private String maintainsOpenedAcctRecords;
    private String trained;
    private String usesManualReceiptBook;
    private String reconcileFloatAcctStat;
    private String moreThanXTransactions;
    private String branchCollectsRegisters;
    private String tariffPosterWellDisplayed;
    private String customersSignRegister;
    private String registerReflected;
    private String outletWellBranded;
    private String registerCompleted;
    private String visitedByStaff;
    private String locatedStrategically;
    private String csLevel;
    private String outletPresentable;
    private String hasFloat;
    private String customerInflow;
    private String agentTrxInForeignCur;
    private String comments;
    private String hasMaterials;
}
