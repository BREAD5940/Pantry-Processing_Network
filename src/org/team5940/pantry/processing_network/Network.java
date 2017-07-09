package org.team5940.pantry.processing_network;

import java.util.Set;
import java.util.HashSet;

/**
 * A Network is a thread that updates a set of {@link Node}s.
 * <br><br>
 * Timing on a network is done in cycles, where all events in a cycle internally appear to happen instantaneously.
 * Once a network is started, cycles occur every cycleDelay, some number of nanoseconds.
 * Due to technical limitations (specifically the amount of time it actually takes a network to complete a cycle and the accuracy of System.nanoTime())
 * 	cycles may occur more slowly than specified but they will never occur more quickly. For timing needs inside of Nodes, {@link Network#getNetworkTime()} is available.
 * <br><br>
 * Nodes are added to a network automatically when they are initialized (as long as the network has not already been started).
 * Nodes must declare on initialization any {@link SourceNode}s they utilize which forces them to form a DAG (Directed Acyclic Graph) where no "looping" dependencies
 * 	are present.
 * This is necessary due to the "instantaneous" nature of network cycles, you cannot have operations that (indirectly) rely on themselves: the cycle would never
 * 	complete.
 * <br><br>
 * Nodes can perform some sort of operation up to once every cycle, when their doUpdate method is internally called.
 * A node is manually updated (if they haven't already been) by the network on a cycle if node.requiresUpdate() returns true.
 * Value nodes are also updated when another node calls node.getValue().
 * This system of updating values within cycles is advantageous because code using the values is guaranteed an up-to-date value (to within this cycle) but the value
 * 	can be buffered (not recalculated, saving computer resources) because it is guaranteed not to change.
 * 
 * @author David Boles
 * 
 */
public class Network extends Thread {

	/**
	 * The lowest number of nanoseconds between the start of cycles for this Network.
	 */
	long cycleDelay;

	/**
	 * The Nodes that this Network updates.
	 */
	Set<Node> nodes;

	/**
	 * The current cycle this Network is on.
	 */
	long currentCycle = -1;

	/**
	 * The value of System.nanoTime() when this is first started.
	 */
	long startTime;
	
	/**
	 * The value of System.nanoTime() when the last (potentially current) cycle started.
	 */
	long lastCycleStart;

	/**
	 * Creates a new Network with the given cycle delay.
	 * 
	 * @param cycleDelay
	 *            The minimum number of nanoseconds between the beginning of cycles.
	 */
	public Network(int cycleDelay) {
		this.cycleDelay = cycleDelay;
		this.nodes = new HashSet<>();
	}

	@Override
	public void run() {
		this.startTime = System.nanoTime();
		this.currentCycle = 0;
		while (!this.isInterrupted()) {
			this.lastCycleStart = System.nanoTime();
//			long cycleStartTime = System.nanoTime();
//			//this.currentCycle++;
//			for (Node node : this.nodes) {
//				if (node.requiresUpdate()) {
//					try {//TODO add documentation about failed updates
//						node.update();
//					} catch (IllegalUpdateThreadException e) {
//						//TODO logging stuff
//					}
//				}
//			}
//			long cycleEndTime = System.nanoTime();
//			long extraTime = this.cycleDelay - (cycleEndTime - cycleStartTime) / 1000;
//			if (extraTime > 0) {
//				try {
//					Thread.sleep(extraTime);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			
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
	protected void addNode(Node node) throws IllegalStateException {
		if (this.isAlive()) {
			throw new IllegalStateException("Network is already running");
		}
		if (node != null) {
			// TODO change this to check that node has this as its Network. 
			nodes.add(node);
		}
	}

	/**
	 * Gets the current cycle that this Network is on.
	 * 
	 * @return The current cycle this Network is on. If Network is not running
	 *         returns -1.
	 */
	public long getCurrentCycle() {
		if (this.isAlive())
			return currentCycle;
		else
			return -1;
	}

	/**
	 * Gets the update rate of this Network in milliseconds. Is not necessarily
	 * the update rate. It could go slower.
	 * 
	 * @return The update rate of the Network in milliseconds.
	 */
	public int getUpdateRate() {
		return this.cycleDelay;
	}

	/**
	 * Gets the length of time this Network has run in milliseconds.
	 * 
	 * @return The length of time this Network has run in milliseconds. If Network is not running returns -1. 
	 */
	public long getNetworkTime() {
		if (this.isAlive())
			return (System.nanoTime() - this.startTime) / 1000;
		else 
			return -1;
	}
}
