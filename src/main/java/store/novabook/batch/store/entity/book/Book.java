package store.novabook.batch.store.entity.book;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "book_status_id")
	private BookStatus bookStatus;

	@NotNull
	private String isbn;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private String descriptionDetail;

	@NotNull
	private String author;

	@NotNull
	private String publisher;


	@NotNull
	private LocalDateTime publicationDate;

	@NotNull
	int inventory;

	@NotNull
	private long price;

	@NotNull
	private Long discountPrice;

	@NotNull
	boolean isPackaged;

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;


	@Builder
	public Book(BookStatus bookStatus,
				String isbn,
				String title,
				String description,
				String descriptionDetail,
				String author,
				String publisher,
				LocalDateTime publicationDate,
				int inventory,
				Long price,
				Long discountPrice,
				boolean isPackaged) {
		this.bookStatus = bookStatus;
		this.isbn = isbn;
		this.title = title;
		this.description = description;
		this.descriptionDetail = descriptionDetail;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.inventory = inventory;
		this.price = price;
		this.discountPrice = discountPrice;
		this.isPackaged = isPackaged;

	}

}
