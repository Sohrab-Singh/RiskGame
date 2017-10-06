package com.game.risk.model;

/**
 * Country object for populating the data related to country.
 *
 * @author sohrab_singh
 */
public class Country {

	/**
	 * Country Name.
	 */
	private String countryName;

	/** X coordinate */
	private String xCoordinate;

	/** Y coordinate */
	private String yCoordinate;

	/**
	 * Current number of armies in the country.
	 */
	private int currentNumberOfArmies;

	/**
	 * Name of the continent it belongs to
	 */
	private String continentName;

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
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * @return the xCoordinate
	 */
	public String getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @param xCoordinate
	 *            the xCoordinate to set
	 */
	public void setxCoordinate(String xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public String getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @param yCoordinate
	 *            the yCoordinate to set
	 */
	public void setyCoordinate(String yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
}
