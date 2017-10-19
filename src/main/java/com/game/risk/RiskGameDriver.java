
package com.game.risk;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.game.risk.core.StartUpPhase;
import com.game.risk.view.WelcomeScreenView;
import com.game.risk.core.ReinforcementPhase;
import com.game.risk.core.parser.MapFileParser;
import com.game.risk.model.Continent;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

/**
 * Risk game driver is main class to call all phases of the game.
 * 
 * @author sohrab_singh
 * @author Sarthak
 *
 */
public class RiskGameDriver {

	private static WelcomeScreenView frame;

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {
		frame = new WelcomeScreenView();
		frame.setVisible(true);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					MapFileParser fileParser = frame.getParser();
					System.out.println(fileParser);
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					int numberOfPlayers = Integer.parseInt(reader.readLine());

					// Startup Phase
					StartUpPhase startUpPhase = new StartUpPhase(fileParser, numberOfPlayers);
					startUpPhase.assignCountries();
					startUpPhase.allocateArmiesToPlayers();
					startUpPhase.assignInitialArmiesToCountries();
					startUpPhase.allocateRemainingArmiesToCountries();

					// Players playing in round robin fashion
					RoundRobinScheduler<Player> robinScheduler = new RoundRobinScheduler<Player>(
							startUpPhase.getPlayerList());
					// As of now we are only giving 5 rounds , when attack phase will be implemented
					// then we will end game when player wins.
					int rounds = 0;
					while (rounds < 5) {

						Player player = robinScheduler.next();
						// Reinforcement phase
						System.out.println("Reinforcement phase begins");
						Continent continent = fileParser.getContinentHashMap()
								.get(player.getCountriesOwned().get(0).getContinentName());
						int reinforcementArmies = ReinforcementPhase.calculateReinforcementArmies(player, continent);
						System.out.println("Total reinforcement armies available for " + player.getPlayerName() + " : "
								+ reinforcementArmies);
						player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);
						System.out.println("Total reinforcement armies available for " + player.getPlayerName() + " : "
								+ reinforcementArmies);
						player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);

						for (Country country : player.getCountriesOwned()) {
							System.out.println("How many armies do you want to assign to your country "
									+ country.getCountryName() + " ?");
							System.out.println("Current number of armies of " + country.getCountryName() + " is "
									+ country.getCurrentNumberOfArmies());
							int armies = Integer.parseInt(reader.readLine());
							player.assignArmiesToCountries(country, armies);
						}

						// Attack phase will be here

						// Fortification phase
						System.out.println("Fortification phase begins");
						Country country1 = null;
						Country country2 = null;
						boolean flag = true;
						while (flag) {
							flag = false;
							System.out.println("Enter the country name from where you want to move some army");
							country1 = fileParser.getCountriesHashMap().get(reader.readLine());
							System.out.println("Enter the country name to which you want to move some army");
							country2 = fileParser.getCountriesHashMap().get(reader.readLine());

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
							System.out.println("Enter the number of armies to move");
							fortificationArmies = Integer.parseInt(reader.readLine());
							if (fortificationArmies > country1.getCurrentNumberOfArmies()) {
								System.out.println("You can not enter more armies than donor country have");
								System.out.println("Enter again");
								countryFlag = true;
							}
						}

						ReinforcementPhase.moveArmiesBetweenCountries(country1, country2, fortificationArmies,
								fileParser.getCountriesGraph().getAdjListHashMap());
						rounds++;
					}
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	static class Mythread extends Thread {
		Thread predecessor;

		public Mythread(Thread predecessor) {
			this.predecessor = predecessor;
		}

		@Override
		public void run() {
			super.run();
			if (predecessor != null && predecessor.isAlive()) {
				try {
					predecessor.join();
					System.out.println("Hello");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
