package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acquiring_asset")

public class AcqAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="asset_name")
    private String assetName;

    @Column(name="asset_desc")
    private String assetDesc;

}
