package org.team5940.pantry.processing_network;

import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A Node is a component of a single Network. Nodes perform some sort of operation 0 or 1 time(s) every network cycle: the first time update() is successfully called on a given network cycle.
 * @author Michael Bentley
 * 
 */
public abstract class Node {

	/**
	 * Update the node. It can only be called 0 or 1 time(s) per Network cycle.
	 */
	protected abstract void doUpdate();

	/**
	 * Updates this if it has not yet been updated the current network cycle.
	 * @throws IllegalUpdateThreadException The thread calling this method is not 
	 */
	public final void update() throws IllegalUpdateThreadException {

	}
	
	/**
	 * This method is used to tell the network whether the node requires an update (update() will be definitely be called this cycle). It should NOT be assumed that this method will only be called once per cycle and the value should not change in successive calls in the same cycle.
	 * @return True if this node should be updated this cycle, false if it can be not updated.
	 */
	public abstract boolean requiresUpdate();

	/**
	 * Gets the Nodes that this Node retrieves data from. Sources should not
	 * change after initialization.
	 * 
	 * @return The Nodes that this uses.
	 */
	public abstract Collection<Node> enumerateSources();
}
