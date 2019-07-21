package com.lms.bs.rest.exception;

import org.springframework.http.HttpStatus;

import com.lms.svc.common.constants.ApplicationCommonConstants;
import com.lms.svc.common.exception.ApplicationError;

public class InvalidBookStatusException extends ApplicationError {
	private static final long serialVersionUID = -8066458944260533648L;

	private final String errorTime;
	private final HttpStatus httpStatus;
	private final String message;
	private final int errorCode;

	public InvalidBookStatusException(String provided, String valid) {
		this.message = "Invalid status [" + provided + "]. Valid statuses are [" + valid + "].";
		this.httpStatus = HttpStatus.FORBIDDEN;
		this.errorCode = ApplicationCommonConstants.INVALID_BOOK_STATUS_ERROR_CODE;
		this.errorTime = ApplicationCommonConstants.getCurrentDateAsString();
	}
	
	public InvalidBookStatusException() {
		this.httpStatus = HttpStatus.FORBIDDEN;
		this.message = "No status of book was provided";
		this.errorCode = ApplicationCommonConstants.INVALID_BOOK_STATUS_ERROR_CODE;
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
