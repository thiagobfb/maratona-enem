package br.com.maratonaenem.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.maratonaenem.service.EscolaService;
import br.com.maratonaenem.web.rest.errors.BadRequestAlertException;
import br.com.maratonaenem.web.rest.util.HeaderUtil;
import br.com.maratonaenem.service.dto.EscolaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
//import java.util.stream.StreamSupport;
//
//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Escola.
 */
@RestController
@RequestMapping("/api")
public class EscolaResource {

    private final Logger log = LoggerFactory.getLogger(EscolaResource.class);

    private static final String ENTITY_NAME = "escola";

    private final EscolaService escolaService;

    public EscolaResource(EscolaService escolaService) {
        this.escolaService = escolaService;
    }

    /**
     * POST  /escolas : Create a new escola.
     *
     * @param escolaDTO the escolaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new escolaDTO, or with status 400 (Bad Request) if the escola has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/escolas")
    @Timed
    public ResponseEntity<EscolaDTO> createEscola(@RequestBody EscolaDTO escolaDTO) throws URISyntaxException {
        log.debug("REST request to save Escola : {}", escolaDTO);
        if (escolaDTO.getId() != null) {
            throw new BadRequestAlertException("A new escola cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EscolaDTO result = escolaService.save(escolaDTO);
        return ResponseEntity.created(new URI("/api/escolas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /escolas : Updates an existing escola.
     *
     * @param escolaDTO the escolaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated escolaDTO,
     * or with status 400 (Bad Request) if the escolaDTO is not valid,
     * or with status 500 (Internal Server Error) if the escolaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/escolas")
    @Timed
    public ResponseEntity<EscolaDTO> updateEscola(@RequestBody EscolaDTO escolaDTO) throws URISyntaxException {
        log.debug("REST request to update Escola : {}", escolaDTO);
        if (escolaDTO.getId() == null) {
            return createEscola(escolaDTO);
        }
        EscolaDTO result = escolaService.save(escolaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, escolaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /escolas : get all the escolas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of escolas in body
     */
    @GetMapping("/escolas")
    @Timed
    public List<EscolaDTO> getAllEscolas() {
        log.debug("REST request to get all Escolas");
        return escolaService.findAll();
        }

    /**
     * GET  /escolas/:id : get the "id" escola.
     *
     * @param id the id of the escolaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the escolaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/escolas/{id}")
    @Timed
    public ResponseEntity<EscolaDTO> getEscola(@PathVariable String id) {
        log.debug("REST request to get Escola : {}", id);
        EscolaDTO escolaDTO = escolaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(escolaDTO));
    }

    /**
     * DELETE  /escolas/:id : delete the "id" escola.
     *
     * @param id the id of the escolaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/escolas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEscola(@PathVariable String id) {
        log.debug("REST request to delete Escola : {}", id);
        escolaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/escolas?query=:query : search for the escola corresponding
     * to the query.
     *
     * @param query the query of the escola search
     * @return the result of the search
     */
    @GetMapping("/_search/escolas")
    @Timed
    public List<EscolaDTO> searchEscolas(@RequestParam String query) {
        log.debug("REST request to search Escolas for query {}", query);
        return escolaService.search(query);
    }

}
