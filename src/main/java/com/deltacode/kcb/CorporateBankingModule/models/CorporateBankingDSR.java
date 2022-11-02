package com.deltacode.kcb.CorporateBankingModule.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CorporateBankingDSR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
