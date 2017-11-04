package com.game.risk.core.util;

/**
 * Util class to store different states of the game
 * 
 * @author Sarthak
 * 
 */
public class PhaseStates {

	/**
	 * State of the game when the startup phase hasn't reached yet
	 */
	public static final int STATE_INACTIVE = -1;

	/**
	 * State to notify when the new turn comes up
	 */
	public static final int STATE_STARTUP = 0;

	/**
	 * Reinforcement Phase state
	 */
	public static final int STATE_REINFORCEMENT = 1;

	/**
	 * Attack Phase state
	 */
	public static final int STATE_ATTACK = 2;

	/**
	 * Fortification Phase state
	 */
	public static final int STATE_FORTIFY = 3;

}
