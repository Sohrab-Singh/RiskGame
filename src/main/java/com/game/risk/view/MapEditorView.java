package com.game.risk.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

import com.game.risk.core.MapFileReader;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;

/**
 * Editor View Class
 * 
 * @author Sarthak
 * @author sohrab_singh
 */
public class MapEditorView {

	/*** Hyphen seperator */
	private static final String SEPERATOR = " - ";

	/** Comma seperator. */
	private static final String COMMA_SEPERATOR = ", ";

	/** DEFAULT country coordinate value. */
	private static final String DEFAULT_COUNTRY_COORDINATE_VALUE = "NULL";

	/** Parser to changing the data stored. */
	private MapFileReader mapFileReader;

	/** Check whether a new map is created or an existing map file is altered **/
	private boolean isNewMap;

	/**
	 * Constructor
	 * 
	 * @param mapFileReader
	 *            reference to the Map File Parser object
	 * @param mapFileWriter
	 *            reference to the Map File Writer object
	 */
	public MapEditorView(MapFileReader mapFileReader) {
		this.mapFileReader = mapFileReader;
	}

	/**
	 * Method to print the adjacency Matrix representation and edit the map
	 * 
	 * @throws IOException
	 */
	public boolean readMapEditor(boolean isNewMap) throws IOException {
		int playersCount = 2;
		this.isNewMap = isNewMap;
		if (!isNewMap)
			printMapElements();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println(":: " + (isNewMap ? "Create a New Map" : " Edit the Map")
					+ " ::\n\n1. Add a Country\n2. Delete a country\n3. Add an Edge\n4. Delete an Edge\n5. Add a Continent\n6. Delete a Continent\n7. Save and Exit");
			int choice = Integer.parseInt(reader.readLine());

			// Initialing variables for the switch case use
			String countryName = null;
			String countryName2 = null;
			String continentName = null;

			switch (choice) {
			case 1:
				System.out.println("\n:: Enter the Country Name and its Continent ::");
				countryName = reader.readLine();
				continentName = reader.readLine();
				Country country = new Country(countryName);
				country.setContinentName(continentName);
				country.setxCoordinate(DEFAULT_COUNTRY_COORDINATE_VALUE);
				country.setyCoordinate(DEFAULT_COUNTRY_COORDINATE_VALUE);
				mapFileReader.getCountriesGraph().addCountry(country);
				mapFileReader.getCountriesHashMap().put(countryName, country);
				if (mapFileReader.getCountriesGraph().getContinentHashMap().get(continentName).getCountries()
						.size() > 1) {
					System.out
							.println("- Add adjacency (Edge) with other countries -\n- Enter Adjacent Country Name -");
					String adjCountryName = reader.readLine();
					mapFileReader.getCountriesGraph().addEdge(country,
							mapFileReader.getCountriesHashMap().get(adjCountryName));
					mapFileReader.getCountriesGraph().addEdge(mapFileReader.getCountriesHashMap().get(adjCountryName),
							country);
				}
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 2:
				System.out.println("\n:: Enter the Country Name to perform Deletion ::");
				countryName = reader.readLine();
				mapFileReader.getCountriesGraph().removeCountry(mapFileReader.getCountriesHashMap().get(countryName));
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 3:
				System.out.println("\n:: Enter the two countries to be connected ::");
				countryName = reader.readLine();
				countryName2 = reader.readLine();
				mapFileReader.getCountriesGraph().addEdge(mapFileReader.getCountriesHashMap().get(countryName),
						mapFileReader.getCountriesHashMap().get(countryName2));
				mapFileReader.getCountriesGraph().addEdge(mapFileReader.getCountriesHashMap().get(countryName2),
						mapFileReader.getCountriesHashMap().get(countryName));
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 4:
				System.out.println("\n:: Enter the two countries to remove their connectivity ::");
				countryName = reader.readLine();
				countryName2 = reader.readLine();
				mapFileReader.getCountriesGraph().removeEdge(mapFileReader.getCountriesHashMap().get(countryName),
						mapFileReader.getCountriesHashMap().get(countryName2));
				mapFileReader.getCountriesGraph().removeEdge(mapFileReader.getCountriesHashMap().get(countryName2),
						mapFileReader.getCountriesHashMap().get(countryName));
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 5:
				System.out.println("\n:: Enter a Name for the new Continent ::");
				continentName = reader.readLine();
				System.out.println("- Enter its control value -");
				int controlValue = Integer.parseInt(reader.readLine());
				Continent continent = new Continent(continentName, controlValue);
				System.out.println("- How many new countries you want to insert? -");
				int count = Integer.parseInt(reader.readLine());
				mapFileReader.getCountriesGraph().addContinent(continent);
				String[] countriesArray = new String[count];
				for (int i = 0; i < countriesArray.length; i++) {
					countriesArray[i] = reader.readLine();
					Country newCountry = new Country(countriesArray[i]);
					newCountry.setContinentName(continentName);
					newCountry.setxCoordinate(DEFAULT_COUNTRY_COORDINATE_VALUE);
					newCountry.setyCoordinate(DEFAULT_COUNTRY_COORDINATE_VALUE);
					mapFileReader.getCountriesGraph().addCountry(newCountry);
					mapFileReader.getCountriesHashMap().put(countriesArray[i], newCountry);
				}
				System.out.println("- Define an edge for each new country created -");
				for (int i = 0; i < countriesArray.length; i++) {
					System.out.println("- Enter count of the Adjacent Country(ies) for " + countriesArray[i] + " -");
					int adjacentCount = Integer.parseInt(reader.readLine());
					System.out.println("- Enter the Country Name(s) to be placed Adjacent -");
					for (int j = 0; j < adjacentCount; j++) {
						String adjacentCountryNm = reader.readLine();
						if (isCountryPresent(adjacentCountryNm) && !mapFileReader.getCountriesGraph().isAdjacent(
								mapFileReader.getCountriesHashMap().get(countriesArray[i]),
								mapFileReader.getCountriesHashMap().get(adjacentCountryNm))) {
							mapFileReader.getCountriesGraph().addEdge(
									mapFileReader.getCountriesHashMap().get(countriesArray[i]),
									mapFileReader.getCountriesHashMap().get(adjacentCountryNm));
							mapFileReader.getCountriesGraph().addEdge(
									mapFileReader.getCountriesHashMap().get(adjacentCountryNm),
									mapFileReader.getCountriesHashMap().get(countriesArray[i]));
						}
					}
				}
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 6:
				System.out.println("\n:: Enter a Continent Name to be deleted ::");
				continentName = reader.readLine();
				boolean isPresent = isContinentPresent(continentName);
				if (isPresent) {
					mapFileReader.getCountriesGraph()
							.removeContinent(mapFileReader.getContinentHashMap().get(continentName));
				} else {
					System.out.println("Invalid Continent Name Input");
				}
				System.out.println("\n::::: Map Updated :::::");
				askForNewLineInput(reader);
				printMapElements();
				break;
			case 7:
				mapFileReader.getMapFileWriter().saveMapToFile(isNewMap);
				break;
			default:
				System.out.println("Invalid Input.");
				break;
			}
			if (choice == 7) {
				break;
			}
		}
		reader.close();
		return true;
	}

	/**
	 * Check whether the User wants to move to the new line
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void askForNewLineInput(BufferedReader reader) throws IOException {
		while (reader.readLine().equalsIgnoreCase("\n")) {

		}
	}

	/**
	 * Check whether the continent is present in the Continent HashMap in
	 * CountriesGraph
	 * 
	 * @param continentName
	 * @return true if the continent is present in the continent HashMap else false
	 */
	private boolean isContinentPresent(String continentName) {
		for (String continent : mapFileReader.getCountriesGraph().getContinentHashMap().keySet()) {
			if (continent.equals(continentName))
				return true;
		}
		return false;
	}

	/**
	 * Check whether the country is present in the Country HashMap in MapFileReader
	 * 
	 * @param countryName
	 * @return true if the country is present in the countries HashMap else false
	 */
	private boolean isCountryPresent(String countryName) {
		for (String country : mapFileReader.getCountriesHashMap().keySet()) {
			if (country.equals(countryName))
				return true;
		}
		return false;
	}

	/**
	 * Print the countries and their adjacent countries stored in graph
	 */
	private void printMapElements() {
		StringBuilder builder = new StringBuilder();
		builder.append("\nCountry - Continent - Adjacent Countries\n\n");
		for (Continent continent : mapFileReader.getContinentHashMap().values()) {
			for (Country country : continent.getCountries()) {
				builder.append(country.getCountryName() + SEPERATOR + continent.getContinentName() + SEPERATOR);
				LinkedList<Country> currentCountryList = mapFileReader.getCountriesGraph().getAdjListHashMap()
						.get(country);
				for (int i = 0; i < currentCountryList.size(); i++) {
					builder.append(currentCountryList.get(i).getCountryName()
							+ (i != currentCountryList.size() - 1 ? COMMA_SEPERATOR : ""));
				}
				builder.append("\n");
			}
			builder.append("\n");
		}
		System.out.print(builder.toString());
	}
}
