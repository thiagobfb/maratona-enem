package br.com.maratonaenem.repository.search;

import br.com.maratonaenem.domain.Escola;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Escola entity.
 */
public interface EscolaSearchRepository extends ElasticsearchRepository<Escola, String> {
}
