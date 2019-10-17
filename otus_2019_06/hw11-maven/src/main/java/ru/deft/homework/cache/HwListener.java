package ru.deft.homework.cache;

/*
 * Created by sgolitsyn on 10/7/19
 */
public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
