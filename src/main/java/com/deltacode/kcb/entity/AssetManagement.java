package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "asset_management")
@DynamicInsert
@DynamicUpdate
public class AssetManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Asset asset;

    @OneToOne
    private UserAccType userAccountType;

    @Column(name="serial_number")
    private String serialNumber;

    @Column(name="account_no")
    private String accountNo;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private Long updatedBy;


}
