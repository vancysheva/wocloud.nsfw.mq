package com.isoft.wocloud.nsfw.mq.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.client.impl.RbProducerClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.server.MQServer;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;
import com.rabbitmq.client.Channel;

public class LogicTest {

	/**
	 * 测试获取的mq客户端是来自同一个实例
	 * 测试结果：每次获得的mqclient是新的实例对象
	 */
	@Test public void testSingleMQClient() {
		MQClient client1 = MQUtil.getMQClient();
		MQClient client2 = MQUtil.getMQClient();
		
		assertNotEquals(client1, client2);
	}

	/**
	 * 测试获取的producer客户端是否来自同一实例
	 * 测试结果：同一个meclient返回的producer client 来自同一实例
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@Test public void testSingleProducerClient() throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient();
		ProducerClient client1 = client.getProducerClient();
		ProducerClient client2 = client.getProducerClient();
	
		assertEquals(client1, client2);
	}
	
	/**
	 * 测试不同的mqclient返回的producer client是否是同一个实例
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@Test public void testSingleProduceFromDiffClient() throws IOException, TimeoutException {
		ProducerClient producerClient1 = MQUtil.getMQClient().getProducerClient();
		ProducerClient producerClient2 = MQUtil.getMQClient().getProducerClient();
		
		assertEquals(producerClient1, producerClient2);
	}
	
	/**
	 * 测试从同一个mqclient中获取不同的producer client中的channel是否是同一个实例
	 * 测试结果：是同一个实例
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@Test public void testSingleChannel() throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient();
		ProducerClient producer = client.getProducerClient();
		ProducerClient producer2 = client.getProducerClient();
		
		RbProducerClient rbClient1 = (RbProducerClient)producer;
		RbProducerClient rbClient2 = (RbProducerClient)producer2;
		
		Channel channel1 = rbClient1.getConnector().getChannel();
		Channel channel2 = rbClient2.getConnector().getChannel();
		
		assertEquals(channel1, channel2);
	}
	
	/**
	 * 测试声明一个消息队列和路由器
	 * 测试结果：相同名称的消息队列和路由器可以用相同的参数重复定义，
	 * 不允许使用不同参数重复定义，否则会报出异常
	 * @throws IOException 
	 * @throws TimeoutException 
	 */
	@Test public void testDeclareQueueAndExchange() throws IOException, TimeoutException {
		RbConnector connector = new RbConnector(MQConfig.host);
		Channel channel = connector.getChannel();
		channel.exchangeDeclare(MQConfig.exchange.getName(), MQConfig.exchange.getType());
		channel.exchangeDeclare(MQConfig.exchange.getName(), MQConfig.exchange.getType());
		channel.queueDeclare(Queue.DOWNLOAD_QUEUE.name(), true, false, false, null);
		channel.queueDeclare(Queue.DOWNLOAD_QUEUE.name(), true, false, false, null);
	}
	
	/**
	 * 测试mqserver类创建消息队列、路由器、绑定关系
	 * 结果：在消息服务器中正确的创建消息队列和路由器
	 */
	@Test public void testMQServer() {
		MQServer server = MQUtil.getMQServer();
		MQUtil.init(server, MQConfig.host);
	}
	
	/**
	 * 测试获取消费者是否是同一个实例
	 * 结果：每次调用方法返回新实例
	 * @throws IOException
	 * @throws TimeoutException
	 */
	@Test public void testMQClient() throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient();
		ConsumerClient consumerClient1 = client.getDatabaseWorker();
		ConsumerClient consumerClient2 = client.getDownloadWorker();
		
		assertNotEquals(consumerClient1, consumerClient2);
		
	}
	
	/**
	 * 测试配置类
	 */
	@Test public void testMQConfig() {
		Host host = MQConfig.host;
		Exchange exchange = MQConfig.exchange;
		
		assertEquals("192.168.202.95", host.getIp());
		assertEquals(5672, host.getPort());
		assertEquals("nsfw", host.getVhost());
		assertEquals("root", host.getUsername());
		assertEquals("admin", host.getPassword());
		
		assertEquals("direct", exchange.getType());
		assertEquals("nsfw-exchange", exchange.getName());
		assertEquals(true, MQConfig.enableConnRecovery);
		assertEquals(10000, MQConfig.recoveryInterval);
	}
	
	/**
	 * 测试队列中消息数量
	 * 测试结果：符合预期结果
	 * @throws IOException
	 * @throws TimeoutException
	 */
	@Test public void testMessageCount() throws IOException, TimeoutException {
		Host host = MQConfig.host;
		host.setIp("10.111.0.4");
		MQClient client = MQUtil.getMQClient();
		ProducerClient producerClient = client.getProducerClient();
		long proCount = producerClient.getMessageCount();
		long downloadCount = client.getDownloadWorker().getMessageCount();
		long databaseCount = client.getDatabaseWorker().getMessageCount();
		long tupuCount = client.getTupuWorker().getMessageCount();
		long thumbnailCount = client.getThumbnailWorker().getMessageCount();
		
		assertEquals(proCount, 73);
		assertEquals(downloadCount, 18);
		assertEquals(databaseCount, 18);
		assertEquals(tupuCount, 19);
		assertEquals(thumbnailCount, 18);
	}
}
