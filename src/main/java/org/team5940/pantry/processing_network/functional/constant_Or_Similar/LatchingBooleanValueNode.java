package org.team5940.pantry.processing_network.functional.constant_Or_Similar;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * Changes a ValueNode to a normal boolean for use when remembering 
 * that something has happened when another variable or object would
 * revert back to a different state
 * 
 * @author Paul Dowd
 *
 */

public class LatchingBooleanValueNode extends ValueNode<Boolean> {
	
	/**
	 * The constant boolean to convert to
	 */
	Boolean isTrue = false;
	
	/**
	 * The ValueNode converted from
	 */
	ValueNode<? extends Boolean> valueNode;
	
	/**
	 * makes the ValueNode constant if the ValueNode is true
	 * 
	 * @param network this' network
	 * @param logger this' logger
	 * @param label this' label
	 * @param valueNode the ValueNode which the state is taken from
	 */
	
	public LatchingBooleanValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Boolean> valueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, valueNode);

		this.valueNode = valueNode;
	}

	@Override
	protected Boolean updateValue() {
		if (valueNode.getValue() == true) {
			isTrue = true;
		}
		return isTrue;
	}
}
