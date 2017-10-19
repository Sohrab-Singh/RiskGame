package com.game.risk.core;

import com.game.risk.core.util.MapValidation;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Map File Parser for reading map data for the game.
 *
 * @author Sarthak
 * @author sohrab_singh
 */
public class MapFileReader {

	/**
	 * FileReader class variable
	 */
	private FileReader fileReader;

	/**
	 * HashMap to store searched countries through the map file
	 */
	private HashMap<String, Country> countriesHashMap;

	/**
	 * HashMap to store searched continents through the map file
	 */
	private HashMap<String, Continent> continentHashMap;

	/**
	 * Countries Graph to store adjacent countries
	 */
	private CountriesGraph countriesGraph;

	/**
	 * Map file writer
	 */
	private MapFileWriter mapFileWriter;

	/**
	 * Map meta deta
	 */
	private List<String> mapMetaData = new ArrayList<>();

	private String fileName;

	/**
	 * Map File Parser Default Constructor
	 */
	public MapFileReader() {
		countriesHashMap = new HashMap<>();
		continentHashMap = new HashMap<>();
		countriesGraph = new CountriesGraph(this);
		mapFileWriter = new MapFileWriter(this);
	}

	/**
	 * Map File Parser constructor.
	 *
	 * @param filename
	 *            Name of the file containing the Map information
	 * @throws FileNotFoundException
	 *             file not found exception
	 */
	public MapFileReader(String filename) throws FileNotFoundException {
		fileReader = new FileReader(filename);
		this.fileName = filename;
		countriesHashMap = new HashMap<String, Country>();
		continentHashMap = new HashMap<String, Continent>();
		countriesGraph = new CountriesGraph(this);
		mapFileWriter = new MapFileWriter(this);
	}

	/**
	 * Method to read and store data into the model classes from map file.
	 *
	 * @return map file reader
	 * @throws IOException
	 *             input output exception
	 */
	public MapFileReader readFile() throws IOException {

		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}

			if (line.startsWith("[Map]")) {
				mapMetaData.add(line);
				while (!(line = reader.readLine()).isEmpty()) {
					mapMetaData.add(line);
				}
				line = reader.readLine();
			}

			if (line.startsWith("[Continents]")) {
				while (!(line = reader.readLine()).startsWith("[")) {
					if (!line.isEmpty()) {
						String[] splitLine = line.split("=");
						Continent continent = new Continent(splitLine[0], Integer.parseInt(splitLine[1]));
						continentHashMap.put(splitLine[0], continent);
						countriesGraph.addContinent(continent);
					}
				}
			}

			if (line.startsWith("[Territories]")) {
				while ((line = reader.readLine()) != null) {

					if (!line.isEmpty()) {
						String[] splits = line.split(",");

						// Condition if country is not present in HashMap
						if (!countriesHashMap.containsKey(splits[0])) {
							Country country = new Country(splits[0]);
							country.setxCoordinate(splits[1]);
							country.setyCoordinate(splits[2]);
							country.setContinentName(splits[3]);
							countriesGraph.addCountry(country);
							countriesHashMap.put(country.getCountryName(), country);
							continentHashMap.get(country.getContinentName()).addCountry(country);
						} else if (countriesHashMap.get(splits[0]).getContinentName() == null) {
							countriesHashMap.get(splits[0]).setxCoordinate(splits[1]);
							countriesHashMap.get(splits[0]).setyCoordinate(splits[2]);
							countriesHashMap.get(splits[0]).setContinentName(splits[3]);
							continentHashMap.get(splits[3]).addCountry(countriesHashMap.get(splits[0]));
						}

						// Check whether adjacent country is already created and present in HashMap
						for (int i = 4; i < splits.length; i++) {
							if (!countriesHashMap.containsKey(splits[i])) {
								Country adjCountry = new Country(splits[i]);
								countriesHashMap.put(splits[i], adjCountry);
							}
							countriesGraph.addEdge(countriesHashMap.get(splits[0]), countriesHashMap.get(splits[i]));
						}
					}
				}
			}
		}
		return this;
	}

	/**
	 * Check File Validation.
	 * 
	 * @return true if file is valid
	 * @throws IOException
	 */
	public boolean checkFileValidation() throws IOException {

		return new MapValidation().validateFile(fileName);
	}

	/**
	 * Get the map meta data.
	 * 
	 * @return mapMetaData map meta data.
	 */
	public List<String> getMapMetaData() {
		return mapMetaData;
	}

	/**
	 * Get the continent HashMap
	 *
	 * @return Continent hashmap
	 */
	public HashMap<String, Continent> getContinentHashMap() {
		return continentHashMap;
	}

	/**
	 * Get the countries HashMap
	 *
	 * @return Countries hashmap
	 */
	public HashMap<String, Country> getCountriesHashMap() {
		return countriesHashMap;
	}

	/**
	 * Get the Countries Graph.
	 *
	 * @return the countriesGraph
	 */
	public CountriesGraph getCountriesGraph() {
		return countriesGraph;
	}

	/**
	 * Get the Map file writer.
	 *
	 * @return MapFileWriter
	 */
	public MapFileWriter getMapFileWriter() {
		return mapFileWriter;
	}
}
