package com.game.risk.core.util;

/**
 * Util class to store different states of the game.
 *
 * @author Sarthak
 */
public class PhaseStates {

	/** State of the game when the startup phase hasn't reached yet. */
	public static final int STATE_INACTIVE = -1;

	/** State to notify when the new turn comes up. */
	public static final int STATE_STARTUP = 0;

	/** Reinforcement Phase state. */
	public static final int STATE_REINFORCEMENT = 1;

	/** Attack Phase state. */
	public static final int STATE_ATTACK = 2;

	/** Fortification Phase state. */
	public static final int STATE_FORTIFY = 3;

	/** State when the Attacker captures defender. */
	public static final int STATE_CAPTURE = 4;

	/** State when further operation is awaited for user input. */
	public static final int STATE_ACTIVE = 5;

	/** State when a new card is to be added after country aquisition. */
	public static final int STATE_UPDATE_CARD = 6;

	/** State when the Attack Phase has been initiated by user. */
	public static final int STATE_INIT_ATTACK_PHASE = 7;

}
