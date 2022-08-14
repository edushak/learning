package com.learning.basics

import spock.lang.Specification

class RoomSpec extends Specification {

    def "area and volume of the room"() {
        when:
        def room1 = new Room("Yellow", 6.2, 4.5, 2.8, 2, 3)

        then:
        assert room1.getArea() == 27.90
        assert room1.getVolume() == 78.120
    }
}

