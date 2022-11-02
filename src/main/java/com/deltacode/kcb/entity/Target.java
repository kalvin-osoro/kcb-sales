package com.deltacode.kcb.entity;

import com.deltacode.kcb.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "target")
@Entity
@SQLDelete(sql = "UPDATE target SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@DynamicInsert
@DynamicUpdate
public class Target {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private  Long id;
    private Integer target;
    private String targetName;
    @Enumerated(EnumType.STRING)
    private Status status= Status.ACTIVE;

    private boolean deleted = false;

}
