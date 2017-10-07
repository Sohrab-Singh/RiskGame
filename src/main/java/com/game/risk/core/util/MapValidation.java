package com.game.risk.core.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Map Validation class is implemented for data validation before loading it with map file parser
 *
 * @author Vida Abdollahi
 */

public class MapValidation {
    /**
     * Array List to store continents which will be found in [Continents] tag
     */
    private ArrayList continentInContinent;

    /**
     * Array List to store continents which will be found in [Territories] tag
     */

    private ArrayList continentInTerritory;

    /**
     * Hash Map to store each country and its adjacent countries as an array list
     */
    private HashMap<String, ArrayList<String>> Countries;

    /**
     * File Reader class variable
     */

    private FileReader fileReader;

    /**
     * Constructor
     *
     * @param filename
     * @throws IOException
     */

    public MapValidation(String filename) throws IOException {

        ArrayList continentInContinent = new ArrayList();
        ArrayList continentInTerritory = new ArrayList();
        HashMap<String, ArrayList<String>> Countries = new HashMap<String, ArrayList<String>>();
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String str = new String(data, "UTF-8");

        //Check to see whether all the tags are defined in the file
        while (true) {

            if (!str.contains("[Map]")) {
                System.out.println("No [Map] tag is defined");
            }
            if (!str.contains("[Territories]")) {
                System.out.println("No [Territories] tag is defined");
                break;
            }
            if (!str.contains("[Continents]")) {
                System.out.println("No [Continents] tag is defined");
                break;
            }

            line = bufferedReader.readLine();

            if (line == null) {
                break;

            }
            // Check for continents format

            if (line.startsWith("[Continents]")) {
                while ((!(line = bufferedReader.readLine()).startsWith("[Territories]"))) {
                    if (!line.isEmpty()) {
                        String pattern = "[^,;=]+=[1-9]+";

                        if (!line.matches(pattern)) {
                            System.out.println("* " + line + ": Invalid format for a continent ");

                        } else {
                            String[] splitLine = line.split("=");
                            if (!continentInContinent.contains(splitLine[0])) {
                                continentInContinent.add(splitLine[0]);

                            } else {
                                System.out.println("*" + splitLine[0] + " is defined more than one time");
                            }
                        }
                    }
                }
            }

            //Check for countries format

            if (line.startsWith("[Territories]")) {
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        String pattern = "[^;,]+,[0-9]+,[0-9]+,[^;,]+,[^;,]+(,[^;,]+)*";

                        if (!line.matches(pattern)) {
                            System.out.println("* " + line + ": Invalid format for a territory ");

                        } else {
                            String[] split = line.split(",");

                            if (!continentInTerritory.contains(split[3])) {
                                continentInTerritory.add(split[3]);

                            }

                            if (!Countries.containsKey(split[0])) {

                                ArrayList<String> arrayList = new ArrayList<String>();
                                for (int i = 4; i < split.length; i++) {
                                    arrayList.add(split[i]);

                                }
                                Countries.put(split[0], arrayList);

                            } else {
                                System.out.println("* " + split[0] + ": is defined more than one time");

                            }

                        }

                    }

                }

            }

        }

        //Check to see whether number of continents in [Continents] tag is equeal with number of continents in [Territories tag]

        if (continentInContinent.size() != continentInTerritory.size()) {
            System.out.println("* Number of continents in [Continents] tag is not equal with number of continents in [Territories] tag");

        }

        // make sure that all continents have at least one country

        for (Object continent : continentInContinent) {
            if (!continentInTerritory.contains(continent)) {
                System.out.println(continent + ": does not have any country");
            }
        }
        // check to make sure that if  country X has country Y as it adjacent then, country Y has country X as its adjacent as well

        for (String country : Countries.keySet()) {

            for (Object adjlist : Countries.get(country)) {
                if ((Countries.get(adjlist.toString()) != null)) {
                    if (Countries.get(adjlist.toString()).contains(country)) {
                        // System.out.println("*" + adjlist + ": Adjacents are OK");
                    }

                } else {
                    System.out.println("*" + adjlist + ": Undefined Country ");
                }

            }

            bufferedReader.close();

        }

    }
}