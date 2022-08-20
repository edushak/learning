package com.learning.basics

/**
 * Developer: Anna
 */
class Street {
    String name
    List<House> houses
    int width
    int length
    // int numberOfParkingSpaces // TODO: move to House
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

    List<House> findHousesInStatus(String desiredStatus) {
        List<House> result = []
        for (House house : houses) {
            if (house.status == desiredStatus) {
                result << house
            }
        }
        return result;
    }
}
