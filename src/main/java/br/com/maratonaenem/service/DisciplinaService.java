package br.com.maratonaenem.service;

import br.com.maratonaenem.service.dto.DisciplinaDTO;
import java.util.List;

/**
 * Service Interface for managing Disciplina.
 */
public interface DisciplinaService {

    /**
     * Save a disciplina.
     *
     * @param disciplinaDTO the entity to save
     * @return the persisted entity
     */
    DisciplinaDTO save(DisciplinaDTO disciplinaDTO);

    /**
     * Get all the disciplinas.
     *
     * @return the list of entities
     */
    List<DisciplinaDTO> findAll();

    /**
     * Get the "id" disciplina.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DisciplinaDTO findOne(String id);

    /**
     * Delete the "id" disciplina.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the disciplina corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DisciplinaDTO> search(String query);
}
