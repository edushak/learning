package com.learning.ai.langchain4j

import spock.lang.Ignore
import spock.lang.Specification

class SimpleSpec extends Specification {

    @Ignore("Need credentials")
    def "Call"() {
        given:
        Simple s = new Simple()
        when:
        String answer = s.call("What is the best Large Language Model?")
        then:
        answer == "bla"
    }
}
