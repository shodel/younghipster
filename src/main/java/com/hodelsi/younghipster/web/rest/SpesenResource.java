package com.hodelsi.younghipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.hodelsi.younghipster.domain.Spesen;
import com.hodelsi.younghipster.repository.SpesenRepository;
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
 * REST controller for managing Spesen.
 */
@RestController
@RequestMapping("/api")
public class SpesenResource {

    private final Logger log = LoggerFactory.getLogger(SpesenResource.class);
        
    @Inject
    private SpesenRepository spesenRepository;
    
    /**
     * POST  /spesens : Create a new spesen.
     *
     * @param spesen the spesen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new spesen, or with status 400 (Bad Request) if the spesen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/spesens",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spesen> createSpesen(@RequestBody Spesen spesen) throws URISyntaxException {
        log.debug("REST request to save Spesen : {}", spesen);
        if (spesen.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("spesen", "idexists", "A new spesen cannot already have an ID")).body(null);
        }
        Spesen result = spesenRepository.save(spesen);
        return ResponseEntity.created(new URI("/api/spesens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("spesen", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /spesens : Updates an existing spesen.
     *
     * @param spesen the spesen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated spesen,
     * or with status 400 (Bad Request) if the spesen is not valid,
     * or with status 500 (Internal Server Error) if the spesen couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/spesens",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spesen> updateSpesen(@RequestBody Spesen spesen) throws URISyntaxException {
        log.debug("REST request to update Spesen : {}", spesen);
        if (spesen.getId() == null) {
            return createSpesen(spesen);
        }
        Spesen result = spesenRepository.save(spesen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("spesen", spesen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /spesens : get all the spesens.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of spesens in body
     */
    @RequestMapping(value = "/spesens",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Spesen> getAllSpesens() {
        log.debug("REST request to get all Spesens");
        List<Spesen> spesens = spesenRepository.findAll();
        return spesens;
    }

    /**
     * GET  /spesens/:id : get the "id" spesen.
     *
     * @param id the id of the spesen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the spesen, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/spesens/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Spesen> getSpesen(@PathVariable Long id) {
        log.debug("REST request to get Spesen : {}", id);
        Spesen spesen = spesenRepository.findOne(id);
        return Optional.ofNullable(spesen)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /spesens/:id : delete the "id" spesen.
     *
     * @param id the id of the spesen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/spesens/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSpesen(@PathVariable Long id) {
        log.debug("REST request to delete Spesen : {}", id);
        spesenRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("spesen", id.toString())).build();
    }

}
