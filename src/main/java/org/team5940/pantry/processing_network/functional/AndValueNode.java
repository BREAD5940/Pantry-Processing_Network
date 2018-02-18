package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * Checks to see whether two values are both true
 * 
 * @author Paul Dowd
 *
 */

public class AndValueNode extends ValueNode<Boolean> {

	/**
	 * Creates the first value being checked
	 */

	ValueNode<? extends Boolean> valueNode1;

	/**
	 * Creates the second value being checked
	 */

	ValueNode<? extends Boolean> valueNode2;

	/**
	 * creates the other value being checked and the boolean to return
	 */

	Boolean bothBooleansTrue = false;

	/**
	 * creates a new AndNode to check whether two values are both true
	 * 
	 * @param network
	 *            this' network
	 * @param logger
	 *            this' logger
	 * @param label
	 *            this' label
	 * @param valueNode1
	 *            the first ValueNode being checked
	 * @param valueNode2
	 *            the second ValueNode being checked
	 */

	public AndValueNode(Network network, Logger logger, String label, ValueNode<? extends Boolean> valueNode1,
			ValueNode<? extends Boolean> valueNode2) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, valueNode1, valueNode2);
		this.valueNode1 = valueNode1;
		this.valueNode2 = valueNode2;
	}

	@Override
	protected Boolean updateValue() {
		if (this.valueNode1.getValue() && valueNode2.getValue()) {
			bothBooleansTrue = true;
		}

		return bothBooleansTrue;
	}

}
