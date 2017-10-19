package com.game.risk.core.util;

import java.util.HashMap;
import java.util.LinkedList;

import com.game.risk.model.Country;

/**
 * @author sohrab_singh
 *
 */
public class FortificationPhaseUtil {
	
	/**
	 * @param country1
	 * @param country2
	 * @param numberOfArmies
	 * @param adjacentMap 
	 */
	public static void moveArmiesBetweenCountries(Country country1, Country country2, int numberOfArmies,
			HashMap<Country, LinkedList<Country>> adjacentMap) {
		if (adjacentMap.get(country1).contains(country2)) {
			country1.setCurrentNumberOfArmies(country1.getCurrentNumberOfArmies() - numberOfArmies);
			country2.setCurrentNumberOfArmies(country2.getCurrentNumberOfArmies() + numberOfArmies);
		}

	}

}
