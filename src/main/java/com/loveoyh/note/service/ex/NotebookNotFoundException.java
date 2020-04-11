package com.loveoyh.note.service.ex;

/**
 * 笔记本未找到异常
 * @author oyh
 *
 */
public class NotebookNotFoundException extends ServiceException {
	private static final long serialVersionUID = 1L;

	public NotebookNotFoundException() {
	}

	public NotebookNotFoundException(String message) {
		super(message);
	}

	public NotebookNotFoundException(Throwable cause) {
		super(cause);
	}

	public NotebookNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotebookNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
