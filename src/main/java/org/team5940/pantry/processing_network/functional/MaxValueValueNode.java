package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * This stores the max value ever returned by the inputed ValueNode. This
 * ValueNode is good for tuning PID to see how far the specified value was
 * overshot. This ValueNode is mostly used for logging. 
 * 
 * @author mbent
 *
 */
public class MaxValueValueNode extends ValueNode<Double> {

	/**
	 * The ValueNode to track the max value of. 
	 */
	ValueNode<? extends Number> valueNode;
	
	/**
	 * The max value ever returned by the ValueNode. 
	 */
	Double maxValue;

	/**
	 * This ValueNode returns the max value the inputed ValueNode ever returns.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param valueNode
	 *            The ValueNode to store the maxValue of.
	 */
	public MaxValueValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> valueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, valueNode);

		this.valueNode = valueNode;
	}

	@Override
	protected Double updateValue() {
		double currentValue = this.valueNode.getValue().doubleValue();
		if (maxValue == null)
			this.maxValue = currentValue;
		else if (maxValue < currentValue)
			this.maxValue = currentValue;
		return this.maxValue;
	}

}
