package vn.edu.r2s.response;

import java.time.OffsetDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

	private final OperationType operationType = OperationType.FAILURE;
	private String message;
	private ErrorCode code;
	private String domain;
	private Map<String, Object> details;
	private final OffsetDateTime timestamp = OffsetDateTime.now();

	public static ErrorResponse of(final ErrorCode errorCode, final String domain) {
		return ErrorResponse.builder()
				.code(errorCode)
				.domain(domain)
				.message("Exception occurred!")
				.build();
	}
	
	public static ErrorResponse of(final ErrorCode errorCode, final String domain, final String message) {
		return ErrorResponse.builder()
				.code(errorCode)
				.domain(domain)
				.message(message)
				.build();
	}
	
	public static ErrorResponse of(final ErrorCode errorCode, final String domain, final String message, final Map<String, Object> details) {
		return ErrorResponse.builder()
				.code(errorCode)
				.domain(domain)
				.message(message)
				.details(details)
				.build();
	}
}
