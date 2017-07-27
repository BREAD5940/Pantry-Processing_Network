package org.team5940.pantry.processing_network;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;

public class NodeTest {

	Network network = new Network(3);

	@Test
	public void testNode_NoUpdateAlsoNoSources() {
		new NodeTesterObject(network, false, null);
	}

	@Test
	public void testNode_DoesUpdateAlsoNoSources() {
		new NodeTesterObject(network, true, null);
	}

	@Test
	public void testNode_HasSources() {
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, true);
		new NodeTesterObject(network, true, sourceNode);
	}
	
	@Test
	public void testNode_NullSources() {
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, true, null);
		new NodeTesterObject(network, true, sourceNode);
	}
}
