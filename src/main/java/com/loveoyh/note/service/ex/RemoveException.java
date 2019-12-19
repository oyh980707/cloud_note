package com.loveoyh.note.service.ex;

public class RemoveException extends ServiceException{
	private static final long serialVersionUID = 1L;

	public RemoveException() {
		super();
	}

	public RemoveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RemoveException(String message, Throwable cause) {
		super(message, cause);
	}

	public RemoveException(String message) {
		super(message);
	}

	public RemoveException(Throwable cause) {
		super(cause);
	}
	
	
}
