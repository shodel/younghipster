package com.hodelsi.younghipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hodelsi.younghipster.domain.Mitarbeiter;
import com.hodelsi.younghipster.repository.MitarbeiterRepository;
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
 * REST controller for managing Mitarbeiter.
 */
@RestController
@RequestMapping("/api")
public class MitarbeiterResource {

    private final Logger log = LoggerFactory.getLogger(MitarbeiterResource.class);
        
    @Inject
    private MitarbeiterRepository mitarbeiterRepository;
    
    /**
     * POST  /mitarbeiters : Create a new mitarbeiter.
     *
     * @param mitarbeiter the mitarbeiter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mitarbeiter, or with status 400 (Bad Request) if the mitarbeiter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mitarbeiters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mitarbeiter> createMitarbeiter(@RequestBody Mitarbeiter mitarbeiter) throws URISyntaxException {
        log.debug("REST request to save Mitarbeiter : {}", mitarbeiter);
        if (mitarbeiter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mitarbeiter", "idexists", "A new mitarbeiter cannot already have an ID")).body(null);
        }
        Mitarbeiter result = mitarbeiterRepository.save(mitarbeiter);
        return ResponseEntity.created(new URI("/api/mitarbeiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mitarbeiter", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mitarbeiters : Updates an existing mitarbeiter.
     *
     * @param mitarbeiter the mitarbeiter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mitarbeiter,
     * or with status 400 (Bad Request) if the mitarbeiter is not valid,
     * or with status 500 (Internal Server Error) if the mitarbeiter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mitarbeiters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mitarbeiter> updateMitarbeiter(@RequestBody Mitarbeiter mitarbeiter) throws URISyntaxException {
        log.debug("REST request to update Mitarbeiter : {}", mitarbeiter);
        if (mitarbeiter.getId() == null) {
            return createMitarbeiter(mitarbeiter);
        }
        Mitarbeiter result = mitarbeiterRepository.save(mitarbeiter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mitarbeiter", mitarbeiter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mitarbeiters : get all the mitarbeiters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mitarbeiters in body
     */
    @RequestMapping(value = "/mitarbeiters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Mitarbeiter> getAllMitarbeiters() {
        log.debug("REST request to get all Mitarbeiters");
        List<Mitarbeiter> mitarbeiters = mitarbeiterRepository.findAll();
        return mitarbeiters;
    }

    /**
     * GET  /mitarbeiters/:id : get the "id" mitarbeiter.
     *
     * @param id the id of the mitarbeiter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mitarbeiter, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/mitarbeiters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mitarbeiter> getMitarbeiter(@PathVariable Long id) {
        log.debug("REST request to get Mitarbeiter : {}", id);
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findOne(id);
        return Optional.ofNullable(mitarbeiter)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mitarbeiters/:id : delete the "id" mitarbeiter.
     *
     * @param id the id of the mitarbeiter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/mitarbeiters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMitarbeiter(@PathVariable Long id) {
        log.debug("REST request to delete Mitarbeiter : {}", id);
        mitarbeiterRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mitarbeiter", id.toString())).build();
    }

}
