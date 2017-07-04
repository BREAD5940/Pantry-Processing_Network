package org.team5940.pantry.processing_network;

import java.util.Collection;

/**
 * A Node is a component of a Network. Nodes perform some sort of operation 0-1 times every network cycle: the first time update() is called on a given network cycle.
 * @author Michael BentleyA node is an element in {@link Network} which is updated every
 *         cycle.
 */
public abstract class Node {

	/**
	 * Update the node. Run 0 - 1 times during a Network cycle.
	 */
	protected abstract void doUpdate();

	/**
	 * Checks if this Node has been updated during the Network cycle yet and if
	 * not it runs doUpdate().
	 */
	public final void update() {

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
