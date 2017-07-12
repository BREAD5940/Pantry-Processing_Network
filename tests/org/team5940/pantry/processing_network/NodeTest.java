package org.team5940.pantry.processing_network;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.SourceNode;

public class NodeTest {
	
	Network network = new Network(3);
	
	@Test
	public void testNode_NoUpdateAlsoNoSources() {
		NodeTesterObject node = new NodeTesterObject(network, false, null);
	}
	
	@Test
	public void testNode_DoesUpdateAlsoNoSources() {
		NodeTesterObject node = new NodeTesterObject(network, true, null);
	}
	
	@Test
	public void testNode_HasSources() {
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true, null);
		NodeTesterObject node = new NodeTesterObject(network,true, sourceNode);
	}

}

