package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.DeparmentoTestSamples.*;
import static com.aiep.evaluacion.domain.EmpleadosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpleadosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empleados.class);
        Empleados empleados1 = getEmpleadosSample1();
        Empleados empleados2 = new Empleados();
        assertThat(empleados1).isNotEqualTo(empleados2);

        empleados2.setId(empleados1.getId());
        assertThat(empleados1).isEqualTo(empleados2);

        empleados2 = getEmpleadosSample2();
        assertThat(empleados1).isNotEqualTo(empleados2);
    }

    @Test
    void deparmentoTest() {
        Empleados empleados = getEmpleadosRandomSampleGenerator();
        Deparmento deparmentoBack = getDeparmentoRandomSampleGenerator();

        empleados.setDeparmento(deparmentoBack);
        assertThat(empleados.getDeparmento()).isEqualTo(deparmentoBack);

        empleados.deparmento(null);
        assertThat(empleados.getDeparmento()).isNull();
    }
}
