package com.isoft.wocloud.nsfw.mq.client.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.common.Constant;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.module.MessageModule;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.route.Route;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.MessageProperties;

public class RbProducerClient implements ProducerClient {

	private RbConnector connector;
	private Exchange exchange;
	private boolean durable;

	public RbProducerClient(Host host, Exchange exchange, boolean durable) throws IOException, TimeoutException {
		this.connector = new RbConnector(host);
		this.exchange = exchange;
		this.durable = durable;
	}
	
	@Override
	public void send(Route route, String msg) throws IOException {
		BasicProperties properties = durable ? MessageProperties.PERSISTENT_TEXT_PLAIN : null;
		String exchangeName = MQConfig.messageModule == MessageModule.SIMPLE ? "" : exchange.getName();
		String routingKey = MQConfig.messageModule == MessageModule.SIMPLE ? route.getQueue().name() : route.name();
		this.connector.getChannel().basicPublish(exchangeName,
				routingKey, properties, msg.getBytes(Constant.CHARSET));
	}

	@Override
	public void sendDownload(String msg) throws IOException {
		send(Route.DOWNLOAD_KEY, msg);
	}

	@Override
	public void sendThumbnail(String msg) throws IOException {
		send(Route.THUMBNAIL_KEY, msg);
	}

	@Override
	public void sendTupu(String msg) throws IOException {
		send(Route.TUPU_KEY, msg);
	}

	@Override
	public void sendDatabase(String msg) throws IOException {
		send(Route.DATABASE_KEY, msg);
	}
	
	/**
	 * 关闭与消息服务器的连接
	 */
	@Override
	public void close() throws IOException, TimeoutException {
		this.connector.close();
	}
	
	@Override
	public void open() throws IOException, TimeoutException {
		System.out.println("open method invoked");
	}
	
	public RbConnector getConnector() {
		return connector;
	}
	
	public Exchange getExchange() {
		return exchange;
	}

	public boolean isDurable() {
		return durable;
	}

	/**
	 * 生产者获得的信息数量所有队列中的信息数量
	 */
	@Override
	public Long getMessageCount() throws IOException {
		long count = 0L;
		for (Queue queue : Queue.values()) {
			count += this.connector.getChannel().messageCount(queue.name());
		}
		return count;
	}
}

