package com.game.risk.core;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Graph class to hold the countries and its adjacent countries
 *
 * @author Sarthak
 * @author sohrab_singh
 */
public class CountriesGraph {

	/**
	 * Vertices count for the CountriesGraph
	 */
	private int countriesCount;

	/**
	 * MapFileReader class variable
	 */
	private MapFileReader mapFileReader;

	/**
	 * List to store continents
	 */
	private HashMap<String, Continent> continentHashMap;

	/**
	 * Store a HashMap of Countries having adjacent countries stored in LinkedList,
	 * representing adjacency list for graph
	 */
	private HashMap<Country, LinkedList<Country>> adjListHashMap;

	/**
	 * Countries Graph constructor
	 * 
	 * @param fileParser
	 *            parser for storing data
	 */
	public CountriesGraph(MapFileReader fileParser) {
		this.mapFileReader = fileParser;
		adjListHashMap = new HashMap<Country, LinkedList<Country>>();
		continentHashMap = new HashMap<String, Continent>();
	}

	public CountriesGraph() {

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
	 *            number of countries present in the Map
	 */
	public void setCountriesCount(int countriesCount) {
		this.countriesCount = countriesCount;
	}

	/**
	 * Get the Adjacent List Map
	 * 
	 * @return the adjListHashMap
	 */
	public HashMap<Country, LinkedList<Country>> getAdjListHashMap() {
		return adjListHashMap;
	}

	/**
	 * Add a connectivity as adjacent countries between two countries
	 *
	 * @param startCountry
	 *            start country of edge
	 * @param endCountry
	 *            end country of edge
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
	 * Get Continent HashMap
	 * 
	 * @return Hashmap of Continents
	 */
	public HashMap<String, Continent> getContinentHashMap() {
		return continentHashMap;
	}

	/**
	 * Set the Continent HashMap
	 * 
	 * @param continentHashMap
	 *            HashMap to store or retrieve the continents via their name as key
	 */
	public void setContinentHashMap(HashMap<String, Continent> continentHashMap) {
		this.continentHashMap = continentHashMap;
	}

	/**
	 * Remove a connectivity as adjacent countries between two countries
	 *
	 * @param startCountry
	 *            start country of edge
	 * @param endCountry
	 *            end country of edge
	 */
	public void removeEdge(Country startCountry, Country endCountry) {
		adjListHashMap.get(startCountry).remove(endCountry);
		adjListHashMap.get(endCountry).remove(startCountry);
	}

	/**
	 * Remove a country from the graph and model classes
	 *
	 * @param country
	 *            country to be removed
	 * @return true if removed else false if country is not present
	 */
	public boolean removeCountry(Country country) {
		if (adjListHashMap.containsKey(country)) {
			LinkedList<Country> adjCountries = adjListHashMap.get(country);
			for (int i = 0; i < adjCountries.size(); i++) {
				Country adjCountry = adjCountries.get(i);
				adjListHashMap.get(adjCountry).remove(country);
			}
			adjListHashMap.remove(country);
			mapFileReader.getCountriesHashMap().remove(country.getCountryName());
			// Removing the country from its respective continent
			mapFileReader.getContinentHashMap().get(country.getContinentName()).removeCountry(country);
			return true;
		}
		return false;
	}

	/**
	 * Add a country to the graph
	 *
	 * @param country
	 *            country to be added
	 */
	public void addCountry(Country country) {
		adjListHashMap.put(country, new LinkedList<Country>());
		continentHashMap.get(country.getContinentName()).addCountry(country);
	}

	/**
	 * Add a continent to the graph
	 *
	 * @param continent
	 *            continent to added
	 */
	public void addContinent(Continent continent) {
		continentHashMap.put(continent.getContinentName(), continent);
	}

	/**
	 * Remove a continent from the graph.
	 *
	 * @param continent
	 *            continent to be removed
	 * @return true if continent is removed else false if continent is not present
	 */
	public boolean removeContinent(Continent continent) {
		if (continentHashMap.containsValue(continent)) {
			for (int i = 0; i < continent.getCountries().size(); i++) {
				// Returns the adjacent countries to the removing country
				LinkedList<Country> adjCountries = adjListHashMap.remove(continent.getCountries().get(i));
				// Removing the country from the adjacent countries, represented in the
				// adjacency linked list for the adjacent country
				for (Country adjCountry : adjCountries) {
					adjListHashMap.get(adjCountry).remove(continent.getCountries().get(i));
				}
				mapFileReader.getCountriesHashMap().remove(continent.getCountries().get(i).getCountryName());
			}

			continentHashMap.remove(continent.getContinentName());
			return true;

		}
		return false;
	}

	/**
	 * Check if two countries are adjacent.
	 * 
	 * @param country
	 *            first country
	 * @param country2
	 *            second country
	 * @return true if adjacent
	 */
	public boolean isAdjacent(Country country, Country country2) {
		for (int i = 0; i < adjListHashMap.get(country).size(); i++) {
			if (country2.getCountryName().equals(adjListHashMap.get(country).get(i).getCountryName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param graph
	 *            CountriesGraph Message Protobuf object
	 */
	public void updateAdjacentCountriesModel(com.game.risk.model.autogen.GameStateDataProtos.CountriesGraph graph) {
		LinkedList<Country> adjCountries = new LinkedList<>();
		for (Country country : mapFileReader.getCountriesHashMap().values()) {
			adjCountries.clear();
			for (com.game.risk.model.autogen.GameStateDataProtos.Country adjCountry : graph.getCountryMapMap()
					.get(country.getCountryName()).getCountryList()) {
				if (mapFileReader.getCountriesHashMap().containsKey(adjCountry.getCountryName())) {
					adjCountries.add(mapFileReader.getCountriesHashMap().get(adjCountry.getCountryName()));
				}
			}
			adjListHashMap.put(country, adjCountries);
		}
	}
}
