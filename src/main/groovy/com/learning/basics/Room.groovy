package com.learning.basics

/**
 * Developer: Oleh
 */
class Room {
    enum RoomType {
        Living, Family, Bedroom, Guest, Entertainment
    }

    // data: properties/attributes/fields ---------------------------------------
    String wallColor // 63EDRFT
    BigDecimal length, width, height // measurements
    // integer - whole number
   // int numberOfWindows //
    int numberOfClosets
    List<Window> windows
    RoomType roomType


    // constructor -----------------------------------

    Room(String wallColor, BigDecimal length, BigDecimal width, BigDecimal height,
         int numberOfClosets, List<Window> windows, RoomType roomType) {
        this.wallColor = wallColor
        this.length = length
        this.width = width
        this.height = height
        this.numberOfClosets = numberOfClosets
        this.windows = windows
        this.roomType = roomType

    }

    // functions/methods ---------------------------------------
    String getWallColor() {
        return wallColor
    }

    BigDecimal getArea() {
        return width * length
    }

    BigDecimal getVolume() {
        return getArea() * height
    }

    float getHeight() {
        return height
    }

    int getNumberOfClosets() {
        return numberOfClosets
    }

    RoomType getRoomType() {
        return roomType
    }

    List<Window> findWindows(Window.Material desiredMaterial) {
        List<Window> result = []
        for (Window window : windows) {
            if (window.material == desiredMaterial) {
                result << window
            }
        }
        return result
    }

    Map<Window.Material, List<Window>> getWindowsByMaterial() {
        // TODO for Ed
    }

    @Override
    public String toString() {
        return "Room{" +
                "wallColor='" + wallColor + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
               // ", numberOfWindows=" + numberOfWindows +
                ", numberOfClosets=" + numberOfClosets +
                ", windows=" + windows +
                ", roomType" + roomType +
                '}';
    }

}
/*
    // Application starting point
    static void main(String[] args) {
        def house = []
        5.times {
            house << new Room("Yellow", 6.2, 4.5, 2.8, 2, 3)
        }
        def room1 = new Room("Yellow", 6.2, 4.5, 2.8, 2, 3)
        def room2 = new Room("Yellow", 6.2, 4.5, 2.8, 1, 2)

        println """
        I created 2 rooms:
            room1: ${room1}
            room2: ${room2}
        """
    }
*/
