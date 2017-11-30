package com.game.risk.core.util;

import com.game.risk.core.StartUpPhase;
import com.game.risk.model.Country;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author sohrab_singh
 * @author Vida Abdollahi
 * @author shubhangi_sheel
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LoggingUtil.class, AttackPhaseUtil.class })
public class AttackPhaseUtilTest {

	/**
	 * Attacker Country
	 */

	private Country attacker;

	/**
	 * Defender Country
	 */

	private Country defender;

	/**
	 * Intial Setup
	 * 
	 * @throws Exception
	 */

	@Before
	public void setUp() throws Exception {

		attacker = new Country("Iran");
		defender = new Country("India");

		attacker.setCurrentNumberOfArmies(5);
		defender.setCurrentNumberOfArmies(4);

		PowerMockito.mockStatic(LoggingUtil.class);
		PowerMockito.doNothing().when(LoggingUtil.class);

	}

	/**
	 * Test Case for StartBattle in attack Phase
	 * 
	 * @throws Exception
	 */

	@Test
	public void startBattle() throws Exception {

		AttackPhaseUtil.startBattle(attacker, defender, 6, 1);
		assertEquals(3, defender.getCurrentNumberOfArmies());

	}

}
