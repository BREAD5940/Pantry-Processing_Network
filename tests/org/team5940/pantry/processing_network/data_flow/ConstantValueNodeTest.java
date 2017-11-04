package org.team5940.pantry.processing_network.data_flow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.team5940.pantry.processing_network.FullSystemTest;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;

public class ConstantValueNodeTest {
	@Test
	public void testConstantValueNode_IntegerValue() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
		ConstantValueNode<Integer> constantValue = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 5);
		assertEquals(Integer.valueOf(5), constantValue.getValue());
		network.start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(Integer.valueOf(5), constantValue.getValue());
		network.interrupt();
	}

	@Test
	public void testConstantValueNode_StringValue() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
		ConstantValueNode<String> constantValue = new ConstantValueNode<String>(network, FullSystemTest.defaultLogger, "Test");
		assertEquals("Test", constantValue.getValue());
		network.start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("Test", constantValue.getValue());
		network.interrupt();
	}

	@Test
	public void testConstantValueNode_NullValue() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
		ConstantValueNode<Integer> constantValue = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, null);
		assertEquals(null, constantValue.getValue());
		network.start();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(null, constantValue.getValue());
		network.interrupt();
	}
}
