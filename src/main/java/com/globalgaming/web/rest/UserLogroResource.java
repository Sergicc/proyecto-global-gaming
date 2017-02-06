package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.UserLogro;

import com.globalgaming.repository.UserLogroRepository;
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
 * REST controller for managing UserLogro.
 */
@RestController
@RequestMapping("/api")
public class UserLogroResource {

    private final Logger log = LoggerFactory.getLogger(UserLogroResource.class);
        
    @Inject
    private UserLogroRepository userLogroRepository;

    /**
     * POST  /user-logroes : Create a new userLogro.
     *
     * @param userLogro the userLogro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userLogro, or with status 400 (Bad Request) if the userLogro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-logroes")
    @Timed
    public ResponseEntity<UserLogro> createUserLogro(@RequestBody UserLogro userLogro) throws URISyntaxException {
        log.debug("REST request to save UserLogro : {}", userLogro);
        if (userLogro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userLogro", "idexists", "A new userLogro cannot already have an ID")).body(null);
        }
        UserLogro result = userLogroRepository.save(userLogro);
        return ResponseEntity.created(new URI("/api/user-logroes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userLogro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-logroes : Updates an existing userLogro.
     *
     * @param userLogro the userLogro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userLogro,
     * or with status 400 (Bad Request) if the userLogro is not valid,
     * or with status 500 (Internal Server Error) if the userLogro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-logroes")
    @Timed
    public ResponseEntity<UserLogro> updateUserLogro(@RequestBody UserLogro userLogro) throws URISyntaxException {
        log.debug("REST request to update UserLogro : {}", userLogro);
        if (userLogro.getId() == null) {
            return createUserLogro(userLogro);
        }
        UserLogro result = userLogroRepository.save(userLogro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userLogro", userLogro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-logroes : get all the userLogroes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userLogroes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/user-logroes")
    @Timed
    public ResponseEntity<List<UserLogro>> getAllUserLogroes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserLogroes");
        Page<UserLogro> page = userLogroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-logroes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-logroes/:id : get the "id" userLogro.
     *
     * @param id the id of the userLogro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userLogro, or with status 404 (Not Found)
     */
    @GetMapping("/user-logroes/{id}")
    @Timed
    public ResponseEntity<UserLogro> getUserLogro(@PathVariable Long id) {
        log.debug("REST request to get UserLogro : {}", id);
        UserLogro userLogro = userLogroRepository.findOne(id);
        return Optional.ofNullable(userLogro)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /user-logroes/:id : delete the "id" userLogro.
     *
     * @param id the id of the userLogro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-logroes/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserLogro(@PathVariable Long id) {
        log.debug("REST request to delete UserLogro : {}", id);
        userLogroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userLogro", id.toString())).build();
    }

}
