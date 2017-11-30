package com.game.risk.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;

/**
 * Map file writer to write the map data into the .map file
 * 
 * @author sohrab_singh
 * @author Sarthak
 */
public class MapFileWriter {

	/** Map File parser used for getting the current map data. */
	private MapFileReader fileParser;

	/** COMMA Separator. */
	private static final String COMMA_SEPERATOR = ",";

	/**
	 * Map File Writer Constructor.
	 *
	 * @param fileParser
	 *            file Parser
	 */
	public MapFileWriter(MapFileReader fileParser) {
		this.fileParser = fileParser;

	}

	/**
	 * Map file writer to write map data to .map file.
	 * 
	 * @param isNewMap
	 *            true if map is new and false if map is already in file.
	 * 
	 * @return Map File Writer
	 * @throws IOException
	 *             input output exception
	 */
	public MapFileWriter saveMapToFile(boolean isNewMap) throws IOException {
		File file = FileSystemView.getFileSystemView().getDefaultDirectory();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		String fileName = file.getAbsolutePath() + "//" + "mapfile-" + dateFormat.format(date) + ".map";
		System.out.println(fileName);
		File newFile = new File(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
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