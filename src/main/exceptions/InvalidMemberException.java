package main.exceptions;

public class InvalidMemberException extends Exception {

	public InvalidMemberException() {
		super();
	}

	public InvalidMemberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidMemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMemberException(String message) {
		super(message);
	}

	public InvalidMemberException(Throwable cause) {
		super(cause);
	}

}
