package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Articulo;

import com.globalgaming.repository.ArticuloRepository;
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
 * REST controller for managing Articulo.
 */
@RestController
@RequestMapping("/api")
public class ArticuloResource {

    private final Logger log = LoggerFactory.getLogger(ArticuloResource.class);
        
    @Inject
    private ArticuloRepository articuloRepository;

    /**
     * POST  /articulos : Create a new articulo.
     *
     * @param articulo the articulo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articulo, or with status 400 (Bad Request) if the articulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articulos")
    @Timed
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to save Articulo : {}", articulo);
        if (articulo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("articulo", "idexists", "A new articulo cannot already have an ID")).body(null);
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.created(new URI("/api/articulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("articulo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articulos : Updates an existing articulo.
     *
     * @param articulo the articulo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articulo,
     * or with status 400 (Bad Request) if the articulo is not valid,
     * or with status 500 (Internal Server Error) if the articulo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articulos")
    @Timed
    public ResponseEntity<Articulo> updateArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to update Articulo : {}", articulo);
        if (articulo.getId() == null) {
            return createArticulo(articulo);
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("articulo", articulo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articulos : get all the articulos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articulos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/articulos")
    @Timed
    public ResponseEntity<List<Articulo>> getAllArticulos(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Articulos");
        Page<Articulo> page = articuloRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articulos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /articulos/:id : get the "id" articulo.
     *
     * @param id the id of the articulo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articulo, or with status 404 (Not Found)
     */
    @GetMapping("/articulos/{id}")
    @Timed
    public ResponseEntity<Articulo> getArticulo(@PathVariable Long id) {
        log.debug("REST request to get Articulo : {}", id);
        Articulo articulo = articuloRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(articulo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /articulos/:id : delete the "id" articulo.
     *
     * @param id the id of the articulo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articulos/{id}")
    @Timed
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        log.debug("REST request to delete Articulo : {}", id);
        articuloRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("articulo", id.toString())).build();
    }

}
