package com.game.risk.core.util;

import com.game.risk.model.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Vida Abdollahi
 */

public class AttackPhaseUtilTest {
    /**
     * Attack Phase
     */

    private AttackPhaseUtil attackPhaseUtil;

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
     * @throws Exception
     */

    //@Before
    public void setUp() throws Exception {

        attacker = new Country("Iran");
        defender = new Country("India");

        attacker.setCurrentNumberOfArmies(5);
        defender.setCurrentNumberOfArmies(4);

        attackPhaseUtil = new AttackPhaseUtil();

    }

    /**
     * Test Case for StartBattle in attack Phase
     * @throws Exception
     */

    //@Test
    public void startBattle() throws Exception {

        attackPhaseUtil.startBattle(attacker,defender,6 ,1);
        assertEquals(3, defender.getCurrentNumberOfArmies());

    }

}

