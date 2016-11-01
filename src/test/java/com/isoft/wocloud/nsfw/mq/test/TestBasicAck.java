package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.exception.AckMessageFailedException;
import com.isoft.wocloud.nsfw.mq.handle.impl.RbConsumeHandler;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestBasicAck {

	public static void main(String[] args) throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient(MQConfig.host);
		
		ProducerClient producerClient = client.getProducerClient();
		producerClient.sendTupu("test");
		producerClient.close();
		final ConsumerClient consumerClient = client.getTupuWorker();
		consumerClient.bind(new RbConsumeHandler() {
			public void handle(String msg, long deliveryTag) {
				System.out.println("[tupu: " + msg + ", " + deliveryTag + "]");
				try {
					consumerClient.basicAck(deliveryTag);
					consumerClient.close();
				} catch (AckMessageFailedException | IOException | TimeoutException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
