package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.ValoracionJuego;
import com.globalgaming.repository.ValoracionJuegoRepository;

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
 * Test class for the ValoracionJuegoResource REST controller.
 *
 * @see ValoracionJuegoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class ValoracionJuegoResourceIntTest {

    private static final Double DEFAULT_VALORACION = 0.0D;
    private static final Double UPDATED_VALORACION = 1D;

    private static final ZonedDateTime DEFAULT_TIME_STAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private ValoracionJuegoRepository valoracionJuegoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restValoracionJuegoMockMvc;

    private ValoracionJuego valoracionJuego;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ValoracionJuegoResource valoracionJuegoResource = new ValoracionJuegoResource();
        ReflectionTestUtils.setField(valoracionJuegoResource, "valoracionJuegoRepository", valoracionJuegoRepository);
        this.restValoracionJuegoMockMvc = MockMvcBuilders.standaloneSetup(valoracionJuegoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValoracionJuego createEntity(EntityManager em) {
        ValoracionJuego valoracionJuego = new ValoracionJuego()
                .valoracion(DEFAULT_VALORACION)
                .timeStamp(DEFAULT_TIME_STAMP);
        return valoracionJuego;
    }

    @Before
    public void initTest() {
        valoracionJuego = createEntity(em);
    }

    @Test
    @Transactional
    public void createValoracionJuego() throws Exception {
        int databaseSizeBeforeCreate = valoracionJuegoRepository.findAll().size();

        // Create the ValoracionJuego

        restValoracionJuegoMockMvc.perform(post("/api/valoracion-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionJuego)))
            .andExpect(status().isCreated());

        // Validate the ValoracionJuego in the database
        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeCreate + 1);
        ValoracionJuego testValoracionJuego = valoracionJuegoList.get(valoracionJuegoList.size() - 1);
        assertThat(testValoracionJuego.getValoracion()).isEqualTo(DEFAULT_VALORACION);
        assertThat(testValoracionJuego.getTimeStamp()).isEqualTo(DEFAULT_TIME_STAMP);
    }

    @Test
    @Transactional
    public void createValoracionJuegoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valoracionJuegoRepository.findAll().size();

        // Create the ValoracionJuego with an existing ID
        ValoracionJuego existingValoracionJuego = new ValoracionJuego();
        existingValoracionJuego.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValoracionJuegoMockMvc.perform(post("/api/valoracion-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingValoracionJuego)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValoracionIsRequired() throws Exception {
        int databaseSizeBeforeTest = valoracionJuegoRepository.findAll().size();
        // set the field null
        valoracionJuego.setValoracion(null);

        // Create the ValoracionJuego, which fails.

        restValoracionJuegoMockMvc.perform(post("/api/valoracion-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionJuego)))
            .andExpect(status().isBadRequest());

        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValoracionJuegos() throws Exception {
        // Initialize the database
        valoracionJuegoRepository.saveAndFlush(valoracionJuego);

        // Get all the valoracionJuegoList
        restValoracionJuegoMockMvc.perform(get("/api/valoracion-juegos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valoracionJuego.getId().intValue())))
            .andExpect(jsonPath("$.[*].valoracion").value(hasItem(DEFAULT_VALORACION.doubleValue())))
            .andExpect(jsonPath("$.[*].timeStamp").value(hasItem(sameInstant(DEFAULT_TIME_STAMP))));
    }

    @Test
    @Transactional
    public void getValoracionJuego() throws Exception {
        // Initialize the database
        valoracionJuegoRepository.saveAndFlush(valoracionJuego);

        // Get the valoracionJuego
        restValoracionJuegoMockMvc.perform(get("/api/valoracion-juegos/{id}", valoracionJuego.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valoracionJuego.getId().intValue()))
            .andExpect(jsonPath("$.valoracion").value(DEFAULT_VALORACION.doubleValue()))
            .andExpect(jsonPath("$.timeStamp").value(sameInstant(DEFAULT_TIME_STAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingValoracionJuego() throws Exception {
        // Get the valoracionJuego
        restValoracionJuegoMockMvc.perform(get("/api/valoracion-juegos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValoracionJuego() throws Exception {
        // Initialize the database
        valoracionJuegoRepository.saveAndFlush(valoracionJuego);
        int databaseSizeBeforeUpdate = valoracionJuegoRepository.findAll().size();

        // Update the valoracionJuego
        ValoracionJuego updatedValoracionJuego = valoracionJuegoRepository.findOne(valoracionJuego.getId());
        updatedValoracionJuego
                .valoracion(UPDATED_VALORACION)
                .timeStamp(UPDATED_TIME_STAMP);

        restValoracionJuegoMockMvc.perform(put("/api/valoracion-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValoracionJuego)))
            .andExpect(status().isOk());

        // Validate the ValoracionJuego in the database
        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeUpdate);
        ValoracionJuego testValoracionJuego = valoracionJuegoList.get(valoracionJuegoList.size() - 1);
        assertThat(testValoracionJuego.getValoracion()).isEqualTo(UPDATED_VALORACION);
        assertThat(testValoracionJuego.getTimeStamp()).isEqualTo(UPDATED_TIME_STAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingValoracionJuego() throws Exception {
        int databaseSizeBeforeUpdate = valoracionJuegoRepository.findAll().size();

        // Create the ValoracionJuego

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValoracionJuegoMockMvc.perform(put("/api/valoracion-juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valoracionJuego)))
            .andExpect(status().isCreated());

        // Validate the ValoracionJuego in the database
        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValoracionJuego() throws Exception {
        // Initialize the database
        valoracionJuegoRepository.saveAndFlush(valoracionJuego);
        int databaseSizeBeforeDelete = valoracionJuegoRepository.findAll().size();

        // Get the valoracionJuego
        restValoracionJuegoMockMvc.perform(delete("/api/valoracion-juegos/{id}", valoracionJuego.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValoracionJuego> valoracionJuegoList = valoracionJuegoRepository.findAll();
        assertThat(valoracionJuegoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
