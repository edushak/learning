package com.learning.util

import spock.lang.Specification

class LRUCacheSpec extends Specification {

    def "putAndGet"() {
        given:
        def cache = new LRUCache(3)

        when:
        cache.put("one", 1)
        cache.put("two", 2)
        cache.put("three", 3)
        then:
        cache.size() == 3
        cache == [
            "one"  : 1,
            "two"  : 2,
            "three": 3
        ]

        when:
        cache.put("four", 4)
        then:
        cache.size() == 3
        cache == [
            "two"  : 2,
            "three": 3,
            "four" : 4
        ]

        when:
        cache.get("two")
        cache.put("five", 5)
        then:
        cache == [
            "two"  : 2,
            "four" : 4,
            "five" : 5
        ]
    }
}
