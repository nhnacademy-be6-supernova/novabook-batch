package store.novabook.batch.common.exception;

/**
 * 다양한 오류 상태를 나타내는 열거형 클래스입니다. 각 오류 상태는 HTTP 상태 코드와 관련된 메시지를 포함합니다.
 * 이 오류 코드는 Nova Book Store 애플리케이션 내에서 예외 처리에 사용됩니다.
 *
 * <p> 각 오류 코드는 다음과 같이 정의됩니다:
 * <ul>
 *     <li>400 - 잘못된 요청 오류</li>
 *     <li>404 - 리소스를 찾을 수 없음</li>
 *     <li>500 - 내부 서버 오류</li>
 * </ul>
 * </p>
 *
 * <p> 예제 사용법:
 * <pre>
 *     throw new BadRequestException(ErrorCode.INVALID_REQUEST_ARGUMENT);
 * </pre>
 * </p>
 */
public enum ErrorCode {

	// 403
	NOT_ENOUGH_PERMISSION("해당 권한이 없습니다."),
	NOT_FOUND_MEMBER_STATUS("회원 상태에 대한 정보를 가져올 수 없습니다."),

	BIRTHDAY_COUPON_NOT_FOUND("생일 쿠폰이 존재하지 않습니다. "),
	JOB_FAIL_BIRTHDAY("생일 쿠폰 작업이 예기치 못한 이유로 중단되었습니다."),
	JOB_FAIL_MEMBER_GRADE("분기별 회원들의 등급계산이 예기치 못한 이유로 중단되었습니다."),
	JOB_FAIL_ELASTIC_SEARCH_UPDATE("엘라스틱 서치 업데이트 작업이 예기치 못한 이유로 중단되었습니다."),
	JOB_FAIL_MEMBER_STATUS_UPDATE("회원 상태를 휴면으로 변경하는 작업이 예기치 못한 이유로 중단되었습니다. "),


	FAILED_CONVERSION("Dto로 변환 하는데 실패했습니다."),
	RESPONSE_BODY_IS_NULL("키매니저의 response body가 null입니다."),
	MISSING_BODY_KEY("응답 본문에 \"body\" 키가 누락되었습니다."),
	MISSING_SECRET_KEY("응답 본문에 \"secret\" 키가 누락되었습니다.");

	private final String message;

	/**
	 * 주어진 메시지를 사용하여 새로운 {@code ErrorCode}를 생성합니다.
	 *
	 * @param message 오류 메시지
	 */
	ErrorCode(String message) {
		this.message = message;
	}

	/**
	 * 오류 메시지를 반환합니다.
	 *
	 * @return 오류 메시지
	 */
	public String getMessage() {
		return message;
	}
}
