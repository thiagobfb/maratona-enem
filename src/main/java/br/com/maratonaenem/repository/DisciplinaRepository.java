package br.com.maratonaenem.repository;

import br.com.maratonaenem.domain.Disciplina;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Disciplina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisciplinaRepository extends MongoRepository<Disciplina, String> {

}
