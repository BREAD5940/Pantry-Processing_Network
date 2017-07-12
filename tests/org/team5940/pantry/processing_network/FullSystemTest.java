package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class FullSystemTest {

	@Test
	public void testRun_WithNodeAndSourceNode() throws InterruptedException {
		Network network = new Network(3);
		SourceNodeTesterObject sourceNode1 = new SourceNodeTesterObject(network, true, null);
		NodeTesterObject node = new NodeTesterObject(network,true, sourceNode1);
		network.start();
		Thread.sleep(100);
		network.interrupt();
	}

}
