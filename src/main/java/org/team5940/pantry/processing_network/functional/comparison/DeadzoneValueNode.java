package org.team5940.pantry.processing_network.functional.comparison;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.constant_or_similar.ConstantValueNode;

/**
 * This is used as a deadzone for a ValueNode. If the value of the specified
 * ValueNode is less than, not equal to, the deadzone then this node stores
 * zero, otherwise this node stores the value of the ValueNode.
 * 
 * @author Michael Bentley
 *
 */
public class DeadzoneValueNode extends ValueNode<Double> {

	/**
	 * The ValueNode to apply the deadzone to.
	 */
	ValueNode<? extends Number> numberValueNode;

	/**
	 * The size of the deadzone.
	 */
	ValueNode<? extends Number> deadzone;

	/**
	 * Creates a deadzone for a ValueNode. The deadzone is the size of whatever the
	 * deadzone ValueNode specifies. Could vary as much as one likes.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param numberValueNode
	 *            The value to adjust with a deadzone.
	 * @param deadzone
	 *            The size of the deadzone.
	 */
	public DeadzoneValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> numberValueNode,
			ValueNode<? extends Number> deadzone) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, numberValueNode, deadzone);
		this.numberValueNode = numberValueNode;
		this.deadzone = deadzone;
	}

	/**
	 * Creates a deadzone for a ValueNode of a constant size. This will create a
	 * ConstantValueNode with the label "Deadzone Constant Value: " + label.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' label.
	 * @param numberValueNode
	 *            The value to adjust with a deadzone
	 * @param deadzone
	 *            The size of the deadzone.
	 */
	public DeadzoneValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> numberValueNode,
			double deadzone) throws IllegalArgumentException, IllegalStateException {
		this(network, logger, label, numberValueNode,
				new ConstantValueNode<Double>(network, logger, "Deadzone Constant Value: " + label, deadzone));
	}

	@Override
	protected Double updateValue() {
		if (Math.abs(numberValueNode.getValue().doubleValue()) < this.deadzone.getValue().doubleValue()) {
			return 0d;
		}
		return numberValueNode.getValue().doubleValue();
	}

}
