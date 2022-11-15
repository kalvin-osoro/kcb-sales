package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_accounts")
public class DSRRegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
