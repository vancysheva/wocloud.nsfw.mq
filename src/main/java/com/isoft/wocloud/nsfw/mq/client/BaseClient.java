package com.isoft.wocloud.nsfw.mq.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface BaseClient {

	/**
	 * 建立与消息服务器的连接
	 * @return
	 * @throws Exception
	 */
	public void open() throws IOException, TimeoutException;
	
	/**
	 * 关闭与消息服务器的链接
	 * @return
	 * @throws Exception
	 */
	public void close() throws IOException, TimeoutException;
	
	/**
	 * 获取消息队列中消息数量
	 * @return
	 * @throws IOException 
	 */
	public Long getMessageCount() throws IOException;
}
