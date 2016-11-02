package com.isoft.wocloud.nsfw.mq.test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.isoft.wocloud.nsfw.mq.common.MQConfig;
import com.isoft.wocloud.nsfw.mq.host.Host;
import com.isoft.wocloud.nsfw.mq.module.MessageModule;
import com.isoft.wocloud.nsfw.mq.server.MQServer;
import com.isoft.wocloud.nsfw.mq.util.MQUtil;

public class TestServer {
	
	public static boolean isIP(String addr)
	{
		if ("localhost".equals(addr)) {
			return true;
		}
		
		if (addr == null) {
			return false;
		}
		
		if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
		{
			return false;
		}
		/**
		 * 判断IP格式和范围
		 */
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		
		Pattern pat = Pattern.compile(rexp);  
		
		Matcher mat = pat.matcher(addr);  
		
		boolean ipAddress = mat.find();

		return ipAddress;
	}

	public static void main(String[] args) {
		String ip = null;
		int inputCount = 0;
		Scanner in = new Scanner(System.in);
		if (args.length == 0) {
			while (!isIP(ip)) {
				System.out.println("请输入消息服务器的ip地址：\n");
				ip = in.nextLine().trim();
				if (isIP(ip)) {
					break;
				} else {
					inputCount++;
				}
				
				if (inputCount == 3) {
					System.exit(0);
				}
			}
		}
		if (args.length > 0) {
			ip = args[0].trim();
			while (!isIP(ip)) {
				System.out.println("请输入消息服务器的ip地址：\n");
				ip = in.nextLine().trim();
				if (isIP(ip)) {
					break;
				} else {
					inputCount++;
				}
				
				if (inputCount == 3) {
					System.exit(0);
				}
			}
		}
		Host host = MQConfig.host;
		host.setIp(ip);
		MQServer server = MQUtil.getMQServer();
		MQUtil.init(server, host, MessageModule.COMPLEX);
	}

}
