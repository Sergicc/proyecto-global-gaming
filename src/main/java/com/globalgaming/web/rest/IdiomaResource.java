package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Idioma;

import com.globalgaming.repository.IdiomaRepository;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Idioma.
 */
@RestController
@RequestMapping("/api")
public class IdiomaResource {

    private final Logger log = LoggerFactory.getLogger(IdiomaResource.class);
        
    @Inject
    private IdiomaRepository idiomaRepository;

    /**
     * POST  /idiomas : Create a new idioma.
     *
     * @param idioma the idioma to create
     * @return the ResponseEntity with status 201 (Created) and with body the new idioma, or with status 400 (Bad Request) if the idioma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/idiomas")
    @Timed
    public ResponseEntity<Idioma> createIdioma(@RequestBody Idioma idioma) throws URISyntaxException {
        log.debug("REST request to save Idioma : {}", idioma);
        if (idioma.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("idioma", "idexists", "A new idioma cannot already have an ID")).body(null);
        }
        Idioma result = idiomaRepository.save(idioma);
        return ResponseEntity.created(new URI("/api/idiomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("idioma", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /idiomas : Updates an existing idioma.
     *
     * @param idioma the idioma to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated idioma,
     * or with status 400 (Bad Request) if the idioma is not valid,
     * or with status 500 (Internal Server Error) if the idioma couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/idiomas")
    @Timed
    public ResponseEntity<Idioma> updateIdioma(@RequestBody Idioma idioma) throws URISyntaxException {
        log.debug("REST request to update Idioma : {}", idioma);
        if (idioma.getId() == null) {
            return createIdioma(idioma);
        }
        Idioma result = idiomaRepository.save(idioma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("idioma", idioma.getId().toString()))
            .body(result);
    }

    /**
     * GET  /idiomas : get all the idiomas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of idiomas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/idiomas")
    @Timed
    public ResponseEntity<List<Idioma>> getAllIdiomas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Idiomas");
        Page<Idioma> page = idiomaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/idiomas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /idiomas/:id : get the "id" idioma.
     *
     * @param id the id of the idioma to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the idioma, or with status 404 (Not Found)
     */
    @GetMapping("/idiomas/{id}")
    @Timed
    public ResponseEntity<Idioma> getIdioma(@PathVariable Long id) {
        log.debug("REST request to get Idioma : {}", id);
        Idioma idioma = idiomaRepository.findOne(id);
        return Optional.ofNullable(idioma)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /idiomas/:id : delete the "id" idioma.
     *
     * @param id the id of the idioma to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/idiomas/{id}")
    @Timed
    public ResponseEntity<Void> deleteIdioma(@PathVariable Long id) {
        log.debug("REST request to delete Idioma : {}", id);
        idiomaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("idioma", id.toString())).build();
    }

}
