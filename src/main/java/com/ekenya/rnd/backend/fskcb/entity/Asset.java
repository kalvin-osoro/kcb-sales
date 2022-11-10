package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.fskcb.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(	name = "assets")
    @DynamicInsert
    @DynamicUpdate
    public class Asset {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name="asset_name")
        private String assetName;

        @Column(name="asset_desc")
        private String assetDesc;

        @Column(name="status")
        @Enumerated(EnumType.STRING)
        private Status status= Status.ACTIVE;

        @Column(name="created_by")
        private Long createdBy;

        @Column(name="created_on")
        private Date createdOn;

        @Column(name="updated_on")
        private Date updatedOn;

        @Column(name="updated_by")
        private Long updatedBy;
}
