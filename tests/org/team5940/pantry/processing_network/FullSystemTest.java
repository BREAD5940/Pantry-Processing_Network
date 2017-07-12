package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class FullSystemTest {

	@BeforeClass
	public static void SetupClass() {
		
	}
	
	@Test
	public void testRun_WithNodeAndSourceNode() throws InterruptedException {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode1 = new SourceNodeTesterObject(network, true, null);
		new NodeTesterObject(network,true, sourceNode1);
		
		network.start();
		Thread.sleep(100);
		network.interrupt();
	}

}


class TestDriveTrainMotorLeft extends Node {

	int speed;
	private SourceNode<?> driveTrain;
	
	public TestDriveTrainMotorLeft(Network network, boolean requireUpdate, SourceNode<?> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, driveTrain);
		// TODO Auto-generated constructor stub
		
		this.driveTrain = driveTrain;
	}

	@Override
	protected void doUpdate() {
		
		try {
			Integer[] driveTrainArray = (Integer[]) driveTrain.getValue();
			speed = (int) driveTrainArray[0];
		} catch (IllegalUpdateThreadException e) {
			// TODO Auto-generated catch block
		}
		
		
		

	}

}

class TestDriveTrainMotorRight extends Node {

	int speed;
	private SourceNode<?> driveTrain;
	
	public TestDriveTrainMotorRight(Network network, boolean requireUpdate, SourceNode<?> driveTrain)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, driveTrain);
		// TODO Auto-generated constructor stub
		
		this.driveTrain = driveTrain;

	}

	@Override
	protected void doUpdate() {
		try {
			Integer[] driveTrainArray = (Integer[]) driveTrain.getValue();
			speed = (int) driveTrainArray[1];
		} catch (IllegalUpdateThreadException e) {
			// TODO Auto-generated catch block
		}
	}

}

class TestDriveTrain extends SourceNode<Integer[]> {

	private SourceNode<Integer> rightInput;
	private SourceNode<Integer> leftInput;

	public TestDriveTrain(Network network, boolean requireUpdate, SourceNode<Integer> leftInput, SourceNode<Integer> rightInput)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, leftInput, rightInput);
		// TODO Auto-generated constructor stub
		this.rightInput = rightInput;
		this.leftInput = leftInput;
	}
	
	@Override
	  public Integer[] updateValue() {
		try {
			Integer[] values = { leftInput.getValue(), rightInput.getValue()};
			return values;
		}
		catch (Exception e) {
			
		}
		return null;
	  } 

}

class TestControllerRightJoystick extends SourceNode<Integer>{

	boolean firstCall = true;
	
	public TestControllerRightJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate);
		// TODO Auto-generated constructor stub
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
			return 0;
	}
}

class TestControllerLeftJoystick extends SourceNode<Integer>{

	boolean firstCall = true;
	
	public TestControllerLeftJoystick(Network network, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate);
		// TODO Auto-generated constructor stub
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
			return 0;
	}
}