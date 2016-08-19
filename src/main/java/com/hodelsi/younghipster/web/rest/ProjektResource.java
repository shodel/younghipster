package com.hodelsi.younghipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hodelsi.younghipster.domain.Projekt;
import com.hodelsi.younghipster.repository.ProjektRepository;
import com.hodelsi.younghipster.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Projekt.
 */
@RestController
@RequestMapping("/api")
public class ProjektResource {

    private final Logger log = LoggerFactory.getLogger(ProjektResource.class);
        
    @Inject
    private ProjektRepository projektRepository;
    
    /**
     * POST  /projekts : Create a new projekt.
     *
     * @param projekt the projekt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projekt, or with status 400 (Bad Request) if the projekt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projekts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projekt> createProjekt(@RequestBody Projekt projekt) throws URISyntaxException {
        log.debug("REST request to save Projekt : {}", projekt);
        if (projekt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("projekt", "idexists", "A new projekt cannot already have an ID")).body(null);
        }
        Projekt result = projektRepository.save(projekt);
        return ResponseEntity.created(new URI("/api/projekts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("projekt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projekts : Updates an existing projekt.
     *
     * @param projekt the projekt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projekt,
     * or with status 400 (Bad Request) if the projekt is not valid,
     * or with status 500 (Internal Server Error) if the projekt couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projekts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projekt> updateProjekt(@RequestBody Projekt projekt) throws URISyntaxException {
        log.debug("REST request to update Projekt : {}", projekt);
        if (projekt.getId() == null) {
            return createProjekt(projekt);
        }
        Projekt result = projektRepository.save(projekt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("projekt", projekt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projekts : get all the projekts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of projekts in body
     */
    @RequestMapping(value = "/projekts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Projekt> getAllProjekts() {
        log.debug("REST request to get all Projekts");
        List<Projekt> projekts = projektRepository.findAll();
        return projekts;
    }

    /**
     * GET  /projekts/:id : get the "id" projekt.
     *
     * @param id the id of the projekt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projekt, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/projekts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Projekt> getProjekt(@PathVariable Long id) {
        log.debug("REST request to get Projekt : {}", id);
        Projekt projekt = projektRepository.findOne(id);
        return Optional.ofNullable(projekt)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projekts/:id : delete the "id" projekt.
     *
     * @param id the id of the projekt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/projekts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProjekt(@PathVariable Long id) {
        log.debug("REST request to delete Projekt : {}", id);
        projektRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("projekt", id.toString())).build();
    }

}
