package store.novabook.batch.common.dto;

public record DatabaseConfigDto(
	String url,
	String username,
	String password
) {
}
