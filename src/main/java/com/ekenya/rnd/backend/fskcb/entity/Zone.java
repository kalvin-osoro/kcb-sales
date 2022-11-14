package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.fskcb.DSRModule.models.DSRTeam;
import com.ekenya.rnd.backend.fskcb.DSRModule.models.ZoneCoordinates;
import com.ekenya.rnd.backend.utils.Status;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "zone_tb",uniqueConstraints = {@UniqueConstraint(columnNames = {"zoneName"})})
@SQLDelete(sql = "UPDATE zone_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class Zone   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zoneName;
    private String zoneCode;
    private String zoneDescription;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<DSRTeam> teams =new HashSet<>();
    @Column(name = "created_by")
    private String createdBy;
    private String updatedBy;
    private Date updatedOn;
    private Date createdOn;

    private Boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "zone")
    private List<ZoneCoordinates> coordinatesList;

}
