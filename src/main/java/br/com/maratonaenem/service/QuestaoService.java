package br.com.maratonaenem.service;

import br.com.maratonaenem.service.dto.QuestaoDTO;
import java.util.List;

/**
 * Service Interface for managing Questao.
 */
public interface QuestaoService {

    /**
     * Save a questao.
     *
     * @param questaoDTO the entity to save
     * @return the persisted entity
     */
    QuestaoDTO save(QuestaoDTO questaoDTO);

    /**
     * Get all the questaos.
     *
     * @return the list of entities
     */
    List<QuestaoDTO> findAll();

    /**
     * Get the "id" questao.
     *
     * @param id the id of the entity
     * @return the entity
     */
    QuestaoDTO findOne(String id);

    /**
     * Delete the "id" questao.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the questao corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<QuestaoDTO> search(String query);
}
