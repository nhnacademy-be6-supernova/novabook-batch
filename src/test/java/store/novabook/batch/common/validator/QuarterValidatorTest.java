package store.novabook.batch.common.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuarterValidatorTest {

	private QuarterValidator quarterValidator;

	@Mock
	private ConstraintValidatorContext context;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		quarterValidator = new QuarterValidator();
	}

	@Test
	void testIsValidWithValidQuarters() {
		assertTrue(quarterValidator.isValid("2023Q1", context));
		assertTrue(quarterValidator.isValid("1999Q2", context));
		assertTrue(quarterValidator.isValid("2020Q3", context));
		assertTrue(quarterValidator.isValid("1987Q4", context));
	}

	@Test
	void testIsValidWithInvalidQuarters() {
		assertFalse(quarterValidator.isValid("2023Q5", context)); // Q5는 유효하지 않음
		assertFalse(quarterValidator.isValid("202Q1", context));  // 연도가 3자리 숫자
		assertFalse(quarterValidator.isValid("20231Q", context)); // 잘못된 형식
		assertFalse(quarterValidator.isValid("Q12023", context)); // 잘못된 형식
		assertFalse(quarterValidator.isValid("abcdQ1", context)); // 숫자가 아닌 연도
	}

	@Test
	void testIsValidWithNull() {
		assertTrue(quarterValidator.isValid(null, context)); // null 체크
	}

	@Test
	void testIsValidWithEmptyString() {
		assertFalse(quarterValidator.isValid("", context)); // 빈 문자열은 유효하지 않음
	}

	@Test
	void testIsValidWithWhitespace() {
		assertFalse(quarterValidator.isValid(" ", context)); // 공백은 유효하지 않음
		assertFalse(quarterValidator.isValid("2023 Q1", context)); // 중간에 공백
	}
}
