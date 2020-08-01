package ru.otus.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.otus.cache.CacheKeeper;
import ru.otus.listeners.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Component
public class BasicCacheKeeper<K, V> implements CacheKeeper<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(BasicCacheKeeper.class);

	private final List<Listener<K, V>> listeners = new ArrayList<>();
	private final Map<String, V> cache = new WeakHashMap<>();

	@Override
	public void put(final K key, final V value) {
		cache.put(String.valueOf(key), value);

		notifyListeners(key, value, "saved in storage");
	}

	@Override
	public void remove(final K key) {
		final V value = cache.remove(String.valueOf(key));

		notifyListeners(key, value, "removed from storage");
	}

	@Override
	public V get(final K key) {
		final V value = cache.get(String.valueOf(key));

		notifyListeners(key, value, "retrieved from storage");

		return value;
	}

	@Override
	public void addListener(final Listener<K, V> listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(final Listener<K, V> listener) {
		listeners.remove(listener);
	}

	private void notifyListeners(final K key, final V value, final String action) {
		listeners.forEach((listener) -> notifyListener(listener, key, value, action));
	}

	private void notifyListener(final Listener<K, V> listener, final K key, final V value, final String action) {
		try {
			listener.notify(key, value, action);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
