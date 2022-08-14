package com.learning.basics

import spock.lang.Specification

class HouseSpec extends Specification {
    def "HouseArea"() {
        when:
        def house1 = new House(
                5, 2, "bricks", "pink", "wood", "black", 20, 30, 9)

        then:
        assert house1.getArea() == 600
    }
}
