package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "branches_tb",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"branchCode"})}
)
@SQLDelete(sql = "UPDATE branches_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class Branch  {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String branchName;
    private String branchCode;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id",nullable = false)
    private Bank bank;
    private Boolean deleted = Boolean.FALSE;
}
