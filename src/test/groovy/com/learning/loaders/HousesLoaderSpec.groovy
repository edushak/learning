package com.learning.loaders

import com.learning.basics.House
import spock.lang.Specification

class HousesLoaderSpec extends Specification {

    def "LoadCSVIntoMemory"() {
        when:
        File inputFile = new File("src/test/resources/data/Westfield_houses.csv")
        List<House> houses = new HousesLoader().loadCSVIntoMemory(inputFile)
        then:
        assert houses.size() == 3
        assert houses[0].getAddress().contains("Codding")
    }
}
