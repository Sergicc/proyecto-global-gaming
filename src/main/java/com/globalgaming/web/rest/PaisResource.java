package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Pais;

import com.globalgaming.repository.PaisRepository;
import com.globalgaming.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pais.
 */
@RestController
@RequestMapping("/api")
public class PaisResource {

    private final Logger log = LoggerFactory.getLogger(PaisResource.class);
        
    @Inject
    private PaisRepository paisRepository;

    /**
     * POST  /pais : Create a new pais.
     *
     * @param pais the pais to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pais, or with status 400 (Bad Request) if the pais has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pais")
    @Timed
    public ResponseEntity<Pais> createPais(@Valid @RequestBody Pais pais) throws URISyntaxException {
        log.debug("REST request to save Pais : {}", pais);
        if (pais.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pais", "idexists", "A new pais cannot already have an ID")).body(null);
        }
        Pais result = paisRepository.save(pais);
        return ResponseEntity.created(new URI("/api/pais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pais", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pais : Updates an existing pais.
     *
     * @param pais the pais to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pais,
     * or with status 400 (Bad Request) if the pais is not valid,
     * or with status 500 (Internal Server Error) if the pais couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pais")
    @Timed
    public ResponseEntity<Pais> updatePais(@Valid @RequestBody Pais pais) throws URISyntaxException {
        log.debug("REST request to update Pais : {}", pais);
        if (pais.getId() == null) {
            return createPais(pais);
        }
        Pais result = paisRepository.save(pais);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pais", pais.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pais : get all the pais.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pais in body
     */
    @GetMapping("/pais")
    @Timed
    public List<Pais> getAllPais() {
        log.debug("REST request to get all Pais");
        List<Pais> pais = paisRepository.findAll();
        return pais;
    }

    /**
     * GET  /pais/:id : get the "id" pais.
     *
     * @param id the id of the pais to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pais, or with status 404 (Not Found)
     */
    @GetMapping("/pais/{id}")
    @Timed
    public ResponseEntity<Pais> getPais(@PathVariable Long id) {
        log.debug("REST request to get Pais : {}", id);
        Pais pais = paisRepository.findOne(id);
        return Optional.ofNullable(pais)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pais/:id : delete the "id" pais.
     *
     * @param id the id of the pais to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pais/{id}")
    @Timed
    public ResponseEntity<Void> deletePais(@PathVariable Long id) {
        log.debug("REST request to delete Pais : {}", id);
        paisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pais", id.toString())).build();
    }

}
