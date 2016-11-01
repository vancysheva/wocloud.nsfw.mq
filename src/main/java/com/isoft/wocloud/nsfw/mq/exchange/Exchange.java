package com.isoft.wocloud.nsfw.mq.exchange;

/**
 * 交换机类型
 * @author vancysheva
 *
 */
public class Exchange {
	
	/**
	 * 交换机类型
	 */
	private String type;
	
	/**
	 * 默认的交换机名字
	 */
	private String name;
	
	public Exchange(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
