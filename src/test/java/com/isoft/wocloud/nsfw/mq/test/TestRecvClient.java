package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.ConsumerClient;
import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.exception.AckMessageFailedException;
import com.isoft.wocloud.nsfw.mq.handle.impl.RbConsumeHandler;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestRecvClient {

	public static void main(String[] args) throws IOException, TimeoutException {
		MQClient client = MQUtil.getMQClient(MQConfig.host);
		
		for (int i=0; i<2; i++) {
			new Thread(getThread(client.getDownloadWorker(), "download", i)).start();
		}
		
		for (int i=0; i<2; i++) {
			new Thread(getThread(client.getThumbnailWorker(), "thumbnail", i)).start();
		}
		
		for (int i=0; i<2; i++) {
			new Thread(getThread(client.getTupuWorker(), "tupu", i)).start();
		}
		
		for (int i=0; i<2; i++) {
			new Thread(getThread(client.getDatabaseWorker(), "database", i)).start();
		}
	}
	
	public static Runnable getThread(final ConsumerClient consumerClient, final String name, final int i) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("[" + name + " " + i + "]准备就绪...");
					consumerClient.bind(new RbConsumeHandler() {
						public void handle(String msg, long deliveryTag) {
							System.out.println(name + + i + " 处理了 " + msg);
							try {
								Thread.sleep(1000);
								consumerClient.basicAck(deliveryTag);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (AckMessageFailedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}

}
