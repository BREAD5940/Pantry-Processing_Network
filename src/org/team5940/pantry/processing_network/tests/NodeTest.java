package org.team5940.pantry.processing_network.tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.SourceNode;

public class NodeTest {
	
	@Test
	public void testNode_NoUpdateNoSources() {
		Network network = new Network(3);
		NodeTesterObject node = new NodeTesterObject(network,null, false);
	}

}
