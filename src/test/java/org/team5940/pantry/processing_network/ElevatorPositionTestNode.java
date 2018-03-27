package org.team5940.pantry.processing_network;

import static org.junit.Assert.assertTrue;

import org.team5940.pantry.logging.loggers.Logger;

public class ElevatorPositionTestNode<T> extends Node {

	ValueNode<? extends Number> elevatorPositionValueNode;
	ValueNode<? extends Number> setElevatorVoltsValueNode;
	double maxElevatorHeight;
	double setVolts;

	public ElevatorPositionTestNode(Network network, Logger logger, String label, boolean requireUpdate,
			ValueNode<? extends Number> elevatorPositionValueNode,
			ValueNode<? extends Number> setElevatorVoltsValueNode, double maxElevatorHeight) {
		super(network, logger, label, requireUpdate, elevatorPositionValueNode, setElevatorVoltsValueNode);

		this.elevatorPositionValueNode = elevatorPositionValueNode;
		this.setElevatorVoltsValueNode = setElevatorVoltsValueNode;
		this.maxElevatorHeight = maxElevatorHeight;
	}

	@Override
	protected void doUpdate() {
		double elevatorPosition = this.elevatorPositionValueNode.getValue().doubleValue();
		assertTrue(elevatorPosition > -0.3);
		assertTrue(elevatorPosition < maxElevatorHeight);
		this.setVolts = this.setElevatorVoltsValueNode.getValue().doubleValue();
		assertTrue(this.setVolts >= -12);
		assertTrue(this.setVolts <= 12);
	}

	public double getSetVolts() {
		return this.setVolts;
	}

}
