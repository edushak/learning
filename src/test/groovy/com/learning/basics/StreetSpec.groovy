package com.learning.basics

import spock.lang.Shared
import spock.lang.Specification

class StreetSpec extends Specification {

    @Shared Street street1, street2, street3, street4

    void setupSpec() {
        List<Window> windows1 = [
                new Window(36.0, 25.0, Window.Material.Plastic, "white"),
                new Window(24.0, 25.0, Window.Material.Plastic, "white"),
                new Window(24.0, 25.0, Window.Material.Wood)
        ]
        def room1 = new Room("Yellow", 6.2, 4.5, 2.8, 2 /*3*/, windows1, Room.RoomType.Family)

        List<Window> windows2 = [
                new Window(36.0, 25.0, Window.Material.Plastic, "white"),
                new Window(24.0, 25.0, Window.Material.Plastic, "white"),
                new Window(24.0, 25.0, Window.Material.Wood)
        ]
        def room2 = new Room("Yellow", 6.2, 4.5, 2.8, 2 /*3*/, windows2, Room.RoomType.Living)

        List<House> houses = [
                new House(House.Material.Bricks, "540 Codding Road", [room1, room2], 3, 2, "30 years tar", "Black", 18.0, 16.0, 20.0, "under construction"),
                new House(House.Material.Blocks, "544 Codding Road", [room1, room2], 3, 1, "30 years tar", "Black", 18.0, 20.0, 18.0, "on sale")
        ]

        street1 = new Street ("Codding Road", houses, 2 /* lines */, 900 /* feet */, "dead end street", '07090', [street3, street4])
        street2 = new Street ("New Road", houses, 2, 900, "newly built road", '07090', [street3, street4])
        street3 = new Street ("First Street", houses, 2, 800, "", '07090', [street1, street2])
        street4 = new Street ("Central Ave", houses, 4, 1800, "", '07091', [street1, street2, street3])
    }

    def isConnectedTo() {
        expect:
        street3.isConnectedTo("Codding Road") == true
        street3.isConnectedTo("Central Ave") == false
    }

    def "getNumberOfHouses"() {
        expect:
        street1.getNumberOfHouses() == 2
    }

    def "findHousesInStatus"() {
        when:
        List <House> searchResult = street1.findHousesInStatus("on sale")
        then:
        searchResult.size() == 1
    }





//    def "findHousesInStatus" () {
//        when:
//        List<House> houses = [
//                new House("320 Central Ave", 4, 2, 1, null, "15 years tar", "Brown", 15, 14, 20),
//                new House("324 Central Ave", 5, 2, 3, null, "10 years tar", "Brown", 16, 14, 19, 'on sale')
//        ]
//        def street = new Street("Central Ave", houses, 4, 12000, "dead end street")
//
//        then:
//        street.getNumberOfHouses() == 2
//
//        when:
//        List <House> searchResult = street.findHousesInStatus("on sale")
//        then:
//        searchResult.size() == 1
//    }
}

/*
    def "getArea" () {
        when:
        def street1 = new Street("New Road", [], 2, 900, "newly built road")
        then:
        assert street1.getArea() == 1800
    }
*/
