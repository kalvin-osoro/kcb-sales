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
@Table(name = "dfs_vooma_agent_contact_details")
@DynamicUpdate
@DynamicInsert
public class DFSVoomaAgentOwnerDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String idNumber;
    private String idType;
    private String phoneNumber;
    private String emailAddress;
    private String dob;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agentOnboardId")
    private DFSVoomaAgentOnboardV1 dfsVoomaAgentOnboardV1;
}
