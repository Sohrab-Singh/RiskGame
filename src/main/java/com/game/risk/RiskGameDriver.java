/**
 * 
 */
package com.game.risk;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.game.risk.core.StartUpPhase;
import com.game.risk.view.WelcomeScreenView;
import com.game.risk.view.WelcomeScreenView.WelcomeScreenInterface;

/**
 * Risk game driver is main class to call all phases of the game.
 * 
 * @author sohrab_singh
 *
 */
public class RiskGameDriver implements WelcomeScreenView.WelcomeScreenInterface {

	/**
	 * WelcomeScreenView object
	 */
	private static WelcomeScreenView frame;
	private static WelcomeScreenInterface welcomeScreenInterface;

	public RiskGameDriver() {
		welcomeScreenInterface = this;
	}

	/**
	 * Main method for Risk Game Driver.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {
		RiskGameDriver driver = new RiskGameDriver();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new WelcomeScreenView();
					frame.setVisible(true);
					frame.addListener(welcomeScreenInterface);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void notifyRiskGameDriver(int numberOfPlayers) {
		
		System.out.println(numberOfPlayers + " ");
		StartUpPhase startUpPhase = new StartUpPhase(frame.getParser(), numberOfPlayers);
	}
}
