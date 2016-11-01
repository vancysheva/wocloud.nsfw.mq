package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestTupuSend {

	public static void main(String[] args) throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient();
		ProducerClient producerClient = client.getProducerClient();
		producerClient.sendThumbnail("1");
		producerClient.sendDatabase("1");
		producerClient.sendTupu("1");
		producerClient.sendDownload("1");
		producerClient.close();
	}

}
