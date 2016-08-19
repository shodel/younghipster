package com.hodelsi.younghipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hodelsi.younghipster.domain.Aufwand;
import com.hodelsi.younghipster.repository.AufwandRepository;
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
 * REST controller for managing Aufwand.
 */
@RestController
@RequestMapping("/api")
public class AufwandResource {

    private final Logger log = LoggerFactory.getLogger(AufwandResource.class);
        
    @Inject
    private AufwandRepository aufwandRepository;
    
    /**
     * POST  /aufwands : Create a new aufwand.
     *
     * @param aufwand the aufwand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new aufwand, or with status 400 (Bad Request) if the aufwand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/aufwands",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aufwand> createAufwand(@RequestBody Aufwand aufwand) throws URISyntaxException {
        log.debug("REST request to save Aufwand : {}", aufwand);
        if (aufwand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("aufwand", "idexists", "A new aufwand cannot already have an ID")).body(null);
        }
        Aufwand result = aufwandRepository.save(aufwand);
        return ResponseEntity.created(new URI("/api/aufwands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aufwand", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aufwands : Updates an existing aufwand.
     *
     * @param aufwand the aufwand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated aufwand,
     * or with status 400 (Bad Request) if the aufwand is not valid,
     * or with status 500 (Internal Server Error) if the aufwand couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/aufwands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aufwand> updateAufwand(@RequestBody Aufwand aufwand) throws URISyntaxException {
        log.debug("REST request to update Aufwand : {}", aufwand);
        if (aufwand.getId() == null) {
            return createAufwand(aufwand);
        }
        Aufwand result = aufwandRepository.save(aufwand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aufwand", aufwand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aufwands : get all the aufwands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of aufwands in body
     */
    @RequestMapping(value = "/aufwands",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Aufwand> getAllAufwands() {
        log.debug("REST request to get all Aufwands");
        List<Aufwand> aufwands = aufwandRepository.findAll();
        return aufwands;
    }

    /**
     * GET  /aufwands/:id : get the "id" aufwand.
     *
     * @param id the id of the aufwand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the aufwand, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/aufwands/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Aufwand> getAufwand(@PathVariable Long id) {
        log.debug("REST request to get Aufwand : {}", id);
        Aufwand aufwand = aufwandRepository.findOne(id);
        return Optional.ofNullable(aufwand)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aufwands/:id : delete the "id" aufwand.
     *
     * @param id the id of the aufwand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/aufwands/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAufwand(@PathVariable Long id) {
        log.debug("REST request to delete Aufwand : {}", id);
        aufwandRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aufwand", id.toString())).build();
    }

}
