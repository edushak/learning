package com.learning.basics

import spock.lang.Specification

class WindowSpec extends Specification {
    def "GetWindowArea"() {
        when:
        def window = new Window(36.0, 48.0, Window.Material.Plastic)
        then:
        window.getWindowArea() == 1728
    }
}
