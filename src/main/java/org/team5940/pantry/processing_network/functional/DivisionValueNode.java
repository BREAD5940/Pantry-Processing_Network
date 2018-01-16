package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * Divides the value of two nodes together and returns them. The values are
 * converted to doubles as a side affect.
 * 
 * @author Michael Bentley
 *
 */
public class DivisionValueNode extends ValueNode<Double> {

	/**
	 * The first ValueNode to be divided.
	 */
	ValueNode<? extends Number> dividendValueNode;

	/**
	 * The second ValueNode to divide the first ValueNode by.
	 */
	ValueNode<? extends Number> divisorValueNode;

	/**
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This' Network.
	 * @param dividendValueNode
	 *            The ValueNode to divide.
	 * @param divisorValueNode
	 *            The ValueNode to divide by.
	 */
	public DivisionValueNode(Network network, Logger logger, ValueNode<? extends Number> dividendValueNode,
			ValueNode<? extends Number> divisorValueNode) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, dividendValueNode, divisorValueNode);

		this.dividendValueNode = dividendValueNode;
		this.divisorValueNode = divisorValueNode;
	}

	/**
	 * The number is converted to a constant value node and that is used as the
	 * value to divide by.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param dividend
	 *            The ValueNode to divide.
	 * @param divisor
	 *            The constant number to divide the ValueNode by.
	 */
	public DivisionValueNode(Network network, Logger logger, ValueNode<? extends Number> dividendValueNode,
			double divisor) {
		this(network, logger, dividendValueNode, new ConstantValueNode<>(network, logger, divisor));
	}

	/**
	 * The number is converted to a constant value node and that is used as the
	 * value to divide.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param dividend
	 *            The constant number to divide.
	 * @param divisor
	 *            The ValueNode to divide the constant by.
	 */
	public DivisionValueNode(Network network, Logger logger, double dividend,
			ValueNode<? extends Number> divisorValueNode) {
		this(network, logger, new ConstantValueNode<>(network, logger, dividend), divisorValueNode);
	}

	@Override
	protected Double updateValue() {
		return this.dividendValueNode.getValue().doubleValue() / this.divisorValueNode.getValue().doubleValue();
	}

}
