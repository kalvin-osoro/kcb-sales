package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dfs_vooma_owner_details")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaOwnerDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String idNumber;
    private String idType;
    private String phoneNumber;
    private String emailAddress;
    @ManyToOne
    @JoinColumn(name = "merchantOnboardId")
    private DFSVoomaMerchantOnboardV1 dfsVoomaMerchantOnboardV1;
}
