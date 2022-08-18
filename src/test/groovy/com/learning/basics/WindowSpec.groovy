package com.learning.basics

import spock.lang.Specification

class WindowSpec extends Specification {
    def "GetWindowArea"() {
        when:
        def window = new Window(36, 48, Window.Material.Plastic)
        then:
        window.getWindowArea() == 1728
    }
}
