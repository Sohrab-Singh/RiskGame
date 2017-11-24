package com.game.risk.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.game.risk.core.MapFileReader;
import com.game.risk.core.util.AttackPhaseUtil;
import com.game.risk.core.util.FortificationPhaseUtil;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.ReinforcementPhaseUtil;

/**
 * Player object for populating the data related to player.
 * 
 * @author sohrab_singh
 * @author Sarthak
 */
public class Player extends Observable {

	/** Player name */
	private String playerName;

	/** Countries owned by the player */
	private List<Country> countriesOwned;

	/** Card List */
	private List<Card> cardList;

	/** Number of armies */
	private int numberOfArmies;

	/** Current Domination Percentage for Player Domination View */
	private double currentDominationPercentage;
	
	/**
	 * MapFileReader Object
	 */
	private MapFileReader fileParser;

	/**
	 * Indicated the Number of Recent Attack Wins to update Card received in a turn
	 */
	private boolean isWinner = false;

	/** Indicate the exchanged armies for the cards */
	private int exchangedArmies;

	/** Player Constructor 
	 * @param fileParser */
	public Player() {
		countriesOwned = new ArrayList<>();
	}

	/**
	 * @return the currentDominationPercentage
	 */
	public double getCurrentDominationPercentage() {
		return currentDominationPercentage;
	}

	/**
	 * @param currentDominationPercentage
	 *            the currentDominationPercentage to set
	 */
	public void setCurrentDominationPercentage(double currentDominationPercentage) {
		this.currentDominationPercentage = currentDominationPercentage;
	}

	/**
	 * Get the player name.
	 *
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the player name.
	 *
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Get the countries owned by player.
	 *
	 * @return the countriesOwned
	 */
	public List<Country> getCountriesOwned() {
		return countriesOwned;
	}

	/**
	 * Set the countries owned by player.
	 *
	 * @param countriesOwned
	 *            the countriesOwned to set
	 */
	public void setCountriesOwned(List<Country> countriesOwned) {
		this.countriesOwned = countriesOwned;
	}

	/**
	 * Get the card list.
	 *
	 * @return the cardList
	 */
	public List<Card> getCardList() {
		return cardList;
	}

	/**
	 * Set the card list.
	 *
	 * @param cardList
	 *            the cardList to set
	 */
	public void setCardList(List<Card> cardList) {
		this.cardList = cardList;
	}

	/**
	 * Get the number of armies.
	 *
	 * @return the numberOfArmies
	 */
	public int getNumberOfArmies() {
		return numberOfArmies;
	}

	/**
	 * Set the number of armies.
	 *
	 * @param numberOfArmies
	 *            the numberOfArmies to set
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

	/**
	 * @return Nummber of Countries Owned by player
	 */
	public int getNumberOfCountriesOwned() {
		return countriesOwned.size();
	}

	/**
	 * Add country to the CountriesOwend list
	 *
	 * @param country
	 */
	public void addCountry(Country country) {
		this.countriesOwned.add(country);
	}

	/**
	 * Remove a country from the CountriesOwned list
	 * 
	 * @param country
	 * @return true or false
	 */
	public boolean removeCountry(Country country) {
		return countriesOwned.remove(country);
	}


	/**
	 * @param player
	 * @param reader
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void startReinforcementPhase(Player player, BufferedReader reader)
			throws NumberFormatException, IOException {
		System.out.println("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
		LoggingUtil.logMessage("\nReinforcement phase begins for " + player.getPlayerName() + "\n");

		if (player.getNumberOfArmies() > 0) {
			for (Country country : player.getCountriesOwned()) {
				System.out.println(
						"How many armies do you want to assign to your country " + country.getCountryName() + " ?");
				System.out.println("Current number of armies of " + country.getCountryName() + " is "
						+ country.getCurrentNumberOfArmies() + " | Available armies : " + player.getNumberOfArmies());
				int armies = Integer.parseInt(reader.readLine());
				player.assignArmiesToCountries(country, armies);
			}
		}
	}

	/**
	 * @param reader
	 * @param country1 
	 * @param country2 
	 * @throws NumberFormatException 
	 * @throws IOException
	 */
	public void startFortificationPhase(BufferedReader reader,Country country1 , Country country2) throws NumberFormatException, IOException {
		boolean countryFlag = true;
		int fortificationArmies = 0;
		while (countryFlag) {
			countryFlag = false;
			System.out.println("Enter the Number of Armies to Move");
			fortificationArmies = Integer.parseInt(reader.readLine());
			if (fortificationArmies > country1.getCurrentNumberOfArmies()) {
				System.out.println("You can not enter more armies than donor country have");
				System.out.println("Enter again");
				countryFlag = true;
			}
		}

		FortificationPhaseUtil.moveArmiesBetweenCountries(country1, country2, fortificationArmies,
				fileParser.getCountriesGraph().getAdjListHashMap());
		LoggingUtil.logMessage(fortificationArmies + " armies has been moved from " + country1 + " to " + country2);
		
	}
	
	/**
	 * After initializing the armies, remaining armies will be given to countries
	 * owned by users.
	 *
	 * @param selectedCountry
	 *            is the country which the player has selected to put an army on
	 * @param numberOfArmies
	 *            number of armies to assign
	 */
	public void assignArmiesToCountries(Country selectedCountry, int numberOfArmies) {
		
		if ((this.getNumberOfArmies()) > 0 && this.getNumberOfArmies() >= numberOfArmies) {
			if (this.getCountriesOwned().contains(selectedCountry)) {
				selectedCountry.setCurrentNumberOfArmies(selectedCountry.getCurrentNumberOfArmies() + numberOfArmies);
				this.setNumberOfArmies(this.getNumberOfArmies() - numberOfArmies);
			} else {
				System.out.println("This country does not belong to you!");
			}
		} else {
			System.out.println("You don't have any army!");
		}
	}
	
	/**
	 * @return
	 */
	public int findReinforcementArmies() {
		System.out.println("\nReinforcement phase begins for " + this.getPlayerName() + "\n");
		LoggingUtil.logMessage("\nReinforcement phase begins for " + this.getPlayerName() + "\n");
		int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(this);
		String message = "Total reinforcement armies available for " + this.getPlayerName() + " : "
				+ reinforcementArmies;
		System.out.println(message);
		LoggingUtil.logMessage(message);
		return reinforcementArmies;
	}

	/**
	 * @param attacker
	 * @param defender
	 * @param diceAttacker
	 * @param diceDefender
	 */
	public void attackOpponent(Country attacker, Country defender, int diceAttacker, int diceDefender) {
		System.out.println("\n:: Before Battle Start ::");
		System.out.println("Attacker Armies: " + attacker.getCurrentNumberOfArmies());
		System.out.println("Defender Armies: " + defender.getCurrentNumberOfArmies());
		AttackPhaseUtil.startBattle(attacker, defender, diceAttacker, diceDefender);
	}


	/**
	 * @return the isWinner
	 */
	public boolean isWinner() {
		return isWinner;
	}

	/**
	 * @param isWinner the isWinner to set
	 */
	public void setWinner(boolean isWinner) {
		this.isWinner = isWinner;
	}

	/**
	 * @return
	 */
	public int getExchangedArmies() {
		return exchangedArmies;
	}

	/**
	 * @param exchangedArmies
	 */
	public void setExchangedArmies(int exchangedArmies) {
		this.exchangedArmies = exchangedArmies;
	}
}