package ru.otus.listeners.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.listeners.Listener;

@Component
public class LogEventListener<K, V> implements Listener<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(LogEventListener.class);

	@Override
	public void notify(final K key, final V value, final String action) {
		logger.info("key:{}, value:{}, action: {}", key, value, action);
	}
}
