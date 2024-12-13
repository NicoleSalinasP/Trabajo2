package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.DeparmentoTestSamples.*;
import static com.aiep.evaluacion.domain.JefeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JefeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jefe.class);
        Jefe jefe1 = getJefeSample1();
        Jefe jefe2 = new Jefe();
        assertThat(jefe1).isNotEqualTo(jefe2);

        jefe2.setId(jefe1.getId());
        assertThat(jefe1).isEqualTo(jefe2);

        jefe2 = getJefeSample2();
        assertThat(jefe1).isNotEqualTo(jefe2);
    }

    @Test
    void deparmentoTest() {
        Jefe jefe = getJefeRandomSampleGenerator();
        Deparmento deparmentoBack = getDeparmentoRandomSampleGenerator();

        jefe.addDeparmento(deparmentoBack);
        assertThat(jefe.getDeparmentos()).containsOnly(deparmentoBack);
        assertThat(deparmentoBack.getJefe()).isEqualTo(jefe);

        jefe.removeDeparmento(deparmentoBack);
        assertThat(jefe.getDeparmentos()).doesNotContain(deparmentoBack);
        assertThat(deparmentoBack.getJefe()).isNull();

        jefe.deparmentos(new HashSet<>(Set.of(deparmentoBack)));
        assertThat(jefe.getDeparmentos()).containsOnly(deparmentoBack);
        assertThat(deparmentoBack.getJefe()).isEqualTo(jefe);

        jefe.setDeparmentos(new HashSet<>());
        assertThat(jefe.getDeparmentos()).doesNotContain(deparmentoBack);
        assertThat(deparmentoBack.getJefe()).isNull();
    }
}
