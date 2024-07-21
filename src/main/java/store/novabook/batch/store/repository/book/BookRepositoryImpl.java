package store.novabook.batch.store.repository.book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.annotation.PostConstruct;
import store.novabook.batch.search.dto.GetBookSearchResponse;
import store.novabook.batch.search.entity.BookDocument;
import store.novabook.batch.store.entity.book.Book;
import store.novabook.batch.store.entity.book.QBook;
import store.novabook.batch.store.entity.category.entity.QBookCategory;
import store.novabook.batch.store.entity.category.entity.QCategory;
import store.novabook.batch.store.entity.image.entity.QBookImage;
import store.novabook.batch.store.entity.image.entity.QImage;
import store.novabook.batch.store.entity.image.entity.QReview;
import store.novabook.batch.store.entity.tag.entity.QBookTag;
import store.novabook.batch.store.entity.tag.entity.QTag;

@Transactional
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {

	private final LocalContainerEntityManagerFactoryBean storeEntityManagerFactory;

	public BookRepositoryImpl(
		@Qualifier("storeEntityManagerFactory") LocalContainerEntityManagerFactoryBean storeEntityManagerFactory) {
		super(Book.class);

		this.storeEntityManagerFactory = storeEntityManagerFactory;
	}

	@PostConstruct
	public void init() {
		setEntityManager(storeEntityManagerFactory.getNativeEntityManagerFactory().createEntityManager());
	}

	@Override
	public List<BookDocument> getAllBookBy(Pageable pageable) {
		QBook qBook = QBook.book;
		QReview qReview = QReview.review;
		QBookTag qBookTag = QBookTag.bookTag;
		QTag qTag = QTag.tag;
		QBookCategory qBookCategory = QBookCategory.bookCategory;
		QCategory qCategory = QCategory.category;
		QImage qImage = QImage.image;
		QBookImage qBookImage = QBookImage.bookImage;

		JPAQuery<GetBookSearchResponse> query = new JPAQuery<>(getEntityManager())
			.select(Projections.constructor(GetBookSearchResponse.class,
				qBook.id,
				qBook.title,
				qBook.author,
				qBook.publisher,
				qImage.source,
				qBook.price,
				qBook.discountPrice,
				qReview.score.avg().coalesce(0.0),
				qBook.isPackaged,
				qReview.id.count().intValue()
			))
			.from(qBook)
			.leftJoin(qBookImage).on(qBook.id.eq(qBookImage.book.id))
			.leftJoin(qImage).on(qBookImage.image.id.eq(qImage.id))
			.leftJoin(qReview).on(qBook.id.eq(qReview.ordersBook.book.id))
			.where(qBook.bookStatus.id.ne(4L))
			.groupBy(qBook.id, qBook.title, qBook.author, qBook.publisher, qImage.source, qBook.price,
				qBook.discountPrice, qBook.isPackaged);

		List<GetBookSearchResponse> results = query.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<BookDocument> bookDocumentList = new ArrayList<>();

		List<Long> bookIds = results.stream()
			.map(GetBookSearchResponse::id)
			.toList();

		List<Tuple> tagTuples = new JPAQuery<>(getEntityManager()).
			select(qBookTag.book.id, qTag.name)
			.from(qBookTag)
			.join(qTag).on(qBookTag.tag.id.eq(qTag.id))
			.where(qBookTag.book.id.in(bookIds))
			.fetch();

		List<Tuple> categoryTuples = new JPAQuery<>(getEntityManager())
			.select(qBookCategory.book.id, qCategory.name)
			.from(qBookCategory)
			.join(qCategory).on(qBookCategory.category.id.eq(qCategory.id))
			.where(qBookCategory.book.id.in(bookIds))
			.fetch();

		Map<Long, List<String>> tagMap = new HashMap<>();
		Map<Long, List<String>> categoryMap = new HashMap<>();

		for (Tuple tuple : tagTuples) {
			Long bookId = tuple.get(qBookTag.book.id);
			String tagName = tuple.get(qTag.name);
			tagMap.computeIfAbsent(bookId, k -> new ArrayList<>()).add(tagName);
		}

		for (Tuple tuple : categoryTuples) {
			Long bookId = tuple.get(qBookCategory.book.id);
			String categoryName = tuple.get(qCategory.name);
			categoryMap.computeIfAbsent(bookId, k -> new ArrayList<>()).add(categoryName);
		}

		for (GetBookSearchResponse getBook : results) {
			Long bookId = getBook.id();
			List<String> tagNames = tagMap.getOrDefault(bookId, Collections.emptyList());
			List<String> categoryNames = categoryMap.getOrDefault(bookId, Collections.emptyList());

			bookDocumentList.add(BookDocument.of(getBook, tagNames, categoryNames));
		}

		return bookDocumentList;
	}
}
