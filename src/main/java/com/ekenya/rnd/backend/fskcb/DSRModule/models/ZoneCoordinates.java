package com.ekenya.rnd.backend.fskcb.DSRModule.models;

import com.ekenya.rnd.backend.fskcb.entity.Zone;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(	name = "tb_zone_coordinates")
@DynamicInsert
@DynamicUpdate
public class ZoneCoordinates{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal Latitude;
    private BigDecimal Longitude;
    @ManyToOne
    private Zone zone;

}
