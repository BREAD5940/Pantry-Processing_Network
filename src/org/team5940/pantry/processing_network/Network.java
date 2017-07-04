package org.team5940.pantry.processing_network;

import java.util.Set;
import java.util.HashSet;

public class Network extends Thread {

	/**
	 * The update rate of this Netwok in milliseconds.
	 */
	int updateRate;

	/**
	 * The Nodes that this Network updates.
	 */
	Set<Node> nodes;

	/**
	 * If the
	 */
	boolean isRunning;

	public Network(int updateRate) {
		this.updateRate = updateRate;
		this.nodes = new HashSet<>();
	}

	@Override
	public void run() {
		while (true) { // TODO this is cancer should change.

		}
	}

	/**
	 * Adds a Node for this Network to update every cycle.
	 * 
	 * @param node
	 *            The Node to add to this Network.
	 * @throws IllegalStateException
	 *             If the Network is already running.
	 * @throws IllegalArgumentException
	 *             If the Node is null.
	 */
	public void addNode(Node node) throws IllegalStateException, IllegalArgumentException {
		if (node == null) {
			throw new IllegalArgumentException("Node is null");
		}
		if (this.isRunning) {
			throw new IllegalStateException("Network is already running");
		}
		nodes.add(node);
	}

	/**
	 * Adds a Set of Nodes to the Network.
	 * 
	 * @param nodes
	 *            The Set to add.
	 * @throws IllegalStateException
	 *             If the Thread is already running.
	 */
	public void addNodes(Set<Node> nodes) throws IllegalStateException {

	}

	/**
	 * Gets the current cycle that this Network is on.
	 * 
	 * @return The current cycle this Network is on.
	 */
	public long getCurrentCycle() {

	}

	/**
	 * Gets the update rate of this Network in milliseconds. Is not necessarily
	 * the update rate. It could go slower.
	 * 
	 * @return The update rate of the Network in milliseconds.
	 */
	public int getUpdateRate() {
		return this.updateRate;
	}

	/**
	 * Gets the length of time this Network has run in milliseconds.
	 * 
	 * @return The length of time this Network has run in milliseconds.
	 */
	public long getNetworkTime() {

	}
}
