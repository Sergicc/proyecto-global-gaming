package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Foto;

import com.globalgaming.domain.User;
import com.globalgaming.repository.FotoRepository;
import com.globalgaming.repository.UserRepository;
import com.globalgaming.security.SecurityUtils;
import com.globalgaming.web.rest.util.HeaderUtil;
import com.globalgaming.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Foto.
 */
@RestController
@RequestMapping("/api")
public class FotoResource {

    private final Logger log = LoggerFactory.getLogger(FotoResource.class);

    @Inject
    private FotoRepository fotoRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /fotos : Create a new foto.
     *
     * @param foto the foto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new foto, or with status 400 (Bad Request) if the foto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fotos")
    @Timed
    public ResponseEntity<Foto> createFoto(@RequestBody Foto foto) throws URISyntaxException {
        log.debug("REST request to save Foto : {}", foto);
        if (foto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("foto", "idexists", "A new foto cannot already have an ID")).body(null);
        }

        User user= userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        foto.setFechaCreacion(ZonedDateTime.now());

        Foto result = fotoRepository.save(foto);
        return ResponseEntity.created(new URI("/api/fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("foto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fotos : Updates an existing foto.
     *
     * @param foto the foto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated foto,
     * or with status 400 (Bad Request) if the foto is not valid,
     * or with status 500 (Internal Server Error) if the foto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fotos")
    @Timed
    public ResponseEntity<Foto> updateFoto(@RequestBody Foto foto) throws URISyntaxException {
        log.debug("REST request to update Foto : {}", foto);
        if (foto.getId() == null) {
            return createFoto(foto);
        }
        Foto result = fotoRepository.save(foto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("foto", foto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fotos : get all the fotos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fotos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fotos")
    @Timed
    public ResponseEntity<List<Foto>> getAllFotos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fotos");
        Page<Foto> page = fotoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fotos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fotos/:id : get the "id" foto.
     *
     * @param id the id of the foto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the foto, or with status 404 (Not Found)
     */
    @GetMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Foto> getFoto(@PathVariable Long id) {
        log.debug("REST request to get Foto : {}", id);
        Foto foto = fotoRepository.findOne(id);
        return Optional.ofNullable(foto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fotos/:id : delete the "id" foto.
     *
     * @param id the id of the foto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFoto(@PathVariable Long id) {
        log.debug("REST request to delete Foto : {}", id);
        fotoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("foto", id.toString())).build();
    }

}
