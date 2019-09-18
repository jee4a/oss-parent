package com.jee4a.oss.framework.io.cache.redis;

import redis.clients.jedis.JedisPubSub;

public class RedisPubSub extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {
		
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		
	}

}
