package ru.otus.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwListener;

public class LogEventListener<K, V> implements HwListener<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(LogEventListener.class);

	@Override
	public void notify(final K key, final V value, final String action) {
		logger.info("key:{}, value:{}, action: {}", key, value, action);
	}
}
