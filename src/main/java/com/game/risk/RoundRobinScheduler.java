package com.game.risk;

import java.util.Iterator;
import java.util.List;

/**
 * Round Robin Scheduler for generic list of objects.
 * 
 * @author sohrab_singh
 * @author Sarthak
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

	/**
	 * Update the current position after saved game reload
	 * 
	 * @param item
	 *            T type
	 * @return current required position T type variable
	 */
	public T getUpdateItem(T item) {
		T value = null;
		while (iterator.hasNext()) {
			value = iterator.next();
			if (item.equals(value)) {
				break;
			}
		}
		return value;
	}

	/**
	 * Get the list of any type T
	 * 
	 * @return list
	 */
	public List<T> getList() {
		return list;
	}

}
