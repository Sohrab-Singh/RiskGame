package com.game.risk.core;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * 
 * @author shubhangisheel
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
		mapFileReader = new MapFileReader("./src/main/resources/Canada.map");
		countriesGraph = new CountriesGraph(mapFileReader);

		Continent continent1 = new Continent("Asia", 7);
		countriesGraph.getContinentHashMap().put("Asia", continent1);

		//Initializing country object
		c1 = new Country("India");
		c2 = new Country("China");
		c3 = new Country("Pakistan");

		c1.setContinentName(continent1.getContinentName());
		c2.setContinentName(continent1.getContinentName());
		c3.setContinentName(continent1.getContinentName());

		System.out.println(countriesGraph.getContinentHashMap().toString());
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

	//	@Test
	//	public void testRemoveCountry(){
	//		
	//	}
}
