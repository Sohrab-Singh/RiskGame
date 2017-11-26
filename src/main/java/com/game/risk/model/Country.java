package com.game.risk.model;

/**
 * Country object for populating the data related to country.
 *
 * @author sohrab_singh
 */
public class Country {

	/** Country Name. */
	private String countryName;

	/** X coordinate */
	private String xCoordinate;

	/** Y coordinate */
	private String yCoordinate;

	/** Owner of this country */
	private String playerName;

	/**
	 * Current number of armies in the country.
	 */
	private int currentNumberOfArmies;

	/**
	 * Name of the continent it belongs to
	 */
	private String continentName;

	/**
	 * if the node is visited during DFS
	 */

	private boolean isVisited;

	/**
	 * @param countryName
	 */
	public Country(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Get the country name.
	 *
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Set the country name.
	 *
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Get the current number of armies.
	 *
	 * @return the currentNumberOfArmies
	 */
	public int getCurrentNumberOfArmies() {
		return currentNumberOfArmies;
	}

	/**
	 * Set the current number of armies.
	 *
	 * @param currentNumberOfArmies
	 *            the currentNumberOfArmies to set
	 */
	public void setCurrentNumberOfArmies(int currentNumberOfArmies) {
		this.currentNumberOfArmies = currentNumberOfArmies;
	}

	/**
	 * Get the Continent Name it belongs to
	 *
	 * @return continentName
	 */
	public String getContinentName() {
		return continentName;
	}

	/**
	 * Set the Continent Name
	 *
	 * @param continentName
	 *            name of continent
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * Get the x coordinate.
	 * 
	 * @return the xCoordinate
	 */
	public String getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * Set the x coordinate.
	 * 
	 * @param xCoordinate
	 *            the xCoordinate to set
	 */
	public void setxCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Get the y coordinate.
	 * 
	 * @return the yCoordinate
	 */
	public String getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * Set the y coordinate.
	 * 
	 * @param yCoordinate
	 *            the yCoordinate to set
	 */
	public void setyCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
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
	 * Set the isVisited parameter
	 * @param visited
	 */
	public void setVisited(boolean visited) {
		isVisited = visited;
	}

	/**
	 * get the is isVisited parameter
	 * @return isVisited
	 */

	public boolean isVisited() {
		return isVisited;
	}

	/*
	 * Decrease an army in a battle lost
	 */
	public void looseArmy() {
		currentNumberOfArmies--;
	}

	/**
	 * Increase an army in a battle won
	 */
	public void addArmy() {
		currentNumberOfArmies++;
	}
}

