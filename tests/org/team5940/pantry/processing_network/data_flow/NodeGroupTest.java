package org.team5940.pantry.processing_network.data_flow;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.team5940.pantry.processing_network.FullSystemTest;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.NodeTesterObject;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.ValueNodeTesterObject;

public class NodeGroupTest {

	@Test
	public void testNodeGroup_Standard() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);

		ValueNode<?> valueNode1 = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true);
		ValueNode<?> valueNode2 = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true);

		ValueNode<?> valueNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true, valueNode1, valueNode2);
		Node node = new NodeTesterObject(network, FullSystemTest.defaultLogger, true, valueNode);

		ValueNode<?> randomNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true);

		NodeGroupTesterObject testGroup = new NodeGroupTesterObject(network, FullSystemTest.defaultLogger, valueNode, randomNode, node);

		Set<Node> outputNodeTest = new HashSet<>();
		outputNodeTest.add(node);
		outputNodeTest.add(randomNode);

		assertEquals(outputNodeTest, testGroup.enumerateOutputs());

		Set<ValueNode<?>> setSources = testGroup.enumerateSources();

		assertEquals(true, setSources.contains(valueNode2));
		assertEquals(true, setSources.contains(valueNode1));

		network.start();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNodeGroup_WrongNetwork_IllegalArgumentException() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
		
		Network wrongNetwork = new Network(2000, FullSystemTest.defaultLogger);
		
		ValueNode<?> valueNode = new ValueNodeTesterObject(wrongNetwork, FullSystemTest.defaultLogger, true);
		
		new NodeGroupTesterObject(network, FullSystemTest.defaultLogger, valueNode);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNodeGroup_NullNode_IllegalArgumentException() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
				
		ValueNode<?> valueNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true);
		
		new NodeGroupTesterObject(network, FullSystemTest.defaultLogger, valueNode, null);
	}
	
	@Test (expected = IllegalStateException.class)
	public void testNodeGroup_NetworkStarted_IllegalStateException() {
		Network network = new Network(2000, FullSystemTest.defaultLogger);
		
		ValueNode<?> valueNode = new ValueNodeTesterObject(network, FullSystemTest.defaultLogger, true);
		
		network.start();
		
		new NodeGroupTesterObject(network, FullSystemTest.defaultLogger, valueNode, null);
	}
}
