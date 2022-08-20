package com.learning.basics

import spock.lang.Specification

class TownSpec extends Specification {

    def "Create a town"() {
        given:
        List<Window> windows = [
            new Window(36.0, 25.0, Window.Material.Plastic),
            new Window(24.0, 25.0, Window.Material.Plastic),
            new Window(24.0, 25.0, Window.Material.Wood)
        ]
        List rooms1 = [
            new Room("Yellow", 6.2, 4.5, 2.8, 2, 3, windows),
            new Room("Yellow", 100.0, 100.0, 120.0, 3, 1, windows)
        ]
        List rooms2 = [
            new Room("Yellow", 6.2, 4.5, 2.8, 2, 3, windows),
            new Room("Yellow", 12.2, 16.5, 9.0, 2, 3, windows),
            new Room("Yellow", 11.0, 14.0, 10.0, 3, 1, windows),
            new Room("Yellow", 12.0, 11.0, 14.0, 3, 1, windows)
        ]
        List rooms3 = [
            new Room("Yellow", 12.2, 16.5, 9.0, 2, 3, windows),
            new Room("Yellow", 11.0, 14.0, 10.0, 3, 1, windows),
            new Room("Yellow", 12.0, 11.0, 14.0, 3, 1, windows)
        ]
        List<House> CoddingRoadHouses = [
            new House(House.Material.Wood, "540 Codding Road",   rooms1, 3, 2, "30 years tar",     "Black",            18, 16, 20),
            new House(House.Material.Bricks, "544 Codding Road", rooms2, 3, 2, "Rubber",           "Red",              18, 20, 15, 'on sale'),
            new House(House.Material.Blocks, "548 Codding Road", rooms3, 1, 1, null /* unknown */, null /* unknown */, 18, 20, 18, 'on sale')
        ]
        List<House> CentralAveHouses = []

        List<Street> streets = [
            new Street("Codding Road", CoddingRoadHouses, 2, 900, "dead end street"),
            new Street("Central Ave", CentralAveHouses, 2, 900, "dead end street")
        ]

        when:
        def town = new Town("Westfield", "John Smith", streets)

        then:
        town.getNumberOfStreets() == 2

        when:
        List<House> searchResult = town.getHousesWithMinNumberOfRooms(4, 'on sale')
        then:
        searchResult.size() == 1
        searchResult.get(0).address == "544 Codding Road"
    }
}
