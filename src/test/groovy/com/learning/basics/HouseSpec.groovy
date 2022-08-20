package com.learning.basics

import spock.lang.Specification

class HouseSpec extends Specification {

    def "HouseArea"() {
        when:
        List<Room> rooms = [
                new Room("bathroom", "white", 3.5, 6.2, 2.7,
                        0, 1),
                new Room("kitchen", "white",
                        6.6, 7.5, 2.4, 2, 0),
                new Room("living room", "grey", 7.8, 9.1, 3.2, 3,1)
        ]
        def house1 = new House(House.Material.Blocks, "540 Codding Road",
                rooms, 3, "30 years tar", "Black", 18, 16, 20, "on sale")
        then:
        assert house1.getArea() == 288
        house1.getHouseMaterial() == House.Material.Blocks
    }

    def "Rooms"() {
        when:
        List<Room> rooms = [
            new Room("bathroom", "white", 3.5, 6.2, 2.7,
                    0, 1),
            new Room("kitchen", "white",
                    6.6, 7.5, 2.4, 2, 0),
            new Room("living room", "grey", 7.8, 9.1, 3.2, 3,1)
        ]
        then:
        rooms.size() == 3

        when:
        def house = new House(House.Material.Bricks, "106 Kelecka", rooms, 1, "Rubber", "Black", 18, 14, 16, 'on sale')
        List<Window> foundWindows = house.findRooms(3)
        then:
        foundWindows != null
        foundWindows.size() == 2
//        when:
//        List<Room> searchResult = rooms.getRoomsWithMinNumberOfWindows(1)
//        then:
//        searchResult.amount() == 1
    }
}


