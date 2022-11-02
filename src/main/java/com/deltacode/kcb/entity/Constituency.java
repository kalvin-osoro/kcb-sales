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
@Table(
        name = "constituency_tb",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"constituencyName"}),
                @UniqueConstraint(columnNames = {"constituencyCode"})}
)
@SQLDelete(sql = "UPDATE constituency_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class Constituency  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String constituencyName;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    private String constituencyCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "county_id",nullable = false)
    private County county;
    @OneToMany(mappedBy = "constituency",cascade = CascadeType.ALL,orphanRemoval = true)
    private Collection<Ward> wards =new HashSet<>();
    private Boolean deleted = Boolean.FALSE;
}
