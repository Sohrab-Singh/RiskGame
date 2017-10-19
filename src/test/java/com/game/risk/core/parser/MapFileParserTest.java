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

    @Before
    public void setUp() throws Exception {
        mapFileReader = new MapFileReader("Quebec.map");
    }

    @Test
    public void readFileTest() throws IOException {
        assertNotNull(mapFileReader.readFile());
    }

    @Test
    public void readFileTest2() {

    }
}