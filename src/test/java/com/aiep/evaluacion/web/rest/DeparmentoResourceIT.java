package com.aiep.evaluacion.web.rest;

import static com.aiep.evaluacion.domain.DeparmentoAsserts.*;
import static com.aiep.evaluacion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.evaluacion.IntegrationTest;
import com.aiep.evaluacion.domain.Deparmento;
import com.aiep.evaluacion.repository.DeparmentoRepository;
import com.aiep.evaluacion.service.dto.DeparmentoDTO;
import com.aiep.evaluacion.service.mapper.DeparmentoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DeparmentoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeparmentoResourceIT {

    private static final String DEFAULT_NOMBRE_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_PRESUPUESTO_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_PRESUPUESTO_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deparmentos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DeparmentoRepository deparmentoRepository;

    @Autowired
    private DeparmentoMapper deparmentoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeparmentoMockMvc;

    private Deparmento deparmento;

    private Deparmento insertedDeparmento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deparmento createEntity() {
        return new Deparmento()
            .nombreDepartamento(DEFAULT_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(DEFAULT_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(DEFAULT_PRESUPUESTO_DEPARTAMENTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deparmento createUpdatedEntity() {
        return new Deparmento()
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);
    }

    @BeforeEach
    public void initTest() {
        deparmento = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDeparmento != null) {
            deparmentoRepository.delete(insertedDeparmento);
            insertedDeparmento = null;
        }
    }

    @Test
    @Transactional
    void createDeparmento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);
        var returnedDeparmentoDTO = om.readValue(
            restDeparmentoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deparmentoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DeparmentoDTO.class
        );

        // Validate the Deparmento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDeparmento = deparmentoMapper.toEntity(returnedDeparmentoDTO);
        assertDeparmentoUpdatableFieldsEquals(returnedDeparmento, getPersistedDeparmento(returnedDeparmento));

        insertedDeparmento = returnedDeparmento;
    }

    @Test
    @Transactional
    void createDeparmentoWithExistingId() throws Exception {
        // Create the Deparmento with an existing ID
        deparmento.setId(1L);
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeparmentoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deparmentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeparmentos() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        // Get all the deparmentoList
        restDeparmentoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deparmento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreDepartamento").value(hasItem(DEFAULT_NOMBRE_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].ubicacionDepartamento").value(hasItem(DEFAULT_UBICACION_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].presupuestoDepartamento").value(hasItem(DEFAULT_PRESUPUESTO_DEPARTAMENTO)));
    }

    @Test
    @Transactional
    void getDeparmento() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        // Get the deparmento
        restDeparmentoMockMvc
            .perform(get(ENTITY_API_URL_ID, deparmento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deparmento.getId().intValue()))
            .andExpect(jsonPath("$.nombreDepartamento").value(DEFAULT_NOMBRE_DEPARTAMENTO))
            .andExpect(jsonPath("$.ubicacionDepartamento").value(DEFAULT_UBICACION_DEPARTAMENTO))
            .andExpect(jsonPath("$.presupuestoDepartamento").value(DEFAULT_PRESUPUESTO_DEPARTAMENTO));
    }

    @Test
    @Transactional
    void getNonExistingDeparmento() throws Exception {
        // Get the deparmento
        restDeparmentoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeparmento() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deparmento
        Deparmento updatedDeparmento = deparmentoRepository.findById(deparmento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDeparmento are not directly saved in db
        em.detach(updatedDeparmento);
        updatedDeparmento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(updatedDeparmento);

        restDeparmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deparmentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deparmentoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDeparmentoToMatchAllProperties(updatedDeparmento);
    }

    @Test
    @Transactional
    void putNonExistingDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, deparmentoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deparmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(deparmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(deparmentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeparmentoWithPatch() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deparmento using partial update
        Deparmento partialUpdatedDeparmento = new Deparmento();
        partialUpdatedDeparmento.setId(deparmento.getId());

        partialUpdatedDeparmento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);

        restDeparmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeparmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeparmento))
            )
            .andExpect(status().isOk());

        // Validate the Deparmento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeparmentoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDeparmento, deparmento),
            getPersistedDeparmento(deparmento)
        );
    }

    @Test
    @Transactional
    void fullUpdateDeparmentoWithPatch() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the deparmento using partial update
        Deparmento partialUpdatedDeparmento = new Deparmento();
        partialUpdatedDeparmento.setId(deparmento.getId());

        partialUpdatedDeparmento
            .nombreDepartamento(UPDATED_NOMBRE_DEPARTAMENTO)
            .ubicacionDepartamento(UPDATED_UBICACION_DEPARTAMENTO)
            .presupuestoDepartamento(UPDATED_PRESUPUESTO_DEPARTAMENTO);

        restDeparmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeparmento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDeparmento))
            )
            .andExpect(status().isOk());

        // Validate the Deparmento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDeparmentoUpdatableFieldsEquals(partialUpdatedDeparmento, getPersistedDeparmento(partialUpdatedDeparmento));
    }

    @Test
    @Transactional
    void patchNonExistingDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, deparmentoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deparmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(deparmentoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeparmento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        deparmento.setId(longCount.incrementAndGet());

        // Create the Deparmento
        DeparmentoDTO deparmentoDTO = deparmentoMapper.toDto(deparmento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeparmentoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(deparmentoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Deparmento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeparmento() throws Exception {
        // Initialize the database
        insertedDeparmento = deparmentoRepository.saveAndFlush(deparmento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the deparmento
        restDeparmentoMockMvc
            .perform(delete(ENTITY_API_URL_ID, deparmento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return deparmentoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Deparmento getPersistedDeparmento(Deparmento deparmento) {
        return deparmentoRepository.findById(deparmento.getId()).orElseThrow();
    }

    protected void assertPersistedDeparmentoToMatchAllProperties(Deparmento expectedDeparmento) {
        assertDeparmentoAllPropertiesEquals(expectedDeparmento, getPersistedDeparmento(expectedDeparmento));
    }

    protected void assertPersistedDeparmentoToMatchUpdatableProperties(Deparmento expectedDeparmento) {
        assertDeparmentoAllUpdatablePropertiesEquals(expectedDeparmento, getPersistedDeparmento(expectedDeparmento));
    }
}
