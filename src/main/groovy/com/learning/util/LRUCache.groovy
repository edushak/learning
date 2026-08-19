package com.learning.util

import groovy.transform.CompileStatic

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

/**
 * Naive LRU implementation
 * @param <T>
 */
@CompileStatic
class LRUCache<T> {
    int cacheLimit
    Map<String,T> cache
    Deque<String> indTracker // store keys in here

    LRUCache(int cacheLimit) {
        this.cacheLimit = cacheLimit
        this.cache = new ConcurrentHashMap<String,T>([:]); // new LinkedHashMap()
        this.indTracker = new ConcurrentLinkedDeque<String>();
    }

    void put(String key, T value) {
        synchronized (key) {
            if (cache.size() >= cacheLimit) {
                // remove to make room for a new element
                String keyToRemove = indTracker.removeFirst()
                cache.remove(keyToRemove)
            }
            indTracker.addLast(key)
            cache.put(key, value)
        }
    }

    T get(String key) {
        synchronized (key) {
            indTracker.remove(key)
            indTracker.addLast(key)
            cache.get(key)
        }
    }

    int size() {
        return cache.size()
    }

    String toString() {
        return cache.inspect()
    }

    @Override
    boolean equals(Object other) {
        if (other instanceof Map) {
            return cache == other
        }
        if (other instanceof LRUCache) {
            return cache == ((LRUCache) other).cache
        }
        return false
    }

    @Override
    int hashCode() {
        return cache != null ? cache.hashCode() : 0
    }
}

/*
class ValueWrapper {
    T value
    ValueWrapper(T value) {
        this.value = value
    }
}
*/
