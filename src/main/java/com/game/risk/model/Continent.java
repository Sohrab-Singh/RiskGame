package com.game.risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Continent object for populating the data related to continent.
 *
 * @author sohrab_singh
 */
public class Continent {

    /**
     * Continent Name.
     */
    private String continentName;

    /**
     * List of countries inside the continent.
     */
    private List<Country> countries;

    /**
     * Control value of continent.
     */
    private int controlValue;

    /**
     * @param continentName
     * @param controlValue
     */
    public Continent(String continentName, int controlValue) {
        this.continentName = continentName;
        this.controlValue = controlValue;
        countries = new ArrayList<Country>();
    }

    /**
     * Get the continent name.
     *
     * @return the continentName
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Set the continent name.
     *
     * @param continentName the continentName to set
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * Get the list of countries inside the continent.
     *
     * @return the countries
     */
    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Set the list of countries inside the continent.
     *
     * @param countries the countries to set
     */
    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    /**
     * Get the control value of continent.
     *
     * @return the controlValue
     */
    public int getControlValue() {
        return controlValue;
    }

    /**
     * Set the control value of continent.
     *
     * @param controlValue the controlValue to set
     */
    public void setControlValue(int controlValue) {
        this.controlValue = controlValue;
    }

    /**
     * Add a country to the countries ArrayList
     *
     * @param country
     */
    public void addCountry(Country country) {
        countries.add(country);
    }

    /**
     * Remove a country from the countries ArrayList
     *
     * @param country
     */
    public void removeCountry(Country country) {
        countries.remove(country);
    }
}
