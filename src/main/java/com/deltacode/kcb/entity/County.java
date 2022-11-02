package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "county_tb",uniqueConstraints = {@UniqueConstraint(columnNames = {"countyName"}),
        @UniqueConstraint(columnNames = {"countyCode"})})
@SQLDelete(sql = "UPDATE county_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class County   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String countyName;
    private Long countyCode;
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @OneToMany(mappedBy = "county", cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Constituency> constituencies =new HashSet<>();
    private Boolean deleted = Boolean.FALSE;
}
