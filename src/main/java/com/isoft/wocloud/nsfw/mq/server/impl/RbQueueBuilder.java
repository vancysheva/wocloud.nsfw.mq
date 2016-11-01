package com.isoft.wocloud.nsfw.mq.server.impl;

import java.io.IOException;

import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exception.BuildeFailedException;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.server.Builder;

public class RbQueueBuilder implements Builder {

	private Queue queue;
	private boolean durable;
	private boolean exclusive = false;
	private boolean autoDelete = false;
	private RbConnector connector;
	
	public RbQueueBuilder(RbConnector connector, Queue queue, boolean durable) {
		this.queue = queue;
		this.durable = durable;
		this.connector = connector;
	}
	
	@Override
	public void build() throws BuildeFailedException {
		try {
			this.connector.getChannel().queueDeclare(queue.name(), durable, exclusive, autoDelete, null);
		} catch (IOException e) {
			throw new BuildeFailedException("创建消息队列[" + queue.name() + "]失败！", e);
		}
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public boolean isExclusive() {
		return exclusive;
	}

	public void setExclusive(boolean exclusive) {
		this.exclusive = exclusive;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}
}
