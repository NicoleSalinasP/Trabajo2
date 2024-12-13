package com.aiep.evaluacion.service;

import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Deparmento}.
 */
public interface DeparmentoService {
    /**
     * Save a deparmento.
     *
     * @param deparmentoDTO the entity to save.
     * @return the persisted entity.
     */
    DeparmentoDTO save(DeparmentoDTO deparmentoDTO);

    /**
     * Updates a deparmento.
     *
     * @param deparmentoDTO the entity to update.
     * @return the persisted entity.
     */
    DeparmentoDTO update(DeparmentoDTO deparmentoDTO);

    /**
     * Partially updates a deparmento.
     *
     * @param deparmentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DeparmentoDTO> partialUpdate(DeparmentoDTO deparmentoDTO);

    /**
     * Get all the deparmentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeparmentoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" deparmento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeparmentoDTO> findOne(Long id);

    /**
     * Delete the "id" deparmento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
