package com.game.risk.core.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.game.risk.core.CountriesGraph;
import com.game.risk.core.strategy.PlayerStrategy;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * @author sohrab_singh
 *
 */
public class BenevolentPlayerStrategy implements PlayerStrategy {

	Player player;

	private CountriesGraph countriesGraph;

	/**
	 * @param player
	 * @param countriesGraph
	 * @param gamePhases
	 * 
	 */
	public BenevolentPlayerStrategy(Player player, CountriesGraph countriesGraph) {
		this.player = player;
		this.countriesGraph = countriesGraph;
	}

	@Override
	public void reinforce() {
		System.out.println("Reinforcement phase begins for Benevolent player");
		LoggingUtil.logMessage("Reinforcement phase begins for Benevolent player");
		List<Country> countriesOwned = player.getCountriesOwned();
		Collections.sort(countriesOwned, Country.ArmyComparator);
		System.out.println("Total number of reinforcement armies calculated for Benevolent player is " + player.findReinforcementArmies());
		player.setNumberOfArmies(player.getNumberOfArmies() + player.findReinforcementArmies());
		Country weakestCountry = countriesOwned.get(0);
		System.out.println("Benevolent player " + player.getNumberOfArmies() + " armies to weak country "
				+ weakestCountry.getCountryName());
		LoggingUtil.logMessage("Benevolent player " + player.getNumberOfArmies() + " armies to weak country "
				+ weakestCountry.getCountryName());
		weakestCountry.setCurrentNumberOfArmies(weakestCountry.getCurrentNumberOfArmies() + player.getNumberOfArmies());
		player.setNumberOfArmies(0);
	}

	@Override
	public void attack() {
		System.out.println("Benevolent player never attacks");
		LoggingUtil.logMessage("Benevolent player never attacks");
	}

	@Override
	public void fortify() {
		List<Country> countriesOwned = player.getCountriesOwned();
		Collections.sort(countriesOwned, Country.ArmyComparator);
		Country weakestCountry = countriesOwned.get(0);
		Country adjacentStrongCountry = getStrongestCountryAdjacent(weakestCountry, countriesOwned);
		if (adjacentStrongCountry != null) {
			int fortificationArmies = (adjacentStrongCountry.getCurrentNumberOfArmies()
					- weakestCountry.getCurrentNumberOfArmies()) / 2;
			weakestCountry.setCurrentNumberOfArmies(weakestCountry.getCurrentNumberOfArmies() + fortificationArmies);
			adjacentStrongCountry
					.setCurrentNumberOfArmies(adjacentStrongCountry.getCurrentNumberOfArmies() - fortificationArmies);
		} else {
			System.out.println("Skipping Fortification phase. Fortification move is not possible");
		}

	}

	private Country getStrongestCountryAdjacent(Country weakestCountry, List<Country> countriesOwned) {
		List<Country> strongCountriesAdjacent = new ArrayList<>();

		for (Country country : countriesOwned) {
			if (countriesGraph.getAdjListHashMap().get(weakestCountry).contains(country)) {
				strongCountriesAdjacent.add(country);
			}
		}
		if (strongCountriesAdjacent.isEmpty()) {
			return null;
		}
		Collections.sort(strongCountriesAdjacent, Country.ArmyComparator);
		return strongCountriesAdjacent.get(0);
	}

}
