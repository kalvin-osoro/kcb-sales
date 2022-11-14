package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VisitsReport {
    private Long merchantID;
    private String merchantName;
    private String location;
    private Long DSRID;
    private Date visitDate;

}
