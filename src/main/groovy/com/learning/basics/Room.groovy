package com.learning.basics

class Room {

    // Application starting point
    static void main(String[] args) {
        /*
        def house = []
        5.times {
            house << new Room("Yellow", 6.2, 4.5, 2.8, 2, 3)
        }
        */
        def room1 = new Room("Yellow", 6.2, 4.5, 2.8, 2, 3)
        def room2 = new Room("Yellow", 6.2, 4.5, 2.8, 1, 2)

        println """
        I created 2 rooms:
            room1: ${room1}
            room2: ${room2}
        """
    }

    // data: properties/fields
    String wallColor // 63EDRFT
    BigDecimal length, width, height // m
    // integer - whole number
    int numberOfWindows
    int numberOfClosets

    Room(String wallColor, BigDecimal length, BigDecimal width, BigDecimal height, int numberOfWindows, int numberOfClosets) {
        this.wallColor = wallColor
        this.length = length
        this.width = width
        this.height = height
        this.numberOfWindows = numberOfWindows
        this.numberOfClosets = numberOfClosets
    }

    // functions/methods
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

    int getNumberOfWindows() {
        return numberOfWindows
    }
    int getNumberOfClosets() {
        return numberOfClosets
    }

    @Override
    public String toString() {
        return "Room{" +
                "wallColor='" + wallColor + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", numberOfWindows=" + numberOfWindows +
                ", numberOfClosets=" + numberOfClosets +
                '}';
    }
}