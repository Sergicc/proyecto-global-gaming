package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.Mensaje;
import com.globalgaming.repository.MensajeRepository;

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
 * Test class for the MensajeResource REST controller.
 *
 * @see MensajeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class MensajeResourceIntTest {

    private static final ZonedDateTime DEFAULT_HORA_ENVIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_HORA_ENVIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ADJUNTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ADJUNTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ADJUNTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ADJUNTO_CONTENT_TYPE = "image/png";

    @Inject
    private MensajeRepository mensajeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMensajeMockMvc;

    private Mensaje mensaje;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MensajeResource mensajeResource = new MensajeResource();
        ReflectionTestUtils.setField(mensajeResource, "mensajeRepository", mensajeRepository);
        this.restMensajeMockMvc = MockMvcBuilders.standaloneSetup(mensajeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
                .horaEnvio(DEFAULT_HORA_ENVIO)
                .contenido(DEFAULT_CONTENIDO)
                .adjunto(DEFAULT_ADJUNTO)
                .adjuntoContentType(DEFAULT_ADJUNTO_CONTENT_TYPE);
        return mensaje;
    }

    @Before
    public void initTest() {
        mensaje = createEntity(em);
    }

    @Test
    @Transactional
    public void createMensaje() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje

        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate + 1);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getHoraEnvio()).isEqualTo(DEFAULT_HORA_ENVIO);
        assertThat(testMensaje.getContenido()).isEqualTo(DEFAULT_CONTENIDO);
        assertThat(testMensaje.getAdjunto()).isEqualTo(DEFAULT_ADJUNTO);
        assertThat(testMensaje.getAdjuntoContentType()).isEqualTo(DEFAULT_ADJUNTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createMensajeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mensajeRepository.findAll().size();

        // Create the Mensaje with an existing ID
        Mensaje existingMensaje = new Mensaje();
        existingMensaje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeMockMvc.perform(post("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMensaje)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMensajes() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get all the mensajeList
        restMensajeMockMvc.perform(get("/api/mensajes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].horaEnvio").value(hasItem(sameInstant(DEFAULT_HORA_ENVIO))))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO.toString())))
            .andExpect(jsonPath("$.[*].adjuntoContentType").value(hasItem(DEFAULT_ADJUNTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].adjunto").value(hasItem(Base64Utils.encodeToString(DEFAULT_ADJUNTO))));
    }

    @Test
    @Transactional
    public void getMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);

        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", mensaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mensaje.getId().intValue()))
            .andExpect(jsonPath("$.horaEnvio").value(sameInstant(DEFAULT_HORA_ENVIO)))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO.toString()))
            .andExpect(jsonPath("$.adjuntoContentType").value(DEFAULT_ADJUNTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.adjunto").value(Base64Utils.encodeToString(DEFAULT_ADJUNTO)));
    }

    @Test
    @Transactional
    public void getNonExistingMensaje() throws Exception {
        // Get the mensaje
        restMensajeMockMvc.perform(get("/api/mensajes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Update the mensaje
        Mensaje updatedMensaje = mensajeRepository.findOne(mensaje.getId());
        updatedMensaje
                .horaEnvio(UPDATED_HORA_ENVIO)
                .contenido(UPDATED_CONTENIDO)
                .adjunto(UPDATED_ADJUNTO)
                .adjuntoContentType(UPDATED_ADJUNTO_CONTENT_TYPE);

        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMensaje)))
            .andExpect(status().isOk());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate);
        Mensaje testMensaje = mensajeList.get(mensajeList.size() - 1);
        assertThat(testMensaje.getHoraEnvio()).isEqualTo(UPDATED_HORA_ENVIO);
        assertThat(testMensaje.getContenido()).isEqualTo(UPDATED_CONTENIDO);
        assertThat(testMensaje.getAdjunto()).isEqualTo(UPDATED_ADJUNTO);
        assertThat(testMensaje.getAdjuntoContentType()).isEqualTo(UPDATED_ADJUNTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMensaje() throws Exception {
        int databaseSizeBeforeUpdate = mensajeRepository.findAll().size();

        // Create the Mensaje

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMensajeMockMvc.perform(put("/api/mensajes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mensaje)))
            .andExpect(status().isCreated());

        // Validate the Mensaje in the database
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMensaje() throws Exception {
        // Initialize the database
        mensajeRepository.saveAndFlush(mensaje);
        int databaseSizeBeforeDelete = mensajeRepository.findAll().size();

        // Get the mensaje
        restMensajeMockMvc.perform(delete("/api/mensajes/{id}", mensaje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mensaje> mensajeList = mensajeRepository.findAll();
        assertThat(mensajeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
