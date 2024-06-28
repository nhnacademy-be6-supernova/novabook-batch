package store.novabook.batch.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import store.novabook.batch.exception.InformationException;
import store.novabook.batch.exception.NovaException;


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

}
