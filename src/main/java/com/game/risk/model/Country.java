package com.game.risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Country object for populating the data related to country.
 *
 * @author sohrab_singh
 */
public class Country {

    /**
     * Country Name.
     */
    String countryName;

    /**
     * List of adjacent countries with the countries.
     */
    List<Country> adjacentCountries;

    /**
     * Current number of armies in the country.
     */
    int currentNumberOfArmies;

    /**
     * @param countryName
     */
    public Country(String countryName) {
        this.countryName = countryName;
        adjacentCountries = new ArrayList<Country>();
    }

    /**
     * Get the country name.
     *
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set the country name.
     *
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Get the list of adjacent countries.
     *
     * @return the adjacentCountries
     */
    public List<Country> getAdjacentCountries() {
        return adjacentCountries;
    }

    /**
     * Set the list of adjacent countries.
     *
     * @param adjacentCountries the adjacentCountries to set
     */
    public void setAdjacentCountries(List<Country> adjacentCountries) {
        this.adjacentCountries = adjacentCountries;
    }

    /**
     * Get the current number of armies.
     *
     * @return the currentNumberOfArmies
     */
    public int getCurrentNumberOfArmies() {
        return currentNumberOfArmies;
    }

    /**
     * Set the current number of armies.
     *
     * @param currentNumberOfArmies the currentNumberOfArmies to set
     */
    public void setCurrentNumberOfArmies(int currentNumberOfArmies) {
        this.currentNumberOfArmies = currentNumberOfArmies;
    }

    /**
     * Add Adjacent Country to the adjacentCountries ArrayList
     *
     * @param country
     */
    public void addAdjacentCountry(Country country) {
        adjacentCountries.add(country);
    }

}
