/**
 * 
 */
package com.game.risk.core.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * Test class for fortification phase.
 * 
 * @author sohrab_singh
 *
 */
public class FortificationPhaseUtilTest {

	private Player player;
	private Continent continent;
	
	private Player player1;
	private Player player2;
	
	private ArrayList<Country> countriesOwned;
	private ArrayList<Country> countriesOwned1;
	
	private Country country1;
	private Country country2;
	private Country country3;
	private Country country4;
	private Country country5;
	private Country country6;
	private Country country7;
	private Country country8;
	private Country country9;
	private Country country10;
	private Country country11;
	private Country country12;
	private Country country13;

	/**
	 * Initial Setup of objects.
	 */
	@Before
	public void setUp() {
		player = new Player();
		continent = new Continent("Europe", 5);
		countriesOwned = new ArrayList<>();
		
		player1 = new Player();
		player2= new Player();
		countriesOwned1 = new ArrayList<>();
		
		country1 = new Country("1C");
		country2 = new Country("2C");
		country3 = new Country("3C");
		country4 = new Country("4C");
		country5 = new Country("5C");
		country6 = new Country("6C");
		country7 = new Country("7C");
		country8 = new Country("8C");
		country9 = new Country("9C");
		country10 = new Country("10C");
		country11 = new Country("11C");
		country12 = new Country("12C");
		country13 = new Country("13C");

		country1.setContinentName(continent.getContinentName());
		country2.setContinentName(continent.getContinentName());
		country3.setContinentName(continent.getContinentName());
		country4.setContinentName(continent.getContinentName());
		country5.setContinentName(continent.getContinentName());
		country6.setContinentName(continent.getContinentName());
		country7.setContinentName(continent.getContinentName());
		country8.setContinentName(continent.getContinentName());
		country9.setContinentName(continent.getContinentName());
		country10.setContinentName(continent.getContinentName());
		country11.setContinentName(continent.getContinentName());
		country12.setContinentName(continent.getContinentName());

		countriesOwned.add(country1);
		countriesOwned.add(country2);
		countriesOwned.add(country3);
		countriesOwned.add(country4);
		countriesOwned.add(country5);
		countriesOwned.add(country6);
		countriesOwned.add(country7);
		countriesOwned.add(country8);
		countriesOwned.add(country9);
		countriesOwned.add(country10);
		countriesOwned.add(country11);
		countriesOwned.add(country12);
		countriesOwned.add(country13);
		
		player.setCountriesOwned(countriesOwned);
		
		countriesOwned1.add(country1);
		countriesOwned1.add(country2);
		player1.setCountriesOwned(countriesOwned1);
	
	}

	/**
	 * Test method for calculating reinforcement armies.
	 */
	@Test
	public void testcalculateReinforcementArmies() {
		country13.setContinentName("Canada");
		int reinforcementArmy = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
		assertEquals(4, reinforcementArmy);

	}
	
	/**
	 * Test method for calculating reinforcement armies.
	 */
	@Test
	public void testcalculateReinforcementArmiesNegative() {
		country13.setContinentName("Asia");
		int reinforcementArmy = ReinforcementPhaseUtil.calculateReinforcementArmies(player2);
		assertEquals(3, reinforcementArmy);

	}

	/**
	 * Test method for calculating reinforcement armies for whole continent.
	 */
	@Test
	public void testcalculateReinforcementArmiesWithWholeContinent() {
		country13.setContinentName(continent.getContinentName());
		int reinforcementArmy = ReinforcementPhaseUtil.calculateReinforcementArmies(player1);
		assertEquals(3, reinforcementArmy);

	}
	
	/**
	 * Test method for calculating reinforcement armies for whole continent.
	 */
	@Test
	public void testcalculateReinforcementArmiesWithWholeContinentAnother() {
		country13.setContinentName(continent.getContinentName());
		int reinforcementArmy = ReinforcementPhaseUtil.calculateReinforcementArmies(player2);
		assertEquals(3, reinforcementArmy);

	}
}
