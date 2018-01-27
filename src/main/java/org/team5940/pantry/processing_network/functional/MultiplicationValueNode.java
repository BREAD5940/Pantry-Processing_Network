package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * Multiplies two number together and returns that value. Also has to convert
 * number type to Double as required side affect.
 * 
 * @author Michael Bentley
 *
 */
public class MultiplicationValueNode extends ValueNode<Double> {

	/**
	 * The first ValueNode to use as a multiple.
	 */
	ValueNode<? extends Number> multiple1ValueNode;

	/**
	 * The second ValueNode to use as a multiple.
	 */
	ValueNode<? extends Number> multiple2ValueNode;

	/**
	 * Multiplies the value of two nodes together and returns them. The values are
	 * converted to doubles as a side affect.
	 * 
	 * @param network
	 *            This' Network.
	 * @param logger
	 *            This' Network.
	 * @param label
	 *            This' label.
	 * @param multiple1ValueNode
	 *            The ValueNode to multiply by.
	 * @param multiple2ValueNode
	 *            The other ValueNode to multiply by.
	 */
	public MultiplicationValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> multiple1ValueNode, ValueNode<? extends Number> multiple2ValueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, multiple1ValueNode, multiple2ValueNode);

		this.multiple1ValueNode = multiple1ValueNode;
		this.multiple2ValueNode = multiple2ValueNode;
	}

	/**
	 * Multiplies the value of two nodes together and returns them. The values are
	 * converted to doubles as a side affect.
	 * 
	 * The second multiple is converted to a constant value node and that is used as
	 * the value to multiply. The label for the value node is "Constant Multiple: "
	 * + label.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param multiple1ValueNode
	 *            The first ValueNode to multiply.
	 * @param multiple2
	 *            The constant multiple to multiply the ValueNode by.
	 */
	public MultiplicationValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> multiple1ValueNode, double multiple2) {
		this(network, logger, label, multiple1ValueNode,
				new ConstantValueNode<>(network, logger, "Constant Multiple: " + label, multiple2));
	}

	@Override
	protected Double updateValue() {
		return this.multiple1ValueNode.getValue().doubleValue() * this.multiple2ValueNode.getValue().doubleValue();
	}

}
