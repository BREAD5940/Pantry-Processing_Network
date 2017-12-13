package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * Inverts the number by multiplying it by negative one. Also has to convert
 * number type to Double as required side affect.
 * 
 * @author Michael
 *
 * @param <T>
 *            The type of Number.
 */
public class InvertValueNode<T extends Number> extends ValueNode<Double> {

	/**
	 * The node to invert the value of.
	 */
	ValueNode<T> valueNode;

	/**
	 * Inverts the number by multiplying it by negative one. Also has to convert
	 * number type to Double as required side affect.
	 * 
	 * @param network
	 *            The Network.
	 * @param logger
	 *            The Logger.
	 * @param valueNode
	 *            The ValueNode to invert.
	 * @throws IllegalArgumentException
	 *             If valueNode is null.
	 * @throws IllegalStateException
	 *             If network has been started.
	 */
	public InvertValueNode(Network network, Logger logger, ValueNode<T> valueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, valueNode);

		if (valueNode == null) {
			logger.throwError(this, new IllegalArgumentException("Value Node is Null"));
		}

		this.valueNode = valueNode;
	}

	@Override
	protected Double updateValue() {
		return this.valueNode.getValue().doubleValue() * -1;
	}

}
