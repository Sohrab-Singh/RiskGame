package com.game.risk.model;

/**
 * @author sohrab_singh
 *
 */
public enum CardType {

	/**
	 * 
	 */
	Infantry(1),

	/**
	 * 
	 */
	Cavalry(2),

	/**
	 * 
	 */
	Artillery(3);

	private int value;

	CardType(int v) {
		value = v;
	}

	int getCardValue() {
		return value;
	}
	
	
}
