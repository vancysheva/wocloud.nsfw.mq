package com.isoft.wocloud.nsfw.mq.client;

import java.io.IOException;

import com.isoft.wocloud.nsfw.mq.exception.AckMessageFailedException;
import com.isoft.wocloud.nsfw.mq.handle.ConsumeHandler;

/**
 * 消费者客户端：连接消息服务器，绑定一个处理器到指定队列
 * @author vancysheva
 *
 */
public interface ConsumerClient extends BaseClient {

	/**
	 * 绑定一个处理器用于处理来自于消息服务器的消息
	 * @param handler
	 * @throws IOException 
	 */
	public void bind(ConsumeHandler handler) throws IOException;
	
	/**
	 * 发送消息回执，告诉消息服务器已经处理完成此次接受的消息，消息服务器会从队列中删除这条消息
	 * @param deliveryTag
	 * @throws AckMessageFailedException 
	 */
	public void basicAck(long deliveryTag) throws AckMessageFailedException; 
}
