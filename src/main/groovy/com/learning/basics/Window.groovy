package com.learning.basics

import groovy.transform.Canonical

@Canonical
class Window {
    // enumerator
    enum Material {
        Plastic, Wood, Metal
    }

    BigDecimal width, height
    Material material
    String color

    BigDecimal getWindowArea() {
        return width * height
    }

    Window.Material getWindowMaterial() {
        return width * height
    }
}
