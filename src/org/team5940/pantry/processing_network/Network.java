package org.team5940.pantry.processing_network;

import java.util.Set;
import java.util.HashSet;

public class Network extends Thread {

	/**
	 * The update rate of this Network in milliseconds.
	 */
	int updateRate;

	/**
	 * The Nodes that this Network updates.
	 */
	Set<Node> nodes;

	/**
	 * The current cycle this Network is on.
	 */
	int currentCycle;

	/**
	 * The time this Network was started.
	 */
	long startTime;

	/**
	 * Creates a new Network with a set update rate.
	 * 
	 * @param updateRate
	 *            The rate to try to update this Network at. Could be slower but
	 *            not faster.
	 */
	public Network(int updateRate) {
		this.updateRate = updateRate;
		this.nodes = new HashSet<>();
	}

	@Override
	public void run() {
		this.startTime = System.nanoTime();
		this.currentCycle = 0;
		while (!this.isInterrupted()) {
			long cycleStartTime = System.nanoTime();
			this.currentCycle++;
			for (Node node : this.nodes) {
				if (node.requiresUpdate()) {
					try {
						node.update();
					} catch (IllegalUpdateThreadException e) {
						e.printStackTrace();
					}
				}
			}
			long cycleEndTime = System.nanoTime();
			long extraTime = this.updateRate - (cycleEndTime - cycleStartTime) / 1000;
			if (extraTime > 0) {
				try {
					Thread.sleep(extraTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Adds a Node for this Network to update every cycle.
	 * 
	 * @param node
	 *            The Node to add to this Network.
	 * @throws IllegalStateException
	 *             If the Network is already running.
	 */
	public void addNode(Node node) throws IllegalStateException {
		if (this.isAlive()) {
			throw new IllegalStateException("Network is already running");
		}
		if (node != null) {
			nodes.add(node);
		}
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
		for (Node node : nodes) {
			this.addNode(node);
		}
	}

	/**
	 * Gets the current cycle that this Network is on.
	 * 
	 * @return The current cycle this Network is on.
	 */
	public long getCurrentCycle() {
		return currentCycle;
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
		return (System.nanoTime() - this.startTime) / 1000;
	}
}
