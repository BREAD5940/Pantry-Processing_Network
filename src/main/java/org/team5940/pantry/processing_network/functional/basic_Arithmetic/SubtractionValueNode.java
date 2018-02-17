package org.team5940.pantry.processing_network.functional.basic_Arithmetic;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.constant_Or_Similar.ConstantValueNode;

/**
 * Subtracts the second number from the first.
 * 
 * 
 * @author Paul Dowd
 *
 */

public class SubtractionValueNode extends ValueNode<Double> {

	/**
	 * The starting value node
	 */

	ValueNode<? extends Number> minuendValueNode;

	/**
	 * The value node being subtracted
	 */

	ValueNode<? extends Number> subtrahendValueNode;

	/**
	 * 
	 * Subtracts the subtrahend node from the minuend node.
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label
	 * @param minuendValueNode
	 *            The value node being subtracted from
	 * @param subtrahendValueNode
	 *            The value node being subtracted
	 */

	public SubtractionValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> minuendValueNode, ValueNode<? extends Number> subtrahendValueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, minuendValueNode, subtrahendValueNode);

		this.minuendValueNode = minuendValueNode;
		this.subtrahendValueNode = subtrahendValueNode;
	}

	/**
	 * 
	 * subtracts the subtrahend node from the minuend node
	 * 
	 * The first constructor makes the subtrahend a constant
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label
	 * @param minuendValueNode
	 *            The value node being subtracted from
	 * @param subtrahend
	 *            The value node being subtracted
	 */

	public SubtractionValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> minuendValueNode, double subtrahend) {
		this(network, logger, label, minuendValueNode,
				new ConstantValueNode<>(network, logger, label + ": Subtrahend Constant", subtrahend));
	}

	/**
	 * 
	 * subtracts the subtrahend node from the minuend node
	 * 
	 * The constructor makes the minuend a constant
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label
	 * @param minuendValueNode
	 *            The value node being subtracted from
	 * @param subtrahend
	 *            The value node being subtracted
	 */

	public SubtractionValueNode(Network network, Logger logger, String label, double minuend,
			ValueNode<? extends Number> subtrahendValueNode) {
		this(network, logger, label, subtrahendValueNode,
				new ConstantValueNode<>(network, logger, label + ": Minuend Constant", minuend));
	}

	@Override
	protected Double updateValue() {
		return this.minuendValueNode.getValue().doubleValue() - this.subtrahendValueNode.getValue().doubleValue();
	}
}
