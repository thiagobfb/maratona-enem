package br.com.maratonaenem.repository;

import br.com.maratonaenem.domain.Aluno;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Aluno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlunoRepository extends MongoRepository<Aluno, String> {

}
