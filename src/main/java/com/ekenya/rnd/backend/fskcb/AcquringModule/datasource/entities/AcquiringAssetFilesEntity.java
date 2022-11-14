package com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acquiring_asset_files")

public class AcquiringAssetFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    private AcquiringAssetEntity acquiringAssetEntity;
    @Column(name="file_path")
    private String filePath;
}
