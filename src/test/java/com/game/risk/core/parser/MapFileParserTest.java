package com.game.risk.core.parser;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
        mapFileParser = new MapFileParser("Quebec.map");
    }

    @Test
    public void readFileTest() throws IOException {
        assertNotNull(mapFileParser.readFile());
    }

    @Test
    public void readFileTest2() {

    }
}