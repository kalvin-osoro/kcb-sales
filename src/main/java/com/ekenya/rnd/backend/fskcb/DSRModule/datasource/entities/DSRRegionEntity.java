package com.ekenya.rnd.backend.fskcb.DSRModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@Table(name = "dbo_dsr_regions")
public class DSRRegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String geoJsonBounds;

    private Date dateCreated = Calendar.getInstance().getTime();

    private Status status = Status.ACTIVE;
}
