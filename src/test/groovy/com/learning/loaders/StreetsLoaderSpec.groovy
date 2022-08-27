package com.learning.loaders

import com.learning.basics.Street
import spock.lang.Specification

class StreetsLoaderSpec extends Specification {

    def "LoadCSVIntoMemory"() {
        when:
        File inputFile = new File("src/test/resources/data/Westfield.csv")
        List<Street> streets = new StreetsLoader().loadCSVIntoMemory(inputFile)
        then:
        assert streets.size() == 2
        assert streets[0].getName().contains("Codding")
    }
}
