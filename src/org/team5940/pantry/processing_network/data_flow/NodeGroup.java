package org.team5940.pantry.processing_network.data_flow;

import java.util.HashSet;
import java.util.Set;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.ValueNode;

public abstract class NodeGroup {

	final Set<Node> nodes;
	final Network network;
	

	public NodeGroup(Network network) {
		this.network = network;
		this.nodes = new HashSet<>();
	}
	
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

	public Set<Node> getNodes() {
		return this.nodes;
	}
	
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
