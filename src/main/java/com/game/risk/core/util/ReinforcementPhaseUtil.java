package com.game.risk.core.util;

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
	 * 
	 * @param player
	 *            current player
	 * @param continent
	 *            continent
	 * @return reinforcement armies
	 * 
	 */
	public static int calculateReinforcementArmies(Player player) {
		int countriesOwned = player.getCountriesOwned().size();
		int reinfoArmies = (int) Math.floor(countriesOwned / 3);

		if (player.getExchangedArmies() != 0)
			return reinfoArmies + (5*player.getExchangedArmies());
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
