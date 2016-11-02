package com.isoft.wocloud.nsfw.mq.common;

import java.io.IOException;
import java.util.Properties;

import com.isoft.wocloud.nsfw.mq.exchange.Exchange;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.module.MessageModule;

public class MQConfig {
	
	public static Host host;
	public static Exchange exchange;
	public static boolean enableConnRecovery;
	public static int recoveryInterval;
	public static MessageModule messageModule;
	
	static {
		try {
			Properties prop = new Properties();
			prop.load(MQConfig.class.getResourceAsStream("/config.properties"));
			
			host = configHost(prop);
			exchange = configExchange(prop);
			enableConnRecovery = Boolean.valueOf(prop.getProperty("mq.nsfw.conn.recovery")); 
			recoveryInterval = Integer.parseInt(prop.getProperty("mq.nsfw.conn.interval"));
			messageModule = configMessageModule(prop);
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
	
	private static MessageModule configMessageModule(Properties prop) {
		String module = prop.getProperty("mq.nsfw.message.module");
		for (MessageModule messageModule : MessageModule.values()) {
			if (messageModule.name().equals(module.toUpperCase())) {
				return messageModule;
			}
		}
		return MessageModule.SIMPLE;
	}
}
