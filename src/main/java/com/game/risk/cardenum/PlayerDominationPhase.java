package com.game.risk.cardenum;

public enum PlayerDominationPhase {

	AFTER_STARTUP(0),

	AFTER_ATTACK(1);

	int state;

	PlayerDominationPhase(int state) {
		this.state = state;
	}
	
	public int getState() {
		return state;
	}

}
