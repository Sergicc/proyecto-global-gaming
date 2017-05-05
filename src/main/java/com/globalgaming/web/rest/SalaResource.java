package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Sala;

import com.globalgaming.domain.User;
import com.globalgaming.repository.SalaCriteriaRepository;
import com.globalgaming.repository.SalaRepository;
import com.globalgaming.repository.UserRepository;
import com.globalgaming.security.AuthoritiesConstants;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing Sala.
 */
@RestController
@RequestMapping("/api")
public class SalaResource {

    private final Logger log = LoggerFactory.getLogger(SalaResource.class);

    @Inject
    private SalaRepository salaRepository;

    @Inject
    private SalaCriteriaRepository salaCriteriaRepository;

    @Inject
    private UserRepository userRepository;
    /**
     * POST  /salas : Create a new sala.
     *
     * @param sala the sala to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sala, or with status 400 (Bad Request) if the sala has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salas")
    @Timed
    public ResponseEntity<Sala> createSala(@RequestBody Sala sala) throws URISyntaxException {
        log.debug("REST request to save Sala : {}", sala);
        if (sala.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sala", "idexists", "A new sala cannot already have an ID")).body(null);
        }

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        sala.setUser(user);


        Sala result = salaRepository.save(sala);
        return ResponseEntity.created(new URI("/api/salas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sala", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salas : Updates an existing sala.
     *
     * @param sala the sala to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sala,
     * or with status 400 (Bad Request) if the sala is not valid,
     * or with status 500 (Internal Server Error) if the sala couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salas")
    @Timed
    public ResponseEntity<Sala> updateSala(@RequestBody Sala sala) throws URISyntaxException {
        log.debug("REST request to update Sala : {}", sala);
        if (sala.getId() == null) {
            return createSala(sala);
        }

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
         Sala saladb = salaRepository.findOne(sala.getId());
        if (!saladb.getUser().equals(user) && !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sala", "UserNotOwner", "El usuario no es el propietario de la sala")).body(null);
        }



        Sala result = salaRepository.save(sala);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sala", sala.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salas : get all the salas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salas in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/salas")
    @Timed
    public ResponseEntity<List<Sala>> getAllSalas(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Salas");
        Page<Sala> page = salaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /salas/:id : get the "id" sala.
     *
     * @param id the id of the sala to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sala, or with status 404 (Not Found)
     */
    @GetMapping("/salas/{id}")
    @Timed
    public ResponseEntity<Sala> getSala(@PathVariable Long id) {
        log.debug("REST request to get Sala : {}", id);
        Sala sala = salaRepository.findOne(id);
        return Optional.ofNullable(sala)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salas/:id : delete the "id" sala.
     *
     * @param id the id of the sala to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salas/{id}")
    @Timed
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        log.debug("REST request to delete Sala : {}", id);
        salaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sala", id.toString())).build();
    }

    @RequestMapping(value = "/sala/byfilters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<Sala>> getSalaByCriteria(
        @RequestParam(value = "nombre", required = false) String nombre,
        @RequestParam(value = "minLimiteUsuarios", required = false) String minLimiteUsuarios,
        @RequestParam(value = "maxLimiteUsuarios", required = false) String maxLimiteUsuarios,
        @RequestParam(value = "descripcion", required = false) String descripcion,
        @RequestParam(value = "juego", required = false) String juego
    ) {
        Map<String, Object> params = new HashMap<>();
        if (nombre != null) {
            params.put("nombre", nombre);
        }

        if (descripcion != null) {
            params.put("descripcion", descripcion);
        }
        if (juego != null) {
            params.put("juego", juego);
        }
        //no filtra por limite de usuarios
        if (minLimiteUsuarios != null) {
            try {
                Integer minLimiteUsuariosInt = Integer.parseInt(minLimiteUsuarios);
                params.put("minLimiteUsuarios", minLimiteUsuariosInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sala",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (maxLimiteUsuarios != null) {
            try {
                Integer maxLimiteUsuariosInt = Integer.parseInt(maxLimiteUsuarios);
                params.put("maxLimiteUsuarios", maxLimiteUsuariosInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sala",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        List<Sala> result = salaCriteriaRepository.filterSalaByCriteria(params);
        if (result.isEmpty()) {
            return new ResponseEntity<>(

                null, HeaderUtil.createAlert("No match for the criteria entered!", "sala"), HttpStatus.OK);
        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("X-Total-Count", String.valueOf(result.size()));
            return new ResponseEntity<>(
                result,
                httpHeaders,
                HttpStatus.OK
            );
        }
    }
}
