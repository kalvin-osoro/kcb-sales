package com.deltacode.kcb.DSRModule.models;

import com.deltacode.kcb.entity.Zone;
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
@Table(	name = "dsr_teams")
@DynamicInsert
@DynamicUpdate
public class DSRTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="team_name")
    private String teamName;

    @Column(name="team_location")
    private String teamLocation;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id",nullable = false)
    private Zone zone;

}
