package br.com.maratonaenem.repository;

import br.com.maratonaenem.domain.Escola;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Escola entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscolaRepository extends MongoRepository<Escola, String> {

}
