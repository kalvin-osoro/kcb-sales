package com.deltacode.kcb.entity;

import com.deltacode.kcb.UserManagement.entity.UserApp;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "security_question")
@Entity
@SQLDelete(sql = "UPDATE security_question SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SecurityQuestion {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;
    private String question;
    private boolean deleted = false;


}
