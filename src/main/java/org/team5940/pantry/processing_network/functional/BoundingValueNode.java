package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * This bounds the Value of a ValueNode to be in between two constants. If it is
 * outside one of the bounds then this ValueNode returns the closer bound.
 * 
 * @author Michael Bentley
 *
 */
public class BoundingValueNode extends ValueNode<Double> {

	/**
	 * The ValueNode whose value should be bounded.
	 */
	ValueNode<? extends Number> boundValueNode;

	/**
	 * The upper bound of the value this ValueNode returns.
	 */
	double upperBound;

	/**
	 * The lower bound of the value this ValueNode returns.
	 */
	double lowerBound;

	/**
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' Label.
	 * @param boundValueNode
	 *            The ValueNode to bound.
	 * @param lowerBound
	 *            The lower bound that this ValueNode can return.
	 * @param upperBound
	 *            The upper bound that this ValueNode can return.
	 * @throws IllegalArgumentException
	 *             If the lowerBound is greater than the upperBound.
	 */
	public BoundingValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> boundValueNode,
			double lowerBound, double upperBound) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, boundValueNode);

		if (lowerBound > upperBound) {
			this.getLogger().throwError(this, new IllegalArgumentException("Lower Bound is greater than upper bound"));
		}

		this.boundValueNode = boundValueNode;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	protected Double updateValue() {
		if (this.boundValueNode.getValue().doubleValue() < this.lowerBound) {
			return this.lowerBound;
		} else if (this.boundValueNode.getValue().doubleValue() > this.upperBound) {
			return this.upperBound;
		}
		return this.boundValueNode.getValue().doubleValue();
	}

}
