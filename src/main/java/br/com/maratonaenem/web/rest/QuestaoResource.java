package br.com.maratonaenem.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.maratonaenem.service.QuestaoService;
import br.com.maratonaenem.web.rest.errors.BadRequestAlertException;
import br.com.maratonaenem.web.rest.util.HeaderUtil;
import br.com.maratonaenem.service.dto.QuestaoDTO;
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
 * REST controller for managing Questao.
 */
@RestController
@RequestMapping("/api")
public class QuestaoResource {

    private final Logger log = LoggerFactory.getLogger(QuestaoResource.class);

    private static final String ENTITY_NAME = "questao";

    private final QuestaoService questaoService;

    public QuestaoResource(QuestaoService questaoService) {
        this.questaoService = questaoService;
    }

    /**
     * POST  /questaos : Create a new questao.
     *
     * @param questaoDTO the questaoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questaoDTO, or with status 400 (Bad Request) if the questao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/questaos")
    @Timed
    public ResponseEntity<QuestaoDTO> createQuestao(@RequestBody QuestaoDTO questaoDTO) throws URISyntaxException {
        log.debug("REST request to save Questao : {}", questaoDTO);
        if (questaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new questao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestaoDTO result = questaoService.save(questaoDTO);
        return ResponseEntity.created(new URI("/api/questaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /questaos : Updates an existing questao.
     *
     * @param questaoDTO the questaoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questaoDTO,
     * or with status 400 (Bad Request) if the questaoDTO is not valid,
     * or with status 500 (Internal Server Error) if the questaoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/questaos")
    @Timed
    public ResponseEntity<QuestaoDTO> updateQuestao(@RequestBody QuestaoDTO questaoDTO) throws URISyntaxException {
        log.debug("REST request to update Questao : {}", questaoDTO);
        if (questaoDTO.getId() == null) {
            return createQuestao(questaoDTO);
        }
        QuestaoDTO result = questaoService.save(questaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, questaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /questaos : get all the questaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questaos in body
     */
    @GetMapping("/questaos")
    @Timed
    public List<QuestaoDTO> getAllQuestaos() {
        log.debug("REST request to get all Questaos");
        return questaoService.findAll();
        }

    /**
     * GET  /questaos/:id : get the "id" questao.
     *
     * @param id the id of the questaoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questaoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/questaos/{id}")
    @Timed
    public ResponseEntity<QuestaoDTO> getQuestao(@PathVariable String id) {
        log.debug("REST request to get Questao : {}", id);
        QuestaoDTO questaoDTO = questaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(questaoDTO));
    }

    /**
     * DELETE  /questaos/:id : delete the "id" questao.
     *
     * @param id the id of the questaoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/questaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestao(@PathVariable String id) {
        log.debug("REST request to delete Questao : {}", id);
        questaoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/questaos?query=:query : search for the questao corresponding
     * to the query.
     *
     * @param query the query of the questao search
     * @return the result of the search
     */
    @GetMapping("/_search/questaos")
    @Timed
    public List<QuestaoDTO> searchQuestaos(@RequestParam String query) {
        log.debug("REST request to search Questaos for query {}", query);
        return questaoService.search(query);
    }

}
