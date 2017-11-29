package com.game.risk.core.strategy;

/**
 * @author sohrab_singh
 * @author shubhangi_sheel
 *
 */
public interface PlayerStrategy {

	
	/**
	 * Interface method to reinforce
	 */
	void reinforce();
	
	
	/**
	 * Interface method to attack
	 */
	void attack();
	
	
	/**
	 * Interface method to fortify
	 */
	void fortify();

}
