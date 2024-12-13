package com.aiep.evaluacion.service.impl;

import com.aiep.evaluacion.domain.Jefe;
import com.aiep.evaluacion.repository.JefeRepository;
import com.aiep.evaluacion.service.JefeService;
import com.aiep.evaluacion.service.dto.JefeDTO;
import com.aiep.evaluacion.service.mapper.JefeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.evaluacion.domain.Jefe}.
 */
@Service
@Transactional
public class JefeServiceImpl implements JefeService {

    private static final Logger LOG = LoggerFactory.getLogger(JefeServiceImpl.class);

    private final JefeRepository jefeRepository;

    private final JefeMapper jefeMapper;

    public JefeServiceImpl(JefeRepository jefeRepository, JefeMapper jefeMapper) {
        this.jefeRepository = jefeRepository;
        this.jefeMapper = jefeMapper;
    }

    @Override
    public JefeDTO save(JefeDTO jefeDTO) {
        LOG.debug("Request to save Jefe : {}", jefeDTO);
        Jefe jefe = jefeMapper.toEntity(jefeDTO);
        jefe = jefeRepository.save(jefe);
        return jefeMapper.toDto(jefe);
    }

    @Override
    public JefeDTO update(JefeDTO jefeDTO) {
        LOG.debug("Request to update Jefe : {}", jefeDTO);
        Jefe jefe = jefeMapper.toEntity(jefeDTO);
        jefe = jefeRepository.save(jefe);
        return jefeMapper.toDto(jefe);
    }

    @Override
    public Optional<JefeDTO> partialUpdate(JefeDTO jefeDTO) {
        LOG.debug("Request to partially update Jefe : {}", jefeDTO);

        return jefeRepository
            .findById(jefeDTO.getId())
            .map(existingJefe -> {
                jefeMapper.partialUpdate(existingJefe, jefeDTO);

                return existingJefe;
            })
            .map(jefeRepository::save)
            .map(jefeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JefeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Jefes");
        return jefeRepository.findAll(pageable).map(jefeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JefeDTO> findOne(Long id) {
        LOG.debug("Request to get Jefe : {}", id);
        return jefeRepository.findById(id).map(jefeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Jefe : {}", id);
        jefeRepository.deleteById(id);
    }
}
