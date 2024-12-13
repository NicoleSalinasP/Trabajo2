package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.DeparmentoTestSamples.*;
import static com.aiep.evaluacion.domain.EmpleadosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeparmentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deparmento.class);
        Deparmento deparmento1 = getDeparmentoSample1();
        Deparmento deparmento2 = new Deparmento();
        assertThat(deparmento1).isNotEqualTo(deparmento2);

        deparmento2.setId(deparmento1.getId());
        assertThat(deparmento1).isEqualTo(deparmento2);

        deparmento2 = getDeparmentoSample2();
        assertThat(deparmento1).isNotEqualTo(deparmento2);
    }

    @Test
    void empleadosTest() {
        Deparmento deparmento = getDeparmentoRandomSampleGenerator();
        Empleados empleadosBack = getEmpleadosRandomSampleGenerator();

        deparmento.addEmpleados(empleadosBack);
        assertThat(deparmento.getEmpleados()).containsOnly(empleadosBack);
        assertThat(empleadosBack.getDeparmento()).isEqualTo(deparmento);

        deparmento.removeEmpleados(empleadosBack);
        assertThat(deparmento.getEmpleados()).doesNotContain(empleadosBack);
        assertThat(empleadosBack.getDeparmento()).isNull();

        deparmento.empleados(new HashSet<>(Set.of(empleadosBack)));
        assertThat(deparmento.getEmpleados()).containsOnly(empleadosBack);
        assertThat(empleadosBack.getDeparmento()).isEqualTo(deparmento);

        deparmento.setEmpleados(new HashSet<>());
        assertThat(deparmento.getEmpleados()).doesNotContain(empleadosBack);
        assertThat(empleadosBack.getDeparmento()).isNull();
    }
}
