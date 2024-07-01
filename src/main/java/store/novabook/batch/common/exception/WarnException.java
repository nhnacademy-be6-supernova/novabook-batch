package store.novabook.batch.common.exception;

public class WarnException extends NovaException {
	protected WarnException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}
