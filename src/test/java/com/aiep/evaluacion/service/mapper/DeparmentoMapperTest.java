package com.aiep.evaluacion.service.mapper;

import static com.aiep.evaluacion.domain.DeparmentoAsserts.*;
import static com.aiep.evaluacion.domain.DeparmentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeparmentoMapperTest {

    private DeparmentoMapper deparmentoMapper;

    @BeforeEach
    void setUp() {
        deparmentoMapper = new DeparmentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDeparmentoSample1();
        var actual = deparmentoMapper.toEntity(deparmentoMapper.toDto(expected));
        assertDeparmentoAllPropertiesEquals(expected, actual);
    }
}
