package org.team5940.pantry.processing_network;

import java.util.HashSet;
import java.util.Set;

import org.team5940.pantry.logging.LabeledObject;
import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;

import com.google.gson.JsonArray;

/**
 * 
 * A NodeGroup is used to allow easy creation for a set of Nodes, such as a
 * Group of Motors. This will also help with being able to log information and
 * putting fancy circles around each NodeGroup.
 * 
 * @author Michael Bentley
 */
public abstract class NodeGroup implements LabeledObject {

	/**
	 * The nodes in this NodeGroup.
	 */
	final Set<Node> nodes;

	/**
	 * The Network that all the Nodes in this NodeGroup belong to.
	 */
	final Network network;
	
	/**
	 * This' logger. 
	 */
	Logger logger;

	/**
	 * Creates a new NodeGroup. A NodeGroup is used to allow easy creation for a
	 * set of Nodes, such as a Group of Motors. This will also help with being
	 * able to log information.
	 * 
	 * @param network
	 *            The Network that all of the Nodes belong to.
	 */
	public NodeGroup(Network network, Logger logger) {
		this.network = network;
		this.nodes = new HashSet<>();
		if (logger == null) 
			throw new IllegalArgumentException("Logger is null");
		this.logger = logger;
	}

	/**
	 * Adds a node to this NodeGroup.
	 * 
	 * Will throw an IllegalStateException if the Network has already been
	 * started. Will also throw and IllegalArgumentException if the node is null
	 * or it does not belong to the correct Network.
	 * 
	 * @param node
	 *            The Node to add to this NodeGroup.
	 */
	protected void addNode(Node node) {
		if (network.getState() != Thread.State.NEW) {
			this.logger.throwError(this, new IllegalStateException("Network not new"));
		}
		if (node == null) {
			this.logger.throwError(this, new IllegalArgumentException("Null node"));
		}
		if (node.getNetwork() != this.network) {
			this.logger.throwError(this, new IllegalArgumentException("Incorrect Network"));
		}
		this.nodes.add(node);
	}

	/**
	 * Gets the Nodes that belong to this NodeGroup.
	 * 
	 * @return The Nodes
	 */
	public Set<Node> getNodes() {
		return this.nodes;
	}

	/**
	 * Gets the ValueNodes that Nodes in this NodeGroup use. Only gets the
	 * ValueNodes that do not belong to this NodeNetwork.
	 * 
	 * @return The external ValueNodes that Nodes in this NodeGroup use.
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
	 * This gets the Nodes that are on the edge of this NodeGroup. This means
	 * any Node that is not used as a source by another Node in this Network.
	 * This will include all Nodes that do not implement ValueNode as they
	 * cannot be used as sources.
	 * 
	 * @return The Nodes not used as sources by Nodes in this Node group.
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
	
	@Override
	public JsonArray getLabel() {
		return LoggingUtils.chainPut(new JsonArray(), "Node Group");
	}

	// TODO add get name inherited from LabledObject in logging.
}
