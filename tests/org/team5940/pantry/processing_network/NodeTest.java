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
		NodeTesterObject node = new NodeTesterObject(network,null, false);
	}
	
	@Test
	public void testNode_DoesUpdateAlsoNoSources() {
		NodeTesterObject node = new NodeTesterObject(network,null, true);
	}
	
	@Test
	public void testNode_DoesUpdateAlsoEmptySources() {
		Set<SourceNode<?>> sources = new HashSet<>();
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
	}
	
	@Test
	public void testNode_HasSources() {
		Set<SourceNode<?>> sources = new HashSet<>();
		SourceNodeTesterObject sourceNode = new SourceNodeTesterObject(network, true);
		sources.add(sourceNode);
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNode_HasNullSources() {
		Set<SourceNode<?>> sources = new HashSet<>();
		sources.add(null);
		NodeTesterObject node = new NodeTesterObject(network,sources, true);
	}

}

