package com.game.risk.model;

import java.util.List;

import com.game.risk.cardenum.CardEnum;

/**
 * Player object for populating the data related to player.
 * 
 * @author sohrab_singh
 *
 */
public class Player {
	/**
	 * Player name.
	 */
	private String playerName;

	/**
	 * Countries owned
	 */
	private List<Country> countriesOwned;

	/**
	 * Card List.
	 */
	private List<CardEnum> cardList;

	/**
	 * Number of armies.
	 */
	private int numberOfArmies;

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
	 * @param playerName the playerName to set
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
	 * @param countriesOwned the countriesOwned to set
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
	 * @param cardList the cardList to set
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
	 * @param numberOfArmies the numberOfArmies to set
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

	/**
	 * Add country to the CountriesOwend list
	 *
	 * @param country
	 */
	public void addCountry(Country country) {
		this.countriesOwned.add(country);
	}
}