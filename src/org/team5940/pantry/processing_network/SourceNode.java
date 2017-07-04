package org.team5940.pantry.processing_network;


public abstract class SourceNode<T extends Object> extends Node {
	
	/**
	 * Current value of the source. 
	 */
	protected T value;
	
	/**
	 * Gets the new value for this SourceNode to store. 
	 * @return The new value. 
	 */
	protected abstract T updateValue();
	
	/**
	 * Runs updateValue() and caches what it returns. Also logs it. 
	 * {@inheritDoc}
	 */
	@Override
	protected void doUpdate() {
		this.value = this.updateValue();
	}
	
	/**
	 * Gets the current value cached by the ValueNode. 
	 * @return The current cached value. 
	 */
	public T getValue() {
		return this.value;
	}
}
