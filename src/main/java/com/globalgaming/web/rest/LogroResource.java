package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Logro;

import com.globalgaming.repository.LogroRepository;
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
 * REST controller for managing Logro.
 */
@RestController
@RequestMapping("/api")
public class LogroResource {

    private final Logger log = LoggerFactory.getLogger(LogroResource.class);
        
    @Inject
    private LogroRepository logroRepository;

    /**
     * POST  /logroes : Create a new logro.
     *
     * @param logro the logro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logro, or with status 400 (Bad Request) if the logro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logroes")
    @Timed
    public ResponseEntity<Logro> createLogro(@RequestBody Logro logro) throws URISyntaxException {
        log.debug("REST request to save Logro : {}", logro);
        if (logro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("logro", "idexists", "A new logro cannot already have an ID")).body(null);
        }
        Logro result = logroRepository.save(logro);
        return ResponseEntity.created(new URI("/api/logroes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("logro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logroes : Updates an existing logro.
     *
     * @param logro the logro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logro,
     * or with status 400 (Bad Request) if the logro is not valid,
     * or with status 500 (Internal Server Error) if the logro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logroes")
    @Timed
    public ResponseEntity<Logro> updateLogro(@RequestBody Logro logro) throws URISyntaxException {
        log.debug("REST request to update Logro : {}", logro);
        if (logro.getId() == null) {
            return createLogro(logro);
        }
        Logro result = logroRepository.save(logro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("logro", logro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logroes : get all the logroes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logroes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/logroes")
    @Timed
    public ResponseEntity<List<Logro>> getAllLogroes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Logroes");
        Page<Logro> page = logroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logroes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /logroes/:id : get the "id" logro.
     *
     * @param id the id of the logro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logro, or with status 404 (Not Found)
     */
    @GetMapping("/logroes/{id}")
    @Timed
    public ResponseEntity<Logro> getLogro(@PathVariable Long id) {
        log.debug("REST request to get Logro : {}", id);
        Logro logro = logroRepository.findOne(id);
        return Optional.ofNullable(logro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /logroes/:id : delete the "id" logro.
     *
     * @param id the id of the logro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logroes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogro(@PathVariable Long id) {
        log.debug("REST request to delete Logro : {}", id);
        logroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("logro", id.toString())).build();
    }

}
