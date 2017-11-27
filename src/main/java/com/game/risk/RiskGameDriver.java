package com.game.risk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
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

	/**
	 * Main method for Risk Game Driver Class
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
	 * @throws IOException
	 */
	public static void startGame(MapFileReader fileParser) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		gamePhases = new RiskGamePhases(fileParser, reader);
		List<Player> players = gamePhases.executeStartupPhase();
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
	 * Function for starting a saved game
	 * 
	 * @param input
	 *            FileInputStream type variable
	 * @throws IOException
	 */
	public static void startLoadedGame(FileInputStream input) throws IOException {
		CountriesGraph graph = CountriesGraph.parseFrom(input);
		com.game.risk.model.autogen.GameStateDataProtos.MapFileReader reader = com.game.risk.model.autogen.GameStateDataProtos.MapFileReader
				.parseFrom(input);
		MapFileReader fileReader = new MapFileReader(reader);
		fileReader.updateCountriesModel();
		fileReader.updateContinentsModel();
		com.game.risk.core.CountriesGraph countriesGraph = new com.game.risk.core.CountriesGraph(fileReader);
		countriesGraph.setContinentHashMap(fileReader.getContinentHashMap());
		countriesGraph.updateAdjacentCountriesModel(graph);
		countriesGraph.setCountriesCount(graph.getCountryCount());
		GameState gameState = GameState.parseFrom(input);

		gamePhases = new RiskGamePhases(fileReader);
		List<Player> players = gamePhases.updatePlayerList(gameState.getPlayersListList());
		gamePhases.initializeCurrentPlayer(players, gameState.getCurrentPlayer());
		GamePhaseView gamePhaseView = new GamePhaseView(gamePhases, fileReader);
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
}
