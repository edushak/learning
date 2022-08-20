package com.learning.basics

import spock.lang.Specification

class RoomSpec extends Specification {

    def "area and volume of the room"() {
        when:
        List<Window> windows = [
            new Window(36, 25, Window.Material.Plastic),
            new Window(24, 25, Window.Material.Plastic),
            new Window(24, 25, Window.Material.Wood)
        ]
        def room1 = new Room("Yellow", 6.2, 4.5, 2.8, 2, 3, windows)

        then:
        assert room1.getArea() == 27.90
        assert room1.getVolume() == 78.120

        when:
        def room = new Room("Yellow", 100.0, 100.0, 120.0, 3, 1, windows)
        then:
        room.getNumberOfClosets() == 1

        when:
        List<Window> searchResult = room.findWindows(Window.Material.Plastic)
        then:
        searchResult.size() == 2
    }
}