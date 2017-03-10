package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.User;
import com.globalgaming.domain.ValoracionJuego;

import com.globalgaming.repository.UserRepository;
import com.globalgaming.repository.ValoracionJuegoRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValoracionJuego.
 */
@RestController
@RequestMapping("/api")
public class ValoracionJuegoResource {

    private final Logger log = LoggerFactory.getLogger(ValoracionJuegoResource.class);

    @Inject
    private ValoracionJuegoRepository valoracionJuegoRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /valoracion-juegos : Create a new valoracionJuego.
     *
     * @param valoracionJuego the valoracionJuego to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valoracionJuego, or with status 400 (Bad Request) if the valoracionJuego has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valoracion-juegos")
    @Timed
    public ResponseEntity<ValoracionJuego> createValoracionJuego(@Valid @RequestBody ValoracionJuego valoracionJuego) throws URISyntaxException {
        log.debug("REST request to save ValoracionJuego : {}", valoracionJuego);
        if (valoracionJuego.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("valoracionJuego", "idexists", "A new valoracionJuego cannot already have an ID")).body(null);
        }

        User user= userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        valoracionJuego.setTimeStamp(ZonedDateTime.now());
        valoracionJuego.setUser(user);

        ValoracionJuego result = valoracionJuegoRepository.save(valoracionJuego);
        return ResponseEntity.created(new URI("/api/valoracion-juegos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("valoracionJuego", result.getId().toString()))
            .body(result);
    }



    /**
     * PUT  /valoracion-juegos : Updates an existing valoracionJuego.
     *
     * @param valoracionJuego the valoracionJuego to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valoracionJuego,
     * or with status 400 (Bad Request) if the valoracionJuego is not valid,
     * or with status 500 (Internal Server Error) if the valoracionJuego couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valoracion-juegos")
    @Timed
    public ResponseEntity<ValoracionJuego> updateValoracionJuego(@Valid @RequestBody ValoracionJuego valoracionJuego) throws URISyntaxException {
        log.debug("REST request to update ValoracionJuego : {}", valoracionJuego);
        if (valoracionJuego.getId() == null) {
            return createValoracionJuego(valoracionJuego);
        }
        ValoracionJuego result = valoracionJuegoRepository.save(valoracionJuego);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("valoracionJuego", valoracionJuego.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valoracion-juegos : get all the valoracionJuegos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of valoracionJuegos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/valoracion-juegos")
    @Timed
    public ResponseEntity<List<ValoracionJuego>> getAllValoracionJuegos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ValoracionJuegos");
        Page<ValoracionJuego> page = valoracionJuegoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/valoracion-juegos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /valoracion-juegos/:id : get the "id" valoracionJuego.
     *
     * @param id the id of the valoracionJuego to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valoracionJuego, or with status 404 (Not Found)
     */
    @GetMapping("/valoracion-juegos/{id}")
    @Timed
    public ResponseEntity<ValoracionJuego> getValoracionJuego(@PathVariable Long id) {
        log.debug("REST request to get ValoracionJuego : {}", id);
        ValoracionJuego valoracionJuego = valoracionJuegoRepository.findOne(id);
        return Optional.ofNullable(valoracionJuego)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /valoracion-juegos/:id : delete the "id" valoracionJuego.
     *
     * @param id the id of the valoracionJuego to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valoracion-juegos/{id}")
    @Timed
    public ResponseEntity<Void> deleteValoracionJuego(@PathVariable Long id) {
        log.debug("REST request to delete ValoracionJuego : {}", id);
        valoracionJuegoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("valoracionJuego", id.toString())).build();
    }

}
