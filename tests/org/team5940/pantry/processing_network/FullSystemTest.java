package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;
import org.junit.Test;

public class FullSystemTest {

	Network network = new Network(150000);
	TestControllerRightJoystick rightJoyStick = new TestControllerRightJoystick(network, false);
	TestControllerLeftJoystick leftJoyStick = new TestControllerLeftJoystick(network, false);
	TestDriveTrain driveTrain = new TestDriveTrain(network, false, leftJoyStick, rightJoyStick);
	TestDriveTrainMotorLeft leftMotor = new TestDriveTrainMotorLeft(network, true, driveTrain);
	TestDriveTrainMotorRight rightMotor = new TestDriveTrainMotorRight(network, true, driveTrain);

	@Test
	public void testRun_WithNodeAndSourceNode() throws InterruptedException {
		network.start();
		Thread.sleep(100);

		assertEquals(2, rightMotor.speed);
		assertEquals(-1, leftMotor.speed);

		Thread.sleep(100);

		assertEquals(3, rightMotor.speed);
		assertEquals(1, leftMotor.speed);

		network.interrupt();
	}
}

class TestDriveTrainMotorLeft extends Node {

	int speed;
	private SourceNode<Integer[]> driveTrain;

	public TestDriveTrainMotorLeft(Network network, boolean requireUpdate, SourceNode<Integer[]> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, driveTrain);
		this.driveTrain = driveTrain;
	}

	@Override
	protected void doUpdate() {
		Integer[] driveTrainArray = driveTrain.getValue();
		speed = driveTrainArray[0];
	}
}

class TestDriveTrainMotorRight extends Node {

	int speed;
	private SourceNode<Integer[]> driveTrain;

	public TestDriveTrainMotorRight(Network network, boolean requireUpdate, SourceNode<Integer[]> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, driveTrain);

		this.driveTrain = driveTrain;
	}

	@Override
	protected void doUpdate() {
		Integer[] driveTrainArray = driveTrain.getValue();
		speed = driveTrainArray[1];
	}
}

class TestDriveTrain extends SourceNode<Integer[]> {

	private SourceNode<Integer> rightInput;
	private SourceNode<Integer> leftInput;

	public TestDriveTrain(Network network, boolean requireUpdate, SourceNode<Integer> leftInput,
			SourceNode<Integer> rightInput) throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, leftInput, rightInput);

		this.rightInput = rightInput;
		this.leftInput = leftInput;
	}

	@Override
	public Integer[] updateValue() {
		Integer[] values = { leftInput.getValue(), rightInput.getValue() };
		return values;
	}
}

class TestControllerRightJoystick extends SourceNode<Integer> {

	boolean firstCall = true;

	public TestControllerRightJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate);
	}

	@Override
	public Integer updateValue() {
		value = getRightJoyStickInput(firstCall);
		firstCall = false;

		return value;
	}

	Integer getRightJoyStickInput(boolean first) {
		if (first)
			return 2;
		else
			return 3;
	}
}

class TestControllerLeftJoystick extends SourceNode<Integer> {

	boolean firstCall = true;

	public TestControllerLeftJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate);
	}

	@Override
	public Integer updateValue() {
		value = getLeftJoyStickInput(firstCall);
		firstCall = false;

		return value;
	}

	int getLeftJoyStickInput(boolean first) {
		if (first)
			return -1;
		else
			return 1;
	}
}
