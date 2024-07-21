package store.novabook.batch.store.repository.book;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import store.novabook.batch.search.entity.BookDocument;

@NoRepositoryBean
public interface BookRepositoryCustom {
	List<BookDocument> getAllBookBy(Pageable pageable);
}
