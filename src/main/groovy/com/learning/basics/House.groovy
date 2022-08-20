package com.learning.basics

/**
 * Developer: Maria
 */
class House {
    enum Material {
        Bricks, Blocks, Wood
    }

    // TODO: Marai, add: int yearBuilt
    String status = 'livable not on sale'

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

    House(Material material, String address, List<Room> rooms, int NumberOfFloors, int numberOfParkingSpaces,
          String RoofMaterial,
          String RoofColor, BigDecimal length, BigDecimal width, BigDecimal height, String status = null) {
        this.material = material
        this.address = address
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

    int getNumberOfFloors() {
        return NumberOfFloors
    }

    int getNumberOfParkingSpaces () {
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

    List<Window> findRooms(int minWindows) {
        // TODO Maria, implement
    }

    List<Room> getRoomsWithMinNumberOfWindows(int desiredMinNumberOfWindows) {
        List<Room> result = []

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            // TODO: Maria, finish
//            List<Room> roomsWithMinNumberOfWindows = room.getWindows();
//            for (Room room : roomsWithMinNumberOfWindows) {
//                if (room.numberOfWindows >= desiredMinNumberOfWindows)
//                    result << room
//            }
        }
        return result
    }
}
