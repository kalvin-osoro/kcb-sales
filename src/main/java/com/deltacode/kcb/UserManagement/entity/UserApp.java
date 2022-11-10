package com.deltacode.kcb.UserManagement.entity;

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
@Table(name = "app_user_tb",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
@SQLDelete(sql = "UPDATE app_user_tb SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String staffId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String middleName;
    private Boolean deleted = Boolean.FALSE;
    @Column(name="dob")
    private String dateOfBirth;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    private  Boolean isVerified = Boolean.FALSE;
    private Boolean firstLogin = Boolean.TRUE;




    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }
}
