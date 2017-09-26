package com.game.risk;

import com.game.risk.core.parser.MapFileParser;

import java.io.IOException;

/**
 * Main Class
 *
 * @author Sarthak
 */
public class App {
    public static void main(String[] args) throws IOException {
        final MapFileParser fileParser = new MapFileParser("res\\Québec.MAP");
        fileParser.readFile();
    }
}
