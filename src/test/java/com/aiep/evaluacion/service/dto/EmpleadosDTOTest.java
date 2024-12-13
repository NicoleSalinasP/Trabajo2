package com.aiep.evaluacion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpleadosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpleadosDTO.class);
        EmpleadosDTO empleadosDTO1 = new EmpleadosDTO();
        empleadosDTO1.setId(1L);
        EmpleadosDTO empleadosDTO2 = new EmpleadosDTO();
        assertThat(empleadosDTO1).isNotEqualTo(empleadosDTO2);
        empleadosDTO2.setId(empleadosDTO1.getId());
        assertThat(empleadosDTO1).isEqualTo(empleadosDTO2);
        empleadosDTO2.setId(2L);
        assertThat(empleadosDTO1).isNotEqualTo(empleadosDTO2);
        empleadosDTO1.setId(null);
        assertThat(empleadosDTO1).isNotEqualTo(empleadosDTO2);
    }
}
