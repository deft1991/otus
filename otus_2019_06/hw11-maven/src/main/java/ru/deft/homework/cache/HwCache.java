package ru.deft.homework.cache;

/*
 * Created by sgolitsyn on 10/7/19
 */
public interface HwCache<K, V> {

    void put(K key, V value);

    void remove(K key);

    V get(K key);

    void addListener(HwListener listener);

    void removeListener(HwListener listener);
}
