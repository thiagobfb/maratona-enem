package br.com.maratonaenem.service.impl;

import br.com.maratonaenem.service.EscolaService;
import br.com.maratonaenem.domain.Escola;
import br.com.maratonaenem.repository.EscolaRepository;
import br.com.maratonaenem.repository.search.EscolaSearchRepository;
import br.com.maratonaenem.service.dto.EscolaDTO;
import br.com.maratonaenem.service.mapper.EscolaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Escola.
 */
@Service
public class EscolaServiceImpl implements EscolaService {

    private final Logger log = LoggerFactory.getLogger(EscolaServiceImpl.class);

    private final EscolaRepository escolaRepository;

    private final EscolaMapper escolaMapper;

    private final EscolaSearchRepository escolaSearchRepository;

    public EscolaServiceImpl(EscolaRepository escolaRepository, EscolaMapper escolaMapper, EscolaSearchRepository escolaSearchRepository) {
        this.escolaRepository = escolaRepository;
        this.escolaMapper = escolaMapper;
        this.escolaSearchRepository = escolaSearchRepository;
    }

    /**
     * Save a escola.
     *
     * @param escolaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EscolaDTO save(EscolaDTO escolaDTO) {
        log.debug("Request to save Escola : {}", escolaDTO);
        Escola escola = escolaMapper.toEntity(escolaDTO);
        escola = escolaRepository.save(escola);
        EscolaDTO result = escolaMapper.toDto(escola);
        escolaSearchRepository.save(escola);
        return result;
    }

    /**
     * Get all the escolas.
     *
     * @return the list of entities
     */
    @Override
    public List<EscolaDTO> findAll() {
        log.debug("Request to get all Escolas");
        return escolaRepository.findAll().stream()
            .map(escolaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one escola by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public EscolaDTO findOne(String id) {
        log.debug("Request to get Escola : {}", id);
        Escola escola = escolaRepository.findOne(id);
        return escolaMapper.toDto(escola);
    }

    /**
     * Delete the escola by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Escola : {}", id);
        escolaRepository.delete(id);
        escolaSearchRepository.delete(id);
    }

    /**
     * Search for the escola corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<EscolaDTO> search(String query) {
        log.debug("Request to search Escolas for query {}", query);
        return StreamSupport
            .stream(escolaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(escolaMapper::toDto)
            .collect(Collectors.toList());
    }
}
