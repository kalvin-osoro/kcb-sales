package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_qn_options")
public class SecQuestionOptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Date dateCreated = Calendar.getInstance().getTime();

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
