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
 * @author sohrab_singh
 *
 */
public class RandomPlayerStrategy implements PlayerStrategy {

	Player player;

	private CountriesGraph countriesGraph;

	private RiskGamePhases gamePhases;

	/**
	 * @param player
	 * @param countriesGraph
	 * @param gamePhases
	 * 
	 */
	public RandomPlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
	}

	@Override
	public void reinforce() {
		System.out.println("\nReinforcement phase begins for random player \n");
		LoggingUtil.logMessage("\nReinforcement phase begins for random player \n");
		int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
		String message = "Total reinforcement armies available for random player : " + reinforcementArmies;
		System.out.println(message);
		LoggingUtil.logMessage(message);
		player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
		while (player.getNumberOfArmies() > 0) {
			Random random = new Random();
			int randomArmies = random.nextInt(player.getNumberOfArmies()) + 1;
			Random random1 = new Random();
			int randomCountryIndex = random1.nextInt(player.getCountriesOwned().size());
			Country country = player.getCountriesOwned().get(randomCountryIndex);
			country.setCurrentNumberOfArmies(country.getCurrentNumberOfArmies() + randomArmies);
			player.setNumberOfArmies(player.getNumberOfArmies() - randomArmies);
		}
	}

	@Override
	public void attack() {
		Random rounds = new Random();
		int noOfRounds = 1 + rounds.nextInt(5);
		while (noOfRounds > 0) {
			Random random = new Random();
			Country attacker = player.getCountriesOwned().get(random.nextInt(player.getCountriesOwned().size()));
			List<Country> defenderList = findingDefenderListToAttack(attacker);

			if (attacker.getCurrentNumberOfArmies() >= 2) {

				if (defenderList.isEmpty()) {
					System.out.println("Attack not possible:No adjacent country to attack");
					LoggingUtil.logMessage("Attack not possible:No adjacent country to attack");
				} else {
					Random random1 = new Random();
					Country defender = defenderList.get(random1.nextInt(defenderList.size()));
					System.out.println("Attacker Armies: " + attacker.getCurrentNumberOfArmies());
					System.out.println("Defender Armies: " + defender.getCurrentNumberOfArmies());

					int diceAttacker = getRandomDice(attacker, 3);
					int diceDefender = getRandomDice(attacker, 2);

					AttackPhaseUtil.startBattle(attacker, defender, diceAttacker, diceDefender);

					if (defender.getCurrentNumberOfArmies() == 0) {
						updateCountryToPlayer(defender, attacker);
					}
				}
				noOfRounds--;
			}

		}
	}

	@Override
	public void fortify() {

		Random random = new Random();
		int randomCountry1Index = random.nextInt(player.getCountriesOwned().size());
		int randomCountry2Index = random.nextInt(player.getCountriesOwned().size());
		Country country1 = player.getCountriesOwned().get(randomCountry1Index);
		Country country2 = player.getCountriesOwned().get(randomCountry2Index);
		Random random1 = new Random();
		int randomArmies = random1.nextInt(country1.getCurrentNumberOfArmies()) + 1;

		while (!countriesGraph.getAdjListHashMap().get(country1).contains(country2)) {
			randomCountry2Index = random.nextInt(player.getCountriesOwned().size());
			country2 = player.getCountriesOwned().get(randomCountry2Index);
		}
		country1.setCurrentNumberOfArmies(country1.getCurrentNumberOfArmies() - randomArmies);
		country2.setCurrentNumberOfArmies(country2.getCurrentNumberOfArmies() + randomArmies);
	}

	/**
	 * @param attacker,
	 *            int maxDice
	 * @param attackerArmies
	 * @return
	 */
	private int getRandomDice(Country attacker, int maxDice) {
		int attackerArmies = attacker.getCurrentNumberOfArmies();
		Random random;
		int diceCount ;
		if (attackerArmies <= maxDice) {
			random = new Random();
			diceCount = 1 + random.nextInt(attackerArmies - 1);
		} else {
			random = new Random();
			diceCount = 1 + random.nextInt(maxDice);
		}
		return diceCount;
	}

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
	 * 
	 */
	private List<Country> findingDefenderListToAttack(Country attacker) {
		LinkedList<Country> adjCountries = countriesGraph.getAdjListHashMap().get(attacker);
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

}
