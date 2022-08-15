package com.learning.basics

import groovy.transform.Canonical

@Canonical
class Window {
    BigDecimal width, height
    String material // TODO: change to fixed: Plastic, Wood, Metal
    String color

    BigDecimal getWindowArea() {
        return width * height
    }
}
