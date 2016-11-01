package com.isoft.wocloud.nsfw.mq.server;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;

public interface MQServer extends Binder {

	public Builder getQueueBuilder(Queue queue, boolean durable);
	public Builder getExchangeBuilder(Exchange exchange, boolean durable);
	public void connect(Host host) throws IOException, TimeoutException;
	public void closed() throws IOException, TimeoutException;
}
