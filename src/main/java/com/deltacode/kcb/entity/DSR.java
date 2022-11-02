package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dsr_tb",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"idNumber"}),
        @UniqueConstraint(columnNames = {"email"})})
@SQLDelete(sql = "UPDATE dsr_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class DSR   {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //staff number
    private String username;
    private Integer idNumber;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String middleName;
    private String dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
    private String gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id",nullable = false)
    private Team team;
    private Boolean deleted = Boolean.FALSE;
   @ManyToOne
    private Target target;

}
