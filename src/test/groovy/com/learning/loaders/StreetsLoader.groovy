package com.learning.loaders

import com.learning.basics.Street
import com.opencsv.CSVReader

class StreetsLoader {

    List<Street> loadCSVIntoMemorySmart(File input) {
        List<Street> result = []

        // using CSV library - https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/
        FileReader filereader = new FileReader(input);
        CSVReader csvReader = new CSVReader(filereader);
        try {
            String[] line;
            csvReader.skip(1);
            while ((line = csvReader.readNext()) != null) {
                def StreetName = line[0]
                def HouseNumber = line[1]
                def YearBuilt = line[2]
                def Status = line[3]
                def Zip = line[4]
                def Length = line[5]

                def zipInt = Integer.parseInt(Length)
                def street = new Street(StreetName, [], zipInt, "some details", Zip, [])
                result.add(street)
            }
        } finally {
            csvReader?.close()
            filereader?.close()
        }
        return result
    }

    /**
     * Can read files in format:
     * <pre>
     *  StreetName,HouseNumber,YearBuilt,Status,Zip
     *  Codding Road,540,2014,"not on sale",07090
     *  Codding Road,544,2014,"on sale",07090
     * </pre>
     * @param input
     * @return List<Street>
     */
    List<Street> loadCSVIntoMemory(File input) {
        if (input == null) {
            return null
        }
        if (!input.exists()) {
            throw new IOException("Cannot read file ${input}!")
        }

        List<Street> result = []
        // v1 - we assume that the format is: StreetName,HouseNumber,YearBuilt,Status,Zip
        int linesCounter = 0;
        def lines = input.readLines()
        lines.each { String line ->
            if (linesCounter == 0) {
                // reading header
                // TODO something
            } else {
                String[] columns = line.split(',')
                def StreetName = columns[0]
                def HouseNumber = columns[1]
                def YearBuilt = columns[2]
                def Status = columns[3]
                def Zip = columns[4]
                def Length = columns[5]

                def zipInt = Integer.parseInt(Length)
                def street = new Street(StreetName, [], zipInt, "some details", Zip, [])
                result.add(street)
            }
            linesCounter = linesCounter + 1;
        }
        return result;
    }

    int saveIntoDb(List<Street> input) {
        // TODO
    }
}
