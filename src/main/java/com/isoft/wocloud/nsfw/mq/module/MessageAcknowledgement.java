package com.isoft.wocloud.nsfw.mq.module;

public enum MessageAcknowledgement {

	/**
	 * 告诉消息服务器删除此消息
	 */
	DELETE,
	/**
	 * 把该消息重新放入队列中
	 */
	DELAY_HANDLE;
}
