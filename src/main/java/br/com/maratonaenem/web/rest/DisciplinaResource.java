package br.com.maratonaenem.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.maratonaenem.service.DisciplinaService;
import br.com.maratonaenem.web.rest.errors.BadRequestAlertException;
import br.com.maratonaenem.web.rest.util.HeaderUtil;
import br.com.maratonaenem.service.dto.DisciplinaDTO;
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

//import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Disciplina.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    private final DisciplinaService disciplinaService;

    public DisciplinaResource(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    /**
     * POST  /disciplinas : Create a new disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disciplinaDTO, or with status 400 (Bad Request) if the disciplina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disciplinas")
    @Timed
    public ResponseEntity<DisciplinaDTO> createDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disciplinas : Updates an existing disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disciplinaDTO,
     * or with status 400 (Bad Request) if the disciplinaDTO is not valid,
     * or with status 500 (Internal Server Error) if the disciplinaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disciplinas")
    @Timed
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            return createDisciplina(disciplinaDTO);
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disciplinas : get all the disciplinas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of disciplinas in body
     */
    @GetMapping("/disciplinas")
    @Timed
    public List<DisciplinaDTO> getAllDisciplinas() {
        log.debug("REST request to get all Disciplinas");
        return disciplinaService.findAll();
        }

    /**
     * GET  /disciplinas/:id : get the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disciplinaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<DisciplinaDTO> getDisciplina(@PathVariable String id) {
        log.debug("REST request to get Disciplina : {}", id);
        DisciplinaDTO disciplinaDTO = disciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(disciplinaDTO));
    }

    /**
     * DELETE  /disciplinas/:id : delete the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disciplinas/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisciplina(@PathVariable String id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/disciplinas?query=:query : search for the disciplina corresponding
     * to the query.
     *
     * @param query the query of the disciplina search
     * @return the result of the search
     */
    @GetMapping("/_search/disciplinas")
    @Timed
    public List<DisciplinaDTO> searchDisciplinas(@RequestParam String query) {
        log.debug("REST request to search Disciplinas for query {}", query);
        return disciplinaService.search(query);
    }

}
