package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_auth_codes")
public class SecurityAuthCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private Long userId;
    @Column(nullable=false)
    private String code;
    @Column(nullable=false)
    private Date dateIssued;
    @Column(nullable=false)
    private Integer expiresInMinutes = 10;
    @Column(nullable=false)
    private AuthCodeType type;
    @Column(nullable=false)
    private Boolean expired = false;
}
