package com.game.risk.core.util;

import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * Reinforcement phase utility class contains all the utility methods used in
 * Reinforcement phase.
 * 
 * @author sohrab_singh
 *
 */
public class ReinforcementPhaseUtil {

	/**
	 * Calculate Reinforcement armies based on various factors such as countries
	 * owned , Continent control value and it will have minimum value of 3.
	 * 
	 * @param player
	 *            current player
	 * @param continent
	 *            continent
	 * @return reinforcement armies
	 * 
	 */
	public static int calculateReinforcementArmies(Player player, Continent continent) {
		int countriesOwned = player.getCountriesOwned().size();
		int reinfoArmies = (int) Math.floor(countriesOwned / 3);

		// If player owns all the countries of continents then reinforcement armies will
		// be the control value of continent.
		if (checkPlayerOwnsWholeContinent(player) && continent != null) {
			reinfoArmies = continent.getControlValue();
		}

		// Minimum number of armies for any player in case reinforcement armies are less
		// than 3.
		if (reinfoArmies < 3) {
			reinfoArmies = 3;
		}
		return reinfoArmies;
	}

	/**
	 * Method to check whether players owns whole continent or not.
	 * 
	 * @param player
	 *            current player
	 * @return true if player owns all the countries of continent.
	 */
	private static boolean checkPlayerOwnsWholeContinent(Player player) {
		Country country1 = player.getCountriesOwned().get(0);
		for (Country country : player.getCountriesOwned()) {
			if (!country.getContinentName().equals(country1.getContinentName())) {
				return false;
			}
		}
		return true;
	}
}
