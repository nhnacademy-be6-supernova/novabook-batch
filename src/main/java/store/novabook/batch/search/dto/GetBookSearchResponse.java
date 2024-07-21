package store.novabook.batch.search.dto;

import lombok.Builder;

@Builder
public record GetBookSearchResponse(
	Long id,
	String title,
	String author,
	String publisher,
	String image,
	Long price,
	Long discountPrice,
	Double score,
	Boolean isPackaged,
	Integer review
) {
}
