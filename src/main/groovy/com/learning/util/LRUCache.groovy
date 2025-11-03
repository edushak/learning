package com.learning.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

/**
 * Naive LRU implementation
 * @param <T>
 */
class LRUCache<T> {
    int cacheLimit
    Map<String,T> cache
    Deque<String> indTracker // store keys in here

    LRUCache(int cacheLimit) {
        this.cacheLimit = cacheLimit
        this.cache = new ConcurrentHashMap<String,T>(new LinkedHashMap());
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
        cache.inspect()
    }

    boolean equals(Map<String,T> compareTo) {
        return cache == compareTo
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
