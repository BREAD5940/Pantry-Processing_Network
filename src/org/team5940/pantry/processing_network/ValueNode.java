package org.team5940.pantry.processing_network;

import org.team5940.pantry.logging.loggers.Logger;

/**
 * A Node that stores data of type T. Data is updated once every Network cycle
 * at most. Data returned is a cached value of the data.
 * 
 * @author Michael Bentley
 *
 * @param <T>
 */
public abstract class ValueNode<T extends Object> extends Node {	
	
	/**
	 * Current value of the source.
	 */
	T value;
	
	
	public ValueNode(Network network, Logger logger, ValueNode<?>... sourcesArray)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, false, sourcesArray);
	}

	/**
	 * Gets the new value for this SourceNode to store. Cannot be null.
	 * 
	 * @return The new value.
	 */
	protected abstract T updateValue();

	/**
	 * Runs updateValue() and caches what it returns even if it is null. Also logs it.
	 * {@inheritDoc}
	 */
	@Override
	protected void doUpdate() {
		this.value = this.updateValue();
		// TODO log
	}

	/**
	 * If the current thread is this' network and this has not updated this cycle, this updates and caches its value. It then returns the last cached value.
	 * 
	 * @return The current cached value, not null
	 */
	public T getValue() {
		if (this.getNetwork() == Thread.currentThread()) { 
			update();
		}
		return this.value;
	}
}
