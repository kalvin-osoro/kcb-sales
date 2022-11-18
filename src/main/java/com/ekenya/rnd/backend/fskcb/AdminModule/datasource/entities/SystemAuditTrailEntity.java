package com.ekenya.rnd.backend.fskcb.AdminModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_system_audit_trails")
public class SystemAuditTrailEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private SystemAuditEvent event;

    private Date eventTime = Calendar.getInstance().getTime();

    private String details;

    private String stackTrace;
}
