package com.game.risk.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.game.risk.model.Country;

/**
 * Class which contains all the utility methods related to attack phase.
 * 
 * @author Sarthak
 * @author sohrab_singh
 *
 */
public class AttackPhaseUtil {

	/**
	 * Start the battle.
	 * 
	 * @param attacker
	 *            the attacker
	 * @param defender
	 *            the defender
	 * @param attackDiceRoll
	 *            the attacker Dice Roll
	 * @param defendDiceRoll
	 *            the defender dice Roll
	 */
	public static void startBattle(Country attacker, Country defender, int attackDiceRoll, int defendDiceRoll) {
		Integer[] attackDice = new Integer[attackDiceRoll];
		Integer[] defendDice = new Integer[defendDiceRoll];
		Random random = new Random();

		for (int i = 0; i < attackDiceRoll; i++)
			attackDice[i] = random.nextInt(6) + 1;
		for (int i = 0; i < defendDiceRoll; i++)
			defendDice[i] = random.nextInt(6) + 1;

		// Sort in descending order to find their respective best dice roll
		Arrays.sort(attackDice, Collections.reverseOrder());
		Arrays.sort(defendDice, Collections.reverseOrder());
		String message = "Caclulating the highest dice roll";
		LoggingUtil.logMessage(message);

		if (attackDiceRoll == 1) {
			// Comparing the best dice roll
			LoggingUtil.logMessage("1 Attacker Dice Roll Chosen");
			LoggingUtil.logMessage("Comparing the best dice roll of attacker [" + attackDice[0] + "] vs defender ["
					+ defendDice[0] + "]");
			if (attackDice[0] > defendDice[0]) {
				LoggingUtil.logMessage("Defender Looses an army");
				defender.looseArmy();
			} else {
				LoggingUtil.logMessage("Attacker looses an army");
				attacker.looseArmy();
			}
		} else if (attackDiceRoll > 1) {
			// Comparing the best dice roll
			LoggingUtil.logMessage(attackDiceRoll + " Attacker Dice Roll Chosen");
			LoggingUtil.logMessage("Comparing the best dice roll of attacker [" + attackDice[0] + "] vs defender ["
					+ defendDice[0] + "]");
			if (attackDice[0] > defendDice[0]) {
				LoggingUtil.logMessage("Defender Looses an army");
				defender.looseArmy();
			} else {
				LoggingUtil.logMessage("Attacker Looses an army");
				attacker.looseArmy();
			}
			// If defender rolled 2 dices
			if (defendDiceRoll == 2) {
				LoggingUtil.logMessage("Defender has 2 Dice Roll, 2nd Highest Dice Roll is Compared");
				LoggingUtil.logMessage(
						"Attack Dice Roll [" + attackDice[1] + "] vs Defender Dice Roll [" + defendDice[1] + "]");
				// Comparing 2nd best dice roll
				if (attackDice[1] > defendDice[1]) {
					LoggingUtil.logMessage("Defender looses an army");
					defender.looseArmy();
				} else {
					LoggingUtil.logMessage("Attacker looses an army");
					attacker.looseArmy();
				}
			}
		}

	}

}
