package br.com.maratonaenem.repository.search;

import br.com.maratonaenem.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Aluno entity.
 */
public interface AlunoSearchRepository extends ElasticsearchRepository<Aluno, String> {
}
