package com.isoft.wocloud.nsfw.mq.exception;

public class BuildeFailedException extends Exception {
	private static final long serialVersionUID = 1L;

	public BuildeFailedException(String msg) {
		super(msg);
	}
	
	public BuildeFailedException(String msg, Throwable e) {
		super(msg, e);
	}
}
