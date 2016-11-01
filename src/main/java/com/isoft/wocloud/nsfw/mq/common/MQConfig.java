package com.isoft.wocloud.nsfw.mq.common;

import java.io.IOException;
import java.util.Properties;

import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;

public class MQConfig {
	
	public static Host host;
	public static Exchange exchange;
	public static boolean enableConnRecovery;
	public static int recoveryInterval;
	
	static {
		try {
			Properties prop = new Properties();
			prop.load(MQConfig.class.getResourceAsStream("/config.properties"));
			
			host = configHost(prop);
			exchange = configExchange(prop);
			enableConnRecovery = Boolean.valueOf(prop.getProperty("mq.nsfw.conn.recovery")); 
			recoveryInterval = Integer.parseInt(prop.getProperty("mq.nsfw.conn.interval"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Host configHost(Properties prop) {
		String ip = prop.getProperty("mq.nsfw.host.ip");
		int port = Integer.parseInt(prop.getProperty("mq.nsfw.host.port"));
		String vhost = prop.getProperty("mq.nsfw.host.vhost");
		String username = prop.getProperty("mq.nsfw.host.username");
		String password = prop.getProperty("mq.nsfw.host.password");
		Host host = new Host(ip, port, vhost, username, password);
		return host;
	}
	
	private static Exchange configExchange(Properties prop) {
		String exchangeType = prop.getProperty("mq.nsfw.exchange.type");
		String exchangeName = prop.getProperty("mq.nsfw.exchange.name");
		Exchange exchange = new Exchange(exchangeType, exchangeName);
		return exchange;
	}
}
