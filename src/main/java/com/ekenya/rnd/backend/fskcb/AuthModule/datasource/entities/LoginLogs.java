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
@Table(name = "login_logs_tb")
public class LoginLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Date loginDate;
    private boolean successful;
    private String fullName;
    private String channel;
}
