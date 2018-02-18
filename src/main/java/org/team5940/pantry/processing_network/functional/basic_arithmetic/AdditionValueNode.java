package org.team5940.pantry.processing_network.functional.basic_arithmetic;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;

/**
 * Adds the two numbers.
 * 
 * 
 * @author Paul Dowd
 *
 */

public class AdditionValueNode extends ValueNode<Double> {

	/**
	 * The first value node being added
	 */

	ValueNode<? extends Number> number1ValueNode;

	/**
	 * The second value node being added
	 */

	ValueNode<? extends Number> number2ValueNode;

	/**
	 * 
	 * Adds the first value node to the second value node.
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label
	 * @param number1ValueNode
	 *            The first value node being added
	 * @param number2ValueNode
	 *            The second value node being added
	 */

	public AdditionValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> number1ValueNode,
			ValueNode<? extends Number> number2ValueNode) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, number1ValueNode, number2ValueNode);

		this.number1ValueNode = number1ValueNode;
		this.number2ValueNode = number2ValueNode;
	}

	/**
	 * 
	 * Adds the first value node to the second value node
	 * 
	 * The constructor makes the second value node a constant
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label
	 * @param number1ValueNode
	 *            The first value node being added
	 * @param number2
	 *            The second value node being added
	 */

	public AdditionValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> number1ValueNode,
			double number2) {
		this(network, logger, label, number1ValueNode,
				new ConstantValueNode<Double>(network, logger, label + ": Addition Constant", number2));
	}

	@Override
	protected Double updateValue() {
		return this.number1ValueNode.getValue().doubleValue() + this.number2ValueNode.getValue().doubleValue();
	}

}
