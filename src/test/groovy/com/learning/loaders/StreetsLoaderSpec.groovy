package com.learning.loaders

import com.learning.basics.Street
import groovy.util.logging.Slf4j
import spock.lang.Shared
import spock.lang.Specification

@Slf4j
class StreetsLoaderSpec extends Specification {

    @Shared loader = new StreetsLoader()

    def "LoadCSVIntoMemory"() {
        when:
        File inputFile = new File("src/test/resources/data/Westfield.csv")
        List<Street> streets = loader.loadCSVIntoMemory(inputFile)
        log.info("loadCSVIntoMemory:")
        streets.each {log.info("${it}") }
        then:
        assert streets.size() == 2
        assert streets[0].getName().contains("Codding")
    }

    def "loadCSVIntoMemorySmart"() {
        when:
        File inputFile = new File("src/test/resources/data/Westfield.csv")
        List<Street> streets = loader.loadCSVIntoMemorySmart(inputFile)
        log.info("loadCSVIntoMemorySmart:")
        streets.each { Street street -> log.info("${street.toString()}") }
        then:
        assert streets.size() == 2
        assert streets[0].getName().contains("Codding")
    }
}
