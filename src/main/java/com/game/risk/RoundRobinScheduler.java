package com.game.risk;

import java.util.Iterator;
import java.util.List;

/**
 * Round Robin Scheduler for generic list of objects.
 * 
 * @author sohrab_singh
 * @param <T>
 *
 */
public class RoundRobinScheduler<T> {

	/** Iterator for list. */
	private Iterator<T> iterator;

	/** Generic list */
	private List<T> list;

	/**
	 * Round robin scheduler.
	 * 
	 * @param list
	 *            list of objects
	 */
	public RoundRobinScheduler(List<T> list) {
		this.list = list;
		iterator = list.iterator();
	}

	/**
	 * Return next object.
	 * 
	 * @return next object.
	 */
	public T next() {
		// if we get to the end, start again
		if (!iterator.hasNext()) {
			iterator = list.iterator();
		}
		return iterator.next();
	}
}
