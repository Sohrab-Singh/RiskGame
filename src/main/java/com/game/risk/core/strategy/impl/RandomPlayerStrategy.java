package com.game.risk.core.strategy.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.game.risk.RiskGamePhases;
import com.game.risk.core.CountriesGraph;
import com.game.risk.core.strategy.PlayerStrategy;
import com.game.risk.core.util.AttackPhaseUtil;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.ReinforcementPhaseUtil;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * The Class RandomPlayerStrategy.
 *
 * @author sohrab_singh
 * @author Sarthak
 */
public class RandomPlayerStrategy implements PlayerStrategy {

	/** The player. */
	Player player;

	/** The countries graph. */
	private CountriesGraph countriesGraph;

	/** The game phases. */
	private RiskGamePhases gamePhases;

	/**
	 * Instantiates a new random player strategy.
	 *
	 * @param player
	 *            the player
	 * @param countriesGraph
	 *            the countries graph
	 * @param gamePhases
	 *            the game phases
	 */
	public RandomPlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
	}

	@Override
	public void reinforce() {
		System.out.println("\n::::: Random Player :::::");
		System.out.println("\n:: Reinforcement Phase ::\n");
		LoggingUtil.logMessage("Reinforcement Phase begins for Random Player.");
		int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
		String message = "Total Reinforcement Armies available for Random Player: " + reinforcementArmies + "\n";
		System.out.println(message);
		LoggingUtil.logMessage(message);
		player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
		while (player.getNumberOfArmies() > 0) {
			Random random = new Random();
			int randomArmies = random.nextInt(player.getNumberOfArmies()) + 1;
			Random countryRandom = new Random();
			int randomCountryIndex = countryRandom.nextInt(player.getCountriesOwned().size());
			Country country = player.getCountriesOwned().get(randomCountryIndex);
			country.setCurrentNumberOfArmies(country.getCurrentNumberOfArmies() + randomArmies);
			System.out.println(country.getCountryName() + " now has " + country.getCurrentNumberOfArmies());
			LoggingUtil.logMessage(country.getCountryName() + " now has " + country.getCurrentNumberOfArmies());
			player.setNumberOfArmies(player.getNumberOfArmies() - randomArmies);
		}
	}

	@Override
	public void attack() {

		System.out.println("\n:: Attack Phase ::\n");
		LoggingUtil.logMessage("Attack Phase begins for Random Player.");

		Random random = new Random();
		Country attacker = player.getCountriesOwned().get(random.nextInt(player.getCountriesOwned().size()));
		List<Country> defenderList = new ArrayList<>();
		defenderList.clear();
		defenderList = findingDefenderListToAttack(attacker);

		if (attacker.getCurrentNumberOfArmies() >= 2) {

			if (!defenderList.isEmpty()) {
				Random random1 = new Random();
				Country defender = defenderList.get(random1.nextInt(defenderList.size()));
				Random randomAttacks = new Random();
				int attacksMaxCount = randomAttacks.nextInt(defender.getCurrentNumberOfArmies()) + 1;
				// Battle b/w Attacker and Defender Starts
				while (attacker.getCurrentNumberOfArmies() > 1 && defender.getCurrentNumberOfArmies() > 0
						&& (attacksMaxCount--) > 0) {
					String attackMessage = "[Attacker] " + attacker.getCountryName() + "("
							+ attacker.getCurrentNumberOfArmies() + ") vs (" + defender.getCurrentNumberOfArmies()
							+ ") " + defender.getCountryName() + " [Defender]";
					System.out.println(attackMessage);
					LoggingUtil.logMessage(attackMessage);

					int diceAttacker = getRandomDice(attacker, 3);
					int diceDefender = getRandomDice(attacker, 2);

					AttackPhaseUtil.startBattle(attacker, defender, diceAttacker, diceDefender);
					System.out.println("\n:: Armies Count after Attack ::");
					System.out.println("[Attacker] " + attacker.getCountryName() + "("
							+ attacker.getCurrentNumberOfArmies() + ")");
					System.out.println("[Defender] " + defender.getCountryName() + "("
							+ defender.getCurrentNumberOfArmies() + ")\n");
				}

				if (defender.getCurrentNumberOfArmies() == 0) {
					updateCountryToPlayer(defender, attacker);
					moveArmiesToDefender(defender, attacker);
				}

			}

		}
	}

	/**
	 * Move armies to defender.
	 *
	 * @param defender
	 *            the defender Country
	 * @param attacker
	 *            the attacker Country
	 */
	private void moveArmiesToDefender(Country defender, Country attacker) {
		int randomArmies = new Random().nextInt(attacker.getCurrentNumberOfArmies());
		if (randomArmies == attacker.getCurrentNumberOfArmies())
			randomArmies--;
		int defenderArmies = defender.getCurrentNumberOfArmies() + randomArmies;
		int attackerArmies = attacker.getCurrentNumberOfArmies() - randomArmies;
		defender.setCurrentNumberOfArmies(defenderArmies);
		attacker.setCurrentNumberOfArmies(attackerArmies);

	}

	@Override
	public void fortify() {
		System.out.println("\n:: Fortify Phase ::\n");
		LoggingUtil.logMessage("Fortify Phase begins for Random Player.");

		Random random = new Random();
		int randomCountry1Index = random.nextInt(player.getCountriesOwned().size());
		Country country1 = player.getCountriesOwned().get(randomCountry1Index);
		List<Country> adjPlayerCountries = findPlayerAdjacentCountries(country1);
		int randomCountry2Index = random.nextInt(adjPlayerCountries.size());

		Country country2 = adjPlayerCountries.get(randomCountry2Index);
		Random random1 = new Random();
		int randomArmies = random1.nextInt(country1.getCurrentNumberOfArmies()) + 1;

		if (randomArmies == country1.getCurrentNumberOfArmies())
			randomArmies--;
		String fortifyMessage = randomArmies + " Armies moved from " + country1.getCountryName() + " to "
				+ country2.getCountryName() + ".";
		System.out.println(fortifyMessage);
		LoggingUtil.logMessage(fortifyMessage);
		country1.setCurrentNumberOfArmies(country1.getCurrentNumberOfArmies() - randomArmies + 1);
		country2.setCurrentNumberOfArmies(country2.getCurrentNumberOfArmies() + randomArmies - 1);
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
	 * Update country to player after defender has lost battle
	 *
	 * @param defender
	 *            the defender Country
	 * @param attacker
	 *            the attacker Country
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
	 * @param attacker
	 *            the attacker
	 * @return the list
	 */
	private List<Country> findingDefenderListToAttack(Country attacker) {
		LinkedList<Country> adjCountries = new LinkedList<>();
		adjCountries.clear();
		adjCountries = countriesGraph.getAdjListHashMap().get(attacker);
		List<Country> defenderCountries = new ArrayList<>();
		for (int i = 0; i < adjCountries.size(); i++) {
			Country adjacentCountry = adjCountries.get(i);
			// Ignoring the player owned countries and finding the opponent
			if (!adjacentCountry.getPlayerName().equals(player.getPlayerName())) {
				defenderCountries.add(adjacentCountry);
			}
		}
		return defenderCountries;
	}

	/**
	 * Find player adjacent countries.
	 *
	 * @param country
	 *            the country
	 * @return the list
	 */
	private List<Country> findPlayerAdjacentCountries(Country country) {
		LinkedList<Country> adjCountries = countriesGraph.getAdjListHashMap().get(country);
		List<Country> adjPlayerCountries = new ArrayList<>();
		for (Country selectedCountry : adjCountries) {
			if (selectedCountry.getPlayerName().equals(country.getPlayerName())) {
				adjPlayerCountries.add(selectedCountry);
			}
		}
		return adjPlayerCountries;
	}

}
