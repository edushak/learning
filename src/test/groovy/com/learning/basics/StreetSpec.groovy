package com.learning.basics

import spock.lang.Specification

public class StreetSpec extends Specification {

    def "Name" () {
        when:
        def Street1 = new Street(30, 15, 190, 80, "School")

        then:
        assert Street1.getArea() == 2850
    }
}
