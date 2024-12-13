package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Deparmento;
import com.aiep.evaluacion.repository.DeparmentoRepository;
import com.aiep.evaluacion.service.DeparmentoService;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import com.aiep.evaluacion.service.mapper.DeparmentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Deparmento}.
 */
@Service
@Transactional
public class DeparmentoServiceImpl implements DeparmentoService {

    private static final Logger LOG = LoggerFactory.getLogger(DeparmentoServiceImpl.class);

    private final DeparmentoRepository deparmentoRepository;

    private final DeparmentoMapper deparmentoMapper;

    public DeparmentoServiceImpl(DeparmentoRepository deparmentoRepository, DeparmentoMapper deparmentoMapper) {
        this.deparmentoRepository = deparmentoRepository;
        this.deparmentoMapper = deparmentoMapper;
    }

    @Override
    public DeparmentoDTO save(DeparmentoDTO deparmentoDTO) {
        LOG.debug("Request to save Deparmento : {}", deparmentoDTO);
        Deparmento deparmento = deparmentoMapper.toEntity(deparmentoDTO);
        deparmento = deparmentoRepository.save(deparmento);
        return deparmentoMapper.toDto(deparmento);
    }

    @Override
    public DeparmentoDTO update(DeparmentoDTO deparmentoDTO) {
        LOG.debug("Request to update Deparmento : {}", deparmentoDTO);
        Deparmento deparmento = deparmentoMapper.toEntity(deparmentoDTO);
        deparmento = deparmentoRepository.save(deparmento);
        return deparmentoMapper.toDto(deparmento);
    }

    @Override
    public Optional<DeparmentoDTO> partialUpdate(DeparmentoDTO deparmentoDTO) {
        LOG.debug("Request to partially update Deparmento : {}", deparmentoDTO);

        return deparmentoRepository
            .findById(deparmentoDTO.getId())
            .map(existingDeparmento -> {
                deparmentoMapper.partialUpdate(existingDeparmento, deparmentoDTO);

                return existingDeparmento;
            })
            .map(deparmentoRepository::save)
            .map(deparmentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeparmentoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Deparmentos");
        return deparmentoRepository.findAll(pageable).map(deparmentoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeparmentoDTO> findOne(Long id) {
        LOG.debug("Request to get Deparmento : {}", id);
        return deparmentoRepository.findById(id).map(deparmentoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Deparmento : {}", id);
        deparmentoRepository.deleteById(id);
    }
}
