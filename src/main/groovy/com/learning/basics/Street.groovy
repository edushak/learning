package com.learning.basics

/**
 * Developer: Anna
 */
class Street {
    String name
    List<House> houses
    int length
    String details
    String zip
    List<Street> connectedTo

    Street(String name, List<House> houses, int length, String details, String zip, List<Street> connectedTo = []) {
        this.name = name
        this.houses = houses
        this.length = length
        this.details = details
        this.zip = zip
        this.connectedTo = connectedTo
    }

    int getNumberOfHouses() {
        return houses.size()
    }

    String getDetails() {
        return details
    }

    String zip () {
        return zip
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

    @Override
    public String toString() {
        return "Street{" +
                "name='" + name + '\'' +
                ", houses=" + houses +
                ", length=" + length +
                ", details='" + details + '\'' +
                ", zip='" + zip + '\'' +
                ", connectedTo=" + connectedTo +
                '}';
    }
}
