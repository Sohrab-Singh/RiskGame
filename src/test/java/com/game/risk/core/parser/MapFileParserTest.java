package com.game.risk.core.parser;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.core.MapFileReader;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test Class for MapFileReader class
 *
 * @author Sarthak
 */
public class MapFileParserTest {
	private MapFileReader mapFileReader;

	/**
	 * Set up of Map File parser.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		File file = new File("C:\\\\Concordia University\\\\Study Material\\\\SOEN 6441 Advanced Programming Practices\\\\RISK Game - Project\\\\Québec.MAP");
		mapFileReader = new MapFileReader(file);
	}

	/**
	 * Read file test
	 * 
	 * @throws IOException
	 */
	@Test
	public void readFileTest() throws IOException {
		assertNotNull(mapFileReader.readFile());
	}

}