/**
 * 
 */
package com.game.risk;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.core.parser.MapFileWriter;

/**
 * Risk game driver is main class to call all phases of the game.
 * 
 * @author sohrab_singh
 *
 */
public class RiskGameDriver {

	public static final Logger LOGGER = Logger.getLogger(RiskGameDriver.class);

	/**
	 * Main method for Risk Game Driver.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		
		LOGGER.info("Execution started. Welcome to Risk Game");

		// Hard coded files
		// To be changed to different files. Later it will changed to MapWriter overrite the same file.
		final String fileName = "/Users/sohrab_singh/Documents/workspace-sts-3.9.0.RELEASE/RiskGame/res/Canada.map";
		final String fileName1 = "/Users/sohrab_singh/Documents/workspace-sts-3.9.0.RELEASE/RiskGame/res/Canada1.map";

		MapFileParser fileParser;
		MapFileWriter writer;
		try {
			fileParser = new MapFileParser(fileName).readFile();
			writer = new MapFileWriter(fileName1, fileParser).saveMapToFile();
			writer.saveMapToFile();
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found" + fileName);
		} catch (IOException e) {
			LOGGER.error("Error occured while reading file.");
		}
	}
}
