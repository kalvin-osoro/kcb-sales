package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import javax.persistence.*;

@Entity
@Table(name = "dbo_aqc_leads")
public class AcquiringLeadEntity {
    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
