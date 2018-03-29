package br.com.maratonaenem.service.impl;

import br.com.maratonaenem.service.DisciplinaService;
import br.com.maratonaenem.domain.Disciplina;
import br.com.maratonaenem.repository.DisciplinaRepository;
import br.com.maratonaenem.repository.search.DisciplinaSearchRepository;
import br.com.maratonaenem.service.dto.DisciplinaDTO;
import br.com.maratonaenem.service.mapper.DisciplinaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Disciplina.
 */
@Service
public class DisciplinaServiceImpl implements DisciplinaService {

    private final Logger log = LoggerFactory.getLogger(DisciplinaServiceImpl.class);

    private final DisciplinaRepository disciplinaRepository;

    private final DisciplinaMapper disciplinaMapper;

    private final DisciplinaSearchRepository disciplinaSearchRepository;

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper, DisciplinaSearchRepository disciplinaSearchRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaSearchRepository = disciplinaSearchRepository;
    }

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DisciplinaDTO save(DisciplinaDTO disciplinaDTO) {
        log.debug("Request to save Disciplina : {}", disciplinaDTO);
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplina = disciplinaRepository.save(disciplina);
        DisciplinaDTO result = disciplinaMapper.toDto(disciplina);
        disciplinaSearchRepository.save(disciplina);
        return result;
    }

    /**
     * Get all the disciplinas.
     *
     * @return the list of entities
     */
    @Override
    public List<DisciplinaDTO> findAll() {
        log.debug("Request to get all Disciplinas");
        return disciplinaRepository.findAll().stream()
            .map(disciplinaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one disciplina by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public DisciplinaDTO findOne(String id) {
        log.debug("Request to get Disciplina : {}", id);
        Disciplina disciplina = disciplinaRepository.findOne(id);
        return disciplinaMapper.toDto(disciplina);
    }

    /**
     * Delete the disciplina by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Disciplina : {}", id);
        disciplinaRepository.delete(id);
        disciplinaSearchRepository.delete(id);
    }

    /**
     * Search for the disciplina corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    public List<DisciplinaDTO> search(String query) {
        log.debug("Request to search Disciplinas for query {}", query);
        return StreamSupport
            .stream(disciplinaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(disciplinaMapper::toDto)
            .collect(Collectors.toList());
    }
}
