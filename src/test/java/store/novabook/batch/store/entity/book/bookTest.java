package store.novabook.batch.store.entity.book;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import store.novabook.batch.store.repository.book.BookRepository;

@SpringJUnitConfig
@DataJpaTest
class bookTest {
	@Autowired
	private BookRepository bookRepository;

	@Test
	void testSaveBook() {
		// given
		BookStatus bookStatus = new BookStatus(); // BookStatus 엔티티에 대한 설정 필요
		Book book = Book.builder()
			.bookStatus(bookStatus)
			.isbn("978-3-16-148410-0")
			.title("Sample Book")
			.description("Sample description")
			.descriptionDetail("Detailed description")
			.author("Sample Author")
			.publisher("Sample Publisher")
			.publicationDate(LocalDateTime.of(2023, 1, 1, 0, 0))
			.inventory(100)
			.price(25000L)
			.discountPrice(20000L)
			.isPackaged(true)
			.build();

		// when
		bookRepository.save(book);

		// then
		assertNotNull(book.getId(), "Book ID should not be null after saving");

		// find by ID
		Book savedBook = bookRepository.findById(book.getId()).orElse(null);
		assertNotNull(savedBook, "Saved Book should not be null");

		assertEquals(book.getTitle(), savedBook.getTitle(), "Titles should match");
		assertEquals(book.getIsbn(), savedBook.getIsbn(), "ISBNs should match");
		assertEquals(book.getAuthor(), savedBook.getAuthor(), "Authors should match");
		assertEquals(book.getPublisher(), savedBook.getPublisher(), "Publishers should match");
		assertEquals(book.getPublicationDate(), savedBook.getPublicationDate(), "Publication dates should match");
		assertEquals(book.getPrice(), savedBook.getPrice(), "Prices should match");
		assertEquals(book.getDiscountPrice(), savedBook.getDiscountPrice(), "Discount prices should match");
		assertEquals(book.isPackaged(), savedBook.isPackaged(), "Packaged status should match");
	}
}