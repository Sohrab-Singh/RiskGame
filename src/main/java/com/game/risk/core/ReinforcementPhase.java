package com.game.risk.core;

import java.util.HashMap;
import java.util.LinkedList;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * @author sohrab_singh
 *
 */
public class ReinforcementPhase {

	/**
	 * @param player
	 * @param continentName
	 * @return reinforcement armies.
	 * 
	 */
	public static int calculateReinforcementArmies(Player player, Continent continentName) {
		int countriesOwned = player.getCountriesOwned().size();
		int reinfoArmies = countriesOwned / 3;

		// If player owns all the countries of continents then reinforcement armies will
		// be the control value of continent.
		if (checkPlayerOwnsWholeContinent(player.getPlayerName(), continentName)) {
			reinfoArmies = continentName.getControlValue();
		}

		// Minimum number of armies for any player in case reinforcement armies are less
		// than 3.
		if (reinfoArmies < 3) {
			reinfoArmies = 3;
		}
		return reinfoArmies;
	}

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

	/**
	 * 
	 * @param playerName
	 * @param continentName
	 * @return true if player owns all the countries of continent.
	 */
	private static boolean checkPlayerOwnsWholeContinent(String playerName, Continent continentName) {
		for (Country country : continentName.getCountries()) {
			if (!country.getPlayerName().equals(playerName)) {
				return false;
			}
		}
		return true;
	}
}
