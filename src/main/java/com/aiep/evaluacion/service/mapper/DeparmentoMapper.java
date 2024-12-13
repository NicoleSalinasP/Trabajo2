package com.aiep.evaluacion.service.mapper;

import com.aiep.evaluacion.domain.Deparmento;
import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import com.aiep.evaluacion.service.dto.JefeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deparmento} and its DTO {@link DeparmentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeparmentoMapper extends EntityMapper<DeparmentoDTO, Deparmento> {
    @Mapping(target = "jefe", source = "jefe", qualifiedByName = "jefeId")
    DeparmentoDTO toDto(Deparmento s);

    @Named("jefeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JefeDTO toDtoJefeId(Jefe jefe);
}
