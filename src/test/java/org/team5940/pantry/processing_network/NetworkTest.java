package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;

public class NetworkTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNetwork_CycleLessThen0_IllegalArgument() {
		new Network(-3, FullSystemTest.defaultLogger);
	}

	@Test
	public void testNetwork_CycleGreaterThan0_Success() {
		new Network(3, FullSystemTest.defaultLogger);
	}

	@Test
	public void testGetCycleDelay_Success() {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		assertEquals(3, network.getCycleDelay());
	}

	@Test
	public void testAddNode_WithoutSources() {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		NodeTesterObject node = new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel,
				true);
		assertTrue(network.nodes.contains(node));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNode_NullNode_IllegalArgument() {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		network.addNode(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNode_SourceNodeNotInSameNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3, FullSystemTest.defaultLogger);
		Network sourceNetwork = new Network(3, FullSystemTest.defaultLogger);
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(sourceNetwork, FullSystemTest.defaultLogger,
				FullSystemTest.defaultLabel, true);
		new NodeTesterObject(nodeNetwork, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true, sourceNode);
	}

	@Test
	public void testAddNode_WithSources() {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger,
				FullSystemTest.defaultLabel, true);
		NodeTesterObject node = new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel,
				true, sourceNode);
		assertTrue(network.nodes.contains(node));
		assertTrue(network.nodes.contains(sourceNode));
	}

	@Test
	public void testRun_StandardNoNodes() {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		network.start();
		network.interrupt();
	}

	@Test
	public void testRun_WithNode() throws InterruptedException {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		NodeTesterObject node = new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel,
				true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.getDidRun());
	}

	@Test
	public void testRun_WithMultipleNodes() throws InterruptedException {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		NodeTesterObject node = new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel,
				true);
		NodeTesterObject node2 = new NodeTesterObject(network, FullSystemTest.defaultLogger,
				FullSystemTest.defaultLabel, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.getDidRun());
		assertTrue(node2.getDidRun());
	}

	@Test
	public void testRun_WithSourceNode() throws InterruptedException {
		Network network = new Network(3, FullSystemTest.defaultLogger);
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger,
				FullSystemTest.defaultLabel, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertEquals(3, sourceNode.getValue().intValue());
	}

	@Test
	public void testRun_UpdateRate() {
		Network network = new Network(20000, FullSystemTest.defaultLogger);

		NodeTesterObject nodeTester = new NodeTesterObject(network, FullSystemTest.defaultLogger,
				FullSystemTest.defaultLabel, true);

		assertEquals(nodeTester.getUpdateCount(), 0);

		network.start();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(nodeTester.getUpdateCount(), 1);

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertEquals(nodeTester.getUpdateCount(), 2);
	}
}
