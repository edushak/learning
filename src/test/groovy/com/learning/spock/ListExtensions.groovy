package com.learning.spock

class ListExtensions {
    static avg(List list) {
        list.sum() / list.size()
    }
}
