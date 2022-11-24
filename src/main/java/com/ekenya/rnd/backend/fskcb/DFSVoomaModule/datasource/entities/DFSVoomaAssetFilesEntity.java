package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import com.ekenya.rnd.backend.fskcb.AcquringModule.datasource.entities.AcquiringAssetEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acquiring_asset_files")

public class DFSVoomaAssetFilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne
    private DFSVoomaAssetEntity dfsVoomaAssetEntity;
    @Column(name="file_path")
    private String filePath;
}
