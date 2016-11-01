package com.isoft.wocloud.nsfw.mq.handle;

import com.rabbitmq.client.Consumer;

/**
 * 消息处理器，用于接受消息服务器的消息，进行业务处理
 * @author vancysheva
 *
 */
public interface ConsumeHandler extends Consumer {

	/**
	 * 处理具体信息的方法
	 * @param msg
	 */
	public void handle(String msg, long deliveryTag);
}
