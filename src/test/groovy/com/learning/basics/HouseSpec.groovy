package com.learning.basics

import spock.lang.Specification

class HouseSpec extends Specification {

    def "HouseArea"() {
        when:
        def house1 = new House("540 Codding Road", 8, 3, null, "30 years tar", "Black", 18, 16, 20)
        then:
        assert house1.getArea() == 288
    }
}
