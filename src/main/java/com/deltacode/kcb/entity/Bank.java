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
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "banks_tb",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"bankCode"})}
)
@SQLDelete(sql = "UPDATE banks_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class Bank  {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String bankName;
    private String bankCode;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Branch> branches =new HashSet<>();
    private Boolean deleted = Boolean.FALSE;
}