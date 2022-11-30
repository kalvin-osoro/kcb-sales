package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities.UserRoleEntity;
import com.ekenya.rnd.backend.fskcb.entity.Zone;
import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_teams")
public class DSRTeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String code;

    private String location;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="status")
    //@Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn = Calendar.getInstance().getTime();

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name="updated_by")
    private String updatedBy;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "zone_id",nullable = false)
//    private Zone zone;
    private Long regionId;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "dbo_team_members",
            joinColumns = @JoinColumn(name = "team_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<DSRAccountEntity> members;
}
