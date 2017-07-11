package org.team5940.pantry.processing_network;

import java.util.Set;

/**
 * A Node that stores data of type T. Data is updated once every Network cycle
 * at most. Data returned is a cached value of the data.
 * 
 * @author Michael Bentley
 *
 * @param <T>
 */
public abstract class SourceNode<T extends Object> extends Node {

	/**
	 * Current value of the source.
	 */
	T value;
	
	
	public SourceNode(Network network, Set<SourceNode<?>> sources, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, sources, requireUpdate);
		// TODO Auto-generated constructor stub
		System.out.println("Sources: " + sources);
	}

	/**
	 * Gets the new value for this SourceNode to store.
	 * 
	 * @return The new value.
	 */
	protected abstract T updateValue();

	/**
	 * Runs updateValue() and caches what it returns. Also logs it.
	 * {@inheritDoc}
	 */
	@Override
	protected void doUpdate() {
		// TODO log the new value. 
		this.value = this.updateValue();
	}

	/**
	 * Gets the current value cached by the ValueNode.
	 * 
	 * @return The current cached value.
	 * @throws IllegalUpdateThreadException 
	 */
	public T getValue() throws IllegalUpdateThreadException {
		if (this.getNetwork() == Thread.currentThread()) { 
			update();
		}
		return this.value;
	}
}
