package com.isoft.wocloud.nsfw.mq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.client.MQClient;
import com.isoft.wocloud.nsfw.mq.client.ProducerClient;
import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestSendClient {

	public static void main(String[] args) throws IOException, TimeoutException {
		Host host = MQConfig.host;
		host.setIp("10.111.0.4");
		MQClient client = MQUtil.getMQClient();
		final ProducerClient producerClient = client.getProducerClient();
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						String msg = String.valueOf(System.currentTimeMillis());
						producerClient.sendDownload(msg);
						System.out.println("[download: " + msg + "]");
						Thread.sleep((long)(Math.random() * 1000));
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						String msg = String.valueOf(System.currentTimeMillis());
						producerClient.sendThumbnail(msg);
						System.out.println("[thumbnail: " + msg + "]");
						Thread.sleep((long)(Math.random() * 1000));
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						String msg = String.valueOf(System.currentTimeMillis());
						producerClient.sendTupu(msg);
						System.out.println("[tupu: " + msg + "]");
						Thread.sleep((long)(Math.random() * 1000));
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						String msg = String.valueOf(System.currentTimeMillis());
						producerClient.sendDatabase(msg );
						System.out.println("[database: " + msg+ "]");
						Thread.sleep((long)(Math.random() * 1000));
					} catch (Exception e) {
						e.printStackTrace();
					} 
					
				}
			}
		}).start();
	}

}
