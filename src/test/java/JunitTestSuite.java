import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.game.risk.core.CountriesGraphTest;
import com.game.risk.core.StartUpPhaseTest;
import com.game.risk.core.parser.MapFileParserTest;
import com.game.risk.core.util.FortificationPhaseUtilTest;
import com.game.risk.core.util.MapValidationTest;
import com.game.risk.core.util.ReinforcementPhaseUtilTest;

/**
 * Test suite class to run all the jUnit test cases
 * @author shubhangi_sheel
 *
 */
@RunWith(Suite.class)
@SuiteClasses({CountriesGraphTest.class,MapFileParserTest.class,FortificationPhaseUtilTest.class, MapValidationTest.class, ReinforcementPhaseUtilTest.class })
public class JunitTestSuite {   
}  