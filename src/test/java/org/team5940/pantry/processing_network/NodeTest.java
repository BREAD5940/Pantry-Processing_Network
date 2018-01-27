package org.team5940.pantry.processing_network;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;

public class NodeTest {

	Network network = new Network(3, FullSystemTest.defaultLogger);

	@Test
	public void testNode_NoUpdateAlsoNoSources() {
		new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, false, null);
	}

	@Test
	public void testNode_DoesUpdateAlsoNoSources() {
		new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true, null);
	}

	@Test
	public void testNode_HasSources() {
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true);
		new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true, sourceNode);
	}
	
	@Test
	public void testNode_NullSources() {
		ValueNodeTesterObject sourceNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true, null);
		new NodeTesterObject(network, FullSystemTest.defaultLogger, FullSystemTest.defaultLabel, true, sourceNode);
	}
}
