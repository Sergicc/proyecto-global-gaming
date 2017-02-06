package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.UserLogro;
import com.globalgaming.repository.UserLogroRepository;

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
 * Test class for the UserLogroResource REST controller.
 *
 * @see UserLogroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class UserLogroResourceIntTest {

    private static final ZonedDateTime DEFAULT_FECHA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private UserLogroRepository userLogroRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserLogroMockMvc;

    private UserLogro userLogro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserLogroResource userLogroResource = new UserLogroResource();
        ReflectionTestUtils.setField(userLogroResource, "userLogroRepository", userLogroRepository);
        this.restUserLogroMockMvc = MockMvcBuilders.standaloneSetup(userLogroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLogro createEntity(EntityManager em) {
        UserLogro userLogro = new UserLogro()
                .fecha(DEFAULT_FECHA);
        return userLogro;
    }

    @Before
    public void initTest() {
        userLogro = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserLogro() throws Exception {
        int databaseSizeBeforeCreate = userLogroRepository.findAll().size();

        // Create the UserLogro

        restUserLogroMockMvc.perform(post("/api/user-logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLogro)))
            .andExpect(status().isCreated());

        // Validate the UserLogro in the database
        List<UserLogro> userLogroList = userLogroRepository.findAll();
        assertThat(userLogroList).hasSize(databaseSizeBeforeCreate + 1);
        UserLogro testUserLogro = userLogroList.get(userLogroList.size() - 1);
        assertThat(testUserLogro.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createUserLogroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userLogroRepository.findAll().size();

        // Create the UserLogro with an existing ID
        UserLogro existingUserLogro = new UserLogro();
        existingUserLogro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLogroMockMvc.perform(post("/api/user-logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserLogro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserLogro> userLogroList = userLogroRepository.findAll();
        assertThat(userLogroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserLogroes() throws Exception {
        // Initialize the database
        userLogroRepository.saveAndFlush(userLogro);

        // Get all the userLogroList
        restUserLogroMockMvc.perform(get("/api/user-logroes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLogro.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(sameInstant(DEFAULT_FECHA))));
    }

    @Test
    @Transactional
    public void getUserLogro() throws Exception {
        // Initialize the database
        userLogroRepository.saveAndFlush(userLogro);

        // Get the userLogro
        restUserLogroMockMvc.perform(get("/api/user-logroes/{id}", userLogro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userLogro.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(sameInstant(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    public void getNonExistingUserLogro() throws Exception {
        // Get the userLogro
        restUserLogroMockMvc.perform(get("/api/user-logroes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserLogro() throws Exception {
        // Initialize the database
        userLogroRepository.saveAndFlush(userLogro);
        int databaseSizeBeforeUpdate = userLogroRepository.findAll().size();

        // Update the userLogro
        UserLogro updatedUserLogro = userLogroRepository.findOne(userLogro.getId());
        updatedUserLogro
                .fecha(UPDATED_FECHA);

        restUserLogroMockMvc.perform(put("/api/user-logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserLogro)))
            .andExpect(status().isOk());

        // Validate the UserLogro in the database
        List<UserLogro> userLogroList = userLogroRepository.findAll();
        assertThat(userLogroList).hasSize(databaseSizeBeforeUpdate);
        UserLogro testUserLogro = userLogroList.get(userLogroList.size() - 1);
        assertThat(testUserLogro.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingUserLogro() throws Exception {
        int databaseSizeBeforeUpdate = userLogroRepository.findAll().size();

        // Create the UserLogro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserLogroMockMvc.perform(put("/api/user-logroes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLogro)))
            .andExpect(status().isCreated());

        // Validate the UserLogro in the database
        List<UserLogro> userLogroList = userLogroRepository.findAll();
        assertThat(userLogroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserLogro() throws Exception {
        // Initialize the database
        userLogroRepository.saveAndFlush(userLogro);
        int databaseSizeBeforeDelete = userLogroRepository.findAll().size();

        // Get the userLogro
        restUserLogroMockMvc.perform(delete("/api/user-logroes/{id}", userLogro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserLogro> userLogroList = userLogroRepository.findAll();
        assertThat(userLogroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
