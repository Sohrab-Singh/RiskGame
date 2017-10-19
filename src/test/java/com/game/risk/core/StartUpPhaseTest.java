package com.game.risk.core;

import com.game.risk.model.Country;
import com.game.risk.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StartUpPhaseTest {
    private MapFileReader mapFileReader;
    private StartUpPhase startUpPhase;
    private int numberofplayers;
    private Player p1;
    private Player p2;
    private ArrayList<Player> list1;
    private Country c1;

    @Before
    public void setUp() throws Exception {
        mapFileReader = new MapFileReader("Canada.map");
        startUpPhase = new StartUpPhase(mapFileReader, 2);
        numberofplayers = 2;
        p1 = new Player();
        p2 = new Player();
        list1= new ArrayList<Player>();
        list1.add(p1);
        list1.add(p2);
        startUpPhase.setPlayersList(list1);

    }

    @Test

    public void getPlayerList() throws Exception {

        assertEquals(list1, startUpPhase.getPlayerList());

    }

    @Test
    public void allocateArmiesToPlayers() throws Exception {
        startUpPhase.allocateArmiesToPlayers();
        assertNotEquals(45, p1.getNumberOfArmies());
    }


    @Test
    public void getNumberOfPlayers() throws Exception {

        startUpPhase.setNumberOfPlayers(2);
        assertEquals(2, startUpPhase.getNumberOfPlayers());
    }

}