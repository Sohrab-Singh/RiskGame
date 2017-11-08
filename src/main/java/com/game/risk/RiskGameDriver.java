package com.game.risk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.game.risk.core.MapFileReader;
import com.game.risk.model.Player;
import com.game.risk.view.AttackPhaseView;
import com.game.risk.view.PhaseView;
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
	private static PhaseObservable phaseObservable;

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
		thread.start();
	}

	/**
	 * Main function for starting game.
	 * 
	 * @param fileParser
	 *            parser to read
	 */
	public static void startGame(MapFileReader fileParser) {
		phaseObservable = new PhaseObservable(fileParser);

		try {
			phaseObservable.startGamePhases();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Method to start startup phase.
	 * 
	 * @param fileParser
	 *            parser to read
	 * @param players
	 *            number of players
	 */
	public static void startStartupPhase(MapFileReader fileParser, List<Player> players) {
		PhaseView view = new PhaseView(fileParser, players);
		phaseObservable.addObserver(view);
	}

	/**
	 * Method to start attack phase.
	 * 
	 * @param fileParser
	 */
	public static void startAttackPhase(MapFileReader fileParser) {
		AttackPhaseView attackView = new AttackPhaseView(fileParser);
		phaseObservable.addObserver(attackView);
	}
}
