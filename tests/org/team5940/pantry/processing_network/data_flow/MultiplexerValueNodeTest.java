package org.team5940.pantry.processing_network.data_flow;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import org.team5940.pantry.processing_network.FullSystemTest;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.NodeTesterObject;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.functional.ConstantValueNode;
import org.team5940.pantry.processing_network.functional.MultiplexerValueNode;

public class MultiplexerValueNodeTest {
	
	public enum TestEnum {
		TEST1, TEST2, TEST3
	}

	@Test
	public void testMultiplexerValueNode_General() throws InterruptedException {
		Network network = new Network(20000, FullSystemTest.defaultLogger);
		StateValueNodeTesterObject stateValueNode = new StateValueNodeTesterObject(network, FullSystemTest.defaultLogger);
		HashMap<Enum<? extends TestEnum>, ValueNode<? extends Integer>> map = new HashMap<Enum<? extends TestEnum>, ValueNode<? extends Integer>>();

		ConstantValueNode<Integer> testNullConst = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 0);
		ConstantValueNode<Integer> test1Const = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 1);

		map.put(null, testNullConst);
		map.put(TestEnum.TEST1, test1Const);
		map.put(TestEnum.TEST2, null);

		MultiplexerValueNode<Integer, TestEnum> multi = new MultiplexerValueNode<Integer, TestEnum>(network, FullSystemTest.defaultLogger, 
				stateValueNode, map);

		new NodeTesterObject(network, FullSystemTest.defaultLogger, true, multi);

		network.start();

		Thread.sleep(10);

		assertEquals(Integer.valueOf(1), multi.getValue());

		Thread.sleep(20);

		assertEquals(null, multi.getValue());

		Thread.sleep(20);

		assertEquals(0, (int) multi.getValue());

		Thread.sleep(20);

		assertEquals(null, multi.getValue());

		network.interrupt();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMultiplexerValueNode_NullEnumValueNode_IllegalArgumentException() throws InterruptedException {
		Network network = new Network(20000, FullSystemTest.defaultLogger);
		HashMap<Enum<? extends TestEnum>, ValueNode<? extends Integer>> map = new HashMap<Enum<? extends TestEnum>, ValueNode<? extends Integer>>();

		ConstantValueNode<Integer> testNullConst = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 0);
		ConstantValueNode<Integer> test1Const = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 1);
		ConstantValueNode<Integer> test2Const = new ConstantValueNode<Integer>(network, FullSystemTest.defaultLogger, 2);

		map.put(null, testNullConst);
		map.put(TestEnum.TEST1, test1Const);
		map.put(TestEnum.TEST2, test2Const);

		new MultiplexerValueNode<Integer, TestEnum>(network, FullSystemTest.defaultLogger, null, map);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMultiplexerValueNode_NullMap_IllegalArgumentException() throws InterruptedException {
		Network network = new Network(20000, FullSystemTest.defaultLogger);

		StateValueNodeTesterObject stateValueNode = new StateValueNodeTesterObject(network, FullSystemTest.defaultLogger);

		new MultiplexerValueNode<Integer, TestEnum>(network, FullSystemTest.defaultLogger, stateValueNode, null);
	}
}
