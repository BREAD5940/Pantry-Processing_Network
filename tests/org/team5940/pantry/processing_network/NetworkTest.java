package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;

public class NetworkTest {

	@Test(expected=IllegalArgumentException.class)
	public void testNetwork_CycleLessThen0_IllegalArgument() {
		Network network = new Network(-3);
	}
	
	@Test
	public void testNetwork_CycleGreaterThan0_Success() {
		Network network = new Network(3);
	}
	
	@Test
	public void testGetCycleDelay() {
		Network network = new Network(3);
		assertEquals(3,network.getCycleDelay());
	}
	
	@Test
	public void testAddNode_WithoutSources() {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network,null, true);
		network.addNode(node);
		assertTrue(network.nodes.contains(node));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_NullNode_IllegalArgument() {
		Network network = new Network(3);
		network.addNode(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_WrongNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3);
		Network diffNetwork = new Network(3);
		NodeTesterObject node = new NodeTesterObject(nodeNetwork,null, true);
		diffNetwork.addNode(node);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_SourceNodeNotInNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3);
		Network sourceNetwork = new Network(3);
		Set<SourceNode<?>> sources = new HashSet<>();
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(sourceNetwork, true);
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(nodeNetwork,sources, true);
		nodeNetwork.addNode(node);
	}
	
	@Test
	public void testAddNode_WithSources() {
		Network network = new Network(3);
		Set<SourceNode<?>> sources = new HashSet<>();
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true);
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
		network.addNode(node);
		assertTrue(network.nodes.contains(node));
	}

}
