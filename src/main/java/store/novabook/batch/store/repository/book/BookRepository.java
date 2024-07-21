package store.novabook.batch.store.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;

import store.novabook.batch.store.entity.book.Book;

public interface BookRepository extends JpaRepository<Book, Long> , BookRepositoryCustom{
}
