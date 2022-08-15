package com.learning.basics

class Town {
    String name;
    String majorName;
    List<Street> streets;

    Town(String name, String majorName, List<Street> streets) {
        this.name = name
        this.majorName = majorName
        this.streets = streets
    }

    int getNumberOfStreets() {
        return streets.size();
    }

    List<House> getHousesOnSaleWithMinNumberOfRooms(int minNumberOfRooms) {
        // TODO
        throw new Exception("Not implemented yet!")
    }
}
