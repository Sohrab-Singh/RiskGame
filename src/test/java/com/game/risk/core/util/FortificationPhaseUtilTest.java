/**
 * 
 */
package com.game.risk.core.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.model.Country;

/**
 * Test class for fortification phase.
 * 
 * @author sohrab_singh
 *
 */
public class FortificationPhaseUtilTest {

	/** country1 */
	private Country country1;

	/** country2 */
	private Country country2;

	/** Number of armies */
	private int numberOfArmies;

	/** Hashmap for countries */
	private HashMap<Country, LinkedList<Country>> hashMap;

	/**
	 * Setup of Fortification phase.
	 */
	@Before
	public void setUp() {
		country1 = new Country("1C");
		country2 = new Country("2C");
		country1.setCurrentNumberOfArmies(5);
		country2.setCurrentNumberOfArmies(7);
		numberOfArmies = 4;
		hashMap = new HashMap<>();
		LinkedList<Country> list = new LinkedList<>();
		list.add(country2);
		hashMap.put(country1, list);

	}

	/**
	 * Test move armies between countries.
	 */
	@Test
	public void testMoveArmiesBetweenCountries() {
		FortificationPhaseUtil.moveArmiesBetweenCountries(country1, country2, numberOfArmies, hashMap);
		assertEquals(1, country1.getCurrentNumberOfArmies());

	}
	
	/**
	 * Test move armies between countries.
	 */
	@Test
	public void testMoveArmiesBetweenCountriesNegative() {
		FortificationPhaseUtil.moveArmiesBetweenCountries(country1, country2, numberOfArmies, hashMap);
		assertEquals(11, country2.getCurrentNumberOfArmies());

	}

}
