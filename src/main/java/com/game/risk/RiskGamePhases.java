package com.game.risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.StartUpPhase;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Country;
import com.game.risk.model.Player;
import com.game.risk.view.AttackPhaseView;

/**
 * Phase observable for Phase view and Player World Domination View.
 * 
 * @author Sarthak
 * @author sohrab_singh
 *
 */
public class RiskGamePhases extends Observable {

	/** Startup Phase */
	private StartUpPhase startUpPhase;

	/**
	 * MapFileReader Object
	 */
	private MapFileReader fileParser;
	
	private BufferedReader reader;

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

	/** Round robin scheduler */
	private RoundRobinScheduler<Player> robinScheduler;

	/**
	 * Default Constructor
	 * 
	 * @param fileParser
	 * @param reader 
	 * 
	 * @return
	 */
	public RiskGamePhases(MapFileReader fileParser,BufferedReader reader) {
		this.fileParser = fileParser;
		this.reader = reader;
	}

	/**
	 * Start game phases which will call all phases.
	 * @param players 
	 * 
	 * @throws NumberFormatException
	 *             number format exception
	 * @throws IOException
	 *             Input output exception
	 */
	public void initiateGame(List<Player> players)  {

		// Players playing in round robin fashion
		robinScheduler = new RoundRobinScheduler<Player>(players);
		player = robinScheduler.next();
		notifyObservers();
	}

	/**
	 * @param reader
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public List<Player> executeStartupPhase() throws IOException  {
		System.out.println(":: Input the no of players playing ::");
		int numberOfPlayers = Integer.parseInt(reader.readLine());
		LoggingUtil.logMessage("Total number of Players playing Risk Game : " + numberOfPlayers);

		startUpPhase = new StartUpPhase(fileParser, numberOfPlayers, reader);
		startUpPhase.assignCountries();
		startUpPhase.allocateArmiesToPlayers();
		startUpPhase.assignInitialArmiesToCountries();
		startUpPhase.populateDominationPercentage();

		return startUpPhase.getPlayerList();
	}
	
	/**
	 * Start the Attack Phase
	 * 
	 * @param attackingCountry
	 * @param defendingCountry
	 * 
	 * @param fileParser
	 *            MapFileReader
	 */
	public void startAttackPhase(Country attackingCountry, Country defendingCountry) {
		AttackPhaseView attackView = new AttackPhaseView(this ,attackingCountry, defendingCountry);
		attackView.setVisible(true);
		setCurrentState(PhaseStates.STATE_ATTACK);
		this.addObserver(attackView);
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

	/**
	 * Get Player List.
	 * 
	 * @return list of players
	 */
	public List<Player> getPlayerList() {
		return startUpPhase.getPlayerList();
	}

	/**
	 * Update Card State
	 */
	public void updateCard() {
		if (player.isWinner()) {
			setChanged();
			notifyObservers(PhaseStates.STATE_UPDATE_CARD);
		}
		//player.setRecentAttackWins(player.getRecentAttackWins() + 1);
	}

	/**
	 * Move to next plaver.
	 */
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
			notifyStateChange(PhaseStates.STATE_STARTUP);
		} else {
			int reinforcementArmies = player.findReinforcementArmies();
			player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
			notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
		}

	}


	/**
	 * Next Player
	 */
	public void nextPlayer() {
		player.setWinner(false);
		player = robinScheduler.next();
		player.setNumberOfArmies(player.getNumberOfArmies() + player.findReinforcementArmies());
		notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
	}

	/**
	 * @param start
	 * @param end
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void startFortificationPhase(Country start, Country end)  {
		try {
			player.startFortificationPhase(reader, start, end);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		notifyStateChange(PhaseStates.STATE_ACTIVE);
	}

	/**
	 * @param state 
	 * 
	 */
	public void notifyStateChange(int state) {
		setCurrentState(state);
		setChanged();
		notifyObservers();
	}
}
