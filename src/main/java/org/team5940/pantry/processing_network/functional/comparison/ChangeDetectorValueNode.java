package org.team5940.pantry.processing_network.functional.comparison;

import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ProcessingNetworkUtils;
import org.team5940.pantry.processing_network.ValueNode;

import com.google.gson.JsonArray;

/**
 * This detects when the value of a ValueNode is changed. When the value is
 * changed this runs valueChanged with the new value as the argument. This uses
 * .equals to see if the value has changed. It will run when on the initial
 * value of the Node is returned.
 * 
 * IMPORTANT. This should not be used to edit variables but only other nodes.
 * IMPORTANT
 * 
 * @author Michael Bentley
 *
 * @param <T>
 *            The type of value the change ValueNode returns.
 * @param <V>
 *            The type of value this ValueNode returns.
 */

public abstract class ChangeDetectorValueNode<T, V> extends ValueNode<V> {

	/**
	 * The ValueNode to check when the value has changed.
	 */
	ValueNode<? extends T> source;

	/**
	 * The previous value of the ValueNode to compare the new value against.
	 */
	T previousValue;

	/**
	 * This detects when a ValueNode value changes. It runs valueChanged when it has
	 * changed with the new value.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param requireUpdate
	 *            If this should be updated.
	 * @param source
	 *            The ValueNode to check if the value has changed.
	 * @param otherSource
	 *            The other sources for this ValueNode.
	 */
	public ChangeDetectorValueNode(Network network, Logger logger, String label, boolean requiresUpdate,
			ValueNode<? extends T> source, ValueNode<?>... otherSources)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, LoggingUtils.chainPut(new JsonArray(), label), requiresUpdate,
				ProcessingNetworkUtils.concatValueNodes(source, otherSources));
		this.source = source;
	}

	@Override
	protected void doUpdate() {
		if (!this.source.getValue().equals(this.previousValue)) {
			this.previousValue = this.source.getValue();

			this.valueChanged(this.source.getValue());
		}
		super.doUpdate();
	}

	/**
	 * Run when the value of the ValueNode has changed. This is run before
	 * doUpdate() is run so what this edits will be activated for the latest cycle.
	 * 
	 * @param newValue
	 *            The new value of the ValueNode.
	 */
	protected abstract void valueChanged(T newValue);
}
