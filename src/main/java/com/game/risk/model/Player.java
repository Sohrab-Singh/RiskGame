package com.game.risk.model;

import java.util.List;

import com.game.risk.cardenum.CardEnum;

public class Player {

	private String playerName;
	
	private List<Country> countriesOwned;
	
	private List<CardEnum> cardList;
	
	private int numberOfArmies;

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the countriesOwned
	 */
	public List<Country> getCountriesOwned() {
		return countriesOwned;
	}

	/**
	 * @param countriesOwned the countriesOwned to set
	 */
	public void setCountriesOwned(List<Country> countriesOwned) {
		this.countriesOwned = countriesOwned;
	}

	/**
	 * @return the cardList
	 */
	public List<CardEnum> getCardList() {
		return cardList;
	}

	/**
	 * @param cardList the cardList to set
	 */
	public void setCardList(List<CardEnum> cardList) {
		this.cardList = cardList;
	}

	/**
	 * @return the numberOfArmies
	 */
	public int getNumberOfArmies() {
		return numberOfArmies;
	}

	/**
	 * @param numberOfArmies the numberOfArmies to set
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}
}
