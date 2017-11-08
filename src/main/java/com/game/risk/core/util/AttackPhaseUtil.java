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

		if (attackDiceRoll == 1) {
			// Comparing the best dice roll
			if (attackDice[0] > defendDice[0])
				defender.looseArmy();
			else
				attacker.looseArmy();
		} else if (attackDiceRoll > 1) {
			// Comparing the best dice roll
			if (attackDice[0] > defendDice[0])
				defender.looseArmy();
			else
				attacker.looseArmy();

			// If defender rolled 2 dices
			if (defendDiceRoll == 2) {
				// Comparing 2nd best dice roll
				if (attackDice[1] > defendDice[1])
					defender.looseArmy();
				else
					attacker.looseArmy();
			}
		}

	}

}
