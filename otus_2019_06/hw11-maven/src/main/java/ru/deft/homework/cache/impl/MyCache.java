package ru.deft.homework.cache.impl;

import ru.deft.homework.cache.HwCache;
import ru.deft.homework.cache.HwListener;

import java.lang.ref.WeakReference;
import java.util.*;

/*
 * Created by sgolitsyn on 10/7/19
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> cacheMap = new WeakHashMap<>();
    private List<WeakReference<HwListener>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach(listener -> Objects.requireNonNull(listener.get()).notify(key, value, "get"));
        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        listeners.forEach(listener -> Objects.requireNonNull(listener.get()).notify(key, null, "get"));
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        V v = cacheMap.get(key);
        listeners.forEach(listener -> Objects.requireNonNull(listener.get()).notify(key, v, "get"));
        return v;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addListener(HwListener listener) {
        listeners.add(new WeakReference(listener));
    }

    @Override
    public void removeListener(HwListener listener) {
        listeners.remove(listener);
    }

    public boolean isEmpty(){
        return cacheMap.isEmpty();
    }
}
