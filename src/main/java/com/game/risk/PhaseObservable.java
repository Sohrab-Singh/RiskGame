package com.game.risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

import com.game.risk.cardenum.PlayerDominationPhase;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.StartUpPhase;
import com.game.risk.core.util.FortificationPhaseUtil;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.core.util.ReinforcementPhaseUtil;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * Phase observable for Phase view and Player World
 * Domination View.
 * 
 * @author Sarthak
 * @author sohrab_singh 
 *
 */
public class PhaseObservable extends Observable {

	/**
	 * MapFileReader Object
	 */
	private MapFileReader fileParser;

	/**
	 * Current State of the game
	 */
	private int currentState;

	/** Player Domination phase */
	private Boolean playerDominationPhase;

	/**
	 * Player object indicating the current player playing the game
	 */
	private Player player;

	private int playersAvailable;
	private StartUpPhase startUpPhase;
	private RoundRobinScheduler<Player> robinScheduler;

	/**
	 * Default Constructor
	 * 
	 * @param reader
	 */
	public PhaseObservable(MapFileReader reader) {
		this.fileParser = reader;
	}

	/**
	 * Start game phases which will call all phases.
	 * 
	 * @throws NumberFormatException
	 *             number format exception
	 * @throws IOException
	 *             Input output exception
	 */
	public void startGamePhases() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(":: Input the no of players playing ::");
		int numberOfPlayers = Integer.parseInt(reader.readLine());
		LoggingUtil.logMessage("Total number of Players playing Risk Game : " + numberOfPlayers);

		// Startup Phase
		startUpPhase = new StartUpPhase(fileParser, numberOfPlayers, reader);
		startUpPhase.assignCountries();
		startUpPhase.allocateArmiesToPlayers();
		startUpPhase.assignInitialArmiesToCountries();

		startUpPhase.populateDominationPercentage();
		RiskGameDriver.startStartupPhase(fileParser, startUpPhase.getPlayerList());

		// Players playing in round robin fashion
		robinScheduler = new RoundRobinScheduler<Player>(startUpPhase.getPlayerList());
		player = robinScheduler.next();
		for (int i = 0; i < getPlayerList().size(); i++) {
			if (getPlayerList().get(i).getNumberOfArmies() > 0) {
				playersAvailable++;
			}
		}
		currentState = PhaseStates.STATE_STARTUP;

		playerDominationPhase = true;

		RiskGameDriver.startCardExchangeView();

		setChanged();
		notifyObservers();

		// As of now we are only giving 5 rounds , when attack phase will be implemented
		// then we will end game when player wins.
		int rounds = 0;
		while (rounds < 0) {

			// Reinforcement phase
			System.out.println("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
			LoggingUtil.logMessage("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
			Continent continent = fileParser.getContinentHashMap()
					.get(player.getCountriesOwned().get(0).getContinentName());
			// int reinforcementArmies =
			// ReinforcementPhaseUtil.calculateReinforcementArmies(player, continent);
			// System.out.println(
			// "Total reinforcement armies available for " + player.getPlayerName() + " : "
			// + reinforcementArmies);
			// player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);

			for (Country country : player.getCountriesOwned()) {
				if (player.getNumberOfArmies() > 0) {
					System.out.println(
							"How many armies do you want to assign to your country " + country.getCountryName() + " ?");
					System.out.println("Current number of armies of " + country.getCountryName() + " is "
							+ country.getCurrentNumberOfArmies() + " | Available armies : "
							+ player.getNumberOfArmies());
					int armies = Integer.parseInt(reader.readLine());
					player.assignArmiesToCountries(country, armies);
				} else {
					break;
				}
			}

			// Attack phase will be here

			// playerDominationPhase = PlayerDominationPhase.AFTER_ATTACK.getState();

			// Fortification phase
			System.out.println("\nFortification phase begins.\n");
			Country country1 = null;
			Country country2 = null;
			boolean flag = true;
			while (flag) {
				flag = false;
				System.out.println("Enter the Country Name from where you want to move some Army");
				country1 = fileParser.getCountriesHashMap().get(reader.readLine());

				System.out.println("Enter the Country Name to which you want to move some Army");
				country2 = FortificationPhaseUtil.retrieveAndSelectAdjacentArmies(country1, reader, fileParser);

				if (country1 == null || country2 == null) {
					System.out.println("You have entered wrong data.");
					System.out.println("Enter again");
					flag = true;
				}
			}

			boolean countryFlag = true;
			int fortificationArmies = 0;
			while (countryFlag) {
				countryFlag = false;
				System.out.println("Enter the Number of Armies to Move");
				fortificationArmies = Integer.parseInt(reader.readLine());
				if (fortificationArmies > country1.getCurrentNumberOfArmies()) {
					System.out.println("You can not enter more armies than donor country have");
					System.out.println("Enter again");
					countryFlag = true;
				}
			}

			FortificationPhaseUtil.moveArmiesBetweenCountries(country1, country2, fortificationArmies,
					fileParser.getCountriesGraph().getAdjListHashMap());
			LoggingUtil.logMessage(fortificationArmies + " armies has been moved from " + country1 + " to " + country2);

			rounds++;
			player = robinScheduler.next();
		}
		reader.close();

	}

	/**
	 * Get the player domination phase.
	 * 
	 * @return the playerDominationPhase
	 */
	public boolean getPlayerDominationPhase() {
		return playerDominationPhase;
	}

	/**
	 * Get the current state.
	 * 
	 * @return Current state.
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * Set the current state.
	 * 
	 * @param currentState
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * Get the player.
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Start Battle by attacking the opponent
	 * 
	 * @param attacker
	 * @param defender
	 * @param diceAttacker
	 * @param diceDefender
	 */
	public void startBattle(Country attacker, Country defender, int diceAttacker, int diceDefender) {
		player.attackOpponent(attacker, defender, diceAttacker, diceDefender);
		if (defender.getCurrentNumberOfArmies() == 0) {
			setCurrentState(PhaseStates.STATE_CAPTURE);
			updateCountryToPlayer(defender, attacker);

		}
		if (attacker.getCurrentNumberOfArmies() == 0)
			setCurrentState(PhaseStates.STATE_ACTIVE);
		setChanged();
		notifyObservers(true);

	}

	private void updateCountryToPlayer(Country defender, Country attacker) {
		ArrayList<Player> playerList = startUpPhase.getPlayerList();
		for (int i = 0; i < startUpPhase.getNumberOfPlayers(); i++) {
			if (playerList.get(i).getPlayerName().equals(defender.getPlayerName())) {
				playerList.get(i).removeCountry(defender);
			} else if (playerList.get(i).getPlayerName().equals(attacker.getPlayerName())) {
				playerList.get(i).addCountry(defender);
			}
		}
		defender.setPlayerName(attacker.getPlayerName());

	}

	public void startActiveState() {
		setCurrentState(PhaseStates.STATE_ACTIVE);
		setChanged();
		notifyObservers();
	}

	public List<Player> getPlayerList() {
		return startUpPhase.getPlayerList();
	}

	public void updateCard() {
		if (player.getRecentAttackWins() == 0) {
			setChanged();
			notifyObservers(PhaseStates.STATE_UPDATE_CARD);
		}
		player.setRecentAttackWins(player.getRecentAttackWins() + 1);
	}

	public void moveToNextPlayer() {
		boolean isCompleted = true;
		for (int i = 0; i < getPlayerList().size(); i++) {
			if (getPlayerList().get(i).getNumberOfArmies() > 0) {
				isCompleted = false;
			}
		}
		while (true) {
			player = robinScheduler.next();

			if (player.getNumberOfArmies() > 0) {
				break;
			} else if (isCompleted) {
				break;
			}
		}

		if (!isCompleted) {
			setCurrentState(PhaseStates.STATE_STARTUP);
			setChanged();
			notifyObservers();
		} else {
			System.out.println("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
			LoggingUtil.logMessage("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
			int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player);
			System.out.println(
					"Total reinforcement armies available for " + player.getPlayerName() + " : " + reinforcementArmies);
			player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
			setCurrentState(PhaseStates.STATE_REINFORCEMENT);
			setChanged();
			notifyObservers();
		}

	}

	public void nextPlayer() {
		player = robinScheduler.next();
	}

	public void updateReinforcementArmies() {
		setCurrentState(PhaseStates.STATE_REINFORCEMENT);
		setChanged();
		notifyObservers();
	}

}
