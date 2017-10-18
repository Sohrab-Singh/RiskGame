package com.game.risk.core.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.game.risk.core.CountriesGraph;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;

/**
 * Map file writer to write the map data into the .map file
 * 
 * @author sohrab_singh
 * @author Sarthak
 */
public class MapFileWriter {

	/** Map File parser used for getting the current map data */
	private MapFileParser fileParser;

	/** Name of file where data to be written. */
	private String fileName;

	/** COMMA Separator */
	private static final String COMMA_SEPERATOR = ",";

	/**
	 * Map File Writer Constructor.
	 * 
	 * @param fileName
	 *            Name of file
	 * @param fileParser
	 *            file Parser
	 */
	public MapFileWriter(String fileName, MapFileParser fileParser) {
		this.fileName = fileName;
		this.fileParser = fileParser;

	}

	/**
	 * Map file writer to write map data to .map file.
	 * 
	 * @return Map File Writer
	 * @throws IOException
	 *             input output exception
	 */
	public MapFileWriter saveMapToFile(boolean isNewMap) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
		StringBuilder builder = new StringBuilder();

		// Writing [Map] tag meta-data.
		if (isNewMap) {
			builder.append("[Map]\n").append("author=${user}");
		} else {
			for (String str : fileParser.getMapMetaData()) {
				builder.append(str);
				builder.append("\n");
			}
		}
		builder.append("\n");

		// Writing [Continents] tag data - (Continent and Control Value).
		builder.append("[Continents]");
		for (Continent continent : fileParser.getContinentHashMap().values()) {
			builder.append("\n");
			builder.append(continent.getContinentName() + "=" + continent.getControlValue());
		}
		builder.append("\n");
		builder.append("\n");

		// Writing [Territories] tag data.
		builder.append("[Territories]");
		for (Continent continent : fileParser.getContinentHashMap().values()) {
			builder.append("\n");
			for (Country country : continent.getCountries()) {
				builder.append(country.getCountryName() + COMMA_SEPERATOR + country.getxCoordinate() + COMMA_SEPERATOR
						+ country.getyCoordinate() + COMMA_SEPERATOR + continent.getContinentName());

				for (Country adjacentCountry : fileParser.getCountriesGraph().getAdjListHashMap().get(country)) {
					builder.append(COMMA_SEPERATOR + adjacentCountry.getCountryName());
				}
				builder.append("\n");
			}
		}

		bufferedWriter.write(builder.toString());
		bufferedWriter.close();
		return this;
	}
}