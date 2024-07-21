package store.novabook.batch.search.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.novabook.batch.search.entity.BookDocument;
import store.novabook.batch.search.repository.ElasticSearchRepository;
import store.novabook.batch.store.repository.book.BookRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchService {
	private final BookRepository bookRepository;
	private final ElasticSearchRepository elasticSearchRepository;

	public void productDocumentsUpdate(int size) {
		elasticSearchRepository.deleteAll();

		int currentPage = 0;
		List<BookDocument> productDocuments;

		do {
			productDocuments = bookRepository.getAllBookBy(PageRequest.of(currentPage, size, Sort.by("id").descending()));
			elasticSearchRepository.saveAll(productDocuments);
			currentPage++;
		} while (currentPage < productDocuments.size());
	}
}
