package org.team5940.pantry.processing_network;

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

	/**
	 * Gets the new value for this SourceNode to store.
	 * 
	 * @return The new value.
	 */
	abstract T updateValue();

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
	 */
	public T getValue() {
		// TODO change this so the Node updates its value. Is challenging
		// owns this.
		return this.value;
	}
}
