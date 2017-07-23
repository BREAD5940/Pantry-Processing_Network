package org.team5940.pantry.processing_network.data_flow;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;

public class ConstantValueNodeTest {
	@Test
	public void testConstantValueNode_IntegerValue() {
		Network network = new Network(2000);
		ConstantValueNode<Integer> constantValue = new ConstantValueNode<Integer>(network, 5);
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
		Network network = new Network(2000);
		ConstantValueNode<String> constantValue = new ConstantValueNode<String>(network, "Test");
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
		Network network = new Network(2000);
		ConstantValueNode<Integer> constantValue = new ConstantValueNode<Integer>(network, null);
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
