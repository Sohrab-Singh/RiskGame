package com.game.risk.core;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Startup class
 *
 * @author Vida Abdollahi
 */

public class StartUp {


    private MapFileParser mapFileParser;
    private ArrayList<Player> playersList;
    private int playerCounts;
    private static final int MINIMUM_NUMBER_PLAYERS = 2;
    private static final int MAXIMUM_NUMBER_PLAYERS = 6;

    public Player currentPlayer;

    /**
     * Constructor
     *
     * @param mapFileParser   reference to the map file parser object
     * @param numberOfPlayers number of players that we need to start the game
     */

    public StartUp(MapFileParser mapFileParser, int numberOfPlayers) {
        this.mapFileParser = mapFileParser;
        this.playerCounts = numberOfPlayers;
        ArrayList<Player> playersList = new ArrayList<Player>();

        for (int i = 0; i < playerCounts; i++) {
            Player player = new Player();
            playersList.add(player);

        }
    }

    /**
     * Set the PlayerCounts
     *
     * @param playerCounts
     */

    public void setPlayerCounts(int playerCounts) {
        this.playerCounts = playerCounts;
    }

    /**
     * Get the playerCounts
     *
     * @return playerCounts which shows the number of players
     */

    public int getPlayerCounts() {
        return playerCounts;
    }

    /**
     * Get the PlayerList
     *
     * @return playerLists
     */

    public ArrayList<Player> getPlayers() {
        return playersList;
    }

    public void setPlayersList(ArrayList<Player> playersList) {
        this.playersList = playersList;
    }

    /**
     * Assign countries randomly to players
     */

    public void assignCountries() {


        for (String key : mapFileParser.getCountriesHashMap().keySet()) {
            Random rand = new Random();
            int randomNum = rand.nextInt((playerCounts - 1) + 1) + 1;

            (playersList.get(randomNum - 1)).addCountry(mapFileParser.getCountriesHashMap().get(key));

        }

    }

    /**
     * Allocate initial armies to the players according to the players number.
     *
     * @param numberOfPlayers can vary between minimum = 2 and maximum = 6
     */
    public void allocateArmiesToPlayers(int numberOfPlayers) {

        for (Player player : playersList) {
            switch (numberOfPlayers) {

                case 2:
                    player.setNumberOfArmies(40);
                    break;
                case 3:
                    player.setNumberOfArmies(35);
                    break;
                case 4:
                    player.setNumberOfArmies(30);
                    break;
                case 5:
                    player.setNumberOfArmies(25);
                    break;
                case 6:
                    player.setNumberOfArmies(20);
                    break;
                default:
                    System.out.println("Number of players is more than" + MAXIMUM_NUMBER_PLAYERS);
                    break;
            }
        }

    }

    /**
     * Each country belonged to a player must have at least one army
     */

    public void assignInitialArmiesToCountries() {
        for (String key : mapFileParser.getCountriesHashMap().keySet()) {

            mapFileParser.getCountriesHashMap().get(key).setCurrentNumberOfArmies(1);
        }
        for (Player player : playersList) {
            int number = player.getNumberOfArmies();
            player.setNumberOfArmies(number - player.getCountriesOwned().size());
        }
    }


    /**
     * After initializing the armies, remaining armies will be given to countries owned by users.
     *
     * @param selectedCountry is the country which the player has selected to put an army on
     * @param selectedPlayer  is the player who needs to place an army on its own countries
     */

    public void assignArmiesToCountries(Country selectedCountry, Player selectedPlayer) {

        if ((selectedPlayer.getNumberOfArmies()) > 0) {
            if (selectedPlayer.getCountriesOwned().contains(selectedCountry)) {
                int current = selectedCountry.getCurrentNumberOfArmies();
                selectedCountry.setCurrentNumberOfArmies(current + 1);
                int current2 = selectedPlayer.getNumberOfArmies();
                selectedPlayer.setNumberOfArmies(current2 - 1);
            } else {
                System.out.println("This country does not belong to you!");
            }
        } else {
            System.out.println("You don't have any army!");
        }
    }

}

