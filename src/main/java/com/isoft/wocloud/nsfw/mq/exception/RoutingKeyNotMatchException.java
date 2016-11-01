package com.isoft.wocloud.nsfw.mq.exception;

/**
 * 路由规则无法匹配异常
 * @author vancysheva
 *
 */
public class RoutingKeyNotMatchException extends Exception {

	private static final long serialVersionUID = 1L;

	public RoutingKeyNotMatchException(String msg) {
		super(msg);
	}
}
