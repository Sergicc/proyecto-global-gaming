package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.RelacionUsuarios;

import com.globalgaming.repository.RelacionUsuariosRepository;
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
 * REST controller for managing RelacionUsuarios.
 */
@RestController
@RequestMapping("/api")
public class RelacionUsuariosResource {

    private final Logger log = LoggerFactory.getLogger(RelacionUsuariosResource.class);
        
    @Inject
    private RelacionUsuariosRepository relacionUsuariosRepository;

    /**
     * POST  /relacion-usuarios : Create a new relacionUsuarios.
     *
     * @param relacionUsuarios the relacionUsuarios to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relacionUsuarios, or with status 400 (Bad Request) if the relacionUsuarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/relacion-usuarios")
    @Timed
    public ResponseEntity<RelacionUsuarios> createRelacionUsuarios(@RequestBody RelacionUsuarios relacionUsuarios) throws URISyntaxException {
        log.debug("REST request to save RelacionUsuarios : {}", relacionUsuarios);
        if (relacionUsuarios.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("relacionUsuarios", "idexists", "A new relacionUsuarios cannot already have an ID")).body(null);
        }
        RelacionUsuarios result = relacionUsuariosRepository.save(relacionUsuarios);
        return ResponseEntity.created(new URI("/api/relacion-usuarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("relacionUsuarios", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /relacion-usuarios : Updates an existing relacionUsuarios.
     *
     * @param relacionUsuarios the relacionUsuarios to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated relacionUsuarios,
     * or with status 400 (Bad Request) if the relacionUsuarios is not valid,
     * or with status 500 (Internal Server Error) if the relacionUsuarios couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/relacion-usuarios")
    @Timed
    public ResponseEntity<RelacionUsuarios> updateRelacionUsuarios(@RequestBody RelacionUsuarios relacionUsuarios) throws URISyntaxException {
        log.debug("REST request to update RelacionUsuarios : {}", relacionUsuarios);
        if (relacionUsuarios.getId() == null) {
            return createRelacionUsuarios(relacionUsuarios);
        }
        RelacionUsuarios result = relacionUsuariosRepository.save(relacionUsuarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("relacionUsuarios", relacionUsuarios.getId().toString()))
            .body(result);
    }

    /**
     * GET  /relacion-usuarios : get all the relacionUsuarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of relacionUsuarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/relacion-usuarios")
    @Timed
    public ResponseEntity<List<RelacionUsuarios>> getAllRelacionUsuarios(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of RelacionUsuarios");
        Page<RelacionUsuarios> page = relacionUsuariosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/relacion-usuarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /relacion-usuarios/:id : get the "id" relacionUsuarios.
     *
     * @param id the id of the relacionUsuarios to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the relacionUsuarios, or with status 404 (Not Found)
     */
    @GetMapping("/relacion-usuarios/{id}")
    @Timed
    public ResponseEntity<RelacionUsuarios> getRelacionUsuarios(@PathVariable Long id) {
        log.debug("REST request to get RelacionUsuarios : {}", id);
        RelacionUsuarios relacionUsuarios = relacionUsuariosRepository.findOne(id);
        return Optional.ofNullable(relacionUsuarios)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /relacion-usuarios/:id : delete the "id" relacionUsuarios.
     *
     * @param id the id of the relacionUsuarios to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/relacion-usuarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteRelacionUsuarios(@PathVariable Long id) {
        log.debug("REST request to delete RelacionUsuarios : {}", id);
        relacionUsuariosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("relacionUsuarios", id.toString())).build();
    }

}
