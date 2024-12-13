package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Empleados;
import com.aiep.evaluacion.repository.EmpleadosRepository;
import com.aiep.evaluacion.service.EmpleadosService;
import com.aiep.evaluacion.service.dto.EmpleadosDTO;
import com.aiep.evaluacion.service.mapper.EmpleadosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Empleados}.
 */
@Service
@Transactional
public class EmpleadosServiceImpl implements EmpleadosService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadosServiceImpl.class);

    private final EmpleadosRepository empleadosRepository;

    private final EmpleadosMapper empleadosMapper;

    public EmpleadosServiceImpl(EmpleadosRepository empleadosRepository, EmpleadosMapper empleadosMapper) {
        this.empleadosRepository = empleadosRepository;
        this.empleadosMapper = empleadosMapper;
    }

    @Override
    public EmpleadosDTO save(EmpleadosDTO empleadosDTO) {
        LOG.debug("Request to save Empleados : {}", empleadosDTO);
        Empleados empleados = empleadosMapper.toEntity(empleadosDTO);
        empleados = empleadosRepository.save(empleados);
        return empleadosMapper.toDto(empleados);
    }

    @Override
    public EmpleadosDTO update(EmpleadosDTO empleadosDTO) {
        LOG.debug("Request to update Empleados : {}", empleadosDTO);
        Empleados empleados = empleadosMapper.toEntity(empleadosDTO);
        empleados = empleadosRepository.save(empleados);
        return empleadosMapper.toDto(empleados);
    }

    @Override
    public Optional<EmpleadosDTO> partialUpdate(EmpleadosDTO empleadosDTO) {
        LOG.debug("Request to partially update Empleados : {}", empleadosDTO);

        return empleadosRepository
            .findById(empleadosDTO.getId())
            .map(existingEmpleados -> {
                empleadosMapper.partialUpdate(existingEmpleados, empleadosDTO);

                return existingEmpleados;
            })
            .map(empleadosRepository::save)
            .map(empleadosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadosDTO> findAll() {
        LOG.debug("Request to get all Empleados");
        return empleadosRepository.findAll().stream().map(empleadosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadosDTO> findOne(Long id) {
        LOG.debug("Request to get Empleados : {}", id);
        return empleadosRepository.findById(id).map(empleadosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Empleados : {}", id);
        empleadosRepository.deleteById(id);
    }
}
