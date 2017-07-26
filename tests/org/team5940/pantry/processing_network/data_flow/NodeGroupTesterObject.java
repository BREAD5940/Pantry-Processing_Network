package org.team5940.pantry.processing_network.data_flow;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.NodeGroup;

public class NodeGroupTesterObject extends NodeGroup {
		
	public NodeGroupTesterObject(Network network, Node... nodes) {
		super(network);
		
		for (Node node : nodes) {
			this.addNode(node);
		}
	}
}
