package vn.edu.r2s.handler;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import vn.edu.r2s.exception.UserNotFoundException;
import vn.edu.r2s.response.ErrorCode;
import vn.edu.r2s.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(exception = { UserNotFoundException.class })
	public ErrorResponse handle(final UserNotFoundException exception) {
		return ErrorResponse.of(exception.getErrorCode(), exception.getDomain(), exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(exception = { Exception.class })
	public ErrorResponse handle(final Exception exception) {
		exception.printStackTrace();
		return ErrorResponse.of(ErrorCode.INTERNAL_SERVER, "system", exception.getMessage());
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = { ConstraintViolationException.class })
	public ErrorResponse handle(final ConstraintViolationException exception) {
		Map<String, Object> details = exception.getConstraintViolations()
				.stream()
				.collect(Collectors.toMap(x -> x.getPropertyPath().toString(), x -> x.getMessage(), (a, b) -> b));

		return ErrorResponse.of(ErrorCode.INVALID, "request", "Validation failed", details);
	}
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = { MethodArgumentNotValidException.class })
	public ErrorResponse handle(final MethodArgumentNotValidException exception) {
		Map<String, Object> details = exception.getBindingResult().getFieldErrors()
				.stream()
				.collect(Collectors.toMap(x -> x.getField(), x -> x.getDefaultMessage(), (a, b) -> b));

		return ErrorResponse.of(ErrorCode.INVALID, "request", "Validation failed", details);
	}
}
