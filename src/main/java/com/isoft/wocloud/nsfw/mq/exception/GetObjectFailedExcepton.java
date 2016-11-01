package com.isoft.wocloud.nsfw.mq.exception;

public class GetObjectFailedExcepton extends Exception {
	
	private static final long serialVersionUID = 1L;

	public GetObjectFailedExcepton(String msg) {
		super(msg);
	}
	
	public GetObjectFailedExcepton(String msg, Throwable e) {
		super(msg, e);
	}
}
