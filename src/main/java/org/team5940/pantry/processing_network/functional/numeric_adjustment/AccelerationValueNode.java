package org.team5940.pantry.processing_network.functional.numeric_adjustment;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;

/**
 * This ValueNode's value moves towards another ValueNode's value at a constant
 * or variable rate. The rate of movement is in units/second. The target value
 * is determined by the set ValueNode.
 * 
 * @author Michael Bentley
 *
 */
public class AccelerationValueNode extends ValueNode<Double> {

	/**
	 * The rate to approach the target value in units/second.
	 */
	ValueNode<? extends Number> rate;

	/**
	 * The target value.
	 */
	ValueNode<? extends Number> target;

	/**
	 * The current value this value node returns.
	 */
	double currentValue;

	/**
	 * The last time this node was updated.
	 */
	double previousTime;

	/**
	 * Creates a new {@link AccelerationValueNode} which moves toward a target value
	 * at a rate set by a ValueNode.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' Label
	 * @param rate
	 *            The rate at which to move towards the target value.
	 * @param target
	 *            The target value for this ValueNode to move slowly towards.
	 */
	public AccelerationValueNode(Network network, Logger logger, String label, ValueNode<? extends Number> rate,
			ValueNode<? extends Number> target) {
		super(network, logger, label, rate, target);
		this.rate = rate;
		this.target = target;
	}

	/**
	 * Creates a new {@link AccelerationValueNode} which moves toward a target value
	 * at a constant rate. This creates a new ConstantValueNode to store the rate.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param label
	 *            This' Label
	 * @param rate
	 *            The rate at which to move towards the target value.
	 * @param target
	 *            The target value for this ValueNode to move slowly towards.
	 */
	public AccelerationValueNode(Network network, Logger logger, String label, double rate,
			ValueNode<? extends Number> target) {
		this(network, logger, label, new ConstantValueNode<>(network, logger, label + ": Rate", rate), target);
	}

	@Override
	protected Double updateValue() {
		if (this.previousTime == 0) {
			this.previousTime = System.currentTimeMillis();
		}

		double targetValue = this.target.getValue().doubleValue();

		double timeInterval = System.currentTimeMillis() - this.previousTime;
		this.previousTime = System.currentTimeMillis();

		double change = (timeInterval / 1000) * this.rate.getValue().doubleValue();

		if (this.currentValue < targetValue) {
			if (this.currentValue + change > targetValue) {
				this.currentValue = targetValue;
			} else {
				this.currentValue += change;
			}
		} else if (this.currentValue > targetValue) {
			if (this.currentValue - change < targetValue) {
				this.currentValue = targetValue;
			} else {
				this.currentValue -= change;
			}
		}

		return this.currentValue;
	}

}
