package store.novabook.batch.exception;

public class CriticalException extends NovaException {
	public CriticalException(ErrorCode errorCode) {
		super(errorCode);
	}
}
