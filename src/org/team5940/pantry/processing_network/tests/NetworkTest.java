package org.team5940.pantry.processing_network.tests;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.SourceNode;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;



public class NetworkTest {
	
	@org.junit.Test
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
//		Node node = new NodeTestImplementation(network, null, true, null)
		network.run();
	}
}
