package com.aiep.evaluacion.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.service.dto.JefeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JefeMapperTest {

    private JefeMapper jefeMapper;

    @BeforeEach
    void setUp() {
        jefeMapper = new JefeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        // Crear un objeto de prueba
        Jefe expected = new Jefe();
        expected.setNombreJefe("Test Jefe");
        expected.setTelefonoJefe("123456789");

        // Convertir a DTO y luego de vuelta a la entidad
        JefeDTO dto = jefeMapper.toDto(expected);
        Jefe actual = jefeMapper.toEntity(dto);

        // Validar que los datos sean equivalentes
        assertEquals(expected.getNombreJefe(), actual.getNombreJefe());
        assertEquals(expected.getTelefonoJefe(), actual.getTelefonoJefe());
    }
}
