package com.ekenya.rnd.backend.fskcb.UserManagement.datasource.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;
@Data
@Entity
@Table(name = "dbo_users",uniqueConstraints = {@UniqueConstraint(columnNames = {"staff_no"}),
        @UniqueConstraint(columnNames = {"email"})})
@SQLDelete(sql = "UPDATE dbo_users SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(name="staff_no")
    private String staffNo;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Boolean deleted = Boolean.FALSE;
    //@Column(name="dob")
    //private String dateOfBirth;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "dbo_user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> roles;
    private  Boolean isVerified = Boolean.FALSE;
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
}