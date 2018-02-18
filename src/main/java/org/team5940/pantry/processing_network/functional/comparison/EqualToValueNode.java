package org.team5940.pantry.processing_network.functional.comparison;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;

/**
 * Checks whether two values are equal. Uses .equals to do this.
 * 
 * 
 * @author Paul Dowd
 *
 */

public class EqualToValueNode extends ValueNode<Boolean> {

	/**
	 * The first value node being compared
	 */

	ValueNode<?> valueNode1;

	/**
	 * The second value node being compared
	 */

	ValueNode<?> valueNode2;

	/**
	 * 
	 * Sees whether first value node is equal to the second value node.
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label.
	 * @param valueNode1
	 *            The first value node being compared
	 * @param valueNode2
	 *            The second value node being compared
	 */

	public EqualToValueNode(Network network, Logger logger, String label, ValueNode<?> valueNode1,
			ValueNode<?> valueNode2) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, valueNode1, valueNode2);

		this.valueNode1 = valueNode1;
		this.valueNode2 = valueNode2;
	}

	/**
	 * 
	 * Sees whether first value node is equal to the second value node.
	 * 
	 * The constructor makes the second value node a constant
	 * 
	 * @param network
	 *            This' network
	 * @param logger
	 *            This' logger
	 * @param label
	 *            This' label.
	 * @param valueNode1
	 *            The first value node being compared
	 * @param value2
	 *            The second value being compared
	 */

	public EqualToValueNode(Network network, Logger logger, String label, ValueNode<?> valueNode1, Object value2) {
		this(network, logger, label, valueNode1,
				new ConstantValueNode<>(network, logger, label + ": Equal To Constant", value2));
	}

	@Override
	protected Boolean updateValue() {
		return this.valueNode1.getValue().equals(this.valueNode2.getValue());
	}

}
