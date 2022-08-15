package com.learning.basics

class Street {
    String name
    List<House> houses
    int width
    int length
    // int numberOfParkingSpaces // FIXME: move to House
    String details

    Street(String name, List<House> houses, int width, int length, String details) {
        this.name = name
        this.houses = houses
        this.width = width
        this.length = length
        this.details = details
    }

    int getNumberOfHouses() {
        return houses.size()
    }

    int getWidth() {
        return width
    }

    String getDetails() {
        return details
    }

    BigDecimal getArea() {
        return width * length
    }
}
