package com.isoft.wocloud.nsfw.mq.client;

import java.io.IOException;

import com.isoft.wocloud.nsfw.mq.route.Route;

/**
 * 生产者客户款，用于发送不同消息到消息服务器
 * @author vancysheva
 *
 */
public interface ProducerClient extends BaseClient {
	
	/**
	 * 向消息服务器发送指定路由规则的消息
	 * @param routingKey
	 * @param msg
	 * @throws IOException
	 */
	public void send(Route route, String msg) throws IOException;
	
	/**
	 * 向消息服务器发送带有下载路由规则的消息
	 * @param msg
	 * @throws IOException
	 */
	public void sendDownload(String msg) throws IOException;
	
	/**
	 * 向消息服务器发送带有文件处理路由规则的消息
	 * @param msg
	 * @throws IOException
	 */
	public void sendThumbnail(String msg) throws IOException;
	
	/**
	 * 向消息服务器发送带有鉴别路由规则的消息
	 * @param msg
	 * @throws IOException
	 */
	public void sendTupu(String msg) throws IOException;
	
	/**
	 * 向消息服务器发送带有数据库路由规则的消息
	 * @param msg
	 * @throws IOException
	 */
	public void sendDatabase(String msg) throws IOException;
}
