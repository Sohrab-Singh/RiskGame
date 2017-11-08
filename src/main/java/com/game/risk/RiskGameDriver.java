package com.game.risk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.SwingUtilities;

import com.game.risk.core.MapFileReader;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.model.Player;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Country;
import com.game.risk.view.AttackPhaseView;
import com.game.risk.view.CardExchangeView;
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
	 * Start the Attack Phase
	 * 
	 * @param attackingCountry
	 * @param defendingCountry
	 * 
	 * @param fileParser
	 *            MapFileReader
	 */
	public static void startAttackPhase(Country attackingCountry, Country defendingCountry) {
		AttackPhaseView attackView = new AttackPhaseView(attackingCountry, defendingCountry);
		attackView.setVisible(true);
		phaseObservable.setCurrentState(PhaseStates.STATE_ATTACK);
		phaseObservable.addObserver(attackView);
	}

	/**
	 * Start the battle
	 * 
	 * @param attacker
	 *            the attacker
	 * @param defender
	 *            the defender
	 * @param diceAttacker
	 *            the dice Attacker
	 * @param diceDefender
	 *            the dice defender.
	 */
	public static void startBattle(Country attacker, Country defender, int diceAttacker, int diceDefender) {
		phaseObservable.startBattle(attacker, defender, diceAttacker, diceDefender);
	}

	/**
	 * Initiate the Active Game State after Attack
	 */
	public static void initiatePostAttackUpdate(boolean isCaptured) {
		phaseObservable.startActiveState();
		// If 1st Recent Attack win, then update as adding card
		if (isCaptured)
			phaseObservable.updateCard();
	}

	/**
	 * Initiate Active Game State
	 */
	public static void initiateActiveState() {
		phaseObservable.startActiveState();
	}

	/**
	 * Re initiate Reinforcement Phase
	 */
	public static void reinitiateReinforcement() {
		phaseObservable.updateReinforcementArmies();
	}

	/**
	 * Start Card Exchange View and attach to observable
	 */
	public static void startCardExchangeView() {
		CardExchangeView view = new CardExchangeView();
		view.setVisible(true);
		phaseObservable.addObserver(view);
	}

	/**
	 * Send control to next player.
	 */
	public static void setControlToNewPlayer() {
		phaseObservable.moveToNextPlayer();

	}

	public static void moveToNextTurn() {
		phaseObservable.nextPlayer();
	}

	public static void initFortification(Country start, Country end) {
		try {
			phaseObservable.startFortify(start, end);
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

}
