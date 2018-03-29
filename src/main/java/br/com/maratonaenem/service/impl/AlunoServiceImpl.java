package br.com.maratonaenem.service.impl;

import br.com.maratonaenem.service.AlunoService;
import br.com.maratonaenem.domain.Aluno;
import br.com.maratonaenem.repository.AlunoRepository;
import br.com.maratonaenem.repository.search.AlunoSearchRepository;
import br.com.maratonaenem.service.dto.AlunoDTO;
import br.com.maratonaenem.service.mapper.AlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Aluno.
 */
@Service
public class AlunoServiceImpl implements AlunoService {

    private final Logger log = LoggerFactory.getLogger(AlunoServiceImpl.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    private final AlunoSearchRepository alunoSearchRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository, AlunoMapper alunoMapper, AlunoSearchRepository alunoSearchRepository) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
        this.alunoSearchRepository = alunoSearchRepository;
    }

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AlunoDTO save(AlunoDTO alunoDTO) {
        log.debug("Request to save Aluno : {}", alunoDTO);
        Aluno aluno = alunoMapper.toEntity(alunoDTO);
        aluno = alunoRepository.save(aluno);
        AlunoDTO result = alunoMapper.toDto(aluno);
        alunoSearchRepository.save(aluno);
        return result;
    }

    /**
     * Get all the alunos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<AlunoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Alunos");
        return alunoRepository.findAll(pageable)
            .map(alunoMapper::toDto);
    }

    /**
     * Get one aluno by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public AlunoDTO findOne(String id) {
        log.debug("Request to get Aluno : {}", id);
        Aluno aluno = alunoRepository.findOne(id);
        return alunoMapper.toDto(aluno);
    }

    /**
     * Delete the aluno by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Aluno : {}", id);
        alunoRepository.delete(id);
        alunoSearchRepository.delete(id);
    }

    /**
     * Search for the aluno corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<AlunoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Alunos for query {}", query);
        Page<Aluno> result = alunoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(alunoMapper::toDto);
    }
}
