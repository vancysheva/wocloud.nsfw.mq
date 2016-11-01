package com.isoft.wocloud.nsfw.mq.exception;

public class AckMessageFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AckMessageFailedException(String msg) {
		super(msg);
	}
	
	public AckMessageFailedException(String msg, Throwable e) {
		super(msg, e);
	}
}
