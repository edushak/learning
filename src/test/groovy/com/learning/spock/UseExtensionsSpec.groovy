package com.learning.spock

import spock.lang.Specification
import spock.util.mop.Use

class UseExtensionsSpec extends Specification {

    @Use(ListExtensions)
    def "can use avg() method"() {
        expect:
        [1, 2, 3].avg() == 2.0
    }
}
