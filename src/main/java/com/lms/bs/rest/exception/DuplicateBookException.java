package com.lms.bs.rest.exception;

import org.springframework.http.HttpStatus;

import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.ApplicationError;

public class DuplicateBookException extends ApplicationError {
	private static final long serialVersionUID = -8066458944260533648L;

	private final String errorTime;
	private final HttpStatus httpStatus;
	private final String message;
	private final int errorCode;

	public DuplicateBookException(String bookName, String author) {
		this.message = String.format("Book [%s] by [%s] already exists.", bookName, author);
		this.httpStatus = HttpStatus.CONFLICT;
		this.errorCode = ApplicationCommonConstants.DUPLICATE_BOOK_ERROR_CODE;
		this.errorTime = ApplicationCommonConstants.getCurrentDateAsString();
	}
	
	@Override
	public String getErrorTime() {
		return errorTime;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public int getErrorCode() {
		return errorCode;
	}

}
