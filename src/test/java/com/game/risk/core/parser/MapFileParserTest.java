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
 * @author shubhangi_sheel
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
		mapFileReader = new MapFileReader(new File("src//test//resources//Quebec.map"));
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