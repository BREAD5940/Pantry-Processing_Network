package org.team5940.pantry.processing_network;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Test;
import org.team5940.pantry.logging.loggers.PrintStreamLogger;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;
import org.team5940.pantry.processing_network.functional.IntegralValueNode;
import org.team5940.pantry.processing_network.functional.PDNode;
import org.team5940.pantry.processing_network.functional.basic_arithmetic.SubtractionValueNode;
import org.team5940.pantry.processing_network.functional.MotorAccelerationValueNode;

public class ElevatorControlLoopTest {

	static final double motorCount = 2;
	static final double stallTorque = 0.71;
	static final double stallCurrent = 134;
	static final double gearRatio = 30;
	static final double freeSpeed = 18730;
	static final double freeCurrent = 0.7;
	static final double pulleyRadius = 0.0323342;
	static final double mass = 20;

	@Test
	public void testControlLoop() {
		PrintStreamLogger logger;
		try {
			logger = new PrintStreamLogger(new PrintStream(new File("/Users/mbent/Desktop/log.json")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger = new PrintStreamLogger(System.out);
		}
		Network network = new Network(20000, logger);

		ConstantValueNode<Double> setHeight = new ConstantValueNode<Double>(network, logger, "Set Height", 1d);

		CircleValueNode<Double> voltageCircleValueNode = new CircleValueNode<>(network, logger, "Circle Volts", 0d);

		CircleValueNode<Double> velocityCircleValueNode = new CircleValueNode<>(network, logger, "Circle Speed", 0d);

		MotorAccelerationValueNode accelerationValueNode = new MotorAccelerationValueNode(network, logger,
				"Elevator Without Gravity Acceleration", voltageCircleValueNode, velocityCircleValueNode, motorCount,
				stallTorque, stallCurrent, gearRatio, freeSpeed, freeCurrent, pulleyRadius, mass);

		SubtractionValueNode gravityAdjustedAccelerationValueNode = new SubtractionValueNode(network, logger,
				"Acceleration", accelerationValueNode, 0);

		IntegralValueNode velocityValueNode = new IntegralValueNode(network, logger, "Velocity",
				gravityAdjustedAccelerationValueNode, 0);

		IntegralValueNode positionValueNode = new IntegralValueNode(network, logger, "Position", velocityValueNode, 0);

		velocityCircleValueNode.setGetValue(velocityValueNode);

		PDNode controlLoopNode = new PDNode(network, logger, "Control Loop", positionValueNode, velocityValueNode,
				setHeight, 1000, 100);

		voltageCircleValueNode.setGetValue(controlLoopNode);

		new ElevatorPositionTestNode<>(network, logger, "Elevator Test", true, positionValueNode, controlLoopNode, 2);

		network.start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue(positionValueNode.getValue() > 0.98);
		assertTrue(positionValueNode.getValue() < 1.02);
	}

}
