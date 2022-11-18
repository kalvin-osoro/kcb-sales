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

    private Long userId;

    private String code;

    private Date dateIssued;

    private int expiresInMinutes;

    private AuthCodeType type;

    private boolean expired;
}
