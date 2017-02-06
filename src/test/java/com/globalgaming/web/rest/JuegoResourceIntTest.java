package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.Juego;
import com.globalgaming.repository.JuegoRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JuegoResource REST controller.
 *
 * @see JuegoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class JuegoResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PORTADA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PORTADA = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PORTADA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PORTADA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TRAILER = "AAAAAAAAAA";
    private static final String UPDATED_TRAILER = "BBBBBBBBBB";

    private static final String DEFAULT_DESARROLLADOR = "AAAAAAAAAA";
    private static final String UPDATED_DESARROLLADOR = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final Integer DEFAULT_EDAD_RECOMENDADA = 1;
    private static final Integer UPDATED_EDAD_RECOMENDADA = 2;

    private static final LocalDate DEFAULT_FECHA_LANZAMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LANZAMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CAPACIDAD_JUGADORES = 1;
    private static final Integer UPDATED_CAPACIDAD_JUGADORES = 2;

    private static final Double DEFAULT_VALORACION_WEB = 1D;
    private static final Double UPDATED_VALORACION_WEB = 2D;

    private static final Double DEFAULT_VALORACION_USERS = 1D;
    private static final Double UPDATED_VALORACION_USERS = 2D;

    @Inject
    private JuegoRepository juegoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restJuegoMockMvc;

    private Juego juego;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JuegoResource juegoResource = new JuegoResource();
        ReflectionTestUtils.setField(juegoResource, "juegoRepository", juegoRepository);
        this.restJuegoMockMvc = MockMvcBuilders.standaloneSetup(juegoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Juego createEntity(EntityManager em) {
        Juego juego = new Juego()
                .titulo(DEFAULT_TITULO)
                .portada(DEFAULT_PORTADA)
                .portadaContentType(DEFAULT_PORTADA_CONTENT_TYPE)
                .descripcion(DEFAULT_DESCRIPCION)
                .trailer(DEFAULT_TRAILER)
                .desarrollador(DEFAULT_DESARROLLADOR)
                .genero(DEFAULT_GENERO)
                .edadRecomendada(DEFAULT_EDAD_RECOMENDADA)
                .fechaLanzamiento(DEFAULT_FECHA_LANZAMIENTO)
                .capacidadJugadores(DEFAULT_CAPACIDAD_JUGADORES)
                .valoracionWeb(DEFAULT_VALORACION_WEB)
                .valoracionUsers(DEFAULT_VALORACION_USERS);
        return juego;
    }

    @Before
    public void initTest() {
        juego = createEntity(em);
    }

    @Test
    @Transactional
    public void createJuego() throws Exception {
        int databaseSizeBeforeCreate = juegoRepository.findAll().size();

        // Create the Juego

        restJuegoMockMvc.perform(post("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juego)))
            .andExpect(status().isCreated());

        // Validate the Juego in the database
        List<Juego> juegoList = juegoRepository.findAll();
        assertThat(juegoList).hasSize(databaseSizeBeforeCreate + 1);
        Juego testJuego = juegoList.get(juegoList.size() - 1);
        assertThat(testJuego.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testJuego.getPortada()).isEqualTo(DEFAULT_PORTADA);
        assertThat(testJuego.getPortadaContentType()).isEqualTo(DEFAULT_PORTADA_CONTENT_TYPE);
        assertThat(testJuego.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testJuego.getTrailer()).isEqualTo(DEFAULT_TRAILER);
        assertThat(testJuego.getDesarrollador()).isEqualTo(DEFAULT_DESARROLLADOR);
        assertThat(testJuego.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testJuego.getEdadRecomendada()).isEqualTo(DEFAULT_EDAD_RECOMENDADA);
        assertThat(testJuego.getFechaLanzamiento()).isEqualTo(DEFAULT_FECHA_LANZAMIENTO);
        assertThat(testJuego.getCapacidadJugadores()).isEqualTo(DEFAULT_CAPACIDAD_JUGADORES);
        assertThat(testJuego.getValoracionWeb()).isEqualTo(DEFAULT_VALORACION_WEB);
        assertThat(testJuego.getValoracionUsers()).isEqualTo(DEFAULT_VALORACION_USERS);
    }

    @Test
    @Transactional
    public void createJuegoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = juegoRepository.findAll().size();

        // Create the Juego with an existing ID
        Juego existingJuego = new Juego();
        existingJuego.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJuegoMockMvc.perform(post("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingJuego)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Juego> juegoList = juegoRepository.findAll();
        assertThat(juegoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJuegos() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);

        // Get all the juegoList
        restJuegoMockMvc.perform(get("/api/juegos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(juego.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].portadaContentType").value(hasItem(DEFAULT_PORTADA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].portada").value(hasItem(Base64Utils.encodeToString(DEFAULT_PORTADA))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].trailer").value(hasItem(DEFAULT_TRAILER.toString())))
            .andExpect(jsonPath("$.[*].desarrollador").value(hasItem(DEFAULT_DESARROLLADOR.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].edadRecomendada").value(hasItem(DEFAULT_EDAD_RECOMENDADA)))
            .andExpect(jsonPath("$.[*].fechaLanzamiento").value(hasItem(DEFAULT_FECHA_LANZAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].capacidadJugadores").value(hasItem(DEFAULT_CAPACIDAD_JUGADORES)))
            .andExpect(jsonPath("$.[*].valoracionWeb").value(hasItem(DEFAULT_VALORACION_WEB.doubleValue())))
            .andExpect(jsonPath("$.[*].valoracionUsers").value(hasItem(DEFAULT_VALORACION_USERS.doubleValue())));
    }

    @Test
    @Transactional
    public void getJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);

        // Get the juego
        restJuegoMockMvc.perform(get("/api/juegos/{id}", juego.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(juego.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.portadaContentType").value(DEFAULT_PORTADA_CONTENT_TYPE))
            .andExpect(jsonPath("$.portada").value(Base64Utils.encodeToString(DEFAULT_PORTADA)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.trailer").value(DEFAULT_TRAILER.toString()))
            .andExpect(jsonPath("$.desarrollador").value(DEFAULT_DESARROLLADOR.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.edadRecomendada").value(DEFAULT_EDAD_RECOMENDADA))
            .andExpect(jsonPath("$.fechaLanzamiento").value(DEFAULT_FECHA_LANZAMIENTO.toString()))
            .andExpect(jsonPath("$.capacidadJugadores").value(DEFAULT_CAPACIDAD_JUGADORES))
            .andExpect(jsonPath("$.valoracionWeb").value(DEFAULT_VALORACION_WEB.doubleValue()))
            .andExpect(jsonPath("$.valoracionUsers").value(DEFAULT_VALORACION_USERS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJuego() throws Exception {
        // Get the juego
        restJuegoMockMvc.perform(get("/api/juegos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);
        int databaseSizeBeforeUpdate = juegoRepository.findAll().size();

        // Update the juego
        Juego updatedJuego = juegoRepository.findOne(juego.getId());
        updatedJuego
                .titulo(UPDATED_TITULO)
                .portada(UPDATED_PORTADA)
                .portadaContentType(UPDATED_PORTADA_CONTENT_TYPE)
                .descripcion(UPDATED_DESCRIPCION)
                .trailer(UPDATED_TRAILER)
                .desarrollador(UPDATED_DESARROLLADOR)
                .genero(UPDATED_GENERO)
                .edadRecomendada(UPDATED_EDAD_RECOMENDADA)
                .fechaLanzamiento(UPDATED_FECHA_LANZAMIENTO)
                .capacidadJugadores(UPDATED_CAPACIDAD_JUGADORES)
                .valoracionWeb(UPDATED_VALORACION_WEB)
                .valoracionUsers(UPDATED_VALORACION_USERS);

        restJuegoMockMvc.perform(put("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJuego)))
            .andExpect(status().isOk());

        // Validate the Juego in the database
        List<Juego> juegoList = juegoRepository.findAll();
        assertThat(juegoList).hasSize(databaseSizeBeforeUpdate);
        Juego testJuego = juegoList.get(juegoList.size() - 1);
        assertThat(testJuego.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testJuego.getPortada()).isEqualTo(UPDATED_PORTADA);
        assertThat(testJuego.getPortadaContentType()).isEqualTo(UPDATED_PORTADA_CONTENT_TYPE);
        assertThat(testJuego.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testJuego.getTrailer()).isEqualTo(UPDATED_TRAILER);
        assertThat(testJuego.getDesarrollador()).isEqualTo(UPDATED_DESARROLLADOR);
        assertThat(testJuego.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testJuego.getEdadRecomendada()).isEqualTo(UPDATED_EDAD_RECOMENDADA);
        assertThat(testJuego.getFechaLanzamiento()).isEqualTo(UPDATED_FECHA_LANZAMIENTO);
        assertThat(testJuego.getCapacidadJugadores()).isEqualTo(UPDATED_CAPACIDAD_JUGADORES);
        assertThat(testJuego.getValoracionWeb()).isEqualTo(UPDATED_VALORACION_WEB);
        assertThat(testJuego.getValoracionUsers()).isEqualTo(UPDATED_VALORACION_USERS);
    }

    @Test
    @Transactional
    public void updateNonExistingJuego() throws Exception {
        int databaseSizeBeforeUpdate = juegoRepository.findAll().size();

        // Create the Juego

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJuegoMockMvc.perform(put("/api/juegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(juego)))
            .andExpect(status().isCreated());

        // Validate the Juego in the database
        List<Juego> juegoList = juegoRepository.findAll();
        assertThat(juegoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJuego() throws Exception {
        // Initialize the database
        juegoRepository.saveAndFlush(juego);
        int databaseSizeBeforeDelete = juegoRepository.findAll().size();

        // Get the juego
        restJuegoMockMvc.perform(delete("/api/juegos/{id}", juego.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Juego> juegoList = juegoRepository.findAll();
        assertThat(juegoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
