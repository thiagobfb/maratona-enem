package br.com.maratonaenem.service;

import br.com.maratonaenem.service.dto.EscolaDTO;
import java.util.List;

/**
 * Service Interface for managing Escola.
 */
public interface EscolaService {

    /**
     * Save a escola.
     *
     * @param escolaDTO the entity to save
     * @return the persisted entity
     */
    EscolaDTO save(EscolaDTO escolaDTO);

    /**
     * Get all the escolas.
     *
     * @return the list of entities
     */
    List<EscolaDTO> findAll();

    /**
     * Get the "id" escola.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EscolaDTO findOne(String id);

    /**
     * Delete the "id" escola.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the escola corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EscolaDTO> search(String query);
}
