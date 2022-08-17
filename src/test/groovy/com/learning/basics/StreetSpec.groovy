package com.learning.basics

import spock.lang.Specification

public class StreetSpec extends Specification {

    def "Name" () {
        when:
        def street1 = new Street("New Road", [], 2, 900, "newly built road")
        then:
        assert street1.getArea() == 1800
    }
    def "Create a streetCR" () {
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
        List houses = [
                new House ("540 Codding Road", 8, 3, null, "30 years tar", "Black", 18, 16, 20),
                new House ("544 Codding Road", 9, 3, null, "30 years tar", "Black", 18, 20, 18)
        ]
        def street = new Street ("Codding Road", houses, 2, 900, "dead end street")

        then: street.getNumberOfHouses () == 2
    }
    def "Create a Street2" () {
        when:
        List houses = [
                new House ("320 Central Ave", 4, 2, null, "15 years tar", "Brown", 15, 14, 20),
                new House ("324 Central Ave", 5, 2, null,"10 years tar", "Brown", 16, 14, 19)
        ]
        def street = new Street ("Central Ave", houses, 4, 12000, "dead end street")
        then: street.getNumberOfHouses() == 2
    }
}
