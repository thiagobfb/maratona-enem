package br.com.maratonaenem.service.impl;

import br.com.maratonaenem.service.QuestaoService;
import br.com.maratonaenem.domain.Questao;
import br.com.maratonaenem.repository.QuestaoRepository;
import br.com.maratonaenem.repository.search.QuestaoSearchRepository;
import br.com.maratonaenem.service.dto.QuestaoDTO;
import br.com.maratonaenem.service.mapper.QuestaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Questao.
 */
@Service
public class QuestaoServiceImpl implements QuestaoService {

    private final Logger log = LoggerFactory.getLogger(QuestaoServiceImpl.class);

    private final QuestaoRepository questaoRepository;

    private final QuestaoMapper questaoMapper;

    private final QuestaoSearchRepository questaoSearchRepository;

    public QuestaoServiceImpl(QuestaoRepository questaoRepository, QuestaoMapper questaoMapper, QuestaoSearchRepository questaoSearchRepository) {
        this.questaoRepository = questaoRepository;
        this.questaoMapper = questaoMapper;
        this.questaoSearchRepository = questaoSearchRepository;
    }

    /**
     * Save a questao.
     *
     * @param questaoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuestaoDTO save(QuestaoDTO questaoDTO) {
        log.debug("Request to save Questao : {}", questaoDTO);
        Questao questao = questaoMapper.toEntity(questaoDTO);
        questao = questaoRepository.save(questao);
        QuestaoDTO result = questaoMapper.toDto(questao);
        questaoSearchRepository.save(questao);
        return result;
    }

    /**
     * Get all the questaos.
     *
     * @return the list of entities
     */
    @Override
    public List<QuestaoDTO> findAll() {
        log.debug("Request to get all Questaos");
        return questaoRepository.findAll().stream()
            .map(questaoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one questao by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public QuestaoDTO findOne(String id) {
        log.debug("Request to get Questao : {}", id);
        Questao questao = questaoRepository.findOne(id);
        return questaoMapper.toDto(questao);
    }

    /**
     * Delete the questao by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Questao : {}", id);
        questaoRepository.delete(id);
        questaoSearchRepository.delete(id);
    }

    /**
     * Search for the questao corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<QuestaoDTO> search(String query) {
        log.debug("Request to search Questaos for query {}", query);
        return StreamSupport
            .stream(questaoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(questaoMapper::toDto)
            .collect(Collectors.toList());
    }
}
