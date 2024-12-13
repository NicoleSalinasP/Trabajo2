package com.aiep.evaluacion.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JefeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JefeDTO.class);
        JefeDTO jefeDTO1 = new JefeDTO();
        jefeDTO1.setId(1L);
        JefeDTO jefeDTO2 = new JefeDTO();
        assertThat(jefeDTO1).isNotEqualTo(jefeDTO2);
        jefeDTO2.setId(jefeDTO1.getId());
        assertThat(jefeDTO1).isEqualTo(jefeDTO2);
        jefeDTO2.setId(2L);
        assertThat(jefeDTO1).isNotEqualTo(jefeDTO2);
        jefeDTO1.setId(null);
        assertThat(jefeDTO1).isNotEqualTo(jefeDTO2);
    }
}
