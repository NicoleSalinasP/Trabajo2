package com.aiep.evaluacion.service.mapper;

import com.aiep.evaluacion.domain.Deparmento;
import com.aiep.evaluacion.domain.Empleados;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import com.aiep.evaluacion.service.dto.EmpleadosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleados} and its DTO {@link EmpleadosDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpleadosMapper extends EntityMapper<EmpleadosDTO, Empleados> {
    @Mapping(target = "deparmento", source = "deparmento", qualifiedByName = "deparmentoId")
    EmpleadosDTO toDto(Empleados s);

    @Named("deparmentoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeparmentoDTO toDtoDeparmentoId(Deparmento deparmento);
}
