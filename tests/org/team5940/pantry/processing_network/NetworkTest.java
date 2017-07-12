package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.SourceNode;

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
	public void testGetCycleDelay_Success() {
		Network network = new Network(3);
		assertEquals(3,network.getCycleDelay());
	}
	
	@Test
	public void testAddNode_WithoutSources() {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network,null, true);
		assertTrue(network.nodes.contains(node));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAddNode_NullNode_IllegalArgument() {
		Network network = new Network(3);
		network.addNode(null);
	}
	
	@Test(expected=IllegalArgumentException.class) //Test needs to be removed -- Node added to Network Automatically So this can't happen
	public void testAddNode_WrongNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3);
		Network diffNetwork = new Network(3);
		NodeTesterObject node = new NodeTesterObject(nodeNetwork,null, true);
		diffNetwork.addNode(node);
	}
	
	@Test(expected=IllegalArgumentException.class) // Talk to David about source nodes being across networks.
	public void testAddNode_SourceNodeNotInNetwork_IllegalArgument() {
		Network nodeNetwork = new Network(3);
		Network sourceNetwork = new Network(3);
		Set<SourceNode<?>> sources = new HashSet<>();
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(sourceNetwork, true);
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(nodeNetwork,sources, true);
	}
	
	@Test
	public void testAddNode_WithSources() {
		Network network = new Network(3);
		Set<SourceNode<?>> sources = new HashSet<>();
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true);
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
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
		NodeTesterObject node = new NodeTesterObject(network,null, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.didRun);
	}
	
	@Test
	public void testRun_WithMultipleNodes() throws InterruptedException {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network,null, true);
		NodeTesterObject node2 = new NodeTesterObject(network,null, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertTrue(node.didRun);
		assertTrue(node2.didRun);
	}
	
	@Test
	public void testRun_WithSourceNode() throws InterruptedException {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
		assertEquals(3,(int)sourceNode.value);
	}
	
	@Test
	public void testRun_WithNodeAndSourceNode() throws InterruptedException {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true);
		Set<SourceNode<?>> sources = new HashSet<>();
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
		network.start();
		Thread.sleep(100);
		network.interrupt();
	}
	
	
	
	//Michaels's Tests
	
	/*@org.junit.Test 
	  public void testNetworkUpdateRate() { 
	    Network networkZeroUpdate = new Network(0); 
	    testNetwork(networkZeroUpdate, 0); 
	 
	    Network networkNegativeUpdate = new Network(-1); 
	    testNetwork(networkNegativeUpdate, -1); 
	 
	    Network networkBigUpdate = new Network(9999999000l); 
	    testNetwork(networkBigUpdate, 9999999000l); 
	 
	    Network networkStandardUpdate = new Network(20000); 
	    testNetwork(networkStandardUpdate, 20000); 
	 
	    Network networkSmallUpdate = new Network(1); 
	    testNetwork(networkSmallUpdate, 1); 
	  } 
	   
	  private void testNetwork(Network network, long updateRate) {     
	    assertEquals(network.getLastCycle(), -1); 
	    assertEquals(network.getLastCycleStart(), -1); 
	    assertEquals(network.getCycleDelay(), updateRate); 
	    network.run(); 
	    assertEquals(network.getCycleDelay(), updateRate); 
	    assertTrue(network.getLastCycle() > 0); 
	    assertTrue(network.getLastCycleStart() > 0); 
	  } 
	   
	  @org.junit.Test 
	  public void testAddingNodes() { 
	    Network network = new Network(2000); 
	    SourceNodeTestImplementation source = new SourceNodeTestImplementation(network, true); 
	    Node node = new NodeTestImplementation(network, true, source); 
//	    Node node = new NodeTestImplementation(network, null, true, null) 
	    network.run(); 
	  } */

}
