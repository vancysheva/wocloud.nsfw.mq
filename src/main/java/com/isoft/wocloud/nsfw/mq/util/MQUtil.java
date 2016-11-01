package com.isoft.wocloud.nsfw.mq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.impl.RbMQClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.exception.BuildeFailedException;
import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.queue.Queue;
import com.isoft.wocloud.nsfw.mq.route.Route;
import com.isoft.wocloud.nsfw.mq.server.Builder;
import com.isoft.wocloud.nsfw.mq.server.MQServer;
import com.isoft.wocloud.nsfw.mq.server.impl.RbMQServer;

/**
 * 消息队列工具类
 * @author vancysheva
 *
 */
public class MQUtil {
	
	/**
	 * 获取消息队列客户端
	 * @return
	 */
	public static MQClient getMQClient() {
		RbMQClient client = new RbMQClient(MQConfig.host);
		return client;
	}
	
	/**
	 * 获取指定消息服务器的消息队列客户端
	 * @param host
	 * @return
	 */
	public static MQClient getMQClient(Host host) {
		RbMQClient client = new RbMQClient(host);
		return client;
	}
	
	public static MQServer getMQServer() {
		return new RbMQServer();
	}
	
	/**
	 * 建立队列、路由器、绑定路由规则
	 * @param server
	 * @param host
	 */
	public static void init(MQServer server, Host host) {
		boolean durable = true;
		Exchange exchange = MQConfig.exchange;
		System.out.println("消息服务器开始初始化...");
		try {
			server.connect(host);
			// 建立队列
			for (Queue queue : Queue.values()) {
				Builder queueBuilder = server.getQueueBuilder(queue, durable);
					try {
						queueBuilder.build();
					} catch (BuildeFailedException e) {
						e.printStackTrace();
					}
			}
			System.out.println("队列建立完毕！");
			
			// 建立路由器
			Builder exchagneBuilder = server.getExchangeBuilder(exchange, durable);
			try {
				exchagneBuilder.build();
			} catch (BuildeFailedException e) {
				e.printStackTrace();
			}
			System.out.println("路由器建立完毕！");
			
			for (Queue queue : Queue.values()) {
				// 绑定路由规则
				server.bind(queue, exchange, Route.getRoute(queue));
			}
			System.out.println("绑定路由规则完毕！");
			
			server.closed();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println("消息服务器初始化完毕!...");
	}
}
