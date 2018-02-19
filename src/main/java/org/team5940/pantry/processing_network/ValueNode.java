package org.team5940.pantry.processing_network;

import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.logging.messages.values.BooleanValueMessage;
import org.team5940.pantry.logging.messages.values.NumberValueMessage;
import org.team5940.pantry.logging.messages.values.ValueMessage;

import com.google.gson.JsonArray;

/**
 * A Node that stores data of type T. Data is updated once every Network cycle
 * at most. Data returned is a cached value of the data.
 * 
 * @author Michael Bentley
 *
 * @param <T>
 *            The type of data this ValueNode stores.
 */
public abstract class ValueNode<T extends Object> extends Node {

	/**
	 * Current value of the source.
	 */
	T value;

	/**
	 * This uses a String as its label for the LabeledObject. This will create a
	 * JsonArray from the String to use.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            The label that describes this Node.
	 * @param sourcesArray
	 *            The ValueNodes this Node relies on.
	 */
	public ValueNode(Network network, Logger logger, String label, ValueNode<?>... sourcesArray)
			throws IllegalArgumentException, IllegalStateException {
		this(network, logger, label, false, sourcesArray);
	}


	/**
	 * Uses a JsonArray as its label. The labels for Value Node and Node will
	 * automatically be added.
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This Logger.
	 * @param label
	 *            The label that describes the Node.
	 * @param sourcesArray
	 *            The ValueNodes this relies on.
	 */
	public ValueNode(Network network, Logger logger, JsonArray label, ValueNode<?>... sourcesArray)
			throws IllegalArgumentException, IllegalStateException {
		this(network, logger, LoggingUtils.chainPut(label, "Value Node"), false, sourcesArray);
	}

	/**
	 * Uses a JsonArray as its label. The labels for Value Node and Node will
	 * automatically be added.
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This Logger.
	 * @param label
	 *            The label that describes the Node.
	 * @param requiresUpdate
	 *            If this node should be updated every cycle no matter what.
	 * @param sourcesArray
	 *            The ValueNodes this relies on.
	 */
	public ValueNode(Network network, Logger logger, JsonArray label, boolean requiresUpdate,
			ValueNode<?>... sourcesArray) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, LoggingUtils.chainPut(label, "Value Node"), requiresUpdate, sourcesArray);
	}

	/**
	 * Uses a JsonArray as its label. The labels for Value Node and Node will
	 * automatically be added.
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This Logger.
	 * @param label
	 *            The label that describes the Node.
	 * @param requiresUpdate
	 *            If this node should be updated every cycle no matter what.
	 * @param sourcesArray
	 *            The ValueNodes this relies on.
	 */
	public ValueNode(Network network, Logger logger, String label, boolean requiresUpdate, ValueNode<?>... sourcesArray)
			throws IllegalArgumentException, IllegalStateException {
		this(network, logger, LoggingUtils.chainPut(new JsonArray(), label), sourcesArray);
	}

	/**
	 * Gets the new value for this SourceNode to store. Cannot be null.
	 * 
	 * @return The new value.
	 */
	protected abstract T updateValue();

	/**
	 * Runs updateValue() and caches what it returns even if it is null. Also logs
	 * it. {@inheritDoc}
	 */
	@Override
	protected void doUpdate() {
		T previousValue = this.value;
		this.value = this.updateValue();
		// This is super cool. The second argument will not be checked if previousValue
		// is null. This means that when it is null it will not run the .equals part and
		// cause the code to crash.
		if (previousValue != null && !previousValue.equals(this.value)) {
			if (this.value instanceof Boolean) {
				this.logger.log(new BooleanValueMessage(this, (Boolean) this.value));
			} else if (this.value instanceof Number) {
				this.logger.log(new NumberValueMessage(this, ((Number) this.value).doubleValue()));
			} else {
				this.logger.log(new ValueMessage(this, this.value));
			}
		}
	}

	/**
	 * If the current thread is this' network and this has not updated this cycle,
	 * this updates and caches its value. It then returns the last cached value.
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
