package com.isoft.wocloud.nsfw.mq.client.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exception.AckMessageFailedException;
import com.isoft.wocloud.nsfw.mq.handle.ConsumeHandler;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.rabbitmq.client.Channel;

public class RbConsumerClient implements ConsumerClient {

	private RbConnector connector;
	private ConsumeHandler handler;
	private Queue queue;
	private boolean autoAck;
	
	public RbConsumerClient(Host host, Queue queue, boolean autoAck) throws IOException, TimeoutException {
		this.connector = new RbConnector(host);
		this.queue = queue;
		this.autoAck = autoAck;
	}
	
	@Override
	public void bind(ConsumeHandler handler) throws IOException {
		this.handler = handler;
		Channel channel = this.connector.getChannel();
		channel.basicQos(1);
		// 第二个参数默认是true， 设置true为消息处理完毕自动返回RabbitMQ，消息从队列里移除
		channel.basicConsume(queue.name(), autoAck, this.handler);
	}
	
	@Override
	public void basicAck(long deliveryTag) throws AckMessageFailedException {
		try {
			this.connector.getChannel().basicAck(deliveryTag, false);
		} catch (Exception e) {
			throw new AckMessageFailedException("发送消息回执失败！", e);
		}
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

	/**
	 * 获取的只是该消费者的队列的信息数量
	 */
	@Override
	public Long getMessageCount() throws IOException {
		return connector.getChannel().messageCount(queue.name());
	}
}
