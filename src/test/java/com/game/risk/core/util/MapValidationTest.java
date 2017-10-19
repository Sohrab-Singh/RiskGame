package com.game.risk.core.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
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
     * @throws Exception
     */
    @Test
    public void testValidateFile() throws Exception {

        assertTrue(mapValidation.validateFile(fileName));
        assertFalse(mapValidation.validateFile(fileName1));
    }


    /**
     * @throws Exception
     */
    @Test
    public void testCheckMandatoryTags() throws Exception {

        assertFalse(mapValidation.checkMandatoryTags(str1));
        assertTrue(mapValidation.checkMandatoryTags(str2));
    }

}