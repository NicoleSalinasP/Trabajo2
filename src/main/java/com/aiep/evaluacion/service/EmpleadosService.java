package com.aiep.evaluacion.service;

import com.aiep.evaluacion.service.dto.EmpleadosDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.evaluacion.domain.Empleados}.
 */
public interface EmpleadosService {
    /**
     * Save a empleados.
     *
     * @param empleadosDTO the entity to save.
     * @return the persisted entity.
     */
    EmpleadosDTO save(EmpleadosDTO empleadosDTO);

    /**
     * Updates a empleados.
     *
     * @param empleadosDTO the entity to update.
     * @return the persisted entity.
     */
    EmpleadosDTO update(EmpleadosDTO empleadosDTO);

    /**
     * Partially updates a empleados.
     *
     * @param empleadosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmpleadosDTO> partialUpdate(EmpleadosDTO empleadosDTO);

    /**
     * Get all the empleados.
     *
     * @return the list of entities.
     */
    List<EmpleadosDTO> findAll();

    /**
     * Get the "id" empleados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmpleadosDTO> findOne(Long id);

    /**
     * Delete the "id" empleados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
