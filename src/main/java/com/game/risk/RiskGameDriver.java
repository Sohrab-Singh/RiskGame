package com.game.risk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.SwingUtilities;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Player;
import com.game.risk.model.autogen.GameStateDataProtos.CountriesGraph;
import com.game.risk.model.autogen.GameStateDataProtos.GameState;
import com.game.risk.view.CardExchangeView;
import com.game.risk.view.GamePhaseView;
import com.game.risk.view.PlayerDominationView;
import com.game.risk.view.WelcomeScreenView;

/**
 * Risk game driver is main class to call all phases of the game.
 * 
 * @author sohrab_singh
 * @author Sarthak
 *
 */
public class RiskGameDriver {

	/** Phase Observable. */
	private static RiskGamePhases gamePhases;
	
	private static LoggingUtil loggingUtil = new LoggingUtil();

	/**
	 * Main method for Risk Game Driver Class.
	 *
	 * @param args
	 *            String[] type argument
	 * @throws IOException
	 *             IO Exception
	 * @throws InterruptedException
	 *             Interrupted Exception
	 * @throws InvocationTargetException
	 *             InvoiceTargetException
	 */
	public static void main(String[] args) throws IOException, InterruptedException, InvocationTargetException {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				WelcomeScreenView frame = new WelcomeScreenView();
				frame.setVisible(true);
			}
		});

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoggingUtil().showLoggingWindow();
			}
		});
		thread.start();
	}

	/**
	 * Main function for starting game.
	 *
	 * @param fileParser
	 *            parser to read
	 * @param playerNames
	 *            the player names
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void startGame(MapFileReader fileParser, List<String> playerNames) throws IOException {
		gamePhases = new RiskGamePhases(fileParser);
		List<Player> players = gamePhases.executeStartupPhase(playerNames);
		System.out.println(players.size());
		GamePhaseView gamePhaseView = new GamePhaseView(gamePhases, fileParser);
		gamePhases.addObserver(gamePhaseView);
		gamePhases.notifyStateChange(PhaseStates.STATE_STARTUP);
		CardExchangeView cardExchangeView = new CardExchangeView();
		PlayerDominationView dominationView = new PlayerDominationView(players);
		for (Player player : players) {
			player.addObserver(cardExchangeView);
			player.addObserver(dominationView);
		}
		dominationView.setVisible(true);
		cardExchangeView.setVisible(true);

	}

	/**
	 * Function for starting a saved game.
	 *
	 * @param input
	 *            FileInputStream type variable
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void startLoadedGame(FileInputStream input) throws IOException {
		CountriesGraph graph = CountriesGraph.parseDelimitedFrom(input);
		com.game.risk.model.autogen.GameStateDataProtos.MapFileReader reader = com.game.risk.model.autogen.GameStateDataProtos.MapFileReader
				.parseDelimitedFrom(input);
		MapFileReader fileReader = new MapFileReader(reader);
		fileReader.updateCountriesModel();
		fileReader.updateContinentsModel();
		com.game.risk.core.CountriesGraph countriesGraph = new com.game.risk.core.CountriesGraph(fileReader);
		countriesGraph.setContinentHashMap(fileReader.getContinentHashMap());
		countriesGraph.updateAdjacentCountriesModel(graph);
		fileReader.setCountriesGraph(countriesGraph);
		countriesGraph.setCountriesCount(graph.getCountryCount());
		GameState gameState = GameState.parseDelimitedFrom(input);
		gamePhases = new RiskGamePhases(fileReader);
		List<Player> players = gamePhases.updatePlayerList(gameState.getPlayersListList());
		gamePhases.initializeCurrentPlayer(players, gameState.getCurrentPlayer());
		GamePhaseView gamePhaseView = new GamePhaseView(gamePhases, fileReader);
		gamePhaseView.setVisible(true);
		gamePhases.addObserver(gamePhaseView);
		gamePhases.notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
		CardExchangeView cardExchangeView = new CardExchangeView();
		PlayerDominationView dominationView = new PlayerDominationView(players);
		for (Player player : players) {
			player.addObserver(cardExchangeView);
			player.addObserver(dominationView);
		}
		dominationView.setVisible(true);
		cardExchangeView.setVisible(true);
	}

	/**
	 * Start tournament mode.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void startTournamentMode() throws IOException {
		List<String> mapfiles = new ArrayList<>();
		List<String> computerPlayers = new ArrayList<>();
		computerPlayers.add("Cheater");
		computerPlayers.add("Random");
		computerPlayers.add("Aggressive");
		computerPlayers.add("Benevolent");
		int gamesToBePlayed = 3;
		HashMap<String, List<String>> gameOutcome = new HashMap<>();
		int mapNumber = 0;
		String path = "C:\\Concordia University\\Study Material\\SOEN 6441 Advanced Programming Practices\\RISK Game - Project\\Québec.MAP";
		String path2 = "C:\\Concordia University\\Study Material\\SOEN 6441 Advanced Programming Practices\\RISK Game - Project\\Annys Piratenwelt.map";
		mapfiles.add(path);
		mapfiles.add(path2);
		for (String fileName : mapfiles) {
			mapNumber++;
			File file = new File(path);
			System.out.println("Game is being played on " + path);

			MapFileReader parser = new MapFileReader(file);
			if (!parser.checkFileValidation()) {
				System.out.println("Invalid File Selected.");
				loggingUtil.logMessage("Invalid File Selected.");
				return;
			}
			parser.readFile();
			List<String> list = new ArrayList<>();
			list.clear();
			for (int i = 0; i < gamesToBePlayed; i++) {
				gamePhases = new RiskGamePhases(parser);
				gamePhases.setTournamentMode(true);

				List<Player> players = gamePhases.executeStartupPhase(computerPlayers);
				String winner = null;
				for (int turn = 0; turn < 20; turn++) {
					for (Player player : players) {
						gamePhases.selectComputerPlayer(player);
						player.executePhases();
						if (player.getCountriesOwned().size() == gamePhases.getTotalCountries()) {
							winner = player.getPlayerName();
							break;
						}
					}
					if (winner != null) {
						System.out.println("Turn Count: " + turn + 1);
						break;
					}
				}
				if (winner == null) {
					winner = "Draw";
					System.out.println("\n** There is Draw between the battle of players for Game" + (i + 1) + " on Map"
							+ mapNumber + " **");
				} else {
					System.out.println("\n** " + winner + " is declared winner for Game" + (i + 1) + " on Map"
							+ mapNumber + " **");
				}
				list.add(winner);
			}
			gameOutcome.put(fileName, list);

		}

		int mapCount = 0;
		for (String key : gameOutcome.keySet()) {
			String message = "\n\n::::::::::: MAP " + (++mapCount) + " Outcome :::::::::::";
			System.out.println(message);
			System.out.println("Map Selected: " + key);
			LoggingUtil.logMessage(message);
			LoggingUtil.logMessage("Map Selected: " + key);
			for (int i = 0; i < gameOutcome.get(key).size(); i++) {
				String winMessage = "Game " + (i + 1) + ": " + gameOutcome.get(key).get(i);
				LoggingUtil.logMessage(winMessage);
				System.out.println(winMessage);
			}
		}

	}
}
