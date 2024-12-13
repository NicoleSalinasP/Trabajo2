package com.aiep.evaluacion.web.rest;

import static com.aiep.evaluacion.domain.EmpleadosAsserts.*;
import static com.aiep.evaluacion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.evaluacion.IntegrationTest;
import com.aiep.evaluacion.domain.Empleados;
import com.aiep.evaluacion.repository.EmpleadosRepository;
import com.aiep.evaluacion.service.dto.EmpleadosDTO;
import com.aiep.evaluacion.service.mapper.EmpleadosMapper;
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
 * Integration tests for the {@link EmpleadosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpleadosResourceIT {

    private static final String DEFAULT_NOMBRES_EMPLEADO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES_EMPLEADO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_EMPLEADO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_EMPLEADO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empleados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmpleadosRepository empleadosRepository;

    @Autowired
    private EmpleadosMapper empleadosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpleadosMockMvc;

    private Empleados empleados;

    private Empleados insertedEmpleados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleados createEntity() {
        return new Empleados()
            .nombresEmpleado(DEFAULT_NOMBRES_EMPLEADO)
            .apellidoEmpleado(DEFAULT_APELLIDO_EMPLEADO)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empleados createUpdatedEntity() {
        return new Empleados()
            .nombresEmpleado(UPDATED_NOMBRES_EMPLEADO)
            .apellidoEmpleado(UPDATED_APELLIDO_EMPLEADO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);
    }

    @BeforeEach
    public void initTest() {
        empleados = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmpleados != null) {
            empleadosRepository.delete(insertedEmpleados);
            insertedEmpleados = null;
        }
    }

    @Test
    @Transactional
    void createEmpleados() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);
        var returnedEmpleadosDTO = om.readValue(
            restEmpleadosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empleadosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmpleadosDTO.class
        );

        // Validate the Empleados in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmpleados = empleadosMapper.toEntity(returnedEmpleadosDTO);
        assertEmpleadosUpdatableFieldsEquals(returnedEmpleados, getPersistedEmpleados(returnedEmpleados));

        insertedEmpleados = returnedEmpleados;
    }

    @Test
    @Transactional
    void createEmpleadosWithExistingId() throws Exception {
        // Create the Empleados with an existing ID
        empleados.setId(1L);
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empleadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmpleados() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        // Get all the empleadosList
        restEmpleadosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombresEmpleado").value(hasItem(DEFAULT_NOMBRES_EMPLEADO)))
            .andExpect(jsonPath("$.[*].apellidoEmpleado").value(hasItem(DEFAULT_APELLIDO_EMPLEADO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)));
    }

    @Test
    @Transactional
    void getEmpleados() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        // Get the empleados
        restEmpleadosMockMvc
            .perform(get(ENTITY_API_URL_ID, empleados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empleados.getId().intValue()))
            .andExpect(jsonPath("$.nombresEmpleado").value(DEFAULT_NOMBRES_EMPLEADO))
            .andExpect(jsonPath("$.apellidoEmpleado").value(DEFAULT_APELLIDO_EMPLEADO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO));
    }

    @Test
    @Transactional
    void getNonExistingEmpleados() throws Exception {
        // Get the empleados
        restEmpleadosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmpleados() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empleados
        Empleados updatedEmpleados = empleadosRepository.findById(empleados.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEmpleados are not directly saved in db
        em.detach(updatedEmpleados);
        updatedEmpleados
            .nombresEmpleado(UPDATED_NOMBRES_EMPLEADO)
            .apellidoEmpleado(UPDATED_APELLIDO_EMPLEADO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(updatedEmpleados);

        restEmpleadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empleadosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmpleadosToMatchAllProperties(updatedEmpleados);
    }

    @Test
    @Transactional
    void putNonExistingEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empleadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empleadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(empleadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(empleadosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpleadosWithPatch() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empleados using partial update
        Empleados partialUpdatedEmpleados = new Empleados();
        partialUpdatedEmpleados.setId(empleados.getId());

        partialUpdatedEmpleados.apellidoEmpleado(UPDATED_APELLIDO_EMPLEADO).correo(UPDATED_CORREO);

        restEmpleadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleados.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpleados))
            )
            .andExpect(status().isOk());

        // Validate the Empleados in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpleadosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmpleados, empleados),
            getPersistedEmpleados(empleados)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmpleadosWithPatch() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the empleados using partial update
        Empleados partialUpdatedEmpleados = new Empleados();
        partialUpdatedEmpleados.setId(empleados.getId());

        partialUpdatedEmpleados
            .nombresEmpleado(UPDATED_NOMBRES_EMPLEADO)
            .apellidoEmpleado(UPDATED_APELLIDO_EMPLEADO)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO);

        restEmpleadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpleados.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmpleados))
            )
            .andExpect(status().isOk());

        // Validate the Empleados in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmpleadosUpdatableFieldsEquals(partialUpdatedEmpleados, getPersistedEmpleados(partialUpdatedEmpleados));
    }

    @Test
    @Transactional
    void patchNonExistingEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empleadosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empleadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(empleadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpleados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        empleados.setId(longCount.incrementAndGet());

        // Create the Empleados
        EmpleadosDTO empleadosDTO = empleadosMapper.toDto(empleados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpleadosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(empleadosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empleados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpleados() throws Exception {
        // Initialize the database
        insertedEmpleados = empleadosRepository.saveAndFlush(empleados);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the empleados
        restEmpleadosMockMvc
            .perform(delete(ENTITY_API_URL_ID, empleados.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return empleadosRepository.count();
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

    protected Empleados getPersistedEmpleados(Empleados empleados) {
        return empleadosRepository.findById(empleados.getId()).orElseThrow();
    }

    protected void assertPersistedEmpleadosToMatchAllProperties(Empleados expectedEmpleados) {
        assertEmpleadosAllPropertiesEquals(expectedEmpleados, getPersistedEmpleados(expectedEmpleados));
    }

    protected void assertPersistedEmpleadosToMatchUpdatableProperties(Empleados expectedEmpleados) {
        assertEmpleadosAllUpdatablePropertiesEquals(expectedEmpleados, getPersistedEmpleados(expectedEmpleados));
    }
}
