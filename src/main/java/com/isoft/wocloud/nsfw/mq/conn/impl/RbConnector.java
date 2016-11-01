package com.isoft.wocloud.nsfw.mq.conn.impl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.isoft.wocloud.nsfw.mq.conn.Connector;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RbConnector implements Connector {
	
	private Connection conn;
	private Channel channel;
	private Host host;
	
	/**
	 * constructor
	 * @param host
	 * @param queue
	 * @param durable
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public RbConnector(Host host) throws IOException, TimeoutException {
		this.host = host;
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(host.getIp());
		factory.setVirtualHost(host.getVhost());
		factory.setUsername(host.getUsername());
		factory.setPassword(host.getPassword());
		factory.setAutomaticRecoveryEnabled(true);
		factory.setNetworkRecoveryInterval(10000);
		conn = factory.newConnection();
		channel = conn.createChannel();

		System.out.println("已连接到[" + host.getIp() + "]上的[" + host.getVhost() + "]虚拟主机");
	}
	
	@Override
	public void close() throws IOException, TimeoutException {
		this.channel.close();
		this.conn.close();
		System.out.println("已关闭[" + host.getIp() + "]上的[" + host.getVhost() + "]虚拟主机的连接");
	}

	public Channel getChannel() {
		return this.channel;
	}

	public Host getHost() {
		return host;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}
