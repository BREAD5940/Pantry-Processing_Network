package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

public class IntegralValueNode extends ValueNode<Double> {

	ValueNode<? extends Number> integrandValueNode;

	double integral = 0;

	public IntegralValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> integrandValueNode) throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, integrandValueNode);

		this.integrandValueNode = integrandValueNode;
	}

	@Override
	protected Double updateValue() {
		this.integral += integrandValueNode.getValue().doubleValue() * this.getNetwork().getCycleDelay();
		return this.integral;
	}

}
