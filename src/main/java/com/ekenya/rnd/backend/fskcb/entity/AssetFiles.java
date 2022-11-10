package com.ekenya.rnd.backend.fskcb.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "asset_files_path")
@DynamicInsert
@DynamicUpdate
public class AssetFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AssetManagement assetManagement;

    @Column(name="file_path")
    private String filePath;


}
