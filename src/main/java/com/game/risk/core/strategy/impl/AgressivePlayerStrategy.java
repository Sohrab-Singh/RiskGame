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
 * @author sohrab_singh
 *
 */
public class AgressivePlayerStrategy implements PlayerStrategy {

	Player player;

	private Country strongestCountry;

	private CountriesGraph countriesGraph;

	private List<Country> defenderCountries;

	private RiskGamePhases gamePhases;

	/**
	 * @param player
	 * @param countriesGraph
	 * @param gamePhases
	 * 
	 */
	public AgressivePlayerStrategy(Player player, CountriesGraph countriesGraph, RiskGamePhases gamePhases) {
		this.player = player;
		this.countriesGraph = countriesGraph;
		this.gamePhases = gamePhases;
		defenderCountries = new ArrayList<>();
	}

	@Override
	public void reinforce() {
		System.out.println("\nReinforcement phase begins for aggressive player \n");
		LoggingUtil.logMessage("\nReinforcement phase begins for aggressive player \n");
		int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
		String message = "Total reinforcement armies available for aggressive player : " + reinforcementArmies;
		System.out.println(message);
		LoggingUtil.logMessage(message);
		player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
		strongestCountry = findStrongestCountry();
		System.out.println("Agressive player assigned all the armies to the strogest country " + strongestCountry);
		LoggingUtil.logMessage("Agressive player assigned all the armies to the strogest country " + strongestCountry);
		strongestCountry
				.setCurrentNumberOfArmies(strongestCountry.getCurrentNumberOfArmies() + player.getNumberOfArmies());
		player.setNumberOfArmies(0);

	}

	@Override
	public void attack() {
		List<Country> defenderList = findingDefenderListToAttack();
		Country attacker = strongestCountry;
		for (Country defender : defenderList) {
			System.out.println("\n:: Before Battle Start ::");
			System.out.println("Attacker Armies: " + attacker.getCurrentNumberOfArmies());
			System.out.println("Defender Armies: " + defender.getCurrentNumberOfArmies());

			int diceAttacker = getRandomDice(attacker, 3);
			int diceDefender = getRandomDice(attacker, 2);

			AttackPhaseUtil.startBattle(attacker, defender, diceAttacker, diceDefender);

			if (defender.getCurrentNumberOfArmies() == 0) {
				updateCountryToPlayer(defender, attacker);
				player.setWinner(true);
			}
			if (attacker.getCurrentNumberOfArmies() == 0) {
				break;
			}
		}
	}

	@Override
	public void fortify() {
		Country secondStrongest = countriesGraph.getAdjListHashMap().get(strongestCountry).get(0);
		for (Country country : countriesGraph.getAdjListHashMap().get(strongestCountry)) {
			if (country.getCurrentNumberOfArmies() > secondStrongest.getCurrentNumberOfArmies()) {
				country = secondStrongest;
			}
		}
		int fortificationArmies = secondStrongest.getCurrentNumberOfArmies() - 1;

		FortificationPhaseUtil.moveArmiesBetweenCountries(secondStrongest, strongestCountry, fortificationArmies,
				countriesGraph.getAdjListHashMap());
		LoggingUtil.logMessage(
				fortificationArmies + " armies has been moved from " + secondStrongest + " to " + strongestCountry);
	}

	/**
	 * 
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
	 * @param attacker,
	 *            int maxDice
	 * @param attackerArmies
	 * @return
	 */
	private int getRandomDice(Country attacker, int maxDice) {
		int attackerArmies = attacker.getCurrentNumberOfArmies();
		Random random;
		if (attackerArmies <= maxDice) {
			random = new Random(attackerArmies);
		} else {
			random = new Random(maxDice);
		}
		int diceCount = random.nextInt();
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
