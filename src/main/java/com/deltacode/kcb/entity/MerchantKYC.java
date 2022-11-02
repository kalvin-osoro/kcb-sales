package com.deltacode.kcb.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "merchant_kyc_docs")
@DynamicInsert
@DynamicUpdate
public class MerchantKYC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private MerchantDetails merchantDetails;
    private String filePath;
}
