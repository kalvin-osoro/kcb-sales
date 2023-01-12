package com.ekenya.rnd.backend.fskcb.AgencyBankingModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AssetCondition;
import com.ekenya.rnd.backend.utils.Status;
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
@Table(name = "agency_bank_visit")
@DynamicUpdate
@DynamicInsert
public class AgencyBankingVisitEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private  Long id;
    private  Long dsrId;
    private String dsrName;
    private String agentName;
    private String reasonForVisit;
    private String branch;
    private String visitDate;
    @Enumerated(EnumType.STRING)
    private Status status=Status.ACTIVE;
    //    private Zone zone;
    private Date createdOn;
    private Date updatedOn;
    private boolean isCompleted=false;
    //yes or no question
    private String scheduled;
    private String location;
    private String latitude;
    private String longitude;
    private String pdqVersionCorrect;
//    status
    private String chargesUpfront;
   private String  maintainsOpenedAcctRecords;
   private String trained;
   private String usesManualReceiptBook;
   private String  reconcileFloatAcctStat;
   private String moreThanXTransactions;
   private String  branchCollectsRegisters;
   private String tariffPosterWellDisplayed;
   private String  customersSignRegister;
   private String  registerReflected;
   private String      outletWellBranded;
   private String registerCompleted;
   private String    visitedByStaff;
   private String locatedStrategically;
   private String     csLevel;
   private String   outletPresentable;
   private String       hasFloat;
   private String customerInflow;
   private String     agentTrxInForeignCur;
   private String    comments;
   private String  hasMaterials;
   private String assetNumber;
   private String serialNumber;
   private String terminalId;
   @Enumerated(EnumType.STRING)
   private AssetCondition assetCondition;

}
