package com.game.risk.core.strategy.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import com.game.risk.RiskGamePhases;
import com.game.risk.core.CountriesGraph;
import com.game.risk.core.strategy.PlayerStrategy;
import com.game.risk.core.util.AttackPhaseUtil;
import com.game.risk.core.util.FortificationPhaseUtil;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.ReinforcementPhaseUtil;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * The Class AgressivePlayerStrategy.
 *
 * @author sohrab_singh
 * @author Sarthak
 */
public class AgressivePlayerStrategy implements PlayerStrategy {

	/** The player. */
	Player player;

	/** The strongest country. */
	private Country strongestCountry;

	/** The countries graph. */
	private CountriesGraph countriesGraph;

	/** The defender countries. */
	private List<Country> defenderCountries;

	/** The game phases. */
	private RiskGamePhases gamePhases;

	/**
	 * Instantiates a new agressive player strategy.
	 *
	 * @param player
	 *            the player
	 * @param countriesGraph
	 *            the countries graph
	 * @param gamePhases
	 *            the game phases
	 */
	public AgressivePlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
		defenderCountries = new ArrayList<>();
	}

	@Override
	public void reinforce() {
		System.out.println("::::: Aggressive Player :::::");
		System.out.println("\n:: Reinforcement Phase ::\n");
		System.out.println("\nReinforcement Phase begins for Aggressive Player \n");
		LoggingUtil.logMessage("\nReinforcement Phase begins for Aggressive Player \n");
		int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
		String message = "Total Reinforcement Armies available for Aggressive Player: " + reinforcementArmies;
		System.out.println(message);
		LoggingUtil.logMessage(message);
		player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
		strongestCountry = findStrongestCountry();
		strongestCountry
				.setCurrentNumberOfArmies(strongestCountry.getCurrentNumberOfArmies() + player.getNumberOfArmies());
		System.out.println(player.getPlayerName() + " assigned all the " + player.getNumberOfArmies()
				+ " Armies to the Strongest Country [" + strongestCountry.getCountryName() + "]");
		LoggingUtil.logMessage(player.getPlayerName() + " assigned all the " + player.getNumberOfArmies()
				+ " Armies to the Strongest Country [" + strongestCountry.getCountryName() + "]");
		player.setNumberOfArmies(0);

	}

	@Override
	public void attack() {
		System.out.println("\n:: Attack Phase ::\n");
		LoggingUtil.logMessage("Attack Phase begins for Aggressive Player.");
		List<Country> defenderList = findingDefenderListToAttack();
		Country attacker = strongestCountry;
		System.out.println("Attacking with the Strongest Country: " + strongestCountry.getCountryName());
		LoggingUtil.logMessage("Attacking with the Strongest Country: " + strongestCountry.getCountryName());
		for (Country defender : defenderList) {
			System.out.println("Defending with Country: " + defender.getCountryName());
			System.out.println(":: Before Battle Start ::");
			System.out.println("Attacker Armies: " + attacker.getCurrentNumberOfArmies());
			System.out.println("Defender Armies: " + defender.getCurrentNumberOfArmies());

			while (defender.getCurrentNumberOfArmies() > 0 && attacker.getCurrentNumberOfArmies() > 1) {
				int diceAttacker = getRandomDice(attacker, 3);
				int diceDefender = getRandomDice(attacker, 2);
				AttackPhaseUtil.startBattle(attacker, defender, diceAttacker, diceDefender);
			}

			if (defender.getCurrentNumberOfArmies() == 0) {
				System.out.println("Aggressive Player captured " + defender.getCountryName());
				updateCountryToPlayer(defender, attacker);
				player.setWinner(true);
			}
			if (attacker.getCurrentNumberOfArmies() == 1) {
				System.out.println("Aggressive Player lost the battle to " + defender.getCountryName());
				break;
			}

			if (AttackPhaseUtil.isattackEnds(player)) {
				break;
			}
		}
	}

	@Override
	public void fortify() {
		System.out.println("\n:: Fortification Phase ::\n");
		LoggingUtil.logMessage("Forrification Phase begins for Aggressive Player.");
		Country selectedCountry = null;
		int sumMaxArmies = 0;
		int sum = 0;
		for (Country country : countriesGraph.getAdjListHashMap().keySet()) {
			if (country.getPlayerName().equals(player.getPlayerName())) {
				sum = country.getCurrentNumberOfArmies();
				for (Country adjCountry : countriesGraph.getAdjListHashMap().get(country)) {
					sum += ((adjCountry.getCurrentNumberOfArmies() > 1
							&& adjCountry.getPlayerName().equals(player.getPlayerName()))
									? adjCountry.getCurrentNumberOfArmies()
									: 0);
				}

				if (sum > sumMaxArmies) {
					sumMaxArmies = sum;
					selectedCountry = country;
				}
			}
		}
		if (selectedCountry != null) {
			for (Country select : countriesGraph.getAdjListHashMap().get(selectedCountry)) {
				if (select.getPlayerName().equals(player.getPlayerName())) {
					int fortificationArmies = (select.getCurrentNumberOfArmies() > 1
							? select.getCurrentNumberOfArmies() - 1
							: 0);
					if (select.getCurrentNumberOfArmies() - fortificationArmies > 0) {
						FortificationPhaseUtil.moveArmiesBetweenCountries(select, selectedCountry, fortificationArmies,
								countriesGraph.getAdjListHashMap());
						LoggingUtil.logMessage(fortificationArmies + " Armies have been moved from "
								+ select.getCountryName() + " to " + selectedCountry.getCountryName());
					}
				}
			}
		} else {
			LoggingUtil.logMessage("No Armies have been moved in Fortification Phase");
		}

	}

	/**
	 * Find strongest country.
	 *
	 * @return the country
	 */
	private Country findStrongestCountry() {
		Country strongestCountry = player.getCountriesOwned().get(0);
		for (Country country : player.getCountriesOwned()) {
			if (country.getCurrentNumberOfArmies() > strongestCountry.getCurrentNumberOfArmies()) {
				strongestCountry = country;
			}
		}
		return strongestCountry;
	}

	/**
	 * Gets the random dice.
	 *
	 * @param attacker
	 *            the attacker
	 * @param maxDice
	 *            the max dice
	 * @return the random dice
	 */
	private int getRandomDice(Country attacker, int maxDice) {
		int attackerArmies = attacker.getCurrentNumberOfArmies();
		Random random;
		int diceCount;
		if (attackerArmies <= maxDice) {
			random = new Random();
			diceCount = 1 + random.nextInt(attackerArmies - 1);
		} else {
			random = new Random();
			diceCount = 1 + random.nextInt(maxDice);
		}
		return diceCount;
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
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getPlayerName().equals(defender.getPlayerName())) {
				playerList.get(i).removeCountry(defender);
			} else if (playerList.get(i).getPlayerName().equals(attacker.getPlayerName())) {
				playerList.get(i).addCountry(defender);
			}
		}
		defender.setPlayerName(attacker.getPlayerName());
	}

	/**
	 * Finding defender list to attack.
	 *
	 * @return the list
	 */
	private List<Country> findingDefenderListToAttack() {
		LinkedList<Country> adjCountries = countriesGraph.getAdjListHashMap().get(strongestCountry);
		for (int i = 0; i < adjCountries.size(); i++) {
			Country adjacentCountry = adjCountries.get(i);
			// Ignoring the player owned countries and finding the opponent
			if (!adjacentCountry.getPlayerName().equals(player.getPlayerName())) {
				defenderCountries.add(adjacentCountry);
			}
		}
		return defenderCountries;
	}

}
