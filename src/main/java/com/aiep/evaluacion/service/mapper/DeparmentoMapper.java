package com.aiep.evaluacion.service.mapper;

import com.aiep.evaluacion.domain.Deparmento;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deparmento} and its DTO {@link DeparmentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeparmentoMapper extends EntityMapper<DeparmentoDTO, Deparmento> {}
