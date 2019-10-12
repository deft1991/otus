package ru.deft.homework.cache.impl;

import lombok.extern.java.Log;
import ru.deft.homework.cache.HwCache;
import ru.deft.homework.cache.HwListener;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.logging.Level;

/*
 * Created by sgolitsyn on 10/7/19
 */
@Log
public class MyCache<K, V> implements HwCache<K, V> {
    private Map<K, V> cacheMap = new WeakHashMap<>();
    private List<WeakReference<HwListener>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        executeListeners(key, value, "put");
        cacheMap.put(key, value);
    }

    @Override
    public void remove(K key) {
        executeListeners(key, null, "remove");
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        V v = cacheMap.get(key);
        executeListeners(key, v, "get");
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

    private void executeListeners(K key, V value, String action) {
        listeners.forEach(listener ->{
            try{
                HwListener hwListener = Objects.requireNonNull(listener.get());
                hwListener.notify(key, value, action);
            } catch (Exception e){
                log.log(Level.SEVERE, e.getMessage());
            }
        });
    }
}
