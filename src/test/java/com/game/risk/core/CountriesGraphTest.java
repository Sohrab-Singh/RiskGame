package com.game.risk.core;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * 
 * @author shubhangi sheel
 * Test class for Countries Graph
 */
public class CountriesGraphTest {

	/**
	 * Country object
	 */
	private Country c1;

	/**
	 * Country object
	 */
	private Country c2;

	/**
	 * Country object
	 */
	private Country c3;

	/**
	 * Country object
	 */
	private Country c4;

	/**
	 * Continent object
	 */
	private Continent continent1;

	/**
	 * Continent object
	 */
	private Continent continent2;

	/**
	 * countries graph object
	 */
	private CountriesGraph countriesGraph;

	/**
	 * MapFileReader object
	 */
	private MapFileReader mapFileReader;

	/**
	 * List to store continents
	 */
	private HashMap<String, Continent> continentHashMap;

	/**
	 * Setup method for the test class
	 * @throws FileNotFoundException
	 */
	@Before
	public void setUp() throws FileNotFoundException{
		mapFileReader = new MapFileReader(new File("./src/main/resources/Canada.map"));
		countriesGraph = new CountriesGraph(mapFileReader);

		continent1 = new Continent("Asia", 7);
		continent2 = new Continent("Africa", 6);
		countriesGraph.getContinentHashMap().put("Asia", continent1);

		//Initializing country objects
		c1 = new Country("India");
		c2 = new Country("China");
		c3 = new Country("Pakistan");
		c4 = new Country("Bhutan");

		c1.setContinentName(continent1.getContinentName());
		c2.setContinentName(continent1.getContinentName());
		c3.setContinentName(continent1.getContinentName());

		countriesGraph.addCountry(c1);
		countriesGraph.addCountry(c2);
		countriesGraph.addCountry(c3);


	}

	/**
	 * Method to test addEdge method of the countries graph class
	 */
	@Test
	public void testAddEdge(){
		countriesGraph.addEdge(c1, c2);
		countriesGraph.addEdge(c1, c3);
		assertEquals(2, countriesGraph.getAdjListHashMap().get(c1).size());
	}

	/**
	 * Method to test removeEdge of the countries graph class
	 */
	@Test
	public void testRemoveEdge(){
		countriesGraph.addEdge(c1, c2);
		countriesGraph.addEdge(c1, c3);
		countriesGraph.addEdge(c2, c3);
		countriesGraph.removeEdge(c1, c2);
		assertEquals(1, countriesGraph.getAdjListHashMap().get(c1).size());
	}

	/**
	 * Method to test add countries to continent 
	 */
	@Test
	public void testAddCountry(){
		c4.setContinentName(continent1.getContinentName());
		countriesGraph.addCountry(c4);
		assertTrue(countriesGraph.getContinentHashMap().get(continent1.getContinentName()).getCountries().contains(c4));

	}

	/**
	 * Method to test add countries to countries hashmap
	 */
	@Test
	public void testAddCountryCountriesMap(){
		c4.setContinentName(continent1.getContinentName());
		countriesGraph.addCountry(c4);
		assertTrue(countriesGraph.getAdjListHashMap().containsKey(c4));

	}

	/**
	 * Method to test addition of continents to the continent hash map
	 */
	@Test
	public void testAddContinent(){
		countriesGraph.addContinent(continent2);
		assertTrue(countriesGraph.getContinentHashMap().containsKey(continent2.getContinentName()));
	}

	/**
	 * Method to test adjacency of continent
	 */
	@Test
	public void addIsAdjacent(){
		countriesGraph.addEdge(c1, c2);
		assertTrue(countriesGraph.isAdjacent(c1, c2));
	}

	/**
	 * Method to test adjacency of continent
	 */
	@Test
	public void addIsAdjacentNegativeTest(){
		assertFalse(countriesGraph.isAdjacent(c1, c4));
	}
}
