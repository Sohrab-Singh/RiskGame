package com.game.risk.core.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapValidationTest {

    private MapValidation mapValidation;
    private String str1;
    private String filename;

    @Before
    public void setUp() throws Exception {
        mapValidation = new MapValidation();
        str1 = " [Continents] [Territories]";
        filename = "res\\Canada.map";
    }

    @Test
    public void validateFile() throws Exception {

        assertTrue(mapValidation.validateFile(filename));
    }


    @Test
    public void checkMandatoryTags() throws Exception {

        assertFalse(mapValidation.checkMandatoryTags(str1));
    }

}