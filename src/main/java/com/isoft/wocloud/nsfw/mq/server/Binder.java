package com.isoft.wocloud.nsfw.mq.server;

import java.io.IOException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.route.Route;

public interface Binder {
	
	/**
	 * 通过指定的路由，绑定交换机和队列
	 * @param queue
	 * @param exchange
	 * @throws IOException
	 */
	public void bind(Queue queue, Exchange exchange, Route route) throws IOException;
	
	/**
	 * 通过指定的路由，绑定消费者和队列
	 * @param queue
	 * @param consumerClient
	 * @param route
	 * @throws IOException
	 */
	public void bind(Queue queue, ConsumerClient consumerClient, Route route) throws IOException;
}
