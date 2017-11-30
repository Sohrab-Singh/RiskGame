package com.game.risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.StartUpPhase;
import com.game.risk.core.strategy.impl.AgressivePlayerStrategy;
import com.game.risk.core.strategy.impl.BenevolentPlayerStrategy;
import com.game.risk.core.strategy.impl.CheaterPlayerStrategy;
import com.game.risk.core.strategy.impl.RandomPlayerStrategy;
import com.game.risk.core.util.AttackPhaseUtil;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Country;
import com.game.risk.model.Player;
import com.game.risk.model.autogen.GameStateDataProtos.Player.CardType;
import com.game.risk.model.autogen.GameStateDataProtos.Player.Cards;
import com.game.risk.view.AttackPhaseView;

/**
 * Phase observable for Phase view and Player World Domination View.
 * 
 * @author Sarthak
 * @author sohrab_singh
 */
public class RiskGamePhases extends Observable {

	/** Startup Phase. */
	private StartUpPhase startUpPhase;

	/** MapFileReader Object. */
	private MapFileReader fileParser;

	/** Buffer reader. */
	private BufferedReader reader;

	/** Current State of the game. */
	private int currentState;

	/** Player Domination phase. */
	private Boolean playerDominationPhase;

	/** Player object indicating the current currentPlayer playing the game. */
	private Player currentPlayer;

	/** The tournament mode. */
	private boolean tournamentMode;

	/** Round robin scheduler. */
	private RoundRobinScheduler<Player> robinScheduler;

	/**
	 * Default Constructor.
	 *
	 * @param fileParser
	 *            the file parser
	 * @param reader
	 *            the reader
	 */
	public RiskGamePhases(MapFileReader fileParser, BufferedReader reader) {
		this.fileParser = fileParser;
		this.reader = reader;
	}

	/**
	 * Instantiates a new risk game phases.
	 *
	 * @param fileParser
	 *            the file parser
	 */
	public RiskGamePhases(MapFileReader fileParser) {
		this.fileParser = fileParser;
	}

	/**
	 * Execute Startup Phase.
	 *
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public List<Player> executeStartupPhase() throws IOException {

		System.out.println(":: Input the no of players playing ::");
		int numberOfPlayers = Integer.parseInt(reader.readLine());
		LoggingUtil.logMessage("Total number of Players playing Risk Game : " + numberOfPlayers);

		startUpPhase = new StartUpPhase(fileParser, numberOfPlayers, reader);

		return executeStartUpProcesses();
	}

	/**
	 * Execute start up processes.
	 *
	 * @return the list
	 */
	public List<Player> executeStartUpProcesses() {
		startUpPhase.assignCountries();
		startUpPhase.allocateArmiesToPlayers();
		startUpPhase.assignInitialArmiesToCountries();
		startUpPhase.populateDominationPercentage();

		List<Player> players = startUpPhase.getPlayerList();
		robinScheduler = new RoundRobinScheduler<Player>(players);
		currentPlayer = robinScheduler.next();
		return players;
	}

	/**
	 * Execute startup phase.
	 *
	 * @param computerPlayers
	 *            the computer players
	 * @return the list
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public List<Player> executeStartupPhase(List<String> computerPlayers) throws IOException {
		startUpPhase = new StartUpPhase(fileParser, computerPlayers);
		return executeStartUpProcesses();
	}

	/**
	 * Checks if is tournament mode.
	 *
	 * @return the tournamentMode
	 */
	public boolean isTournamentMode() {
		return tournamentMode;
	}

	/**
	 * Sets the tournament mode.
	 *
	 * @param tournamentMode
	 *            the tournamentMode to set
	 */
	public void setTournamentMode(boolean tournamentMode) {
		this.tournamentMode = tournamentMode;
	}

	/**
	 * Start the Attack Phase.
	 *
	 * @param attackingCountry
	 *            the attacking country
	 * @param defendingCountry
	 *            the defending country
	 */
	public void startAttackPhase(Country attackingCountry, Country defendingCountry) {
		AttackPhaseView attackView = new AttackPhaseView(this, attackingCountry, defendingCountry);
		LoggingUtil.logMessage("\nSelect Dice Rolls to begin Attack.");
		attackView.setVisible(true);
		setCurrentState(PhaseStates.STATE_ATTACK);
		this.addObserver(attackView);
	}

	/**
	 * Get the currentPlayer domination phase.
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
	 *            the new current state
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * Get the currentPlayer.
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return currentPlayer;
	}

	/**
	 * Get the current player.
	 *
	 * @return currentPlayer Player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Set the current player of the game.
	 *
	 * @param currentPlayer
	 *            the new current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Start Battle by attacking the opponent.
	 *
	 * @param attacker
	 *            the attacker
	 * @param defender
	 *            the defender
	 * @param diceAttacker
	 *            the dice attacker
	 * @param diceDefender
	 *            the dice defender
	 */
	public void startBattle(Country attacker, Country defender, int diceAttacker, int diceDefender) {
		currentPlayer.attackOpponent(attacker, defender, diceAttacker, diceDefender);
		if (defender.getCurrentNumberOfArmies() == 0) {
			setCurrentState(PhaseStates.STATE_CAPTURE);
			updateCountryToPlayer(defender, attacker);
			currentPlayer.setWinner(true);

		}
		setChanged();
		notifyObservers(true);
	}

	/**
	 * Notify attack ends.
	 */
	public void notifyAttackEnds() {
		setCurrentState(PhaseStates.STATE_ACTIVE);
		setChanged();
		if (AttackPhaseUtil.isattackEnds(currentPlayer)) {
			notifyObservers("attack");
			LoggingUtil.logMessage(currentPlayer.getPlayerName() + " cannot attack anymore!");
		} else {
			notifyObservers();
		}
	}

	/**
	 * Update country to player.
	 *
	 * @param defender
	 *            the defender
	 * @param attacker
	 *            the attacker
	 */
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
		return robinScheduler.getList();
	}

	/**
	 * Set Player List.
	 *
	 * @param playerList
	 *            the new player list
	 * @return list of players
	 */
	public void setPlayerList(List<Player> playerList) {
		robinScheduler.setList(playerList);
	}

	/**
	 * Update Card State.
	 */
	public void updateCard() {
		if (currentPlayer.isWinner()) {
			currentPlayer.addCard();
		}
	}

	/**
	 * Update domination percentage.
	 */
	public void updateDominationPercentage() {
		startUpPhase.populateDominationPercentage();
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
			currentPlayer = robinScheduler.next();

			if (currentPlayer.getNumberOfArmies() > 0) {
				break;
			} else if (isCompleted) {
				break;
			}
		}

		if (!isCompleted) {
			notifyStateChange(PhaseStates.STATE_STARTUP);
		} else {
			int reinforcementArmies = currentPlayer.findReinforcementArmies();
			currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() + reinforcementArmies);
			notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
		}

	}

	/**
	 * Next Player.
	 */
	public void nextPlayer() {

		currentPlayer.setWinner(false);
		currentPlayer = robinScheduler.next();
		currentPlayer.viewCards();
		while (currentPlayer.getCardList().size() >= 5) {
			currentPlayer.exchangeCardsWithArmies();
			currentPlayer.removeCardsFromDeck();
		}
		currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() + currentPlayer.findReinforcementArmies());
		notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
	}

	/**
	 * Start fortification phase.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @throws NumberFormatException
	 *             the number format exception
	 */
	public void startFortificationPhase(Country start, Country end) {
		LoggingUtil.logMessage("Fortification Phase starts");
		setCurrentState(PhaseStates.STATE_FORTIFY);
		AttackPhaseView view = new AttackPhaseView(this, start, end);
		LoggingUtil.logMessage("\nSelect the Number of Armies to Fortify.");
		view.setVisible(true);
		this.addObserver(view);
	}

	/**
	 * Notify State Change.
	 *
	 * @param state
	 *            the state
	 */
	public void notifyStateChange(int state) {
		setCurrentState(state);
		setChanged();
		notifyObservers();
	}

	/**
	 * Notify State Change.
	 *
	 * @param state
	 *            the state
	 */
	public void notifyStateChange(String state) {
		setChanged();
		notifyObservers(state);
	}

	/**
	 * Populate the player list and create a Player Robin Scheduler after game
	 * reload.
	 *
	 * @param playerList
	 *            the player list
	 * @return the list
	 */
	public List<Player> updatePlayerList(List<com.game.risk.model.autogen.GameStateDataProtos.Player> playerList) {
		List<Player> players = new ArrayList<>();
		for (com.game.risk.model.autogen.GameStateDataProtos.Player player : playerList) {
			Player newPlayer = new Player();
			newPlayer.setPlayerName(player.getPlayerName());
			for (com.game.risk.model.autogen.GameStateDataProtos.Country country : player.getCountryOwnedList()) {
				if (fileParser.getCountriesHashMap().containsKey(country.getCountryName())) {
					newPlayer.addCountry(fileParser.getCountriesHashMap().get(country.getCountryName()));
				}
			}
			List<com.game.risk.model.CardType> cards = new ArrayList<>();
			for (Cards card : player.getCardListList()) {
				if (card.getCard().equals(CardType.ARTILLERY)) {
					cards.add(com.game.risk.model.CardType.values()[3]);
				} else if (card.getCard().equals(CardType.INFANTRY)) {
					cards.add(com.game.risk.model.CardType.values()[1]);
				} else {
					cards.add(com.game.risk.model.CardType.values()[2]);
				}
			}
			newPlayer.setCardList(cards);
			newPlayer.setCurrentDominationPercentage(player.getPercentageDomination());
			newPlayer.setNumberOfArmies(player.getNumberOfArmies());
			players.add(newPlayer);
		}
		return players;

	}

	/**
	 * Initialize current player.
	 *
	 * @param players
	 *            the players
	 * @param messagePlayer
	 *            the message player
	 */
	public void initializeCurrentPlayer(List<Player> players,
			com.game.risk.model.autogen.GameStateDataProtos.Player messagePlayer) {
		Player presentPlayer = null;
		for (Player player : players) {
			if (player.getPlayerName().equals(messagePlayer.getPlayerName())) {
				presentPlayer = player;
				break;
			}
		}
		robinScheduler = new RoundRobinScheduler<Player>(players);
		currentPlayer = robinScheduler.getUpdateItem(presentPlayer);

	}

	/**
	 * Gets the file parser.
	 *
	 * @return the file parser
	 */
	public MapFileReader getFileParser() {
		return fileParser;
	}

	/**
	 * Sets the file parser.
	 *
	 * @param fileParser
	 *            the new file parser
	 */
	public void setFileParser(MapFileReader fileParser) {
		this.fileParser = fileParser;
	}

	/**
	 * Set Player's Strategy.
	 *
	 * @param player
	 *            the player
	 */
	public void selectComputerPlayer(Player player) {

		switch (player.getPlayerName()) {

		case "Aggressive":
			player.setPlayerStrategy(new AgressivePlayerStrategy(currentPlayer, fileParser.getCountriesGraph(), this));
			break;
		case "Benevolent":
			player.setPlayerStrategy(new BenevolentPlayerStrategy(currentPlayer, fileParser.getCountriesGraph()));
			break;
		case "Random":
			player.setPlayerStrategy(new RandomPlayerStrategy(currentPlayer, fileParser.getCountriesGraph(), this));
			break;
		case "Cheater":
			player.setPlayerStrategy(new CheaterPlayerStrategy(currentPlayer, fileParser.getCountriesGraph(), this));
			break;
		default:
			break;
		}
	}

	/**
	 * Gets the total countries.
	 *
	 * @return the total countries
	 */
	public int getTotalCountries() {

		return fileParser.getCountriesHashMap().size();
	}

}
