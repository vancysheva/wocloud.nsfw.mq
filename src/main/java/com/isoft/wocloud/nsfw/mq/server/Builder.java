package com.isoft.wocloud.nsfw.mq.server;

import com.isoft.wocloud.nsfw.mq.exception.BuildeFailedException;

public interface Builder {
	
	public void build() throws BuildeFailedException;
}
