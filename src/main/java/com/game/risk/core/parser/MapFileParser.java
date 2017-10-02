package com.game.risk.core.parser;

import com.game.risk.core.Graph;
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
    private HashMap<String, Country> countriesHashMap;

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
        countriesHashMap = new HashMap<String, Country>();
        continentHashMap = new HashMap<String, Continent>();
    }

    /**
     * Method to read and store data into the model classes from map file
     *
     * @return the country HashMap
     * @throws IOException
     */
    public HashMap<String, Country> readFile() throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        Graph graph = new Graph(this);
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
                        if (!countriesHashMap.containsKey(splits[0])) {
                            Country country = new Country(splits[0]);
                            country.setContinentName(splits[3]);
                            continentHashMap.get(splits[3]).addCountry(country);
                            countriesHashMap.put(country.getCountryName(), country);
                            graph.addCountry(country);
                        }

                        // Check whether adjacent country is already created and present in HashMap
                        for (int i = 4; i < splits.length; i++) {
                            if (countriesHashMap.containsKey(splits[i])) {
                                countriesHashMap.get(splits[0]).addAdjacentCountry(countriesHashMap.get(splits[i]));
                                graph.addEdge(countriesHashMap.get(splits[0]), countriesHashMap.get(splits[i]));
                            } else {
                                Country adjCountry = new Country(splits[i]);
                                adjCountry.setContinentName(splits[3]);
                                countriesHashMap.get(splits[0]).addAdjacentCountry(adjCountry);
                                countriesHashMap.put(splits[i], adjCountry);
                                graph.addEdge(countriesHashMap.get(splits[0]), adjCountry);
                            }
                        }
                    }
                }
            }
        }
        return countriesHashMap;
    }

    public HashMap<String, Continent> getContinentHashMap() {
        return continentHashMap;
    }

    public HashMap<String, Country> getCountriesHashMap() {
        return countriesHashMap;
    }
}
