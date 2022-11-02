package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
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
    private Set<Team> teams =new HashSet<>();
    private Boolean deleted = Boolean.FALSE;

}
