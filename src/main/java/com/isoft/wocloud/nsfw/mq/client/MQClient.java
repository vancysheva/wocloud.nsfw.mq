package com.isoft.wocloud.nsfw.mq.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.queue.Queue;

/**
 * 消息队列客户端，用于获得生产者、消费者
 * @author vancysheva
 *
 */
public interface MQClient {
	
	public ProducerClient getProducerClient() throws IOException, TimeoutException;
	
	public ConsumerClient getConsumerClient(Queue queue) throws IOException, TimeoutException;

	// 获取不同类型的消费者
	public ConsumerClient getDownloadWorker() throws IOException, TimeoutException;
	public ConsumerClient getThumbnailWorker() throws IOException, TimeoutException;
	public ConsumerClient getTupuWorker() throws IOException, TimeoutException;
	public ConsumerClient getDatabaseWorker() throws IOException, TimeoutException;
}
