package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Etiqueta;

import com.globalgaming.repository.EtiquetaRepository;
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
 * REST controller for managing Etiqueta.
 */
@RestController
@RequestMapping("/api")
public class EtiquetaResource {

    private final Logger log = LoggerFactory.getLogger(EtiquetaResource.class);
        
    @Inject
    private EtiquetaRepository etiquetaRepository;

    /**
     * POST  /etiquetas : Create a new etiqueta.
     *
     * @param etiqueta the etiqueta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etiqueta, or with status 400 (Bad Request) if the etiqueta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etiquetas")
    @Timed
    public ResponseEntity<Etiqueta> createEtiqueta(@RequestBody Etiqueta etiqueta) throws URISyntaxException {
        log.debug("REST request to save Etiqueta : {}", etiqueta);
        if (etiqueta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("etiqueta", "idexists", "A new etiqueta cannot already have an ID")).body(null);
        }
        Etiqueta result = etiquetaRepository.save(etiqueta);
        return ResponseEntity.created(new URI("/api/etiquetas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("etiqueta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etiquetas : Updates an existing etiqueta.
     *
     * @param etiqueta the etiqueta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etiqueta,
     * or with status 400 (Bad Request) if the etiqueta is not valid,
     * or with status 500 (Internal Server Error) if the etiqueta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etiquetas")
    @Timed
    public ResponseEntity<Etiqueta> updateEtiqueta(@RequestBody Etiqueta etiqueta) throws URISyntaxException {
        log.debug("REST request to update Etiqueta : {}", etiqueta);
        if (etiqueta.getId() == null) {
            return createEtiqueta(etiqueta);
        }
        Etiqueta result = etiquetaRepository.save(etiqueta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("etiqueta", etiqueta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etiquetas : get all the etiquetas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of etiquetas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/etiquetas")
    @Timed
    public ResponseEntity<List<Etiqueta>> getAllEtiquetas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Etiquetas");
        Page<Etiqueta> page = etiquetaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/etiquetas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /etiquetas/:id : get the "id" etiqueta.
     *
     * @param id the id of the etiqueta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etiqueta, or with status 404 (Not Found)
     */
    @GetMapping("/etiquetas/{id}")
    @Timed
    public ResponseEntity<Etiqueta> getEtiqueta(@PathVariable Long id) {
        log.debug("REST request to get Etiqueta : {}", id);
        Etiqueta etiqueta = etiquetaRepository.findOne(id);
        return Optional.ofNullable(etiqueta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /etiquetas/:id : delete the "id" etiqueta.
     *
     * @param id the id of the etiqueta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etiquetas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtiqueta(@PathVariable Long id) {
        log.debug("REST request to delete Etiqueta : {}", id);
        etiquetaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("etiqueta", id.toString())).build();
    }

}
