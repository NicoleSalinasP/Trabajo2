package com.aiep.evaluacion.service.mapper;

import static com.aiep.evaluacion.domain.EmpleadosAsserts.*;
import static com.aiep.evaluacion.domain.EmpleadosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmpleadosMapperTest {

    private EmpleadosMapper empleadosMapper;

    @BeforeEach
    void setUp() {
        empleadosMapper = new EmpleadosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmpleadosSample1();
        var actual = empleadosMapper.toEntity(empleadosMapper.toDto(expected));
        assertEmpleadosAllPropertiesEquals(expected, actual);
    }
}
