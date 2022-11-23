package com.ekenya.rnd.backend.fskcb.AuthModule.datasource.entities;

import com.ekenya.rnd.backend.utils.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dbo_sec_qns")
public class SecurityQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private SecurityQuestionType type;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "dbo_qn_options",
            joinColumns = @JoinColumn(name = "qn_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private List<SecQuestionOptionEntity> options;

    private Date dateCreated = Calendar.getInstance().getTime();

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;
}
