package com.learning.basics

import spock.lang.Specification

class HouseSpec extends Specification {

    def "HouseArea"() {
        when:
        def house1 = new House("540 Codding Road", 8, 3, null, "30 years tar", "Black", 18, 16,20)
        then:
        assert house1.getArea() == 288
    }
}

/*
{
    def house1 = new House(5,2,"brick","pink","wood","black",20,30,9)
    def house2 = new House(10,3,"blocks","white","wood","brown",45,45,13)
    def house3 = new House(7,1,"wood","brown","wood","white",34,16,10)
}
*/
