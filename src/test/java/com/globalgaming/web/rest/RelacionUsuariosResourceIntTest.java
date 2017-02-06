package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.RelacionUsuarios;
import com.globalgaming.repository.RelacionUsuariosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.globalgaming.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RelacionUsuariosResource REST controller.
 *
 * @see RelacionUsuariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class RelacionUsuariosResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA_EMISION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_EMISION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_RESOLUCION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_RESOLUCION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_RESULTADO = false;
    private static final Boolean UPDATED_RESULTADO = true;

    @Inject
    private RelacionUsuariosRepository relacionUsuariosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restRelacionUsuariosMockMvc;

    private RelacionUsuarios relacionUsuarios;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RelacionUsuariosResource relacionUsuariosResource = new RelacionUsuariosResource();
        ReflectionTestUtils.setField(relacionUsuariosResource, "relacionUsuariosRepository", relacionUsuariosRepository);
        this.restRelacionUsuariosMockMvc = MockMvcBuilders.standaloneSetup(relacionUsuariosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelacionUsuarios createEntity(EntityManager em) {
        RelacionUsuarios relacionUsuarios = new RelacionUsuarios()
                .fechaEmision(DEFAULT_FECHA_EMISION)
                .fechaResolucion(DEFAULT_FECHA_RESOLUCION)
                .resultado(DEFAULT_RESULTADO);
        return relacionUsuarios;
    }

    @Before
    public void initTest() {
        relacionUsuarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelacionUsuarios() throws Exception {
        int databaseSizeBeforeCreate = relacionUsuariosRepository.findAll().size();

        // Create the RelacionUsuarios

        restRelacionUsuariosMockMvc.perform(post("/api/relacion-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionUsuarios)))
            .andExpect(status().isCreated());

        // Validate the RelacionUsuarios in the database
        List<RelacionUsuarios> relacionUsuariosList = relacionUsuariosRepository.findAll();
        assertThat(relacionUsuariosList).hasSize(databaseSizeBeforeCreate + 1);
        RelacionUsuarios testRelacionUsuarios = relacionUsuariosList.get(relacionUsuariosList.size() - 1);
        assertThat(testRelacionUsuarios.getFechaEmision()).isEqualTo(DEFAULT_FECHA_EMISION);
        assertThat(testRelacionUsuarios.getFechaResolucion()).isEqualTo(DEFAULT_FECHA_RESOLUCION);
        assertThat(testRelacionUsuarios.isResultado()).isEqualTo(DEFAULT_RESULTADO);
    }

    @Test
    @Transactional
    public void createRelacionUsuariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relacionUsuariosRepository.findAll().size();

        // Create the RelacionUsuarios with an existing ID
        RelacionUsuarios existingRelacionUsuarios = new RelacionUsuarios();
        existingRelacionUsuarios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelacionUsuariosMockMvc.perform(post("/api/relacion-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingRelacionUsuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RelacionUsuarios> relacionUsuariosList = relacionUsuariosRepository.findAll();
        assertThat(relacionUsuariosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRelacionUsuarios() throws Exception {
        // Initialize the database
        relacionUsuariosRepository.saveAndFlush(relacionUsuarios);

        // Get all the relacionUsuariosList
        restRelacionUsuariosMockMvc.perform(get("/api/relacion-usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relacionUsuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaEmision").value(hasItem(sameInstant(DEFAULT_FECHA_EMISION))))
            .andExpect(jsonPath("$.[*].fechaResolucion").value(hasItem(sameInstant(DEFAULT_FECHA_RESOLUCION))))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getRelacionUsuarios() throws Exception {
        // Initialize the database
        relacionUsuariosRepository.saveAndFlush(relacionUsuarios);

        // Get the relacionUsuarios
        restRelacionUsuariosMockMvc.perform(get("/api/relacion-usuarios/{id}", relacionUsuarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relacionUsuarios.getId().intValue()))
            .andExpect(jsonPath("$.fechaEmision").value(sameInstant(DEFAULT_FECHA_EMISION)))
            .andExpect(jsonPath("$.fechaResolucion").value(sameInstant(DEFAULT_FECHA_RESOLUCION)))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRelacionUsuarios() throws Exception {
        // Get the relacionUsuarios
        restRelacionUsuariosMockMvc.perform(get("/api/relacion-usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelacionUsuarios() throws Exception {
        // Initialize the database
        relacionUsuariosRepository.saveAndFlush(relacionUsuarios);
        int databaseSizeBeforeUpdate = relacionUsuariosRepository.findAll().size();

        // Update the relacionUsuarios
        RelacionUsuarios updatedRelacionUsuarios = relacionUsuariosRepository.findOne(relacionUsuarios.getId());
        updatedRelacionUsuarios
                .fechaEmision(UPDATED_FECHA_EMISION)
                .fechaResolucion(UPDATED_FECHA_RESOLUCION)
                .resultado(UPDATED_RESULTADO);

        restRelacionUsuariosMockMvc.perform(put("/api/relacion-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelacionUsuarios)))
            .andExpect(status().isOk());

        // Validate the RelacionUsuarios in the database
        List<RelacionUsuarios> relacionUsuariosList = relacionUsuariosRepository.findAll();
        assertThat(relacionUsuariosList).hasSize(databaseSizeBeforeUpdate);
        RelacionUsuarios testRelacionUsuarios = relacionUsuariosList.get(relacionUsuariosList.size() - 1);
        assertThat(testRelacionUsuarios.getFechaEmision()).isEqualTo(UPDATED_FECHA_EMISION);
        assertThat(testRelacionUsuarios.getFechaResolucion()).isEqualTo(UPDATED_FECHA_RESOLUCION);
        assertThat(testRelacionUsuarios.isResultado()).isEqualTo(UPDATED_RESULTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingRelacionUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = relacionUsuariosRepository.findAll().size();

        // Create the RelacionUsuarios

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRelacionUsuariosMockMvc.perform(put("/api/relacion-usuarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relacionUsuarios)))
            .andExpect(status().isCreated());

        // Validate the RelacionUsuarios in the database
        List<RelacionUsuarios> relacionUsuariosList = relacionUsuariosRepository.findAll();
        assertThat(relacionUsuariosList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRelacionUsuarios() throws Exception {
        // Initialize the database
        relacionUsuariosRepository.saveAndFlush(relacionUsuarios);
        int databaseSizeBeforeDelete = relacionUsuariosRepository.findAll().size();

        // Get the relacionUsuarios
        restRelacionUsuariosMockMvc.perform(delete("/api/relacion-usuarios/{id}", relacionUsuarios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelacionUsuarios> relacionUsuariosList = relacionUsuariosRepository.findAll();
        assertThat(relacionUsuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
