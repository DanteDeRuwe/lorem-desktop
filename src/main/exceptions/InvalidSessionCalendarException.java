package main.exceptions;

public class InvalidSessionCalendarException extends Exception {

	public InvalidSessionCalendarException() {
		super();
	}

	public InvalidSessionCalendarException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidSessionCalendarException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSessionCalendarException(String message) {
		super(message);
	}

	public InvalidSessionCalendarException(Throwable cause) {
		super(cause);
	}

}
