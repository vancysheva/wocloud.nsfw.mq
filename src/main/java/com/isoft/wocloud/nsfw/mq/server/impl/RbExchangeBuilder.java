package com.isoft.wocloud.nsfw.mq.server.impl;

import java.io.IOException;

import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exception.BuildeFailedException;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.server.Builder;

public class RbExchangeBuilder implements Builder {

	private Exchange exchange;
	private boolean durable;
	private RbConnector connector;
	
	public RbExchangeBuilder(RbConnector connector, Exchange exchange, boolean durable) {
		this.exchange = exchange;
		this.durable = durable;
		this.connector = connector;
	}
	
	@Override
	public void build() throws BuildeFailedException {
		try {
			this.connector.getChannel().exchangeDeclare(exchange.getName(), exchange.getType(), durable);
		} catch (IOException e) {
			throw new BuildeFailedException("创建" + exchange.getType() + "类型的路由器[" + exchange.getName() + "]失败！", e);
		}
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
}
