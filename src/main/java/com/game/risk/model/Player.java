package com.game.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.game.risk.cardenum.CardEnum;

/**
 * Player object for populating the data related to player.
 * 
 * @author sohrab_singh
 *
 */
public class Player {

	/** Player name */
	private String playerName;

	/** Countries owned by the player */
	private List<Country> countriesOwned;

	/** Card List */
	private List<CardEnum> cardList;

	/** Number of armies */
	private int numberOfArmies;

	private double currentDominationPercentage;

	/** Player Constructor */
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
	public List<CardEnum> getCardList() {
		return cardList;
	}

	/**
	 * Set the card list.
	 *
	 * @param cardList
	 *            the cardList to set
	 */
	public void setCardList(List<CardEnum> cardList) {
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

	@Override
	public String toString() {
		return "Player [playerName=" + playerName + ", countriesOwned=" + countriesOwned + ", cardList=" + cardList
				+ ", numberOfArmies=" + numberOfArmies + "]";
	}

}