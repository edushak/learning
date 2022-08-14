package com.learning.basics

class Street {
    String name
    int numberOfHouses
    int width
    int length
    int numberOfParkingSpaces
    String mainBuildings

    Street(String name, int numberOfHouses, int width, int length, int numberOfParkingSpaces, String mainBuildings) {
        this.name = name
        this.numberOfHouses = numberOfHouses
        this.width = width
        this.length = length
        this.numberOfParkingSpaces = numberOfParkingSpaces
        this.mainBuildings = mainBuildings
    }

    int getNumberOfHouses() {
        return numberOfHouses
    }

    int getWidth() {
        return width
    }

    int getNumberOfParkingSpaces() {
        return numberOfParkingSpaces
    }

    String getMainBuildings() {
        return mainBuildings
    }

    BigDecimal getArea() {
        return width * length
    }
}
