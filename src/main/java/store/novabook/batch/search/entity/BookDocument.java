package store.novabook.batch.search.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import store.novabook.batch.search.dto.GetBookSearchResponse;

@Getter
@Document(indexName = "supernova")
public class BookDocument {
	@Id
	@Field(type = FieldType.Long)
	private final Long id;

	@Field(type = FieldType.Text, analyzer = "korean")
	private final String title;

	@Field(type = FieldType.Text, analyzer = "korean")
	private final String author;

	@Field(type = FieldType.Text, analyzer = "korean")
	private final String publisher;

	@Field(type = FieldType.Text)
	private final String image;

	@Field(type = FieldType.Long)
	private final Long price;

	@Field(type = FieldType.Long)
	private final Long discountPrice;

	@Field(type = FieldType.Integer)
	private final Double score;

	@Field(type = FieldType.Boolean)
	private final Boolean isPackaged;

	@Field(type = FieldType.Integer)
	private final Integer review;

	@Field(type = FieldType.Text, analyzer = "korean")
	private final List<String> categoryList;

	@Field(type = FieldType.Text, analyzer = "korean")
	private final List<String> tagList;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private final LocalDateTime createdAt;

	@Builder
	public BookDocument(Long id, String title, String author, String publisher, String image, Long price, Long discountPrice, Double score, Boolean isPackaged, Integer review, List<String> tagList,
		List<String> categoryList) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.image = image;
		this.price = price;
		this.discountPrice = discountPrice;
		this.score = score;
		this.isPackaged = isPackaged;
		this.review = review;
		this.tagList = tagList;
		this.categoryList = categoryList;
		this.createdAt = LocalDateTime.now();
	}

	public static BookDocument of(GetBookSearchResponse response, List<String> tags, List<String> categories) {
		return BookDocument.builder()
			.id(response.id())
			.title(response.title())
			.author(response.author())
			.publisher(response.publisher())
			.image(response.image())
			.price(response.price())
			.discountPrice(response.discountPrice())
			.score(response.score())
			.isPackaged(response.isPackaged())
			.review(response.review())
			.tagList(tags)
			.categoryList(categories)
			.build();
	}

}
