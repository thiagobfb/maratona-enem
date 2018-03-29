package br.com.maratonaenem.service;

import br.com.maratonaenem.service.dto.AlunoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Aluno.
 */
public interface AlunoService {

    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save
     * @return the persisted entity
     */
    AlunoDTO save(AlunoDTO alunoDTO);

    /**
     * Get all the alunos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlunoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" aluno.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AlunoDTO findOne(String id);

    /**
     * Delete the "id" aluno.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the aluno corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AlunoDTO> search(String query, Pageable pageable);
}
