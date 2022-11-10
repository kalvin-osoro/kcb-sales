package com.ekenya.rnd.backend.fskcb.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "merchant_details")
@DynamicInsert
@DynamicUpdate
public class MerchantDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MerchantAccountType merchAgentAccountType;

    @Column(name="business_name")
    private String businessName;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="email")
    private String email;

    @ManyToOne
    private BusinessType businessType;

    @Column(name="natureof_business")
    private String natureBusiness;

    @ManyToOne
    private LiquidationType liquidationType;


    @Column(name="liquidation_rate")
    private double liquidationRate;

    @ManyToOne
    private Branch bankBranch;

    @Column(name="liquidation_account_name")
    private String accountName;


    @Column(name="liquidation_account_number")
    private String accountNumber;

    @ManyToOne
    private County county;

    @Column(name="town")
    private String town;
    @Column(name="street_name")
    private String streetName;
    @Column(name="building_name")
    private String buildingName;
    @Column(name="room_number")
    private String roomNumber;
    @Column(name="latitude")
    private String  latitude ;
    @Column(name="longitude")
    private String  longitude ;
    @Column(name="merchant_id_number")
    private String merchantIDNumber;
    @Column(name = "merchant_surname")
    private String merchantSurname;
    @Column(name = "merchant_first_name")
    private String merchantFirstName;
    @Column(name = "merchant_last_name")
    private String merchantLastName;
    @Column(name = "merchant_gender")
    private String merchantGender;
    @Column(name = "merchant_dob")
    private String merchantDob;
    @Column(name="merchant_temp_accountid")
    private String accountId;
    @Column(name= "created_on")
    private Date createdOn;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToMany(mappedBy = "merchantDetails")
    @ToString.Exclude
    private List<MerchantKYC> merchantKYCList;
}
