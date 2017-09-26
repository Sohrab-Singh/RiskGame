package com.game.risk.core.parser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Class for MapFileParser class
 *
 * @author Sarthak
 */
public class MapFileParserTest {
    private MapFileParser mapFileParser;

    @Before
    public void setUp() throws Exception {
        mapFileParser = new MapFileParser("res\\Québec.MAP");
    }

    @Test
    public void readFile() throws Exception {
        assertEquals(47, mapFileParser.readFile());
    }

}