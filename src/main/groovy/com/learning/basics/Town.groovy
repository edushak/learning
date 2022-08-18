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

    List<Street> getStreets() {
        return streets
    }

    int getNumberOfStreets() {
        return streets.size();
    }

    // loops
    // conditional code blocks
    List<House> getHousesWithMinNumberOfRooms(int desiredMinNumberOfRooms, String desiredStatus) {
        List<House> result = []

        for (int i = 0; i < streets.size(); i++) {
            Street street = streets.get(i);
            // for (Street street : getStreets()) {
            List<House> housesOneOnStreet = street.getHouses();
            for (House house : housesOneOnStreet) {
                if (house.numberOfRooms >= desiredMinNumberOfRooms && house.status == desiredStatus) {
                    result << house
                }
            }
        }

        return result;
    }
}
