package com.game.risk.core;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.model.Country;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Sarthak
 */
public class Graph {

    /**
     * Vertices count for the Graph
     */
    private int countriesCount;

    /**
     * MapFileParser class variable
     */
    private MapFileParser mapFileParser;

    /**
     * Store a HashMap of Countries having adjacent countries stored in LinkedList
     */
    private HashMap<Country, LinkedList<Country>> adjListHashMap;

    /**
     * @param fileParser
     */
    public Graph(MapFileParser fileParser) {
        this.mapFileParser = fileParser;
        adjListHashMap = new HashMap<Country, LinkedList<Country>>();
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
     * Add a connectivity as adjacent countries between two countries
     *
     * @param startCountry
     * @param endCountry
     */
    public void addEdge(Country startCountry, Country endCountry) {
        if (adjListHashMap.containsKey(startCountry)) adjListHashMap.get(startCountry).add(endCountry);
        else {
            LinkedList<Country> linkedList = new LinkedList<Country>();
            linkedList.add(endCountry);
            adjListHashMap.put(startCountry, linkedList);
        }

        if (adjListHashMap.containsKey(endCountry)) adjListHashMap.get(endCountry).add(startCountry);
        else {
            LinkedList<Country> linkedList = new LinkedList<Country>();
            linkedList.add(startCountry);
            adjListHashMap.put(endCountry, linkedList);
        }
        // Update the adjacent country to the country model
        mapFileParser.getCountriesHashMap().get(startCountry.getCountryName()).addAdjacentCountry(endCountry);
        mapFileParser.getCountriesHashMap().get(endCountry.getCountryName()).addAdjacentCountry(startCountry);
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
        // Removing the adjacent Country from the country model
        mapFileParser.getCountriesHashMap().get(startCountry.getCountryName()).removeAdjacentCountry(endCountry);
        mapFileParser.getCountriesHashMap().get(endCountry.getCountryName()).removeAdjacentCountry(startCountry);
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
                mapFileParser.getCountriesHashMap().get(adjCountry.getCountryName()).removeAdjacentCountry(country);
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
     * Add a country to the graph and model classes
     *
     * @param country
     */
    public void addCountry(Country country) {
        adjListHashMap.put(country, new LinkedList<Country>());
        mapFileParser.getCountriesHashMap().put(country.getCountryName(), country);
        mapFileParser.getContinentHashMap().get(country.getContinentName()).addCountry(country);
    }

}
