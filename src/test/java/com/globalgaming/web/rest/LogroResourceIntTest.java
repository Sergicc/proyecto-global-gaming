package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.Logro;
import com.globalgaming.repository.LogroRepository;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LogroResource REST controller.
 *
 * @see LogroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class LogroResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICONO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICONO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICONO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICONO_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_VALORPUNTOS = 1;
    private static final Integer UPDATED_VALORPUNTOS = 2;

    @Inject
    private LogroRepository logroRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLogroMockMvc;

    private Logro logro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LogroResource logroResource = new LogroResource();
        ReflectionTestUtils.setField(logroResource, "logroRepository", logroRepository);
        this.restLogroMockMvc = MockMvcBuilders.standaloneSetup(logroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Logro createEntity(EntityManager em) {
        Logro logro = new Logro()
                .nombre(DEFAULT_NOMBRE)
                .descripcion(DEFAULT_DESCRIPCION)
                .icono(DEFAULT_ICONO)
                .iconoContentType(DEFAULT_ICONO_CONTENT_TYPE)
                .valorpuntos(DEFAULT_VALORPUNTOS);
        return logro;
    }

    @Before
    public void initTest() {
        logro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogro() throws Exception {
        int databaseSizeBeforeCreate = logroRepository.findAll().size();

        // Create the Logro

        restLogroMockMvc.perform(post("/api/logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logro)))
            .andExpect(status().isCreated());

        // Validate the Logro in the database
        List<Logro> logroList = logroRepository.findAll();
        assertThat(logroList).hasSize(databaseSizeBeforeCreate + 1);
        Logro testLogro = logroList.get(logroList.size() - 1);
        assertThat(testLogro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLogro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testLogro.getIcono()).isEqualTo(DEFAULT_ICONO);
        assertThat(testLogro.getIconoContentType()).isEqualTo(DEFAULT_ICONO_CONTENT_TYPE);
        assertThat(testLogro.getValorpuntos()).isEqualTo(DEFAULT_VALORPUNTOS);
    }

    @Test
    @Transactional
    public void createLogroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logroRepository.findAll().size();

        // Create the Logro with an existing ID
        Logro existingLogro = new Logro();
        existingLogro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogroMockMvc.perform(post("/api/logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLogro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Logro> logroList = logroRepository.findAll();
        assertThat(logroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogroes() throws Exception {
        // Initialize the database
        logroRepository.saveAndFlush(logro);

        // Get all the logroList
        restLogroMockMvc.perform(get("/api/logroes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].iconoContentType").value(hasItem(DEFAULT_ICONO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icono").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICONO))))
            .andExpect(jsonPath("$.[*].valorpuntos").value(hasItem(DEFAULT_VALORPUNTOS)));
    }

    @Test
    @Transactional
    public void getLogro() throws Exception {
        // Initialize the database
        logroRepository.saveAndFlush(logro);

        // Get the logro
        restLogroMockMvc.perform(get("/api/logroes/{id}", logro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.iconoContentType").value(DEFAULT_ICONO_CONTENT_TYPE))
            .andExpect(jsonPath("$.icono").value(Base64Utils.encodeToString(DEFAULT_ICONO)))
            .andExpect(jsonPath("$.valorpuntos").value(DEFAULT_VALORPUNTOS));
    }

    @Test
    @Transactional
    public void getNonExistingLogro() throws Exception {
        // Get the logro
        restLogroMockMvc.perform(get("/api/logroes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogro() throws Exception {
        // Initialize the database
        logroRepository.saveAndFlush(logro);
        int databaseSizeBeforeUpdate = logroRepository.findAll().size();

        // Update the logro
        Logro updatedLogro = logroRepository.findOne(logro.getId());
        updatedLogro
                .nombre(UPDATED_NOMBRE)
                .descripcion(UPDATED_DESCRIPCION)
                .icono(UPDATED_ICONO)
                .iconoContentType(UPDATED_ICONO_CONTENT_TYPE)
                .valorpuntos(UPDATED_VALORPUNTOS);

        restLogroMockMvc.perform(put("/api/logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLogro)))
            .andExpect(status().isOk());

        // Validate the Logro in the database
        List<Logro> logroList = logroRepository.findAll();
        assertThat(logroList).hasSize(databaseSizeBeforeUpdate);
        Logro testLogro = logroList.get(logroList.size() - 1);
        assertThat(testLogro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLogro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testLogro.getIcono()).isEqualTo(UPDATED_ICONO);
        assertThat(testLogro.getIconoContentType()).isEqualTo(UPDATED_ICONO_CONTENT_TYPE);
        assertThat(testLogro.getValorpuntos()).isEqualTo(UPDATED_VALORPUNTOS);
    }

    @Test
    @Transactional
    public void updateNonExistingLogro() throws Exception {
        int databaseSizeBeforeUpdate = logroRepository.findAll().size();

        // Create the Logro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogroMockMvc.perform(put("/api/logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logro)))
            .andExpect(status().isCreated());

        // Validate the Logro in the database
        List<Logro> logroList = logroRepository.findAll();
        assertThat(logroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogro() throws Exception {
        // Initialize the database
        logroRepository.saveAndFlush(logro);
        int databaseSizeBeforeDelete = logroRepository.findAll().size();

        // Get the logro
        restLogroMockMvc.perform(delete("/api/logroes/{id}", logro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Logro> logroList = logroRepository.findAll();
        assertThat(logroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
