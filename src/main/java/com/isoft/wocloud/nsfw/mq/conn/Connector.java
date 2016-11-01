package com.isoft.wocloud.nsfw.mq.conn;

/**
 * 连接消息服务器的连接器
 * @author vancysheva
 *
 */
public interface Connector {
	
	/**
	 * 关闭连接
	 * @throws Exception
	 */
	public void close() throws Exception;
}
