package com.game.risk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import com.game.risk.core.MapEditor;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.StartUpPhase;
import com.game.risk.core.util.FortificationPhaseUtil;
import com.game.risk.core.util.ReinforcementPhaseUtil;
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

		String fileName = "C:\\Concordia University\\Study Material\\SOEN 6441 Advanced Programming Practices\\RISK Game - Project\\Québec.MAP";
		MapFileReader fileParser = new MapFileReader(fileName);
		if (!fileParser.checkFileValidation()) {
			System.out.println("Invalid File Selected!");
			return;
		}
		fileParser.readFile();
		MapEditor view = new MapEditor(fileParser);
		// If the existing map is to be editied
		view.readMapEditor(false);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(":: Input the no of players playing ::");
		int numberOfPlayers = Integer.parseInt(reader.readLine());

		// Startup Phase
		StartUpPhase startUpPhase = new StartUpPhase(fileParser, numberOfPlayers, reader);
		startUpPhase.assignCountries();
		startUpPhase.allocateArmiesToPlayers();
		startUpPhase.assignInitialArmiesToCountries();
		startUpPhase.allocateRemainingArmiesToCountries();

		// Players playing in round robin fashion
		RoundRobinScheduler<Player> robinScheduler = new RoundRobinScheduler<Player>(startUpPhase.getPlayerList());

		// As of now we are only giving 5 rounds , when attack phase will be implemented
		// then we will end game when player wins.
		int rounds = 0;
		while (rounds < 5) {

			Player player = robinScheduler.next();

			// Reinforcement phase
			System.out.println("\nReinforcement phase begins for " + player.getPlayerName() + "\n");
			Continent continent = fileParser.getContinentHashMap()
					.get(player.getCountriesOwned().get(0).getContinentName());
			int reinforcementArmies = ReinforcementPhaseUtil.calculateReinforcementArmies(player, continent);
			System.out.println(
					"Total reinforcement armies available for " + player.getPlayerName() + " : " + reinforcementArmies);
			player.setNumberOfArmies(player.getNumberOfArmies() + reinforcementArmies);

			for (Country country : player.getCountriesOwned()) {
				if (player.getNumberOfArmies() > 0) {
					System.out.println(
							"How many armies do you want to assign to your country " + country.getCountryName() + " ?");
					System.out.println("Current number of armies of " + country.getCountryName() + " is "
							+ country.getCurrentNumberOfArmies() + " | Available armies : "
							+ player.getNumberOfArmies());
					int armies = Integer.parseInt(reader.readLine());
					player.assignArmiesToCountries(country, armies);
				} else {
					break;
				}
			}

			// Attack phase will be here

			// Fortification phase
			System.out.println("\nFortification phase begins.\n");
			Country country1 = null;
			Country country2 = null;
			boolean flag = true;
			while (flag) {
				flag = false;
				System.out.println("Enter the Country Name from where you want to move some Army");
				country1 = fileParser.getCountriesHashMap().get(reader.readLine());

				System.out.println("Enter the Country Name to which you want to move some Army");
				country2 = FortificationPhaseUtil.retrieveAndSelectAdjacentArmies(country1, reader, fileParser);

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
				System.out.println("Enter the Number of Armies to Move");
				fortificationArmies = Integer.parseInt(reader.readLine());
				if (fortificationArmies > country1.getCurrentNumberOfArmies()) {
					System.out.println("You can not enter more armies than donor country have");
					System.out.println("Enter again");
					countryFlag = true;
				}
			}

			FortificationPhaseUtil.moveArmiesBetweenCountries(country1, country2, fortificationArmies,
					fileParser.getCountriesGraph().getAdjListHashMap());
			rounds++;
		}
		reader.close();

	}
}
