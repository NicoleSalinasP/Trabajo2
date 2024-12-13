package com.aiep.evaluacion.service;

import com.aiep.evaluacion.service.dto.JefeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Jefe}.
 */
public interface JefeService {
    /**
     * Save a jefe.
     *
     * @param jefeDTO the entity to save.
     * @return the persisted entity.
     */
    JefeDTO save(JefeDTO jefeDTO);

    /**
     * Updates a jefe.
     *
     * @param jefeDTO the entity to update.
     * @return the persisted entity.
     */
    JefeDTO update(JefeDTO jefeDTO);

    /**
     * Partially updates a jefe.
     *
     * @param jefeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JefeDTO> partialUpdate(JefeDTO jefeDTO);

    /**
     * Get all the jefes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JefeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jefe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JefeDTO> findOne(Long id);

    /**
     * Delete the "id" jefe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
