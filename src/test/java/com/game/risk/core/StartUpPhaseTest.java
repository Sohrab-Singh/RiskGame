package com.game.risk.core;

import com.game.risk.model.Country;
import com.game.risk.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Test Class for StartUp Phase
 *
 * @author Vida Abdollahi
 * @author Sarthak
 */

public class StartUpPhaseTest {

	/** MapFileReader object */
	private MapFileReader mapFileReader;

	/** StartUpPhase object */
	private StartUpPhase startUpPhase;

	/** Number of Players playing the game */
	private int numberOfPlayers;

	/** Player 1 to create mock object */
	private Player p1;

	/** Player 2 to create mock object */
	private Player p2;

	/** ArrayList to hold Player objects */
	private ArrayList<Player> list1;

	/** Country object to create mock object */
	private Country c1;

	private Country c2;

	private ArrayList<Country> list2;

	/**
	 * Setup the initial objects
	 *
	 * @throws Exception
	 */

	@Before
	public void setUp() throws Exception {
		mapFileReader = new MapFileReader(
				"./src/main/resources/Canada.map");
		startUpPhase = new StartUpPhase(mapFileReader, 2, null);
		numberOfPlayers = 2;
		p1 = new Player();
		p1.setPlayerName("Vida");
		p2 = new Player();
		p2.setPlayerName("Shubhangi");
		list1 = new ArrayList<Player>();
		list1.add(p1);
		list1.add(p2);
		startUpPhase.setPlayersList(list1);
		list2.add(c1);
		list2.add(c2);
		p1.setCountriesOwned(list2);




	}

	/**
	 * Test method for getting player list
	 *
	 * @throws Exception
	 */
	@Test
	public void getPlayerList() throws Exception {

		assertEquals(list1.size(), startUpPhase.getPlayerList().size());

	}

	/**
	 * Test method for assigning initial armies to each country
	 *
	 * @throws Exception
	 */
	@Test
	public void assignInitialArmiesToCountries() throws Exception
	{
		startUpPhase.assignInitialArmiesToCountries();

		//Current number of armies - countries owned (2)
		assertEquals(38, p1.getNumberOfArmies());

	}

	/**
	 * Test method for allocating armies to players
	 *
	 * @throws Exception
	 */
	@Test
	public void allocateArmiesToPlayers() throws Exception {
		startUpPhase.allocateArmiesToPlayers();
		assertNotEquals(45, p1.getNumberOfArmies());
	}

	@Test

	/**
	 * Test method for Domination Percentage
	 *
	 * @throws Exception
	 */
	public void DominationPercentage() throws Exception
	{
		startUpPhase.populateDominationPercentage();
		assertNotEquals(10, p1.getCurrentDominationPercentage() );
	}

	/**
	 * Test method for getting number of players
	 *
	 * @throws Exception
	 */
	@Test
	public void getCountOfPlayers() throws Exception {

		startUpPhase.setNumberOfPlayers(2);
		assertEquals(2, startUpPhase.getNumberOfPlayers());
	}

	/**
	 * Get the Country object
	 *
	 * @return
	 */
	public Country getC1() {
		return c1;
	}

	/**
	 * Set the Country object
	 *
	 * @param c1
	 *            Country object
	 */
	public void setC1(Country c1) {
		this.c1 = c1;
	}

	/**
	 * Set the number of players playing the game
	 *
	 * @param numberOfPlayers
	 *            num of players
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * Get the number of Players
	 *
	 * @return an int variable indicating number of players
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

}

