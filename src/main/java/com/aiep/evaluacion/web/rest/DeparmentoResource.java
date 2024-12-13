package com.aiep.evaluacion.web.rest;

import com.aiep.evaluacion.repository.DeparmentoRepository;
import com.aiep.evaluacion.service.DeparmentoService;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import com.aiep.evaluacion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.evaluacion.domain.Deparmento}.
 */
@RestController
@RequestMapping("/api/deparmentos")
public class DeparmentoResource {

    private static final Logger LOG = LoggerFactory.getLogger(DeparmentoResource.class);

    private static final String ENTITY_NAME = "deparmento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeparmentoService deparmentoService;

    private final DeparmentoRepository deparmentoRepository;

    public DeparmentoResource(DeparmentoService deparmentoService, DeparmentoRepository deparmentoRepository) {
        this.deparmentoService = deparmentoService;
        this.deparmentoRepository = deparmentoRepository;
    }

    /**
     * {@code POST  /deparmentos} : Create a new deparmento.
     *
     * @param deparmentoDTO the deparmentoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deparmentoDTO, or with status {@code 400 (Bad Request)} if the deparmento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DeparmentoDTO> createDeparmento(@RequestBody DeparmentoDTO deparmentoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Deparmento : {}", deparmentoDTO);
        if (deparmentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new deparmento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        deparmentoDTO = deparmentoService.save(deparmentoDTO);
        return ResponseEntity.created(new URI("/api/deparmentos/" + deparmentoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, deparmentoDTO.getId().toString()))
            .body(deparmentoDTO);
    }

    /**
     * {@code PUT  /deparmentos/:id} : Updates an existing deparmento.
     *
     * @param id the id of the deparmentoDTO to save.
     * @param deparmentoDTO the deparmentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deparmentoDTO,
     * or with status {@code 400 (Bad Request)} if the deparmentoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deparmentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeparmentoDTO> updateDeparmento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeparmentoDTO deparmentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Deparmento : {}, {}", id, deparmentoDTO);
        if (deparmentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deparmentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deparmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        deparmentoDTO = deparmentoService.update(deparmentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deparmentoDTO.getId().toString()))
            .body(deparmentoDTO);
    }

    /**
     * {@code PATCH  /deparmentos/:id} : Partial updates given fields of an existing deparmento, field will ignore if it is null
     *
     * @param id the id of the deparmentoDTO to save.
     * @param deparmentoDTO the deparmentoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deparmentoDTO,
     * or with status {@code 400 (Bad Request)} if the deparmentoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the deparmentoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the deparmentoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DeparmentoDTO> partialUpdateDeparmento(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DeparmentoDTO deparmentoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Deparmento partially : {}, {}", id, deparmentoDTO);
        if (deparmentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deparmentoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deparmentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DeparmentoDTO> result = deparmentoService.partialUpdate(deparmentoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deparmentoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /deparmentos} : get all the deparmentos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deparmentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DeparmentoDTO>> getAllDeparmentos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Deparmentos");
        Page<DeparmentoDTO> page = deparmentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deparmentos/:id} : get the "id" deparmento.
     *
     * @param id the id of the deparmentoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deparmentoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeparmentoDTO> getDeparmento(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Deparmento : {}", id);
        Optional<DeparmentoDTO> deparmentoDTO = deparmentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deparmentoDTO);
    }

    /**
     * {@code DELETE  /deparmentos/:id} : delete the "id" deparmento.
     *
     * @param id the id of the deparmentoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeparmento(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Deparmento : {}", id);
        deparmentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
