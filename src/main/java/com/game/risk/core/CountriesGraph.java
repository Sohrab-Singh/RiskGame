package com.game.risk.core;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Graph class to hold the countries and its adjacent countries
 *
 * @author Sarthak
 */
public class CountriesGraph {

    /**
     * Vertices count for the CountriesGraph
     */
    private int countriesCount;

    /**
     * MapFileParser class variable
     */
    private MapFileParser mapFileParser;

    /**
     * List to store continents
     */
    private HashMap<String, Continent> continentHashMap;

    /**
     * Store a HashMap of Countries having adjacent countries stored in LinkedList, representing adjacency list for graph
     */
    private HashMap<Country, LinkedList<Country>> adjListHashMap;

    /**
     * @param fileParser
     */
    public CountriesGraph(MapFileParser fileParser) {
        this.mapFileParser = fileParser;
        adjListHashMap = new HashMap<Country, LinkedList<Country>>();
        continentHashMap = new HashMap<String, Continent>();
    }

    /**
     * Get the count of countries in the graph
     *
     * @return countriesCount
     */
    public int getCountriesCount() {
        return countriesCount;
    }

    /**
     * Set the count of countries in the graph
     *
     * @param countriesCount
     */
    public void setCountriesCount(int countriesCount) {
        this.countriesCount = countriesCount;
    }

    /**
     * @return the adjListHashMap
     */
    public HashMap<Country, LinkedList<Country>> getAdjListHashMap() {
        return adjListHashMap;
    }
    
    /**
     * Get Continent 
     */

    /**
     * Add a connectivity as adjacent countries between two countries
     *
     * @param startCountry
     * @param endCountry
     */
    public void addEdge(Country startCountry, Country endCountry) {
        if (adjListHashMap.containsKey(startCountry)) {
            adjListHashMap.get(startCountry).add(endCountry);
        } else {
            LinkedList<Country> linkedList = new LinkedList<Country>();
            linkedList.add(endCountry);
            adjListHashMap.put(startCountry, linkedList);
        }
    }

    /**
     * Remove a connectivity as adjacent countries between two countries
     *
     * @param startCountry
     * @param endCountry
     */
    public void removeEdge(Country startCountry, Country endCountry) {
        adjListHashMap.get(startCountry).remove(endCountry);
        adjListHashMap.get(endCountry).remove(startCountry);
    }

    /**
     * Remove a country from the graph and model classes
     *
     * @param country
     * @return
     */
    public boolean removeCountry(Country country) {
        if (adjListHashMap.containsKey(country)) {
            LinkedList<Country> adjCountries = adjListHashMap.get(country);
            for (int i = 0; i < adjCountries.size(); i++) {
                Country adjCountry = adjCountries.get(i);
                adjListHashMap.get(adjCountry).remove(country);
            }
            adjListHashMap.remove(country);
            mapFileParser.getCountriesHashMap().remove(country.getCountryName());
            // Removing the country from its respective continent
            mapFileParser.getContinentHashMap().get(country.getContinentName()).removeCountry(country);
            return true;
        }
        return false;
    }

    /**
     * Add a country to the graph
     *
     * @param country
     */
    public void addCountry(Country country) {
        adjListHashMap.put(country, new LinkedList<Country>());
    }

    /**
     * Add a continent to the graph
     *
     * @param continent
     */
    public void addContinent(Continent continent) {
        continentHashMap.put(continent.getContinentName(), continent);
    }

    /**
     * Remove a continent from the graph
     *
     * @param continent
     * @return
     */
    public boolean removeContinent(Continent continent) {
        if (continentHashMap.containsValue(continent)) {
            continentHashMap.remove(continent.getContinentName());
            for (Country country : adjListHashMap.keySet()) {
                if (country.getContinentName().equals(continent.getContinentName())) {
                    // Returns the adjacent countries to the removing country
                    LinkedList<Country> adjCountries = adjListHashMap.remove(country);
                    // Removing the country from the adjacent countries, represented in the adjacency linked list for the adjacent country
                    IntStream.range(0, adjCountries.size()).forEach(i -> adjListHashMap.get(adjCountries.get(i)).remove(country));
                }
            }
            return true;
        }
        return false;
    }
}
