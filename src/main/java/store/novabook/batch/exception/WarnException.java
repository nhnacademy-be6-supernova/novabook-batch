package store.novabook.batch.exception;

public class WarnException extends NovaException {
	protected WarnException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
