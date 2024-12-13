package com.aiep.evaluacion.service.mapper;

import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.service.dto.JefeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jefe} and its DTO {@link JefeDTO}.
 */
@Mapper(componentModel = "spring")
public interface JefeMapper extends EntityMapper<JefeDTO, Jefe> {}
