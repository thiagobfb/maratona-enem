package br.com.maratonaenem.repository.search;

import br.com.maratonaenem.domain.Questao;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Questao entity.
 */
public interface QuestaoSearchRepository extends ElasticsearchRepository<Questao, String> {
}
