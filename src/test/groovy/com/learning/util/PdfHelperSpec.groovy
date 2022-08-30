package com.learning.util

import spock.lang.Specification

class PdfHelperSpec extends Specification {

    def "read"() {
        given:
        File pdf = new File("src/test/resources/data/I-134-Anna-IOE9231177612.pdf")
        when:
        String textFromPdf = PdfHelper.read(pdf)
        then:
        textFromPdf.size() > 100
        textFromPdf.contains('Anna')
    }
}
