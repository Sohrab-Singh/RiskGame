package com.game.risk.core.strategy.impl;

import java.util.ArrayList;
import java.util.Collections;
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
 * @author Sarthak
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

			int diceAttacker = getRandomDice(attacker, 3);
			int diceDefender = getRandomDice(attacker, 2);
			while (defender.getCurrentNumberOfArmies() != 0 && attacker.getCurrentNumberOfArmies() != 1) {
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
		}
	}

	@Override
	public void fortify() {
		System.out.println("\n:: Fortification Phase ::\n");
		LoggingUtil.logMessage("Forrification Phase begins for Aggressive Player.");
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
