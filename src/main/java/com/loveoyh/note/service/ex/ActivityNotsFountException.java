package com.loveoyh.note.service.ex;

/**
 * 活动未找到异常
 * @author oyh
 *
 */
public class ActivityNotsFountException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ActivityNotsFountException() {
	}

	public ActivityNotsFountException(String message) {
		super(message);
	}

	public ActivityNotsFountException(Throwable cause) {
		super(cause);
	}

	public ActivityNotsFountException(String message, Throwable cause) {
		super(message, cause);
	}

	public ActivityNotsFountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
