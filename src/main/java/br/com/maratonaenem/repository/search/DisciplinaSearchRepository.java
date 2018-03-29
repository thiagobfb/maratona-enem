package br.com.maratonaenem.repository.search;

import br.com.maratonaenem.domain.Disciplina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Disciplina entity.
 */
public interface DisciplinaSearchRepository extends ElasticsearchRepository<Disciplina, String> {
}
