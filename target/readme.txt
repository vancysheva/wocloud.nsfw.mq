		//消息队列客户端调用方法说明
		
		MQClient client = MQUtil.getMQClient();
		ProducerClient producerClient = client.getProducerClient();
		producerClient.sendTupu("");
		producerClient.sendDownload("");
		producerClient.sendDatabase("");
		producerClient.sendThumbnail("");
		
		final ConsumerClient consumerClient = client.getTupuWorker();
		consumerClient.bind(new RbConsumeHandler() {
			@Override
			public void handle(String msg, long deliveryTag) {
				System.out.println(msg);
				try {
					// 调用下面方法告诉消息服务器消息处理完毕（消息服务器会将该消息从队列中删除）
					consumerClient.basicAck(deliveryTag);
				} catch (AckMessageFailedException e) {
					e.printStackTrace();
				}
			}
		});