package com.game.risk.cardenum;

import java.awt.Color;

/**
 * @author sohrab_singh
 *
 */
public enum PlayerEnum {

	PLAYER_1(Color.RED),

	PLAYER_2(Color.BLUE),

	PLAYER_3(Color.YELLOW),

	PLAYER_4(Color.GREEN),

	PLAYER_5(Color.CYAN),

	PLAYER_6(Color.PINK);

	private Color color;

	PlayerEnum(Color color) {
		this.color = color;
	}

	Color getColor() {
		return color;
	}
	
}
