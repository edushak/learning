package com.learning.basics

import spock.lang.Specification

class WindowSpec extends Specification {
    def "GetWindowArea"() {
        when:
        def window = new Window(36, 48, 'PVC', 'White')
        then:
        window.getWindowArea() == 1728
    }
}
