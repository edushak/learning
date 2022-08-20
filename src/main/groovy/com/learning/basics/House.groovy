package com.learning.basics

/**
 * Developer: Maria
 */
class House {
    // TODO: add enum Material, like we have for Window
    String status = 'livable not on sale';

    int NumberOfRooms
    int NumberOfFloors
    int numberOfParkingSpaces
    String address
    String WallsMaterial
    String RoofMaterial
    String RoofColor
    BigDecimal length, width, height

    Date lastSold
    BigDecimal soldPrice
    BigDecimal todayEstimatedPrice

    House(String address, int NumberOfRooms, int NumberOfFloors, int numberOfParkingSpaces, String WallsMaterial, String RoofMaterial,
          String RoofColor, BigDecimal length, BigDecimal width, BigDecimal height, String status = null) {
        this.address = address
        this.NumberOfRooms = NumberOfRooms
        this.NumberOfFloors = NumberOfFloors
        this.numberOfParkingSpaces = numberOfParkingSpaces
        this.WallsMaterial = WallsMaterial
        this.RoofMaterial = RoofMaterial
        this.RoofColor = RoofColor
        this.length = length
        this.width = width
        this.height = height
        this.status = status
    }

    int getNumberOfRooms() {
        return NumberOfRooms
    }

    int getNumberOfFloors() {
        return NumberOfFloors
    }

    int getNumberOfParkingSpaces () {
        return numberOfParkingSpaces
    }

    String getWallsMaterial() {
        return WallsMaterial
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

    List<Window> findRooms(int minWindows) {
        // TODO implement
    }
}
