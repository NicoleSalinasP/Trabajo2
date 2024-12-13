package com.aiep.evaluacion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeparmentoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeparmentoDTO.class);
        DeparmentoDTO deparmentoDTO1 = new DeparmentoDTO();
        deparmentoDTO1.setId(1L);
        DeparmentoDTO deparmentoDTO2 = new DeparmentoDTO();
        assertThat(deparmentoDTO1).isNotEqualTo(deparmentoDTO2);
        deparmentoDTO2.setId(deparmentoDTO1.getId());
        assertThat(deparmentoDTO1).isEqualTo(deparmentoDTO2);
        deparmentoDTO2.setId(2L);
        assertThat(deparmentoDTO1).isNotEqualTo(deparmentoDTO2);
        deparmentoDTO1.setId(null);
        assertThat(deparmentoDTO1).isNotEqualTo(deparmentoDTO2);
    }
}
