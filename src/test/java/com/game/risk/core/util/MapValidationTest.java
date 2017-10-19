package com.game.risk.core.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    /**
     * Set up the initial objects
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mapValidation = new MapValidation();
        str1 = " [Continents] [Territories]";
        str2 = "[Map] [Continents] [Territories]";
        fileName = "Canada.map";
        fileName1 = "Invalid_Canada.map";
    }

    /**
     * Test method for testing validation of a file
     * @throws Exception
     */
    @Test
    public void testValidateFile() throws Exception {

        assertTrue(mapValidation.validateFile(fileName));
        assertFalse(mapValidation.validateFile(fileName1));
    }


    /**
     * test method for checking mandatory tags like [Map]
     * @throws Exception
     */
    @Test
    public void testCheckMandatoryTags() throws Exception {

        assertFalse(mapValidation.checkMandatoryTags(str1));
        assertTrue(mapValidation.checkMandatoryTags(str2));
    }

}