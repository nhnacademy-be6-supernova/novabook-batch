package store.novabook.batch.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import store.novabook.batch.search.entity.BookDocument;

public interface ElasticSearchRepository extends ElasticsearchRepository<BookDocument, Long> {
}