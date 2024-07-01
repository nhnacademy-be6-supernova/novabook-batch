package store.novabook.batch.common.exception;

public class CriticalException extends NovaException {
	public CriticalException(ErrorCode errorCode) {
		super(errorCode);
	}
}
