package com.game.risk.core.parser;

import org.junit.Before;
import org.junit.Test;

import com.game.risk.core.MapFileReader;

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
		mapFileReader = new MapFileReader("//Users//sohrab_singh//Documents//workspace-sts-3.9.0.RELEASE//RiskGame//src//test//resources//Quebec.map");
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