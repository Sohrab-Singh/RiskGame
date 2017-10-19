package com.game.risk.core;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.model.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Startup class.
 *
 * @author Vida Abdollahi
 * @author sohrab_singh
 */

public class StartUpPhase {

	/** Map file parser */
	private MapFileParser mapFileParser;

	/** Player List */
	private ArrayList<Player> playersList;

	/** Number of players */
	private int numberOfPlayers;

	/** Minimum number of players */
	private static final int MINIMUM_NUMBER_PLAYERS = 2;

	/** Maximum number of players. */
	private static final int MAXIMUM_NUMBER_PLAYERS = 6;


	/**
	 * Startup Constructor.
	 *
	 * @param mapFileParser
	 *            reference to the map file parser object
	 * @param numberOfPlayers
	 *            number of players that we need to start the game
	 */
	public StartUpPhase(MapFileParser mapFileParser, int numberOfPlayers) {
		this.mapFileParser = mapFileParser;
		this.numberOfPlayers = numberOfPlayers;
		playersList = new ArrayList<Player>();

		for (int i = 0; i < numberOfPlayers; i++) {
			Player player = new Player();
			playersList.add(player);

		}
	}


	/**
	 * Get the PlayerList
	 *
	 * @return playerLists
	 */
	public ArrayList<Player> getPlayerList() {
		return playersList;
	}

	/**
	 * Set the player list.
	 * 
	 * @param playersList Set the PlayerList
	 */
	public void setPlayersList(ArrayList<Player> playersList) {
		this.playersList = playersList;
	}

	/**
	 * Assign countries randomly to players
	 */
	public void assignCountries() {

		for (String key : mapFileParser.getCountriesHashMap().keySet()) {
			Random rand = new Random();
			playersList.get(rand.nextInt((numberOfPlayers))).addCountry(mapFileParser.getCountriesHashMap().get(key));

		}

	}

	/**
	 * Allocate initial armies to the players according to the players number.
	 *
	 */
	public void allocateArmiesToPlayers() {

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
	 * Each country belonged to a player must have at least one army.
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
	 * @return the numberOfPlayers
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * @param numberOfPlayers the numberOfPlayers to set
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}



}
