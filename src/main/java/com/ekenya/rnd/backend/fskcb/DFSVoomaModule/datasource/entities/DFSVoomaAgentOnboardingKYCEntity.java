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
@Table(name = "dfs_vooma_agent_onboarding_kyc")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaAgentOnboardingKYCEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private  DFSVoomaAgentOnboardingEntity dfsVoomaAgentOnboardingEntity;
    private String filePath;
}
