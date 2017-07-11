package org.team5940.pantry.processing_network.tests;

import static org.junit.Assert.*;

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
	public void testgetCycleDelay() {
		Network network = new Network(3);
		assertEquals(3,network.getCycleDelay());
	}
	
	@Test
	public void testRun_Basic() {
		Network network = new Network(3);
		network.run();
	}

}
