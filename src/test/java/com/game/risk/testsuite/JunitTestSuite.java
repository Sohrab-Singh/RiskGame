package com.game.risk.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.game.risk.core.CountriesGraphTest;
import com.game.risk.core.StartUpPhaseTest;
import com.game.risk.core.parser.MapFileParserTest;
import com.game.risk.core.strategy.impl.AgressivePlayerStrategyTest;
import com.game.risk.core.strategy.impl.BenevolentPlayerStrategyTest;
import com.game.risk.core.strategy.impl.CheaterPlayerStrategyTest;
import com.game.risk.core.strategy.impl.RandomPlayerStrategyTest;
import com.game.risk.core.util.AttackPhaseUtilTest;
import com.game.risk.core.util.FortificationPhaseUtilTest;
import com.game.risk.core.util.MapValidationTest;
import com.game.risk.core.util.ReinforcementPhaseUtilTest;

/**
 * Test suite class to run all the jUnit test cases
 * 
 * @author shubhangi_sheel
 * @author sohrab_singh
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ StartUpPhaseTest.class, CheaterPlayerStrategyTest.class, RandomPlayerStrategyTest.class,
		BenevolentPlayerStrategyTest.class, AgressivePlayerStrategyTest.class, AttackPhaseUtilTest.class,
		CountriesGraphTest.class, MapFileParserTest.class, FortificationPhaseUtilTest.class, MapValidationTest.class,
		ReinforcementPhaseUtilTest.class })
public class JunitTestSuite {
}