package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agent_details")
@SQLDelete(sql = "UPDATE agent_details SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate

public class AgentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AgentAccountType agentAccountType;

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
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
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
    @Column(name="agent_id_number")
    private String agentIdNumber;
    @Column(name = "agent_surname")
    private String agentSurname;
    @Column(name = "agent_first_name")
    private String agentFirstName;
    @Column(name = "agent_last_name")
    private String agentLastName;
    @Column(name = "agent_gender")
    private String agentGender;
    @Column(name = "agent_dob")
    private String agentDob;
    @Column(name="agent_temp_accountid")
    private String accountId;
    @Column(name= "created_on")
    private Date createdOn;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;
    private  boolean deleted=false;

    @OneToMany(mappedBy = "agentDetails")
    @ToString.Exclude
    private List<AgentKYCDocs> agentKYCDocsList;
}
