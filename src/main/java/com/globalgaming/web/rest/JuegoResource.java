package com.globalgaming.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.globalgaming.domain.Juego;

import com.globalgaming.domain.User;
import com.globalgaming.domain.ValoracionJuego;
import com.globalgaming.repository.JuegoCriteriaRepository;
import com.globalgaming.repository.JuegoRepository;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Inject
    private ValoracionJuegoRepository valoracionJuegoRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private JuegoCriteriaRepository juegoCriteriaRepository;

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

    @RequestMapping(value = "/juego/byfilters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<List<Juego>> getJuegoByCriteria(
        @RequestParam(value = "titulo", required = false) String titulo,
        @RequestParam(value = "descripcion", required = false) String descripcion,
        @RequestParam(value = "desarrollador", required = false) String desarrollador,
        @RequestParam(value = "genero", required = false) String genero,
        @RequestParam(value = "edadRecomendada", required = false) String edadRecomendada,
        @RequestParam(value = "minCapacidadJugadores", required = false) String minCapacidadJugadores,
        @RequestParam(value = "maxCapacidadJugadores", required = false) String maxCapacidadJugadores,
        @RequestParam(value = "minValoracionWeb", required = false) String minValoracionWeb,
        @RequestParam(value = "maxValoracionWeb", required = false) String maxValoracionWeb,
        @RequestParam(value = "minValoracionUsers", required = false) String minValoracionUsers,
        @RequestParam(value = "maxValoracionUsers", required = false) String maxValoracionUsers,
        @RequestParam(value = "idioma", required = false) String idioma
        ) {
        Map<String, Object> params = new HashMap<>();

        if (titulo != null) {
            params.put("titulo", titulo);
        }
        if (descripcion != null) {
            params.put("descripcion", descripcion);
        }
        if (desarrollador != null) {
            params.put("desarrollador", desarrollador);
        }
        if (genero != null) {
            params.put("genero", genero);
        }
        //filtro por edad recomendada ya funciona
        if (edadRecomendada != null) {
            params.put("edadRecomendada", edadRecomendada);
        }
        if (maxCapacidadJugadores != null) {
            try {
                Integer maxCapacidadJugadoresInt = Integer.parseInt(maxCapacidadJugadores);
                params.put("maxCapacidadJugadores", maxCapacidadJugadoresInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (minCapacidadJugadores != null) {
            try {
                Integer minCapacidadJugadoresInt = Integer.parseInt(minCapacidadJugadores);
                params.put("minCapacidadJugadores", minCapacidadJugadoresInt);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (minValoracionWeb != null) {
            try {
                Integer minValoracionWebDouble = Integer.parseInt(minValoracionWeb);
                params.put("minValoracionWeb", minValoracionWebDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (maxValoracionWeb != null) {
            try {
                Integer maxValoracionWebDouble = Integer.parseInt(maxValoracionWeb);
                params.put("maxValoracionWeb", maxValoracionWebDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (minValoracionUsers != null) {
            try {
                Integer minValoracionUsersDouble = Integer.parseInt(minValoracionUsers);
                params.put("minValoracionUsers", minValoracionUsersDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (maxValoracionUsers != null) {
            try {
                Integer maxValoracionUsersDouble = Integer.parseInt(maxValoracionUsers);
                params.put("maxValoracionUsers", maxValoracionUsersDouble);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("juego",
                    "number format exception on param",
                    "A numeric param cannot have non numeric characters")).body(null);
            }
        }
        if (idioma != null) {
            params.put("idioma", idioma);
        }
        List<Juego> result = juegoCriteriaRepository.filterJuegoByCriteria(params);
        if (result.isEmpty()) {
            return new ResponseEntity<>(

                null, HeaderUtil.createAlert("No match for the criteria entered!", "juego"), HttpStatus.OK);
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

        @PostMapping("/juegos/{idJuego}/valoracion/{valoracion}")
    @Timed
    public ResponseEntity<ValoracionJuego> valorarJuego(@PathVariable Long idJuego,
                                                        @PathVariable Double valoracion)
        throws URISyntaxException {

        log.debug("REST request to rate a Juego : {}", idJuego);

        Juego juego = juegoRepository.findOne(idJuego);

        User user= userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();

        ValoracionJuego valoracionJuego = new ValoracionJuego();

        valoracionJuego.setValoracion(valoracion);
        valoracionJuego.setJuego(juego);
        valoracionJuego.setTimeStamp(ZonedDateTime.now());
        valoracionJuego.setUser(user);

        ValoracionJuego result = valoracionJuegoRepository.save(valoracionJuego);


        return ResponseEntity.created(new URI("/api/valoracion-juegos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("valoracionJuego", result.getId().toString()))
            .body(result);
    }
}


