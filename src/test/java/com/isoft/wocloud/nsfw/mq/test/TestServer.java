package com.isoft.wocloud.nsfw.mq.test;

import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.server.MQServer;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestServer {

	public static void main(String[] args) {
		Host host = MQConfig.host;
		if (args.length > 0) {
			host.setIp(args[0].trim());
		} 
		MQServer server = MQUtil.getMQServer();
		MQUtil.init(server, host);
	}

}
