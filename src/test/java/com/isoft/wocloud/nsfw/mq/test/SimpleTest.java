package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.handle.ConsumeHandler;
import com.isoft.wocloud.nsfw.mq.handle.impl.RbConsumeHandler;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

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
		MQClient client = MQUtil.getMQClient(host);
//		MQUtil.init(MQUtil.getMQServer(), host, MessageModule.SIMPLE);
//		
//		ProducerClient producerClient = client.getProducerClient();
//		producerClient.sendDatabase(Queue.DATABASE_QUEUE.name());
//		producerClient.sendDownload(Queue.DOWNLOAD_QUEUE.name());
//		producerClient.sendTupu(Queue.TUPU_QUEUE.name());
//		producerClient.sendThumbnail(Queue.THUMBNAIL_QUEUE.name());
//		
//		producerClient.close();
		
		ConsumerClient databaseConsumer = client.getDatabaseWorker();
		ConsumerClient downloadConsumer = client.getDownloadWorker();
		ConsumerClient tupuConsumer = client.getTupuWorker();
		ConsumerClient thumbnailConsumer = client.getThumbnailWorker();
		
		ConsumeHandler consumer = new RbConsumeHandler() {
			public void handle(String msg, long deliveryTag) {
				System.out.println(msg);
			}
		};
		databaseConsumer.bind(consumer);
		downloadConsumer.bind(consumer);
		tupuConsumer.bind(consumer);
		thumbnailConsumer.bind(consumer);
	}
}

