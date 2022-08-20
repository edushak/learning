package com.learning.basics

import spock.lang.Specification

class StreetSpec extends Specification {

    def "getArea" () {
        when:
        def street1 = new Street("New Road", [], 2, 900, "newly built road")
        then:
        assert street1.getArea() == 1800
    }

    def "getNumberOfHouses" () {
        when:
       /*
        List <Room>
        List <Room> 540CoddingRoadRoom = [
                new Room ("White", 7, 8, 4, 2, 1),
                new Room ("Yellow", 9, 9, 4, 2, 2),
                new Room ("Brown", 4, 3, 4, 1, 1),
                new Room ("White", 6, 2, 4, 1, 1),
                new Room ("White", 8, 8, 4, 2, 1),
                new Room ("White", 7, 8, 4, 2, 1),
                new Room ("White", 7, 8, 4, 3, 1),
                new Room ("White", 7, 7, 4, 2, 1)
        ]
        List <Room> 544CoddingRoadRoom = [
                new Room ("White", 7, 8, 4, 2, 1),
                new Room ("Yellow", 9, 9, 4, 2, 2),
                new Room ("Brown", 4, 3, 4, 1, 1),
                new Room ("White", 6, 2, 4, 1, 1),
                new Room ("Blue", 8, 8, 4, 2, 2),
                new Room ("Pink", 4, 8, 4, 2, 1),
                new Room ("White", 7, 8, 4, 4, 1),
                new Room ("White", 7, 7, 4, 2, 1)
        ]
        */
        List<House> houses = [
            new House(House.Material.Bricks,"540 Codding Road", rooms, 3, null, "30 years tar", "Black", 18, 16, 20),
            new House(House.Material.Bricks,"544 Codding Road", rooms, 3, null, "30 years tar", "Black", 18, 20, 18, "on sale")
        ]
        def street = new Street ("Codding Road", houses, 2, 900, "dead end street")

        then:
        street.getNumberOfHouses() == 2
    }

    def "findHousesInStatus" () {
        when:
        List<House> houses = [
                new House("320 Central Ave", 4, 2, 1, null, "15 years tar", "Brown", 15, 14, 20),
                new House("324 Central Ave", 5, 2, 3, null, "10 years tar", "Brown", 16, 14, 19, 'on sale')
        ]
        def street = new Street("Central Ave", houses, 4, 12000, "dead end street")

        then:
        street.getNumberOfHouses() == 2

        when:
        List <House> searchResult = street.findHousesInStatus("on sale")
        then:
        searchResult.size() == 1
    }
}
