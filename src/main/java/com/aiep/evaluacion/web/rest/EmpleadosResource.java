package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.repository.EmpleadosRepository;
import com.aiep.evaluacion.service.EmpleadosService;
import com.aiep.evaluacion.service.dto.EmpleadosDTO;
import com.aiep.evaluacion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.evaluacion.domain.Empleados}.
 */
@RestController
@RequestMapping("/api/empleados")
public class EmpleadosResource {

    private static final Logger LOG = LoggerFactory.getLogger(EmpleadosResource.class);

    private static final String ENTITY_NAME = "empleados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpleadosService empleadosService;

    private final EmpleadosRepository empleadosRepository;

    public EmpleadosResource(EmpleadosService empleadosService, EmpleadosRepository empleadosRepository) {
        this.empleadosService = empleadosService;
        this.empleadosRepository = empleadosRepository;
    }

    /**
     * {@code POST  /empleados} : Create a new empleados.
     *
     * @param empleadosDTO the empleadosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empleadosDTO, or with status {@code 400 (Bad Request)} if the empleados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmpleadosDTO> createEmpleados(@RequestBody EmpleadosDTO empleadosDTO) throws URISyntaxException {
        LOG.debug("REST request to save Empleados : {}", empleadosDTO);
        if (empleadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new empleados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empleadosDTO = empleadosService.save(empleadosDTO);
        return ResponseEntity.created(new URI("/api/empleados/" + empleadosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empleadosDTO.getId().toString()))
            .body(empleadosDTO);
    }

    /**
     * {@code PUT  /empleados/:id} : Updates an existing empleados.
     *
     * @param id the id of the empleadosDTO to save.
     * @param empleadosDTO the empleadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleadosDTO,
     * or with status {@code 400 (Bad Request)} if the empleadosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empleadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadosDTO> updateEmpleados(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadosDTO empleadosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Empleados : {}, {}", id, empleadosDTO);
        if (empleadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empleadosDTO = empleadosService.update(empleadosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadosDTO.getId().toString()))
            .body(empleadosDTO);
    }

    /**
     * {@code PATCH  /empleados/:id} : Partial updates given fields of an existing empleados, field will ignore if it is null
     *
     * @param id the id of the empleadosDTO to save.
     * @param empleadosDTO the empleadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empleadosDTO,
     * or with status {@code 400 (Bad Request)} if the empleadosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the empleadosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the empleadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpleadosDTO> partialUpdateEmpleados(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmpleadosDTO empleadosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Empleados partially : {}, {}", id, empleadosDTO);
        if (empleadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empleadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empleadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpleadosDTO> result = empleadosService.partialUpdate(empleadosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empleadosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /empleados} : get all the empleados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empleados in body.
     */
    @GetMapping("")
    public List<EmpleadosDTO> getAllEmpleados() {
        LOG.debug("REST request to get all Empleados");
        return empleadosService.findAll();
    }

    /**
     * {@code GET  /empleados/:id} : get the "id" empleados.
     *
     * @param id the id of the empleadosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empleadosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadosDTO> getEmpleados(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Empleados : {}", id);
        Optional<EmpleadosDTO> empleadosDTO = empleadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empleadosDTO);
    }

    /**
     * {@code DELETE  /empleados/:id} : delete the "id" empleados.
     *
     * @param id the id of the empleadosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleados(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Empleados : {}", id);
        empleadosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
