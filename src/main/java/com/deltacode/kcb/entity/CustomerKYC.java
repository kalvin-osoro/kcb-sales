package com.deltacode.kcb.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(	name = "customer_kyc")
@DynamicInsert
@DynamicUpdate
public class CustomerKYC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CustomerDetails customerDetails;
    private String filePath;
}