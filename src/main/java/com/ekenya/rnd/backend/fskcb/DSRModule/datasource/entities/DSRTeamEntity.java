package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_accounts")
public class DSRTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "zone_id",nullable = false)
//    private Zone zone;
    private Long regionId;
}
