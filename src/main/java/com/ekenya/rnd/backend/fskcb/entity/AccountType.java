package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.fskcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "account_type_tb",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"accountTypeName"})}
)
@SQLDelete(sql = "UPDATE account_type_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class AccountType  {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String accountTypeName;
    private String accountTypeCode;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    @Column(name="created_on")
    private Date createdOn;
    private Boolean deleted = Boolean.FALSE;
}

