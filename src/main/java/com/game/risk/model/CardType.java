package com.game.risk.model;

/**
 * The Enum CardType.
 *
 * @author sohrab_singh
 */
public enum CardType {

	/** The Infantry. */
	Infantry(1),

	/** The Cavalry. */
	Cavalry(2),

	/** The Artillery. */
	Artillery(3);

	/** The value. */
	private int value;

	/**
	 * Instantiates a new card type.
	 *
	 * @param v
	 *            the v
	 */
	CardType(int v) {
		value = v;
	}

	/**
	 * Gets the card value.
	 *
	 * @return the card value
	 */
	int getCardValue() {
		return value;
	}

}
