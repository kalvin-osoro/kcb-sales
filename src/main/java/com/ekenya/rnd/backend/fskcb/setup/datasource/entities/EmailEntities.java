package com.ekenya.rnd.backend.fskcb.setup.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
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
@Table(name = "dbo_email")
public class EmailEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessUnit;

    private String profileCode;
    private String email;
//    @Enumerated(EnumType.STRING)
//    private Status status;
}
