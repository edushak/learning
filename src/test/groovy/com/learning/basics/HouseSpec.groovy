package com.learning.basics

import spock.lang.Shared
import spock.lang.Specification

class HouseSpec extends Specification {

    @Shared List<Room> rooms = [
            new Room("white", 3.5, 6.2, 2.7, 0, [
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Plastic)
            ], Room.RoomType.Living),
            new Room("white", 3.5, 6.2, 2.7, 1, [
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Plastic),
                    new Window(36.0, 48.0, Window.Material.Wood)
            ], Room.RoomType.Family),
            // basement
            new Room("white", 3.5, 6.2, 2.7, 1, [
                    new Window(36.0, 48.0, Window.Material.Metal),
                    new Window(36.0, 48.0, Window.Material.Metal)
            ], Room.RoomType.Entertainment),
    ];

    def "HouseArea"() {
        when:
        def house1 = new House(House.Material.Blocks, "540 Codding Road", 1999, rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")
        then:
        house1.rooms.size() == 3
        house1.getArea() == 288
        house1.getHouseMaterial() == House.Material.Blocks
    }

    def "findRooms"() {
        when:
        def house2 = new House(
                House.Material.Bricks, "106 Kelecka", 1986, rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")

        List<Window> foundWindows = house2.findRooms(3)
        then:
        foundWindows.size() == 1

        when:
        List<Room> searchResult = house2.findRooms(2)
        then:
        searchResult.size() == 3

        when:
        searchResult = house2.findRooms(1)
        then:
        searchResult.size() == 3
    }

    def numberOfRoomsWithClosets() {
        given:
        def house3 = new House(
                House.Material.Bricks, "106 Kelecka", 1986, rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")

        when:
        List<Room> getNumberOfRoomsWithClosets = house3.numberOfRoomsWithClosets(1)
        then:
        house3.numberOfRooms == 3 && getNumberOfRoomsWithClosets.size() == 2
    }

    def "findRooms ByType"() {
        when:
        def house3 = new House( House.Material.Bricks, "106 Kelecka", 1999, rooms, 3, 2,
                "30 years tar", "Black", 18.0, 16.0, 20.0, "on sale")

        List<Room> searchResult = house3.getRoomsByType(Room.RoomType.Family)
        then:
        searchResult.size() == 1
//        searchResult.get() == Room.RoomType.Family
    }
}
