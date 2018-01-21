package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * This detects when the value of a ValueNode is changed. When the value is
 * changed this runs a {@link GenericLambda} with the new value as the argument.
 * This uses .equals to see if the value has changed.
 * 
 * @author Michael Bentley
 *
 * @param <T>
 *            The type of value the ValueNode returns.
 */
public class ChangeDetectorNode<T> extends Node {

	/**
	 * The ValueNode to detect when the value switches. 
	 */
	ValueNode<T> source;
	
	/**
	 * The previous value of the source. 
	 */
	T previousValue;
	
	/**
	 * The GenericLambda to run with the new value when the value of source changes. 
	 */
	GenericLambda<T> newValueLambda;

	/**
	 * This detects when a value from a ValueNode changes. When it does it runs
	 * newValueLambda with the new value as the argument.
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This' Logger.
	 * @param requiresUpdate
	 *            Specifies if this node should be updated by the network.
	 * @param source
	 *            The ValueNode to check for changes.
	 * @param newValueLambda
	 *            The lambda to run if changes are found. The argument will be the
	 *            new value of the source.
	 */
	public ChangeDetectorNode(Network network, Logger logger, boolean requiresUpdate, ValueNode<T> source,
			GenericLambda<T> newValueLambda) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, requiresUpdate, source);
		LoggingUtils.checkArgument(newValueLambda);
		this.source = source;
		this.newValueLambda = newValueLambda;
	}

	@Override
	protected void doUpdate() {
		if (this.previousValue == null) {
			this.previousValue = this.source.getValue();
		} else if (!this.previousValue.equals(this.source.getValue())) {
			this.previousValue = this.source.getValue();
			this.newValueLambda.lambda(this.source.getValue());
		}
	}

}
