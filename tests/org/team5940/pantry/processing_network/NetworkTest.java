package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.ValueNode;

public class NetworkTest {

	@Test(expected=IllegalArgumentException.class)
	public void testNetwork_CycleLessThen0_IllegalArgument() {
		new Network(-3);
	}
	
	@Test
	public void testNetwork_CycleGreaterThan0_Success() {
		new Network(3);
	}
	
	@Test
	public void testGetCycleDelay_Success() {
		Network network = new Network(3);
		assertEquals(3,network.getCycleDelay());
	}
	
	@Test
	public void testAddNode_WithoutSources() {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network, true, null);
		assertTrue(network.nodes.contains(node));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_NullNode_IllegalArgument() {
		Network network = new Network(3);
		network.addNode(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_SourceNodeNotInSameNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3);
		Network sourceNetwork = new Network(3);
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(sourceNetwork, true, null);
		new NodeTesterObject(nodeNetwork, true, sourceNode);
	}
	
	@Test
	public void testAddNode_WithSources() {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true, null);
		NodeTesterObject node = new NodeTesterObject(network, true, sourceNode);
		assertTrue(network.nodes.contains(node));
		assertTrue(network.nodes.contains(sourceNode));
	}
	
	@Test
	public void testRun_StandardNoNodes() {
		Network network = new Network(3);
		network.start();
		network.interrupt();
	}
	
	@Test
	public void testRun_WithNode() throws InterruptedException {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network, true, null);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.getDidRun());
	}
	
	@Test
	public void testRun_WithMultipleNodes() throws InterruptedException {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network, true, null);
		NodeTesterObject node2 = new NodeTesterObject(network, true, null);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.getDidRun());
		assertTrue(node2.getDidRun());
	}
	
	@Test
	public void testRun_WithSourceNode() throws InterruptedException {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true, null);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertEquals(3, sourceNode.getValue().intValue());
	}
}
