package com.game.risk.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.game.risk.core.MapFileReader;
import com.game.risk.model.Country;

/**
 * 
 * Fortification phase utility class contains utility methods for the
 * fortification phase.
 * 
 * @author sohrab_singh
 * @author Sarthak
 */
public class FortificationPhaseUtil {

	/*** Hyphen seperator */
	private static final String SEPERATOR = " - ";

	/**
	 * Method to move armies between countries owned by particular player.
	 * 
	 * @param country1
	 *            country from where army to be moved
	 * @param country2
	 *            country to which army to be moved.
	 * @param numberOfArmies
	 *            number of armies to move
	 * @param adjacentMap
	 *            adjacent map to check adjacency between countries
	 */
	public static void moveArmiesBetweenCountries(Country country1, Country country2, int numberOfArmies,
			HashMap<Country, LinkedList<Country>> adjacentMap) {
		if (adjacentMap.get(country1).contains(country2)) {
			country1.setCurrentNumberOfArmies(country1.getCurrentNumberOfArmies() - numberOfArmies);
			country2.setCurrentNumberOfArmies(country2.getCurrentNumberOfArmies() + numberOfArmies);
		}

	}

	/**
	 * Retrieve its adjacent countries and ask the user for input
	 * 
	 * @param country
	 *            Country object
	 * @param reader
	 *            BufferedReader object
	 * @param fileReader
	 *            MapFileReader object
	 * @return the country object indicating the country name entered by player
	 * @throws IOException
	 */
	public static Country retrieveAndSelectAdjacentArmies(Country country, BufferedReader reader,
			MapFileReader fileReader) throws IOException {
		HashMap<Country, LinkedList<Country>> adjacentMap = fileReader.getCountriesGraph().getAdjListHashMap();
		for (int i = 0; i < adjacentMap.get(country).size(); i++) {
			System.out.print(adjacentMap.get(country).get(i).getCountryName()
					+ ((i != adjacentMap.size() - 1) ? SEPERATOR : ""));
		}
		System.out.println();
		String countryName = reader.readLine();
		return fileReader.getCountriesHashMap().get(countryName);
	}
}
