package com.loveoyh.note.service.ex;

/**
 * �ʼǱ�δ�ҵ��쳣
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
