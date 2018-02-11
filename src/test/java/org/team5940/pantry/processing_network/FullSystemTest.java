package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;
import org.junit.Test;
import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.logging.messages.Message;

public class FullSystemTest {
	
	public static String defaultLabel = "test label";

	public static Logger defaultLogger = new Logger() {

		@Override
		public void log(Message message) {

		}
	};

	Network network = new Network(200000, defaultLogger);
	TestControllerRightJoystick rightJoyStick = new TestControllerRightJoystick(network, false);
	TestControllerLeftJoystick leftJoyStick = new TestControllerLeftJoystick(network, false);
	TestDriveTrain driveTrain = new TestDriveTrain(network, false, leftJoyStick, rightJoyStick);
	TestDriveTrainMotorLeft leftMotor = new TestDriveTrainMotorLeft(network, true, driveTrain);
	TestDriveTrainMotorRight rightMotor = new TestDriveTrainMotorRight(network, true, driveTrain);

	@Test
	public void testRun_WithNodeAndSourceNode() throws InterruptedException {
		network.start();
		Thread.sleep(100);

		assertEquals(Integer.valueOf(2), rightMotor.getSpeed());
		assertEquals(-1, leftMotor.getSpeed());

		Thread.sleep(200);

		assertEquals(null, rightMotor.getSpeed());
		assertEquals(1, leftMotor.getSpeed());

		Thread.sleep(200);

		assertEquals(Integer.valueOf(3), rightMotor.getSpeed());
		assertEquals(1, leftMotor.getSpeed());

		network.interrupt();
	}
}

class TestDriveTrainMotorLeft extends Node {

	private int speed;
	private ValueNode<Integer[]> driveTrain;

	public TestDriveTrainMotorLeft(Network network, boolean requireUpdate, ValueNode<Integer[]> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel,  requireUpdate, driveTrain);
		this.driveTrain = driveTrain;
	}

	@Override
	protected void doUpdate() {
		Integer[] driveTrainArray = driveTrain.getValue();
		speed = driveTrainArray[0];
	}

	public int getSpeed() {
		return speed;
	}
}

class TestDriveTrainMotorRight extends Node {

	private Integer speed;
	private ValueNode<Integer[]> driveTrain;

	public TestDriveTrainMotorRight(Network network, boolean requireUpdate, ValueNode<Integer[]> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, requireUpdate, driveTrain);

		this.driveTrain = driveTrain;
	}

	@Override
	protected void doUpdate() {
		Integer[] driveTrainArray = driveTrain.getValue();
		speed = driveTrainArray[1];
	}

	public Integer getSpeed() {
		return speed;
	}
}

class TestDriveTrain extends ValueNode<Integer[]> {

	private ValueNode<Integer> rightInput;
	private ValueNode<Integer> leftInput;

	public TestDriveTrain(Network network, boolean requireUpdate, ValueNode<Integer> leftInput,
			ValueNode<Integer> rightInput) throws IllegalArgumentException, IllegalStateException {
		super(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, leftInput, rightInput);

		this.rightInput = rightInput;
		this.leftInput = leftInput;
	}

	@Override
	public Integer[] updateValue() {
		Integer[] values = { leftInput.getValue(), rightInput.getValue() };
		return values;
	}
}

class TestControllerRightJoystick extends ValueNode<Integer> {

	int call = 0;

	public TestControllerRightJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel);
	}

	@Override
	public Integer updateValue() {
		value = getRightJoyStickInput(call);
		call++;

		return value;
	}

	Integer getRightJoyStickInput(int call) {
		if (call == 0)
			return 2;
		else if (call == 1)
			return null;
		else
			return 3;
	}
}

class TestControllerLeftJoystick extends ValueNode<Integer> {

	boolean firstCall = true;

	public TestControllerLeftJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel);
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
