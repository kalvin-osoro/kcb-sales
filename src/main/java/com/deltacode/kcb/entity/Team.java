//package com.deltacode.kcb.entity;
//
//import com.deltacode.kcb.utils.Status;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.SQLDelete;
//import org.hibernate.annotations.Where;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "team_tb",uniqueConstraints = {@UniqueConstraint(columnNames = {"teamName"}),
//        @UniqueConstraint(columnNames = {"teamCode"})})
//@SQLDelete(sql = "UPDATE team_tb SET deleted = true WHERE id = ?")
//@Where(clause = "deleted = false")
//@DynamicInsert
//@DynamicUpdate
//public class Team  {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String teamName;
//    private String description;
//    @Enumerated(EnumType.STRING)
//    private Status status= Status.ACTIVE;
//    private String teamCode;
//    private String teamManager;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "zone_id",nullable = false)
//    private Zone zone;
//
//    private Boolean deleted = Boolean.FALSE;
//
//}
//
