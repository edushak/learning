package com.learning.basics

/**
 * Developer: Anna
 */
class Street {
    String name
    List<House> houses
    int width
    int length
    String details
    String zip
    List<Street> connectedTo

    Street(String name, List<House> houses, int width, int length, String details, String zip, List<Street> connectedTo = []) {
        this.name = name
        this.houses = houses
        this.width = width
        this.length = length
        this.details = details
        this.zip = zip
        this.connectedTo = connectedTo
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

    String zip () {
        return zip
    }

    String connected () {
        return connected
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

    boolean isConnectedTo(String streetNameInQuestion) {
        for (Street actuallyConnected: connectedTo){
            if (actuallyConnected.name == streetNameInQuestion) {
                return true;
            }
        }
        return false;
    }
}
