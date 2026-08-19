package com.learning.util

import groovy.util.logging.Slf4j

@Slf4j
class IOHelper {

    static String getFileText(String fileName) {
        IOHelper.class.getResourceAsStream("/" + fileName)?.text
    }
}
