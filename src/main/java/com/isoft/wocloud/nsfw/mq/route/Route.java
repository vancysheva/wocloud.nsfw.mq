package com.isoft.wocloud.nsfw.mq.route;

import com.isoft.wocloud.nsfw.mq.queue.Queue;

/**
 * 路由规则
 * @author vancysheva
 *
 */
public enum Route {

	DOWNLOAD_KEY, THUMBNAIL_KEY, TUPU_KEY, DATABASE_KEY, UNDEFINED_KEY;
	
	public static Route getRoute(Queue queue) {
		switch (queue) {
		case DOWNLOAD_QUEUE:
			return DOWNLOAD_KEY;
		case THUMBNAIL_QUEUE:
			return THUMBNAIL_KEY;
		case TUPU_QUEUE:
			return TUPU_KEY;
		case DATABASE_QUEUE:
			return DATABASE_KEY;
		default:
			return UNDEFINED_KEY;
		}
	}
	
	public Queue getQueue() {
		switch (this) {
		case DOWNLOAD_KEY:
			return Queue.DOWNLOAD_QUEUE;
		case THUMBNAIL_KEY:
			return Queue.THUMBNAIL_QUEUE;
		case TUPU_KEY:
			return Queue.TUPU_QUEUE;
		case DATABASE_KEY:
			return Queue.DATABASE_QUEUE;
		default:
			return Queue.NSFW_QUEUE;
		}
	}
}
