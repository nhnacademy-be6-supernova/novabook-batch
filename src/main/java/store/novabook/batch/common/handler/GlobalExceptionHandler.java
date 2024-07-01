package store.novabook.batch.common.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import store.novabook.batch.common.exception.InformationException;
import store.novabook.batch.common.exception.NovaException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InformationException.class)
	public void handle(InformationException exception, HttpServletRequest request) {
		log.info("error code: {} {}", exception.getErrorCode(), exception.getMessage());
	}

	@ExceptionHandler(NovaException.class)
	protected void handleNovaException(NovaException ex, WebRequest request) {
		log.error("error code: {} {}", ex.getErrorCode(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	protected void handleException(Exception ex, WebRequest request) {
		log.error("error code: {}", ex.getMessage());
	}

}
