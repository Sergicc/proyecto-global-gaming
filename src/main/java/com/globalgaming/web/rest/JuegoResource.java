package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Juego;

import com.globalgaming.repository.JuegoRepository;
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
 * REST controller for managing Juego.
 */
@RestController
@RequestMapping("/api")
public class JuegoResource {

    private final Logger log = LoggerFactory.getLogger(JuegoResource.class);
        
    @Inject
    private JuegoRepository juegoRepository;

    /**
     * POST  /juegos : Create a new juego.
     *
     * @param juego the juego to create
     * @return the ResponseEntity with status 201 (Created) and with body the new juego, or with status 400 (Bad Request) if the juego has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/juegos")
    @Timed
    public ResponseEntity<Juego> createJuego(@RequestBody Juego juego) throws URISyntaxException {
        log.debug("REST request to save Juego : {}", juego);
        if (juego.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego", "idexists", "A new juego cannot already have an ID")).body(null);
        }
        Juego result = juegoRepository.save(juego);
        return ResponseEntity.created(new URI("/api/juegos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("juego", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /juegos : Updates an existing juego.
     *
     * @param juego the juego to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated juego,
     * or with status 400 (Bad Request) if the juego is not valid,
     * or with status 500 (Internal Server Error) if the juego couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/juegos")
    @Timed
    public ResponseEntity<Juego> updateJuego(@RequestBody Juego juego) throws URISyntaxException {
        log.debug("REST request to update Juego : {}", juego);
        if (juego.getId() == null) {
            return createJuego(juego);
        }
        Juego result = juegoRepository.save(juego);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("juego", juego.getId().toString()))
            .body(result);
    }

    /**
     * GET  /juegos : get all the juegos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of juegos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/juegos")
    @Timed
    public ResponseEntity<List<Juego>> getAllJuegos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Juegos");
        Page<Juego> page = juegoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/juegos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /juegos/:id : get the "id" juego.
     *
     * @param id the id of the juego to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the juego, or with status 404 (Not Found)
     */
    @GetMapping("/juegos/{id}")
    @Timed
    public ResponseEntity<Juego> getJuego(@PathVariable Long id) {
        log.debug("REST request to get Juego : {}", id);
        Juego juego = juegoRepository.findOne(id);
        return Optional.ofNullable(juego)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /juegos/:id : delete the "id" juego.
     *
     * @param id the id of the juego to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/juegos/{id}")
    @Timed
    public ResponseEntity<Void> deleteJuego(@PathVariable Long id) {
        log.debug("REST request to delete Juego : {}", id);
        juegoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("juego", id.toString())).build();
    }

}
