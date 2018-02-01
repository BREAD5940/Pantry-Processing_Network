package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * This detects when the value of a ValueNode is changed. When the value is
 * changed this runs valueChanged with the new value as the argument. This uses
 * .equals to see if the value has changed.
 * 
 * IMPORTANT. This should not be used to edit variables but only other nodes.
 * IMPORTANT
 * 
 * @author Michael Bentley
 *
 * @param <T>
 *            The type of value the ValueNode returns.
 */

public abstract class ChangeDetectorNode<T> extends Node {

	/**
	 * The ValueNode to check when the value has changed.
	 */
	ValueNode<T> source;

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
	 *            @param label
	 *            This' label.
	 * @param requireUpdate
	 *            If this should be updated.
	 * @param source
	 *            The ValueNode to check if the value has changed.
	 */
	public ChangeDetectorNode(Network network, Logger logger, String label, boolean requireUpdate, ValueNode<T> source)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, requireUpdate, source);
		this.source = source;
	}

	@Override
	protected void doUpdate() {
		if (this.previousValue == null) {
			this.previousValue = this.source.getValue();
		} else if (!this.previousValue.equals(this.source.getValue())) {
			this.previousValue = this.source.getValue();
			this.valueChanged(this.source.getValue());
		}
	}

	/**
	 * Run when the value of the ValueNode has changed.
	 * 
	 * @param newValue
	 *            The new value of the ValueNode.
	 */
	protected abstract void valueChanged(T newValue);
}
