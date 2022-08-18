package com.learning.basics

import spock.lang.Specification

class TownSpec extends Specification {

    def "Create a town"() {
        when:
        List<House> CentralAveHouses = []
        List<House> CoddingRoadHouses = [
            new House("540 Codding Road", 8, 3, null, "30 years tar", "Black", 18, 16, 20),
            new House("544 Codding Road", 9, 3, null, "30 years tar", "Black", 18, 20, 18, 'on sale'),
            new House("548 Codding Road", 3, 1, null, "30 years tar", "Black", 18, 20, 18, 'on sale')
        ]
        List<Street> streets = [
            new Street("Codding Road", CoddingRoadHouses, 2, 900, "dead end street"),
            new Street("Central Ave", CentralAveHouses, 2, 900, "dead end street")
        ]
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
