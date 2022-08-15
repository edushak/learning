package com.learning.basics

class House {
    String address
    int NumberOfRooms
    int NumberOfFloors
    String WallsMaterial
    String RoofMaterial
    String RoofColor
    BigDecimal length, width, height

    Date lastSold
    BigDecimal soldPrice
    BigDecimal todayEstimatedPrice

    /*
    {
        def house1 = new House(5,2,"brick","pink","wood","black",20,30,9)
        def house2 = new House(10,3,"blocks","white","wood","brown",45,45,13)
        def house3 = new House(7,1,"wood","brown","wood","white",34,16,10)
    }
    */

    // TODO: move to Room class - String WallsColor
    House(String address, int NumberOfRooms, int NumberOfFloors, String WallsMaterial, String RoofMaterial, String RoofColor, BigDecimal length, BigDecimal width, BigDecimal height) {
        this.address = address
        this.NumberOfRooms = NumberOfRooms
        this.NumberOfFloors = NumberOfFloors
        this.WallsMaterial = WallsMaterial
        this.RoofMaterial = RoofMaterial
        this.RoofColor = RoofColor
        this.length = length
        this.width = width
        this.height = height
    }

    int getNumberOfRooms() {
        return NumberOfRooms
    }

    int getNumberOfFloors() {
        return NumberOfFloors
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
}
