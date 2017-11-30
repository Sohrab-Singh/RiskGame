package com.game.risk.core.util;

import com.game.risk.core.CountriesGraph;
import com.game.risk.core.MapFileReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Test class for Map Validation class
 * @author Vida Abdollahi
 * @author sohrab_singh
 *
 */
public class MapValidationTest {

    private MapValidation mapValidation;
    private String str1;
    private String str2;
    private String fileName;
    private String fileName1;
    private MapFileReader mapFileReader;
    private CountriesGraph graph;

    /**
     * Set up the initial objects
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mapValidation = new MapValidation(null);
        str1 = " [Continents] [Territories]";
        str2 = "[Map] [Continents] [Territories]";
        fileName = "src//test//resources//Canada.map";
        fileName1 = "src//test//resources//Invalid_Canada.map";
        mapFileReader = new MapFileReader(new File(fileName1));
        graph = mapFileReader.getCountriesGraph();
    }

    /**
     * Test method for testing validation of a file
     * @throws Exception
     */
    @Test

    public void testValidateFile() throws Exception {

        assertTrue(mapValidation.validateFile(new File(fileName)));
    }
    
    /**
     * Negative Test method for testing validation of a file
     * @throws Exception
     */
    @Test

    public void testValidateFileNegative() throws Exception {
        assertFalse(mapValidation.validateFile(new File(fileName1)));
    }


    /**
     * test method for checking mandatory tags like [Map]
     * @throws Exception
     */
    @Test

    public void testCheckMandatoryTags() throws Exception {

        assertTrue(mapValidation.checkMandatoryTags(str2));
    }

    
    /**
     * Negative test method for checking mandatory tags like [Map]
     * @throws Exception
     */
    @Test

    public void testCheckMandatoryTagsNegative() throws Exception {

        assertFalse(mapValidation.checkMandatoryTags(str1));
    }

}