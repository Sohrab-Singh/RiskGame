package com.game.risk.core.strategy.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.game.risk.RiskGamePhases;
import com.game.risk.core.CountriesGraph;
import com.game.risk.core.strategy.PlayerStrategy;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * @author sohrab_singh
 *
 */
public class CheaterPlayerStrategy implements PlayerStrategy {

	Player player;

	private CountriesGraph countriesGraph;

	private RiskGamePhases gamePhases;

	/**
	 * @param player
	 * @param countriesGraph
	 * @param gamePhases
	 * 
	 */
	public CheaterPlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
	}

	@Override
	public void reinforce() {
		for (Country country : player.getCountriesOwned()) {
			country.setCurrentNumberOfArmies(2 * country.getCurrentNumberOfArmies());
		}
	}

	@Override
	public void attack() {
		List<Country> countries = new ArrayList<>();
		for (Country country : player.getCountriesOwned()) {
			countries.add(country);
		}
		for (Country attacker : countries) {
			List<Country> defenderList = findingNeighboursOfOtherPlayers(attacker);
			if (!defenderList.isEmpty()) {
				for (Country defender : defenderList) {
					updateCountryToPlayer(defender, attacker);
				}
			}
		}
	}

	@Override
	public void fortify() {
		for (Country country : player.getCountriesOwned()) {
			List<Country> defenderList = findingNeighboursOfOtherPlayers(country);
			if (!defenderList.isEmpty()) {
				for (Country country2 : defenderList) {
					country2.setCurrentNumberOfArmies(2 * country2.getCurrentNumberOfArmies());
				}
			}
		}

	}

	private void updateCountryToPlayer(Country defender, Country attacker) {
		List<Player> playerList = gamePhases.getPlayerList();
		Iterator<Player> playerIterator = playerList.iterator();
		while (playerIterator.hasNext()) {
			Player aplayer = playerIterator.next();
			if (aplayer.getPlayerName().equals(defender.getPlayerName())) {
				aplayer.removeCountry(defender);
			} else if (aplayer.getPlayerName().equals(attacker.getPlayerName())) {
				aplayer.addCountry(defender);
			}
		}
		defender.setPlayerName(attacker.getPlayerName());
	}

	/**
	 * 
	 */
	private List<Country> findingNeighboursOfOtherPlayers(Country attacker) {
		LinkedList<Country> adjCountries = countriesGraph.getAdjListHashMap().get(attacker);
		List<Country> defenderCountries = new ArrayList<>();
		if (adjCountries != null) {
			for (int i = 0; i < adjCountries.size(); i++) {
				Country adjacentCountry = adjCountries.get(i);
				// Ignoring the player owned countries and finding the opponent
				if (!adjacentCountry.getPlayerName().equals(attacker.getPlayerName())) {
					defenderCountries.add(adjacentCountry);
				}
			}
		}
		return defenderCountries;
	}
}
