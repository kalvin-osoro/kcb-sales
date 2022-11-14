package com.ekenya.rnd.backend.fskcb.entity;

import com.ekenya.rnd.backend.utils.Status;
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
@Table(	name = "towns")
@DynamicInsert
@DynamicUpdate
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="town_name")
    private String townName;

    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

}
