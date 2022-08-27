package com.learning.basics

/**
 * Developer: Maria
 */
class House {
    enum Material {
        Bricks, Blocks, Wood
    }

    String status = 'livable not on sale'
    int yearBuilt
    List<Room> rooms
    int NumberOfFloors
    Material material
    int numberOfParkingSpaces
    String address
    String RoofMaterial
    String RoofColor
    BigDecimal length, width, height

    Date lastSold
    BigDecimal soldPrice
    BigDecimal todayEstimatedPrice

    House(Material material, String address, int yearBuilt, List<Room> rooms, int NumberOfFloors,
          int numberOfParkingSpaces, String RoofMaterial,
          String RoofColor, BigDecimal length, BigDecimal width, BigDecimal height, String status = null) {
        this.material = material
        this.address = address
        this.yearBuilt = yearBuilt
        this.rooms = rooms
        this.NumberOfFloors = NumberOfFloors
        this.numberOfParkingSpaces = numberOfParkingSpaces
        this.RoofMaterial = RoofMaterial
        this.RoofColor = RoofColor
        this.length = length
        this.width = width
        this.height = height
        this.status = status
    }

    int getNumberOfRooms() {
        return rooms.size();
    }

    int getYearBuilt() {
        return yearBuilt
    }

    int getNumberOfFloors() {
        return NumberOfFloors
    }

    int getNumberOfParkingSpaces() {
        return numberOfParkingSpaces
    }

    String getRoofMaterial() {
        return RoofMaterial
    }

    String getRoofColor() {
        return RoofColor
    }

    BigDecimal getArea() {
        return width * length
    }

    Material getHouseMaterial() {
        return material
    }

    List<Room> findRooms(int minWindows) {
        List<Room> result = []
        for (Room room : rooms) {
            if (room.windows.size() >= minWindows) {
                result << room
            }
        }
        return result;
    }


    List<Room> numberOfRoomsWithClosets(int numberOfClosets) {
        List<Room> result = []
        for (Room room : rooms) {
            if (room.numberOfClosets >= numberOfClosets) {
                result << room
            }
        }
        return result;
    }

}

