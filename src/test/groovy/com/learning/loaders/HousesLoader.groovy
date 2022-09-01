package com.learning.loaders

import com.learning.basics.House

class HousesLoader {
    List<House> loadCSVIntoMemory(File input) {
        if (input == null) {
            return null
        }
        if (!input.exists()) {
            throw new IOException("Cannot read file ${input}!")
        }

        List<House> result = []
        // v1 - we assume that the format is: HouseMaterial,Address,YearBuilt,
        // NumberOfFloors,numberOfParkingSpaces,RoofMaterial,RoofColor
        int linesCounter = 0;
        def lines = input.readLines()
        lines.each { String line ->
            if (linesCounter == 0) {
                // reading header
            } else {
                String[] columns = line.split(',')
                def materialNew = columns[0]
                def Address = columns[1]
                def YearBuilt = columns[2]
                def NumberOfFloors = columns[3]
                def numberOfParkingSpaces = columns[4]
                def RoofMaterial = columns[5]
                def RoofColor = columns[6]
                House.Material material
                try {
                    material = House.Material.valueOf(materialNew)
                } catch (Exception any) {

                }

                def yearsInt = Integer.parseInt(YearBuilt)
                def floorsInt = Integer.parseInt(NumberOfFloors)
                def parkingInt = Integer.parseInt(numberOfParkingSpaces)

                def house = new House(material, Address, yearsInt, [], floorsInt, parkingInt, RoofMaterial,
                        RoofColor, null, null, null)
                result.add(house)
            }
            linesCounter = linesCounter + 1;
        }
        return result;
    }

//    int saveIntoDb(List<House> input) {
        // TODO
//    }
}
