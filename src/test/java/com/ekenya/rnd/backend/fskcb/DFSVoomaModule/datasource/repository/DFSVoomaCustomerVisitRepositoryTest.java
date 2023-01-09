package com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.repository;

import com.ekenya.rnd.backend.fskcb.DFSVoomaModule.datasource.entities.DFSVoomaCustomerVisitEntity;
import com.ekenya.rnd.backend.utils.Status;
import com.ekenya.rnd.backend.utils.Utility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class DFSVoomaCustomerVisitRepositoryTest {
    @Autowired
    DFSVoomaCustomerVisitRepository underTest;

    @Test
    void shouldfindAllByDsrId() {
        //given
        DFSVoomaCustomerVisitEntity dfsVoomaCustomerVisitEntity = new DFSVoomaCustomerVisitEntity();
        dfsVoomaCustomerVisitEntity.setDsrId(1L);
        dfsVoomaCustomerVisitEntity.setDsrName("John Doe");
        dfsVoomaCustomerVisitEntity.setVisitDate("2020-01-01");
        dfsVoomaCustomerVisitEntity.setVisitType("New");
        dfsVoomaCustomerVisitEntity.setStatus(Status.ACTIVE);
        dfsVoomaCustomerVisitEntity.setHighlights("This is a highlight");
        underTest.save(dfsVoomaCustomerVisitEntity);

        //when
        underTest.findAllByDsrId(1L);

        //then
        assertThat(underTest.findAllByDsrId(1L)).isEqualTo(1);



    }

    @Test
    void countTotalVisits() {
    }
}