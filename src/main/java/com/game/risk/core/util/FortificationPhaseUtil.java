package com.game.risk.core.util;

import java.util.HashMap;
import java.util.LinkedList;

import com.game.risk.model.Country;

/**
 * 
 * Fortification phase utility class contains utility methods for the
 * fortification phase.
 * 
 * @author sohrab_singh
 *
 */
public class FortificationPhaseUtil {

	/**
	 * Method to move armies between countries owned by particular player.
	 * 
	 * @param country1
	 *            country from where army to be moved
	 * @param country2
	 *            country to which army to be moved.
	 * @param numberOfArmies
	 *            number of armies to move
	 * @param adjacentMap
	 *            adjacent map to check adjacency between countries
	 */
	public static void moveArmiesBetweenCountries(Country country1, Country country2, int numberOfArmies,
			HashMap<Country, LinkedList<Country>> adjacentMap) {
		if (adjacentMap.get(country1).contains(country2)) {
			country1.setCurrentNumberOfArmies(country1.getCurrentNumberOfArmies() - numberOfArmies);
			country2.setCurrentNumberOfArmies(country2.getCurrentNumberOfArmies() + numberOfArmies);
		}

	}

}
