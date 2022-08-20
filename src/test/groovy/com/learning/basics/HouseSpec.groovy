package com.learning.basics

import spock.lang.Shared
import spock.lang.Specification

class HouseSpec extends Specification {

    @Shared List<Room> rooms = [
            new Room("white", 3.5, 6.2, 2.7,0, 1, [
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Plastic),
            ]),
            new Room("white", 3.5, 6.2, 2.7,0, 1, [
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Wood)
            ]),
            // basement
            new Room("white", 3.5, 6.2, 2.7,0, 1, [
                    new Window(36.0, 48.0, Window.Material.Metal),
                    new Window(36.0, 48.0, Window.Material.Metal)
            ]),
    ];

    def "HouseArea"() {
        when:
        def house1 = new House(
                House.Material.Blocks, "540 Codding Road", rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")
        then:
        house1.rooms.size() == 3
        house1.getArea() == 288
        house1.getHouseMaterial() == House.Material.Blocks
    }

    def "findRooms"() {
        when:
        def house2 = new House(
                House.Material.Bricks, "106 Kelecka", rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")

        List<Window> foundWindows = house2.findRooms(3)
        then:
        foundWindows != null
        foundWindows.size() == 3
//        when:
//        List<Room> searchResult = rooms.getRoomsWithMinNumberOfWindows(1)
//        then:
//        searchResult.amount() == 1
    }
}
