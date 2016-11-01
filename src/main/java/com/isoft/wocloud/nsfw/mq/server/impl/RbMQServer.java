package com.isoft.wocloud.nsfw.mq.server.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.route.Route;
import com.isoft.wocloud.nsfw.mq.server.Builder;
import com.isoft.wocloud.nsfw.mq.server.MQServer;

public class RbMQServer implements MQServer {

	private RbConnector connector;
	
	public RbMQServer() {
	}
	
	@Override
	public Builder getQueueBuilder(Queue queue, boolean durable) {
		return new RbQueueBuilder(connector, queue, durable);
	}

	@Override
	public Builder getExchangeBuilder(Exchange exchange, boolean durable) {
		return new RbExchangeBuilder(connector, exchange, durable);
	}

	@Override
	public void connect(Host host) throws IOException, TimeoutException {
		this.connector = new RbConnector(host);
	}

	@Override
	public void closed() throws IOException, TimeoutException {
		this.connector.close();
	}
	
	@Override
	public void bind(Queue queue, Exchange exchange, Route route) throws IOException {
		this.connector.getChannel().queueBind(queue.name(), exchange.getName(), route.name());
	}
	
	public RbConnector getConnector() {
		return connector;
	}

	public void setConnector(RbConnector connector) {
		this.connector = connector;
	}

	@Override
	public void bind(Queue queue, ConsumerClient consumerClient, Route route) throws IOException {
		System.out.println(this.getClass().getName() + " does not support this method");
	}
}
