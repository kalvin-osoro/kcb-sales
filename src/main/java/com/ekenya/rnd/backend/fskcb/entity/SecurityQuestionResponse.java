//package com.deltacode.kcb.entity;
//
//import com.deltacode.kcb.UserManagement.entity.UserApp;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@DynamicUpdate
//@DynamicInsert
//@Table(name = "security_question_response")
//@Entity
//public class SecurityQuestionResponse {
//    @Id
//    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
//    private Long id;
//    private String response;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "security_question_id")
//    private SecurityQuestion securityQuestion;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserApp userApp;
//}
