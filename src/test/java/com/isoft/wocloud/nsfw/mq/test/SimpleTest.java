package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.conn.impl.RbConnector;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.route.Route;
import com.rabbitmq.client.Channel;

/**
 * 简单消息模型测试
 * @author vancysheva
 *
 */
public class SimpleTest {
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Host host = MQConfig.host;
		host.setIp("10.111.0.4");
		host.setUsername("nsfw");
		host.setPassword("nsfw");
		
		RbConnector rbConnector = new RbConnector(host);
		
		Channel channel1 = rbConnector.getConn().createChannel();
		Channel channel2 = rbConnector.getConn().createChannel();
		Channel channel3 = rbConnector.getConn().createChannel();
		Channel channel4 = rbConnector.getConn().createChannel();
		
		SimpleTest.sendMessage(channel1, Queue.DOWNLOAD_QUEUE);
		SimpleTest.sendMessage(channel2, Queue.THUMBNAIL_QUEUE);
		SimpleTest.sendMessage(channel3, Queue.TUPU_QUEUE);
		SimpleTest.sendMessage(channel4, Queue.DATABASE_QUEUE);
		
		rbConnector.close();
	}
	
	public static void sendMessage(Channel channel, Queue queue) throws IOException, TimeoutException {
		channel.basicPublish("", "", null, queue.name().getBytes("UTF-8"));
	}
	
	public static void build (Channel channel, Queue queue) throws IOException {
		channel.queueDeclare(queue.name(), false, false, false, null);
	}
}

