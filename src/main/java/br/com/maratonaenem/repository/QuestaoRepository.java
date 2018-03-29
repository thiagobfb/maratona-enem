package br.com.maratonaenem.repository;

import br.com.maratonaenem.domain.Questao;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Questao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestaoRepository extends MongoRepository<Questao, String> {

}
