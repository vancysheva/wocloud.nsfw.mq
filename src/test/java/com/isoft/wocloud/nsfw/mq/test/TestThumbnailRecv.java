package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.exception.AckMessageFailedException;
import com.isoft.wocloud.nsfw.mq.handle.impl.RbConsumeHandler;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestThumbnailRecv {

	public static void main(String[] args) throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient();
		final ConsumerClient consumerClient = client.getThumbnailWorker();
		
		consumerClient.bind(new RbConsumeHandler() {
			public void handle(String msg, long deliveryTag) {
				System.out.println("[THUMBNAIL: " + msg + "]");
				try {
					consumerClient.basicAck(deliveryTag);
				} catch (AckMessageFailedException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
