package vn.edu.r2s.exception;

import lombok.Getter;
import lombok.Setter;
import vn.edu.r2s.response.ErrorCode;

@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode = ErrorCode.ALREADY_EXIST;
	private final String domain = "user";
	private String message;

	public UserAlreadyExistException(final String message) {
		this.message = message;
	}
}
