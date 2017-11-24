package com.game.risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import com.game.risk.core.MapFileReader;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.model.Player;
import com.game.risk.view.CardExchangeView;
import com.game.risk.view.GamePhaseView;
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
		GamePhaseView gamePhaseView = new GamePhaseView(gamePhases,fileParser, players);
		gamePhases.addObserver(gamePhaseView);
		gamePhases.initiateGame(players);
		CardExchangeView cardExchangeView = new CardExchangeView();
		gamePhases.addObserver(cardExchangeView);
		cardExchangeView.setVisible(true);
	}
}
