package com.game.risk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.game.risk.core.MapFileReader;
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

	private static PhaseObservable phaseObservable;

	/**
	 * Main method for Risk Game Driver Class
	 * 
	 * @param args
	 *            String[] type argument
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
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

	public static void startGame(MapFileReader fileParser) {
		PhaseView view = new PhaseView(fileParser);

		phaseObservable = new PhaseObservable(fileParser);
		phaseObservable.addObserver(view);
		try {
			phaseObservable.startGamePhases();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

	public static void startAttackPhase(MapFileReader fileParser) {
		AttackPhaseView attackView = new AttackPhaseView(fileParser);
		phaseObservable.addObserver(attackView);
	}

}
