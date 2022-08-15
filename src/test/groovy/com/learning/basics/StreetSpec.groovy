package com.learning.basics

import spock.lang.Specification

public class StreetSpec extends Specification {

    def "Name" () {
        when:
        def street1 = new Street("New Road", [], 2, 900, "newly built road")
        then:
        assert street1.getArea() == 1800
    }
}
