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
	 * @param label
	 *            This' label.
	 * @param dividendValueNode
	 *            The ValueNode to divide.
	 * @param divisorValueNode
	 *            The ValueNode to divide by.
	 */
	public DivisionValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> dividendValueNode, ValueNode<? extends Number> divisorValueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, dividendValueNode, divisorValueNode);

		this.dividendValueNode = dividendValueNode;
		this.divisorValueNode = divisorValueNode;
	}

	/**
	 * The number is converted to a constant value node and that is used as the
	 * value to divide by. The label for the constant value node will be "Constant
	 * Divisor: " + label.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param dividend
	 *            The ValueNode to divide.
	 * @param divisor
	 *            The constant number to divide the ValueNode by.
	 */
	public DivisionValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> dividendValueNode, double divisor) {
		this(network, logger, label, dividendValueNode,
				new ConstantValueNode<>(network, logger, "Constant Divisor: " + label, divisor));
	}

	/**
	 * The number is converted to a constant value node and that is used as the
	 * value to divide. The label for the constant value node will be "Constant
	 * Dividend: " + label.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param dividend
	 *            The constant number to divide.
	 * @param divisor
	 *            The ValueNode to divide the constant by.
	 */
	public DivisionValueNode(Network network, Logger logger, String label, double dividend,
			ValueNode<? extends Number> divisorValueNode) {
		this(network, logger, label, new ConstantValueNode<>(network, logger, "Constant Dividend: " + label, dividend),
				divisorValueNode);
	}

	@Override
	protected Double updateValue() {
		return this.dividendValueNode.getValue().doubleValue() / this.divisorValueNode.getValue().doubleValue();
	}

}
