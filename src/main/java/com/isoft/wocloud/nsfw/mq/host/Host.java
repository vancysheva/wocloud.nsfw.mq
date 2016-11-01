package com.isoft.wocloud.nsfw.mq.host;

/**
 * 消息服务器的主机信息
 * @author vancysheva
 *
 */
public class Host {

	private String ip;
	private int port;
	private String vhost;
	private String username;
	private String password;
	
	public Host(String ip, int port, String vhost, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.vhost = vhost;
		this.username = username;
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getVhost() {
		return vhost;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setVhost(String vhost) {
		this.vhost = vhost;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
