package com.learning.util

import spock.lang.Specification

class PdfHelperSpec extends Specification {

    def "read"() {
        given:
        File pdf = new File("src/test/resources/data/pdf-sample.pdf")
        when:
        String textFromPdf = PdfHelper.read(pdf)
        then:
        textFromPdf.size() > 10
        textFromPdf.contains('Dummy')
    }
}
