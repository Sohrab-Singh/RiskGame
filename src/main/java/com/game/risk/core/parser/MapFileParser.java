package com.game.risk.core.parser;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Map File Parser for reading map data for the game
 *
 * @author Sarthak
 */
public class MapFileParser {

    /**
     * FileReader class variable
     */
    private FileReader fileReader;

    /**
     * HashMap to store searched countries through the map file
     */
    private HashMap<String, Country> hashMap;

    /**
     * HashMap to store searched continents through the map file
     */
    private HashMap<String, Continent> continentHashMap;

    /**
     * Constructor
     *
     * @param filename
     * @throws FileNotFoundException
     */
    public MapFileParser(String filename) throws FileNotFoundException {
        fileReader = new FileReader(filename);
        hashMap = new HashMap<String, Country>();
        continentHashMap = new HashMap<String, Continent>();
    }

    /**
     * Method to read and store data into the model classes from map file
     *
     * @return
     * @throws IOException
     */
    public long readFile() throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }

            if (line.startsWith("[Continents]")) {
                while (!(line = reader.readLine()).startsWith("[")) {
                    if (!line.isEmpty()) {
                        String[] splitLine = line.split("=");
                        Continent continent = new Continent(splitLine[0], Integer.parseInt(splitLine[1]));
                        continentHashMap.put(splitLine[0], continent);
                    }
                }
            }

            if (line.startsWith("[Territories]")) {
                while ((line = reader.readLine()) != null) {

                    if (!line.isEmpty()) {
                        String[] splits = line.split(",");

                        //Condition if country is not present in HashMap
                        if (!hashMap.containsKey(splits[0])) {
                            Country country = new Country(splits[0]);
                            continentHashMap.get(splits[3]).addCountry(country);
                            hashMap.put(country.getCountryName(), country);
                        }

                        // Check whether adjacent country is already created and present in HashMap
                        for (int i = 4; i < splits.length; i++) {
                            if (hashMap.containsKey(splits[i])) {
                                hashMap.get(splits[0]).addAdjacentCountry(hashMap.get(splits[0]));
                            } else {
                                Country adjCountry = new Country(splits[i]);
                                hashMap.get(splits[0]).addAdjacentCountry(adjCountry);
                                hashMap.put(splits[i], adjCountry);
                            }
                        }
                    }
                }
            }
        }
        return hashMap.size();
    }
}
