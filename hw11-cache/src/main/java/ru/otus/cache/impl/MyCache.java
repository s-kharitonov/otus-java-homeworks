package ru.otus.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwCache;
import ru.otus.cache.HwListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    private final Map<String, V> cache = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        cache.put(String.valueOf(key), value);

        notifyListeners(key, value, "saved in storage");
    }

    @Override
    public void remove(K key) {
        final V value = cache.remove(String.valueOf(key));

        notifyListeners(key, value, "removed from storage");
    }

    @Override
    public V get(K key) {
        final V value = cache.get(String.valueOf(key));

        notifyListeners(key, value, "retrieved from storage");

        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(final K key, final V value, final String action) {
        listeners.forEach((listener) -> notifyListener(listener, key, value, action));
    }

    private void notifyListener(final HwListener<K, V> listener, final K key, final V value, final String action) {
        try {
            listener.notify(key, value, action);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}
