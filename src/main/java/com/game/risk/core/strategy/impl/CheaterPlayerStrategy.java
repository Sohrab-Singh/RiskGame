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
 * The Class CheaterPlayerStrategy.
 *
 * @author sohrab_singh
 */
public class CheaterPlayerStrategy implements PlayerStrategy {

	/** The player. */
	Player player;

	/** The countries graph. */
	private CountriesGraph countriesGraph;

	/** The game phases. */
	private RiskGamePhases gamePhases;

	/**
	 * Instantiates a new cheater player strategy.
	 *
	 * @param player
	 *            the player
	 * @param countriesGraph
	 *            the countries graph
	 * @param gamePhases
	 *            the game phases
	 */
	public CheaterPlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.game.risk.core.strategy.PlayerStrategy#reinforce()
	 */
	@Override
	public void reinforce() {
		for (Country country : player.getCountriesOwned()) {
			country.setCurrentNumberOfArmies(2 * country.getCurrentNumberOfArmies());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.game.risk.core.strategy.PlayerStrategy#attack()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.game.risk.core.strategy.PlayerStrategy#fortify()
	 */
	@Override
	public void fortify() {
		for (Country country : player.getCountriesOwned()) {
			List<Country> defenderList = findingNeighboursOfOtherPlayers(country);
			if (!defenderList.isEmpty()) {
				country.setCurrentNumberOfArmies(2 * country.getCurrentNumberOfArmies());
			}
		}

	}

	/**
	 * Update country to player.
	 *
	 * @param defender
	 *            the defender
	 * @param attacker
	 *            the attacker
	 */
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
	 * Finding neighbours of other players.
	 *
	 * @param attacker
	 *            the attacker
	 * @return the list
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
