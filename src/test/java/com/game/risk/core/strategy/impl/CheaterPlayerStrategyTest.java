package com.game.risk.core.strategy.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.game.risk.RiskGamePhases;
import com.game.risk.core.CountriesGraph;
import com.game.risk.core.strategy.PlayerStrategy;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

import static org.mockito.Mockito.*;

/**
 * @author sohrab_singh
 * @author Sarthak
 */
public class CheaterPlayerStrategyTest {

	private PlayerStrategy cheaterPlayerStrategy;

	private Country country1;

	private Country country2;

	private Country country3;

	private Country country4;

	private Country country5;

	private RiskGamePhases riskGamePhases;

	Player player;

	Player player2;

	private CountriesGraph countriesGraph;

	/**
	 * 
	 */
	@Before
	public void setUp() {
		List<Player> players = new ArrayList<>();
		player = new Player();
		player2 = new Player();
		country1 = new Country("India");
		country2 = new Country("Canada");
		country3 = new Country("US");
		country4 = new Country("Pakistan");
		country5 = new Country("Nepal");
		country1.setCurrentNumberOfArmies(3);
		country2.setCurrentNumberOfArmies(6);
		country3.setCurrentNumberOfArmies(9);
		country4.setCurrentNumberOfArmies(7);
		country5.setCurrentNumberOfArmies(11);
		country1.setPlayerName("Sohrab");
		country2.setPlayerName("Sohrab");
		country3.setPlayerName("Sohrab");
		country4.setPlayerName("Sarthak");
		country5.setPlayerName("Sarthak");
		player.setPlayerName("Sohrab");
		player2.setPlayerName("Sarthak");
		player.addCountry(country1);
		player.addCountry(country2);
		player.addCountry(country3);
		player2.addCountry(country4);
		player2.addCountry(country5);
		players.add(player);
		players.add(player2);
		countriesGraph = new CountriesGraph();
		LinkedList<Country> adjCountries1 = new LinkedList<>();
		adjCountries1.add(country4);
		adjCountries1.add(country2);
		adjCountries1.add(country5);
		HashMap<Country, LinkedList<Country>> adjacencyListHashMap = new HashMap<>();
		adjacencyListHashMap.put(country1, adjCountries1);
		LinkedList<Country> adjCountries3 = new LinkedList<>();
		adjCountries3.clear();
		adjCountries3.add(country2);
		adjCountries3.add(country5);
		adjacencyListHashMap.put(country3, adjCountries3);
		LinkedList<Country> adjCountries2 = new LinkedList<>();
		adjCountries2.clear();
		adjCountries2.add(country3);
		adjCountries2.add(country5);
		adjacencyListHashMap.put(country2, adjCountries2);
		LinkedList<Country> adjCountries4 = new LinkedList<>();
		adjCountries4.clear();
		adjCountries4.add(country1);
		adjacencyListHashMap.put(country4, adjCountries4);
		LinkedList<Country> adjCountries5 = new LinkedList<>();
		adjCountries5.clear();
		adjCountries5.add(country2);
		adjCountries5.add(country3);
		adjacencyListHashMap.put(country5, adjCountries5);
		countriesGraph.setAdjListHashMap(adjacencyListHashMap);
		riskGamePhases = new RiskGamePhases(null);
		riskGamePhases = mock(RiskGamePhases.class);
		when(riskGamePhases.getPlayerList()).thenReturn(players);
		cheaterPlayerStrategy = new CheaterPlayerStrategy(player, countriesGraph, riskGamePhases);
	}

	/**
	 * 
	 */
	@Test
	public void testReinforce() {
		
		cheaterPlayerStrategy.reinforce();
		assertEquals(6, country1.getCurrentNumberOfArmies());
		assertEquals(12, country2.getCurrentNumberOfArmies());
		assertEquals(18, country3.getCurrentNumberOfArmies());

	}

	/**
	 * 
	 */
	@Test
	public void testAttack() {
		assertEquals("Sarthak", country4.getPlayerName());
		assertEquals("Sarthak", country5.getPlayerName());
		cheaterPlayerStrategy.attack();
		assertEquals("Sohrab", country4.getPlayerName());
		assertEquals("Sohrab", country5.getPlayerName());
	}


	/**
	 * 
	 */
	@Test
	public void testFortify() {
		cheaterPlayerStrategy.fortify();
		assertEquals(18, country3.getCurrentNumberOfArmies());
		//Country 5 belongs to Human Player [Sarthak], so no change
		assertEquals(11, country5.getCurrentNumberOfArmies());
		assertEquals(6, country1.getCurrentNumberOfArmies());
	}

}