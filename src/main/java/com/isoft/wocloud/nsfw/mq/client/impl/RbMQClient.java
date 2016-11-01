package com.isoft.wocloud.nsfw.mq.client.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;

public class RbMQClient implements MQClient {
	
	private static ProducerClient producerClient;
	
	private Host host;
	
	private Exchange exchange = MQConfig.exchange;
	private boolean durable = true;
	private boolean autoAck = false;
	
	public RbMQClient(Host host, Exchange exchange, boolean durable) {
		this.host = host;
		this.exchange = exchange;
		this.durable = durable;
	}
	
	public RbMQClient(Host host) {
		this.host = host;
	}
	
	@Override
	public ProducerClient getProducerClient() throws IOException, TimeoutException {
		if (producerClient == null) {
			producerClient = new RbProducerClient(host, exchange, durable);
		}
		return producerClient;
	}

	@Override
	public ConsumerClient getConsumerClient(Queue queue) throws IOException, TimeoutException {
		return new RbConsumerClient(host, queue, autoAck);
	}
	
	@Override
	public ConsumerClient getDownloadWorker() throws IOException, TimeoutException {
		return new RbConsumerClient(host, Queue.DOWNLOAD_QUEUE, autoAck);
	}
	
	@Override
	public ConsumerClient getThumbnailWorker() throws IOException, TimeoutException {
		return new RbConsumerClient(host, Queue.THUMBNAIL_QUEUE, autoAck);
	}

	@Override
	public ConsumerClient getTupuWorker() throws IOException, TimeoutException {
		return new RbConsumerClient(host, Queue.TUPU_QUEUE, autoAck);
	}

	@Override
	public ConsumerClient getDatabaseWorker() throws IOException, TimeoutException {
		return new RbConsumerClient(host, Queue.DATABASE_QUEUE, autoAck);
	}

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}
}
