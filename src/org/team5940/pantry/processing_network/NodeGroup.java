package org.team5940.pantry.processing_network;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Michael Bentley
 *
 */
public abstract class NodeGroup {
	
	/**
	 * The nodes in this NodeGroup. 
	 */
	final Set<Node> nodes;
	
	/**
	 * The Network that all the Nodes in this NodeGroup belong to. 
	 */
	final Network network;
	
	/**
	 * Creates a new NodeGroup. A NodeGroup is used to 
	 * @param network
	 */
	public NodeGroup(Network network) {
		this.network = network;
		this.nodes = new HashSet<>();
	}
	
	/**
	 * 
	 * @param node
	 */
	protected void addNode(Node node) {
		if (network.getState() != Thread.State.NEW) {
			throw new IllegalStateException("Network not new");
		}
		if (node == null) {
			throw new IllegalArgumentException("Null node");
		}
		if (node.getNetwork() != this.network) {
			throw new IllegalArgumentException("Incorrect Network");
		}
		this.nodes.add(node);
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Node> getNodes() {
		return this.nodes;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<ValueNode<?>> enumerateSources() {
		Set<ValueNode<?>> sources = new HashSet<>();
		for (Node node : this.nodes) {
			for (ValueNode<?> sourceNode : node.enumerateSources()) {
				if (!this.nodes.contains(sourceNode)) {
					sources.add(sourceNode);
				}
			}
		}
		return sources;
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<Node> enumerateOutputs() {
		Set<Node> outputs = new HashSet<>();

		Set<ValueNode<?>> sources = new HashSet<>();

		for (Node node : this.nodes) {
			sources.addAll(node.enumerateSources());
		}

		for (Node node : this.nodes) {
			if (!sources.contains(node)) {
				outputs.add(node);
			}
		}

		return outputs;
	}

	// TODO add get name inherited from LabledObject in logging.
}
