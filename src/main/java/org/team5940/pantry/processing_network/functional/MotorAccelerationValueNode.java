package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

public class MotorAccelerationValueNode extends ValueNode<Double> {

	double Kt;
	double kG;
	double Kv;
	double resistance;
	double kr;
	double mass;
	ValueNode<? extends Number> setVoltsValueNode;
	ValueNode<? extends Number> currentVelocityValueNode;

	public MotorAccelerationValueNode(Network network, Logger logger, String label,
			ValueNode<? extends Number> setVoltsValueNode, ValueNode<? extends Number> currentVelocityValueNode,
			double motorCount, double stallTorque, double stallCurrent, double gearRatio, double freeSpeed,
			double freeCurrent, double pulleyRadius, double mass)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, setVoltsValueNode);
		this.Kt = (motorCount * stallTorque) / stallCurrent;
		this.kG = gearRatio;
		this.Kv = ((freeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - resistance * freeCurrent));
		this.resistance = stallCurrent / 12;
		this.mass = mass;
		this.setVoltsValueNode = setVoltsValueNode;
		this.currentVelocityValueNode = currentVelocityValueNode;
		this.kr = pulleyRadius;

	}

	@Override
	protected Double updateValue() {
		double setVolts = this.setVoltsValueNode.getValue().doubleValue();
		double currentVelocity = this.currentVelocityValueNode.getValue().doubleValue();

		System.out.println("Set Volts: " + setVolts);
		System.out.println("Current Velocity: " + currentVelocity);

		double value = (-Kt * kG * kG / (Kv * resistance * kr * kr * mass)) * currentVelocity
				+ (kG * Kt / (resistance * kr * mass)) * setVolts;

		System.out.println("Current Acceleration: " + value);

		return value;
	}

}
