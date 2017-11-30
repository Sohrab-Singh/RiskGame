package com.game.risk.core.util;

import com.game.risk.core.CountriesGraph;
import com.game.risk.core.MapFileReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Test class for Map Validation class.
 *
 * @author Vida Abdollahi
 * @author sohrab_singh
 */
public class MapValidationTest {

	/** The map validation. */
	private MapValidation mapValidation;

	/** The str 1. */
	private String str1;

	/** The str 2. */
	private String str2;

	/** The file name. */
	private String fileName;

	/** The file name 1. */
	private String fileName1;

	/** The map file reader. */
	private MapFileReader mapFileReader;

	/** The map file reader 1. */
	private MapFileReader mapFileReader1;

	/** The graph. */
	private CountriesGraph graph;

	/** The graph 1. */
	private CountriesGraph graph1;

	/**
	 * Set up the initial objects.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		str1 = " [Continents] [Territories]";
		str2 = "[Map] [Continents] [Territories]";
		fileName = "//Users//sohrab_singh//Documents//workspace-sts-3.9.0.RELEASE//RiskGame//src//test//resources//3D Cliff.map";
		fileName1 = "//Users//sohrab_singh//Documents//workspace-sts-3.9.0.RELEASE//RiskGame//src//test//resources//Invalid_Canada.map";
		mapFileReader = new MapFileReader(new File(fileName));
		mapFileReader1 = new MapFileReader(new File(fileName1));
		graph = mapFileReader.getCountriesGraph();
		graph1 = mapFileReader1.getCountriesGraph();
		mapValidation = new MapValidation(graph);
	}

	/**
	 * Test method for testing validation of a file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	//@Test
	public void testValidateFile() throws Exception {

		assertTrue(mapValidation.validateFile(new File(fileName)));
	}

	/**
	 * Negative Test method for testing validation of a file.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testValidateFileNegative() throws Exception {
		assertFalse(mapValidation.validateFile(new File(fileName1)));
	}

	/**
	 * test method for checking mandatory tags like [Map].
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test

	public void testCheckMandatoryTags() throws Exception {

		assertTrue(mapValidation.checkMandatoryTags(str2));
	}

	/**
	 * Negative test method for checking mandatory tags like [Map].
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test

	public void testCheckMandatoryTagsNegative() throws Exception {

		assertFalse(mapValidation.checkMandatoryTags(str1));
	}

}