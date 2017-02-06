package com.globalgaming.web.rest;

import com.globalgaming.ProyectoGlobalGamingApp;

import com.globalgaming.domain.UserExt;
import com.globalgaming.repository.UserExtRepository;

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
 * Test class for the UserExtResource REST controller.
 *
 * @see UserExtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoGlobalGamingApp.class)
public class UserExtResourceIntTest {

    private static final byte[] DEFAULT_AVATAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_AVATAR = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_AVATAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_AVATAR_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NICK = "AAAAAAAAAA";
    private static final String UPDATED_NICK = "BBBBBBBBBB";

    private static final String DEFAULT_ID_BATTLENET = "AAAAAAAAAA";
    private static final String UPDATED_ID_BATTLENET = "BBBBBBBBBB";

    private static final String DEFAULT_ID_STEAM = "AAAAAAAAAA";
    private static final String UPDATED_ID_STEAM = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ID_ORIGIN = "BBBBBBBBBB";

    private static final String DEFAULT_ID_LOL = "AAAAAAAAAA";
    private static final String UPDATED_ID_LOL = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PUNTOS = 1;
    private static final Integer UPDATED_PUNTOS = 2;

    @Inject
    private UserExtRepository userExtRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restUserExtMockMvc;

    private UserExt userExt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserExtResource userExtResource = new UserExtResource();
        ReflectionTestUtils.setField(userExtResource, "userExtRepository", userExtRepository);
        this.restUserExtMockMvc = MockMvcBuilders.standaloneSetup(userExtResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserExt createEntity(EntityManager em) {
        UserExt userExt = new UserExt()
                .avatar(DEFAULT_AVATAR)
                .avatarContentType(DEFAULT_AVATAR_CONTENT_TYPE)
                .nick(DEFAULT_NICK)
                .idBattlenet(DEFAULT_ID_BATTLENET)
                .idSteam(DEFAULT_ID_STEAM)
                .idOrigin(DEFAULT_ID_ORIGIN)
                .idLol(DEFAULT_ID_LOL)
                .pais(DEFAULT_PAIS)
                .puntos(DEFAULT_PUNTOS);
        return userExt;
    }

    @Before
    public void initTest() {
        userExt = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserExt() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Create the UserExt

        restUserExtMockMvc.perform(post("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isCreated());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate + 1);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testUserExt.getAvatarContentType()).isEqualTo(DEFAULT_AVATAR_CONTENT_TYPE);
        assertThat(testUserExt.getNick()).isEqualTo(DEFAULT_NICK);
        assertThat(testUserExt.getIdBattlenet()).isEqualTo(DEFAULT_ID_BATTLENET);
        assertThat(testUserExt.getIdSteam()).isEqualTo(DEFAULT_ID_STEAM);
        assertThat(testUserExt.getIdOrigin()).isEqualTo(DEFAULT_ID_ORIGIN);
        assertThat(testUserExt.getIdLol()).isEqualTo(DEFAULT_ID_LOL);
        assertThat(testUserExt.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testUserExt.getPuntos()).isEqualTo(DEFAULT_PUNTOS);
    }

    @Test
    @Transactional
    public void createUserExtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userExtRepository.findAll().size();

        // Create the UserExt with an existing ID
        UserExt existingUserExt = new UserExt();
        existingUserExt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserExtMockMvc.perform(post("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingUserExt)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserExts() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get all the userExtList
        restUserExtMockMvc.perform(get("/api/user-exts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userExt.getId().intValue())))
            .andExpect(jsonPath("$.[*].avatarContentType").value(hasItem(DEFAULT_AVATAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(Base64Utils.encodeToString(DEFAULT_AVATAR))))
            .andExpect(jsonPath("$.[*].nick").value(hasItem(DEFAULT_NICK.toString())))
            .andExpect(jsonPath("$.[*].idBattlenet").value(hasItem(DEFAULT_ID_BATTLENET.toString())))
            .andExpect(jsonPath("$.[*].idSteam").value(hasItem(DEFAULT_ID_STEAM.toString())))
            .andExpect(jsonPath("$.[*].idOrigin").value(hasItem(DEFAULT_ID_ORIGIN.toString())))
            .andExpect(jsonPath("$.[*].idLol").value(hasItem(DEFAULT_ID_LOL.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS.toString())))
            .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS)));
    }

    @Test
    @Transactional
    public void getUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);

        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", userExt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userExt.getId().intValue()))
            .andExpect(jsonPath("$.avatarContentType").value(DEFAULT_AVATAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.avatar").value(Base64Utils.encodeToString(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.nick").value(DEFAULT_NICK.toString()))
            .andExpect(jsonPath("$.idBattlenet").value(DEFAULT_ID_BATTLENET.toString()))
            .andExpect(jsonPath("$.idSteam").value(DEFAULT_ID_STEAM.toString()))
            .andExpect(jsonPath("$.idOrigin").value(DEFAULT_ID_ORIGIN.toString()))
            .andExpect(jsonPath("$.idLol").value(DEFAULT_ID_LOL.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS.toString()))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS));
    }

    @Test
    @Transactional
    public void getNonExistingUserExt() throws Exception {
        // Get the userExt
        restUserExtMockMvc.perform(get("/api/user-exts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);
        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // Update the userExt
        UserExt updatedUserExt = userExtRepository.findOne(userExt.getId());
        updatedUserExt
                .avatar(UPDATED_AVATAR)
                .avatarContentType(UPDATED_AVATAR_CONTENT_TYPE)
                .nick(UPDATED_NICK)
                .idBattlenet(UPDATED_ID_BATTLENET)
                .idSteam(UPDATED_ID_STEAM)
                .idOrigin(UPDATED_ID_ORIGIN)
                .idLol(UPDATED_ID_LOL)
                .pais(UPDATED_PAIS)
                .puntos(UPDATED_PUNTOS);

        restUserExtMockMvc.perform(put("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserExt)))
            .andExpect(status().isOk());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate);
        UserExt testUserExt = userExtList.get(userExtList.size() - 1);
        assertThat(testUserExt.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testUserExt.getAvatarContentType()).isEqualTo(UPDATED_AVATAR_CONTENT_TYPE);
        assertThat(testUserExt.getNick()).isEqualTo(UPDATED_NICK);
        assertThat(testUserExt.getIdBattlenet()).isEqualTo(UPDATED_ID_BATTLENET);
        assertThat(testUserExt.getIdSteam()).isEqualTo(UPDATED_ID_STEAM);
        assertThat(testUserExt.getIdOrigin()).isEqualTo(UPDATED_ID_ORIGIN);
        assertThat(testUserExt.getIdLol()).isEqualTo(UPDATED_ID_LOL);
        assertThat(testUserExt.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testUserExt.getPuntos()).isEqualTo(UPDATED_PUNTOS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserExt() throws Exception {
        int databaseSizeBeforeUpdate = userExtRepository.findAll().size();

        // Create the UserExt

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserExtMockMvc.perform(put("/api/user-exts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userExt)))
            .andExpect(status().isCreated());

        // Validate the UserExt in the database
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserExt() throws Exception {
        // Initialize the database
        userExtRepository.saveAndFlush(userExt);
        int databaseSizeBeforeDelete = userExtRepository.findAll().size();

        // Get the userExt
        restUserExtMockMvc.perform(delete("/api/user-exts/{id}", userExt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserExt> userExtList = userExtRepository.findAll();
        assertThat(userExtList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
